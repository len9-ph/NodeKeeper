package com.lgadetsky.nodekeeper.client.presenter;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.NodeKeeper;
import com.lgadetsky.nodekeeper.client.event.AddChildEvent;
import com.lgadetsky.nodekeeper.client.event.AddChildEventHandler;
import com.lgadetsky.nodekeeper.client.event.AddRootEvent;
import com.lgadetsky.nodekeeper.client.event.AddRootEventHandler;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEvent;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEventHandler;
import com.lgadetsky.nodekeeper.client.event.DeleteEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEventHandler;
import com.lgadetsky.nodekeeper.client.event.MessageEvent;
import com.lgadetsky.nodekeeper.client.event.MessageEventHandler;
import com.lgadetsky.nodekeeper.client.event.RefreshEvent;
import com.lgadetsky.nodekeeper.client.event.RefreshEventHandler;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateTreeEvent;
import com.lgadetsky.nodekeeper.client.view.CustomTreePanelView;
import com.lgadetsky.nodekeeper.client.view.NodeKeeperDisplay;
import com.lgadetsky.nodekeeper.client.view.NodeTablePanelView;
import com.lgadetsky.nodekeeper.client.view.TreeEditPanelView;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeKeeperPresenter extends Presenter {
    private HandlerManager eventBus = new HandlerManager(null);
    private NodeKeeperDisplay display;
    private List<Node> nodes = new LinkedList<Node>();
    private List<Node> changeNodes = new LinkedList<Node>();
    
    public NodeKeeperPresenter(NodeKeeperDisplay display) {
        this.display = display;
        
        setUpLocalEventBus();
        
        Presenter treePresenter = new TreeEditPanelPresenter(eventBus, 
                new TreeEditPanelView(), new CustomTreePanelView());
        
        treePresenter.go(display.getContainer());

        Presenter tablePresenter = new NodeTablePanelPresenter(eventBus,
                new NodeTablePanelView());

        tablePresenter.go(display.getContainer());

        NodeKeeper.getRpc().getAllNodes(new AsyncCallback<List<Node>>() {
            @Override
            public void onSuccess(List<Node> result) {
                nodes.addAll(result);
                eventBus.fireEvent(new UpdateStateEvent(nodes));
            }
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Internal error");
            }
        });
        
    }
    
    private void setUpLocalEventBus() {
        eventBus.addHandler(RefreshEvent.TYPE,
                new RefreshEventHandler() {
                    @Override
                    public void onRefresh(RefreshEvent event) {
                        
                        if (changeNodes.isEmpty())
                            display.showPopUpMessage("Everything is up to date");
                        else {
                            NodeKeeper.getRpc().saveChanges(changeNodes, new AsyncCallback<List<Node>>() {
                                @Override
                                public void onSuccess(List<Node> result) {
                                    nodes.clear();
                                    nodes.addAll(result);
                                    changeNodes.clear();
                                    eventBus.fireEvent(new UpdateStateEvent(nodes));
                                }
                                @Override
                                public void onFailure(Throwable caught) {
                                    Window.alert("Internal error");
                                }
                            });
                        }
                    }
                });
        
        eventBus.addHandler(AddRootEvent.TYPE,
                new AddRootEventHandler() {
                    @Override
                    public void onAddRoot(AddRootEvent event) {
                        Node newNode = new Node();
                        changeNodes.add(newNode);
                        eventBus.fireEvent(new UpdateTreeEvent(newNode));
                    }
                });
        
        eventBus.addHandler(AddChildEvent.TYPE, 
                new AddChildEventHandler() {
                    @Override
                    public void onAddChild(AddChildEvent event) {
                        Node newNode = new Node(event.getParentNode().getId());
                        changeNodes.add(newNode);
                        eventBus.fireEvent(new UpdateTreeEvent(newNode));
                    }
                });
        
        eventBus.addHandler(BoxChangeEvent.TYPE,
                new BoxChangeEventHandler() {
                    @Override
                    public void onBoxChange(BoxChangeEvent event) {
                        if (changeNodes.contains(event.getNode()))
                            changeNodes.remove(event.getNode());
                        Node changedNode = event.getNode();
                        
                        switch (event.getField()) {
                            case "name":
                                changedNode.setName(event.getValue());
                                break;
                            case "ip":
                                changedNode.setIp(event.getValue());
                                break;
                            case "port":
                                changedNode.setPort(event.getValue());
                                break;
                            default:
                                break;
                        }
                        changeNodes.add(changedNode);
                    }
                });
        
        eventBus.addHandler(DeleteEvent.TYPE, 
                new DeleteEventHandler() {
                    @Override
                    public void onDelete(DeleteEvent event) {
                        Node deletedNode = event.getNode();
                       
                        if (deletedNode.getId().equals(-1)) 
                            changeNodes.remove(deletedNode);
                        else {
                            deletedNode.setDeleted(true);
                            for (Node n : nodes) {
                                if (n.getParentId().equals(deletedNode.getId())) {
                                    n.setDeleted(true);
                                    changeNodes.add(n);
                                }
                            }
                            changeNodes.add(deletedNode);
                        }
                
                    }
                });
        
        eventBus.addHandler(MessageEvent.TYPE, 
                new MessageEventHandler() {
                    @Override
                    public void onMessageSend(MessageEvent event) {
                        display.showPopUpMessage(event.getMessage());
                    }
                });
    }
    
    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }

}
