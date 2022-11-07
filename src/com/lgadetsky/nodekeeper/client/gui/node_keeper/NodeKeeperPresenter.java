package com.lgadetsky.nodekeeper.client.gui.node_keeper;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.NodeKeeper;
import com.lgadetsky.nodekeeper.client.event.AddEvent;
import com.lgadetsky.nodekeeper.client.event.AddEvent.ItemType;
import com.lgadetsky.nodekeeper.client.event.AddEventHandler;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEvent;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEventHandler;
import com.lgadetsky.nodekeeper.client.event.DeleteEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEventHandler;
import com.lgadetsky.nodekeeper.client.event.MessageEvent;
import com.lgadetsky.nodekeeper.client.event.MessageEventHandler;
import com.lgadetsky.nodekeeper.client.event.RefreshEvent;
import com.lgadetsky.nodekeeper.client.event.RefreshEventHandler;
import com.lgadetsky.nodekeeper.client.event.SelectEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEventHandler;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateTreeEvent;
import com.lgadetsky.nodekeeper.client.gui.Presenter;
import com.lgadetsky.nodekeeper.client.gui.manager_panel.ManagerPanelPresenter;
import com.lgadetsky.nodekeeper.client.gui.manager_panel.ManagerPanelView;
import com.lgadetsky.nodekeeper.client.gui.node_keeper.NodeKeeperDisplay.NodeKeeperActionHandler;
import com.lgadetsky.nodekeeper.client.gui.nodes_table.NodeTablePanelPresenter;
import com.lgadetsky.nodekeeper.client.gui.nodes_table.NodeTablePanelView;
import com.lgadetsky.nodekeeper.client.gui.selected_panel.SelectedPanelPresenter;
import com.lgadetsky.nodekeeper.client.gui.selected_panel.SelectedPanelView;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification.NotificationType;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeKeeperPresenter extends Presenter {
    private HandlerManager eventBus = new HandlerManager(null);
    private NodeKeeperDisplay display;
    private List<Node> nodes = new LinkedList<Node>();
    private List<Node> changeNodes = new LinkedList<Node>();
    private Node selectedNode;

    public NodeKeeperPresenter(NodeKeeperDisplay display) {
        this.display = display;

        bind();
        setUpLocalEventBus();

        initPresenters();

        downloadData();

    }

    public void bind() {
        display.setNodeKeeperActionHandler(new NodeKeeperActionHandler() {

            @Override
            public void onChoice() {
                fixChangeNodes();
                refreshData();
            }
        });
    }

    public void setUpLocalEventBus() {
        eventBus.addHandler(RefreshEvent.TYPE, new RefreshEventHandler() {
            @Override
            public void onRefresh(RefreshEvent event) {
                if (changeNodes.isEmpty()) {
                    display.showPopUpMessage(StringConstants.UP_TO_DATE, NotificationType.DEFAULT);
                } else if (validate()) {
                    refreshData();
                } else {
                    display.showChoiceDialogBox(StringConstants.CHOICE_MESSAGE);
                }
            }
        });

        eventBus.addHandler(SelectEvent.TYPE, new SelectEventHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                selectedNode = event.getSelectedNode();
            }
        });

        eventBus.addHandler(AddEvent.TYPE, new AddEventHandler() {

            @Override
            public void onAdd(AddEvent event) {
                if (event.getItemType() == ItemType.ROOT) {
                    Node newNode = new Node();
                    changeNodes.add(newNode);
                    eventBus.fireEvent(new UpdateTreeEvent(newNode));
                } else {
                    if (selectedNode != null) {
                        if (selectedNode.getId() != -1) {
                            Node newNode = new Node(selectedNode.getId());
                            changeNodes.add(newNode);
                            eventBus.fireEvent(new UpdateTreeEvent(newNode));
                        } else {
                            display.showPopUpMessage(StringConstants.PARENT_ITEM_NOT_VALID, NotificationType.DEFAULT);
                        }
                    } else {
                        display.showPopUpMessage(StringConstants.PARENT_ITEM_WAS_NOT_SELECTED, NotificationType.DEFAULT);
                    }
                }
            }
        });

        eventBus.addHandler(BoxChangeEvent.TYPE, new BoxChangeEventHandler() {

            @Override
            public void onBoxChange(BoxChangeEvent event) {
                if (changeNodes.contains(selectedNode)) {
                    changeNodes.remove(selectedNode);
                }

                switch (event.getField()) {
                    case StringConstants.NAME:
                        selectedNode.setName(event.getValue());
                        break;
                    case StringConstants.IP:
                        selectedNode.setIp(event.getValue());
                        break;
                    case StringConstants.PORT:
                        selectedNode.setPort(event.getValue());
                        break;
                }
                changeNodes.add(selectedNode);
            }
        });

        eventBus.addHandler(DeleteEvent.TYPE, new DeleteEventHandler() {

            @Override
            public void onDelete(DeleteEvent event) {
                delete(selectedNode);
                selectedNode = null;
            }
        });

        eventBus.addHandler(MessageEvent.TYPE, new MessageEventHandler() {

            @Override
            public void onMessageSend(MessageEvent event) {
                display.showPopUpMessage(event.getMessage(), NotificationType.DEFAULT);
            }
        });
    }

    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }

    private void delete(Node node) {
        if (node.getId().equals(-1)) {
            changeNodes.remove(node);
        } else {
            deleteUtil(node.getId());
            node.setDeleted(true);
            changeNodes.add(node);
        }
    }

    private void deleteUtil(Integer parentId) {
        for (Node n : changeNodes) {
            if (n.getParentId() != null && n.getParentId().equals(parentId) && !n.isDeleted()) {
                changeNodes.remove(n);
            }
        }

        for (Node n : nodes) {
            if (n.getParentId() != null && n.getParentId().equals(parentId) && !n.isDeleted()) {
                n.setDeleted(true);
                changeNodes.add(n);
                deleteUtil(n.getId());
            }
        }
    }

    private Node selectUtil() {
        for (Node n : nodes)
            if (n.getId().equals(selectedNode.getId())) {
                return n;
            } else if ((n.getName() != null && n.getName().equals(selectedNode.getName())) ) {
                return n;
            }
        return selectedNode;
    }

    private void downloadData() {
        NodeKeeper.getRpc().getAllNodes(new AsyncCallback<List<Node>>() {

            @Override
            public void onSuccess(List<Node> result) {
                nodes.addAll(result);
                Collections.sort(nodes, new Node());
                eventBus.fireEvent(new UpdateStateEvent(nodes));
            }

            @Override
            public void onFailure(Throwable caught) {
                NodeKeeperPresenter.this.display.showPopUpMessage(StringConstants.ERROR, NotificationType.ERROR);
            }
        });
    }

    private void refreshData() {
        NodeKeeper.getRpc().saveChanges(changeNodes, new AsyncCallback<List<Node>>() {
            @Override
            public void onFailure(Throwable caught) {
                display.showPopUpMessage(StringConstants.ERROR, NotificationType.ERROR);
            }

            @Override
            public void onSuccess(List<Node> result) {
                refreshUtil(result);
            }
        });
    }

    private void refreshUtil(List<Node> result) {
        nodes.clear();
        nodes.addAll(result);
        changeNodes.clear();
        Collections.sort(nodes, new Node());
        eventBus.fireEvent(new UpdateStateEvent(nodes));

        if (selectedNode != null) {
            selectedNode = selectUtil();
            eventBus.fireEvent(new SelectEvent(selectedNode));
        }
    }

    private void fixChangeNodes() {
        Iterator<Node> iter = changeNodes.iterator();

        while (iter.hasNext()) {
            Node curNode = iter.next();
            if (curNode.getName().isEmpty() && !curNode.isDeleted()) {
                if (curNode.equals(selectedNode) && curNode.getId().equals(-1))
                    selectedNode = null;
                iter.remove();
            }
        }
    }

    private boolean validate() {
        for (Node n : changeNodes) {
            if (n.getName().isEmpty() && !n.isDeleted()) {
                return false;
            }
        }
        return true;
    }

    private void initPresenters() {
        Presenter selectedPresenter = new SelectedPanelPresenter(eventBus, new SelectedPanelView());
        selectedPresenter.go(display.getContainer());

        Presenter managerPresenter = new ManagerPanelPresenter(eventBus, new ManagerPanelView());
        managerPresenter.go(display.getContainer());

        Presenter tablePresenter = new NodeTablePanelPresenter(eventBus, new NodeTablePanelView());
        tablePresenter.go(display.getContainer());
    }

}
