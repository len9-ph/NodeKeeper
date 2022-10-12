package com.lgadetsky.nodekeeper.client.presenter;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.lgadetsky.nodekeeper.client.NodeKeeperServiceAsync;
import com.lgadetsky.nodekeeper.client.event.ChangedNodeEvent;
import com.lgadetsky.nodekeeper.client.event.ChangedNodeEventHandler;
import com.lgadetsky.nodekeeper.client.event.RefreshEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEventHandler;
import com.lgadetsky.nodekeeper.client.presenter.AllNodesDisplay.AllNodesDisplayActionHandler;
import com.lgadetsky.nodekeeper.shared.Node;

public class AllNodesPresenter {
    private LinkedList<Node> nodes = new LinkedList<>();
    private LinkedList<Node> changedNodes = new LinkedList<>();
    
    //private final NodeKeeperServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final AllNodesDisplay allNodesDisplay;
    
    public AllNodesPresenter(NodeKeeperServiceAsync rpcSerice, HandlerManager eventBus, AllNodesDisplay allNodesDisplay) {
        //this.rpcService = rpcSerice;
        this.eventBus = eventBus;
        this.allNodesDisplay = allNodesDisplay;
        
        bind();
        setUpLocalEventBus();
    }
    
    void setUpLocalEventBus() {
        eventBus.addHandler(UpdateStateEvent.TYPE, 
                new UpdateStateEventHandler() {
                    @Override
                    public void onUpdate(UpdateStateEvent event) {
                        updateNodes(event.getNodes());
                    }
                });
        eventBus.addHandler(ChangedNodeEvent.TYPE, 
                new ChangedNodeEventHandler() {
                    @Override
                    public void onChange(ChangedNodeEvent event) {
                       addChangedNode(event.getOldNode(), event.getChangedNode());
                    }
                });
        
    }
    
    
    void bind() {
        allNodesDisplay.setDisplayActionHandler(new AllNodesDisplayActionHandler() {
            @Override
            public void onRefreshButtonClick(ClickEvent event) {
                eventBus.fireEvent(new RefreshEvent());
            }
        });
    }
    
    private void updateNodes(List<Node> nodes) {
        this.changedNodes.clear();
        this.nodes.clear();
        this.nodes.addAll(nodes);
    }
    
    private void addChangedNode(Node oldNode, Node newNode) {
        if(changedNodes.contains(oldNode))
            changedNodes.remove(oldNode);
        changedNodes.add(newNode);
    }
}
