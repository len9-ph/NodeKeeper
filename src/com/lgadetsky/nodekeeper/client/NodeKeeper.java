package com.lgadetsky.nodekeeper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.lgadetsky.nodekeeper.client.gui.node_keeper.NodeKeeperPresenter;
import com.lgadetsky.nodekeeper.client.gui.node_keeper.NodeKeeperView;

public class NodeKeeper implements EntryPoint {
    private static NodeKeeperServiceAsync rpcService = GWT.create(NodeKeeperService.class);
    
    @Override
    public void onModuleLoad() {
        NodeKeeperView display = new NodeKeeperView();
        NodeKeeperPresenter keeperPresenter = new NodeKeeperPresenter(display);
        keeperPresenter.go(RootPanel.get("page"));
    }

    public static NodeKeeperServiceAsync getRpc() {
        return rpcService;
    }
}








//package com.lgadetsky.nodekeeper.client;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.event.dom.client.ClickEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
//import com.google.gwt.event.dom.client.KeyUpEvent;
//import com.google.gwt.event.dom.client.KeyUpHandler;
//import com.google.gwt.event.logical.shared.SelectionEvent;
//import com.google.gwt.event.logical.shared.SelectionHandler;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Button;
//import com.google.gwt.user.client.ui.DecoratedPopupPanel;
//import com.google.gwt.user.client.ui.FlowPanel;
//import com.google.gwt.user.client.ui.Grid;
//import com.google.gwt.user.client.ui.HTML;
//import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.user.client.ui.ScrollPanel;
//import com.google.gwt.user.client.ui.TextBox;
//import com.google.gwt.user.client.ui.Tree;
//import com.google.gwt.user.client.ui.TreeItem;
//import com.lgadetsky.nodekeeper.shared.Node;
//
//
///**
// * Entry point classes define <code>onModuleLoad()</code>.
// */
//public class NodeKeeper implements EntryPoint {
//    private final NodeKeeperServiceAsync service = GWT.create(NodeKeeperService.class);
//    
//    private final String ITEM_WAS_NOT_SELECTED = "Item was not selected";
//    private final String PARENT_ITEM_WAS_NOT_SELECTED = "Parent item not selected";
//    private final String PARENT_ITEM_NOT_VALID = "Parent has no id and cannot have children";
//    private final String EVERYTHING_UP_TO_DATE = "Everything is up to date";
//    private final String ERROR = "Internal error";
//    private final String ID = "id";
//    private final String PARENT_ID = "parentId";
//    private final String NAME = "name";
//    private final String IP = "ip";
//    private final String PORT = "port";
//    
//    // List with data for tree
//    private LinkedList<Node> nodes = new LinkedList<>();
//    
//    // List for changed data
//    private LinkedList<Node> changedNodes = new LinkedList<>();
//    
//    // Map that connect node items with their treeItem's
//    private HashMap<TreeItem, Node> treeItemToNodeMap = new HashMap<>();
//    
//    private DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
//    
//    // Tree for tree panel
//    private Tree mainTree;
//    
//    // Two big blocks of layout
//    private FlowPanel upperPanel = new FlowPanel();
//    private FlowPanel lowerPanel = new FlowPanel();
//    
//    // Blocks in upperPanel creating
//    
//    // Tree panel 
//    private FlowPanel treePanel = new FlowPanel();
//    private ScrollPanel treeScroll = new ScrollPanel();
//    private FlowPanel selectedPanel = new FlowPanel();
//    private FlowPanel selectedTable = new FlowPanel();
//    private Label treeText = new Label("Tree"); 
//    
//    // Selected node panel
//    private Label selectedText = new Label("Selected");
//    private Grid selectedGrid = new Grid(5, 2);
//    
//    // Stub for refactoring
//    private Label idLabel = new Label();
//    private Label parentLabel = new Label();
//    private Label nameLabel = new Label();
//    private Label ipLabel = new Label();
//    private Label portLabel = new Label();
//    
//    private TextBox nameBox = new TextBox();
//    private TextBox ipBox = new TextBox();
//    private TextBox portBox = new TextBox();
//    
//    // Blocks in lowerPanel creating
//    
//    // Button panel
//    private FlowPanel buttonPanel = new FlowPanel();
//    private Button addRootButton = new Button("Add root node");
//    private Button addChildButton = new Button("Add child");
//    private Button editButton = new Button("Edit");
//    private Button deleteButton = new Button("Delete");
//    private Label selectedNodeLabel = new Label("Selected node: ");
//    private Label selectedNodeTextLabel = new Label("");
//    
//    // All nodes panel
//    private Label allNodesText = new Label("All nodes");
//    private FlowPanel allNodesPanel = new FlowPanel();
//    private FlowPanel allNodesTable = new FlowPanel();
//    private Button refreshButton = new Button("Refresh");
//    private Grid allNodesGrid = new Grid(1, 5);
//    
//    
//    @Override
//    public void onModuleLoad() {
//        // Main panel styles setting
//        upperPanel.setStyleName("upperPanel");
//        lowerPanel.setStyleName("lowerPanel");
//        
//        // Tree panel draw
//        treePanel.add(treeText);
//        treeScroll.setStyleName("treeScroll");
//        treePanel.add(treeScroll);
//        treePanel.setStyleName("treePanel");
//        
//        upperPanel.add(treePanel);
//        
//        // Selected panel draw
//        selectedGrid.setText(0, 0, ID);
//        selectedGrid.setText(1, 0, PARENT_ID);
//        selectedGrid.setText(2, 0, NAME);
//        selectedGrid.setText(3, 0, IP);
//        selectedGrid.setText(4, 0, PORT);
//        
//        // Handle keyDown in boxes
//        nameBox.addKeyUpHandler(new KeyUpHandler() {
//            
//            @Override
//            public void onKeyUp(KeyUpEvent event) {
//                refreshState(nameBox, 1);
//            }
//        });
//        
//        ipBox.addKeyUpHandler(new KeyUpHandler() {
//            
//            @Override
//            public void onKeyUp(KeyUpEvent event) {
//                refreshState(ipBox, 2);
//            }
//        });
//        
//        portBox.addKeyUpHandler(new KeyUpHandler() {
//            
//            @Override
//            public void onKeyUp(KeyUpEvent event) {
//                refreshState(portBox, 3);
//            }
//        });
//        
//        selectedGrid.setStyleName("selectedGrid");
//        selectedTable.setStyleName("selectedNodeTable");
//        selectedPanel.setStyleName("selectedNodePanel");
//        
//        selectedPanel.add(selectedText);
//        selectedTable.add(selectedGrid);
//        selectedPanel.add(selectedTable);
//        
//        upperPanel.add(selectedPanel);
//        
//        // Lower panel assembly
//        // Button panel draw
//        buttonPanel.add(addRootButton);
//        buttonPanel.add(addChildButton);
//        buttonPanel.add(editButton);
//        buttonPanel.add(deleteButton);
//        buttonPanel.add(selectedNodeLabel);
//        buttonPanel.add(selectedNodeTextLabel);
//        buttonPanel.setStyleName("buttonPanel");
//        selectedNodeTextLabel.setStyleName("selectedLabel");
//        selectedNodeLabel.setStyleName("selectedLabel");
//        
//        // Buttons handling
//        addRootButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                addRootNode();
//            }
//        });
//        
//        addChildButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                addChildNode();
//            }
//        });
//        
//        editButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                //editNode();
//                
//                if(mainTree.getSelectedItem() != null) {
//                    TreeItem selectedItem = mainTree.getSelectedItem();
//                    nameBox.setText(treeItemToNodeMap.get(selectedItem).getName());
//                    ipBox.setText(treeItemToNodeMap.get(selectedItem).getIp());
//                    portBox.setText(treeItemToNodeMap.get(selectedItem).getPort());
//                    
//                    selectedGrid.setWidget(2, 1, nameBox);
//                    selectedGrid.setWidget(3, 1, ipBox);
//                    selectedGrid.setWidget(4, 1, portBox);
//                } else {
//                    simplePopup.setWidget(new HTML(ITEM_WAS_NOT_SELECTED));
//                    simplePopup.show();
//                }
//            }
//        });
//        
//        deleteButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                deleteNode();
//            }
//        });
//        
//        lowerPanel.add(buttonPanel);
//        
//        // All nodes panel draw 
//        allNodesPanel.add(allNodesText);
//        allNodesTable.add(refreshButton);
//        // Refresh button assembly
//        refreshButton.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                saveState();
//            }
//        });
//        
//        allNodesGrid.setText(0, 0, ID);
//        allNodesGrid.setText(0, 1, PARENT_ID);
//        allNodesGrid.setText(0, 2, NAME);
//        allNodesGrid.setText(0, 3, IP);
//        allNodesGrid.setText(0, 4, PORT);
//        allNodesTable.add(allNodesGrid);
//        allNodesPanel.add(allNodesTable);
//        
//        allNodesGrid.setStyleName("allNodesGrid");
//        allNodesTable.setStyleName("allNodesPanel");
//        
//        lowerPanel.add(allNodesPanel);
//        
//        // Add panel to main panel
//        RootPanel.get("page").add(upperPanel);
//        RootPanel.get("page").add(lowerPanel);
//        
//        // Initialize page with data
//        initialize();
//    }
//    
//
//    /**
//     * Get data from db and if request success init tree 
//     */
//    private void initialize() {
//        nodes.clear();
//        service.getAllNodes(new AsyncCallback<List<Node>>() {
//            
//            @Override
//            public void onSuccess(List<Node> result) {
//                nodes.addAll(result);
//                mainTree = createTree();
//               
//                mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
//                    @Override
//                    public void onSelection(SelectionEvent<TreeItem> event) {
//                        TreeItem selectedItem = mainTree.getSelectedItem();
//                        
//                        selectedNodeTextLabel.setText(treeItemToNodeMap.get(selectedItem).getId().toString());
//                        idLabel.setText(treeItemToNodeMap.get(selectedItem).getId().toString());
//                        parentLabel.setText(treeItemToNodeMap.get(selectedItem).getParentId().toString());
//                        nameLabel.setText(treeItemToNodeMap.get(selectedItem).getName());
//                        ipLabel.setText(treeItemToNodeMap.get(selectedItem).getIp());
//                        portLabel.setText(treeItemToNodeMap.get(selectedItem).getPort());
//                        
//                        if (treeItemToNodeMap.get(selectedItem).getParentId() > -1)
//                            parentLabel.setText(treeItemToNodeMap.get(selectedItem).getParentId().toString());
//                        else
//                            parentLabel.setText("");
//                        selectedGrid.setWidget(0, 1, idLabel);
//                        selectedGrid.setWidget(1, 1, parentLabel);
//                        selectedGrid.setWidget(2, 1, nameLabel);
//                        selectedGrid.setWidget(3, 1, ipLabel);
//                        selectedGrid.setWidget(4, 1, portLabel);
//                    }
//                });
//                treeScroll.add(mainTree);
//                redrawAllNodesTable();
//            }
//               
//            @Override
//            public void onFailure(Throwable caught) {
//                Window.alert(ERROR);
//            }
//        });
//    }
//    
//    /**
//     * Function that create tree from nodes list
//     * @return - tree that contains all nodes from list
//     */
//    private Tree createTree() {
//        final Tree t = new Tree();
//        
//        for (Node n : nodes) {
//            if (n.getParentId() == -1) {
//                TreeItem item = new TreeItem(new HTML(n.getName()));
//                treeItemToNodeMap.put(item, n);
//                t.addItem(item);
//            } else {
//                TreeItem item = new TreeItem(new HTML(n.getName()));
//                treeItemToNodeMap.put(item, n);
//                TreeItem parentItem = null;
//                for (Node node : nodes) {
//                    if (node.getId().equals(n.getParentId())) {
//                        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemToNodeMap.entrySet();
//                        
//                        for (Map.Entry<TreeItem, Node> pair : entrySet) {
//                            if(node.equals(pair.getValue())) 
//                                parentItem = pair.getKey();
//                        }
//                    }
//                }
//                parentItem.addItem(item);
//            }
//        }
//        return t;
//    }
//    
//    /**
//     * Function that create empty node, add it to tree and add it to changedNodes list
//     */
//    private void addRootNode() {
//        Node newNode = new Node();
//        changedNodes.add(newNode);
//        TreeItem newItem = new TreeItem(new HTML(newNode.getName()));
//        treeItemToNodeMap.put(newItem, newNode);
//        mainTree.addItem(newItem);
//    }
//    
//    /**
//     * Function that create empty child node, dependes on selected item
//     */
//    private void addChildNode() {
//        if (mainTree.getSelectedItem() != null) {
//            TreeItem parentItem = mainTree.getSelectedItem();
//            
//            Node curNode = treeItemToNodeMap.get(parentItem);
//            if (curNode.getId().equals(-1)) {
//                simplePopup.setWidget(new HTML(PARENT_ITEM_NOT_VALID));
//                simplePopup.show();
//            } else {
//                Node newNode = new Node(Integer.valueOf(selectedNodeTextLabel.getText()));
//                TreeItem newItem = new TreeItem(new HTML(newNode.getName()));
//                changedNodes.add(newNode);
//                treeItemToNodeMap.put(newItem, newNode);
//                parentItem.addItem(newItem);
//            }
//        } else {
//            simplePopup.setWidget(new HTML(PARENT_ITEM_WAS_NOT_SELECTED));
//            simplePopup.show();
//        }
//    }
//    
//    
//    /**
//     * delete node
//     */
//    private void deleteNode() {
//        if (mainTree.getSelectedItem() != null) {
//            TreeItem selectedItem = mainTree.getSelectedItem();
//            
//            Node curNode = treeItemToNodeMap.get(selectedItem);
//            if (curNode.getId().equals(-1)) {
//                changedNodes.remove(curNode);
//                treeItemToNodeMap.remove(selectedItem);
//            } else {
//                curNode.setDeleted(true);
//                for (Node n : nodes) {
//                    // if node has children remove them
//                    if (n.getParentId().equals(curNode.getId())) {
//                        n.setDeleted(true);
//                        changedNodes.add(n);
//                    }
//                }
//                changedNodes.add(curNode);
//                treeItemToNodeMap.remove(selectedItem);
//            }
//            if (curNode.getParentId().equals(-1))
//                mainTree.removeItem(selectedItem);
//            else 
//                selectedItem.getParentItem().removeItem(selectedItem);
//        } else {
//            simplePopup.setWidget(new HTML(ITEM_WAS_NOT_SELECTED));
//            simplePopup.show();
//        }
//    }
//    
//    /**
//     * Util func that redraw table with all nodes depend on nodes
//     */
//    private void redrawAllNodesTable() {
//        allNodesGrid.resize(nodes.size() + 1, 5);
//        for(int i = 0; i < nodes.size(); i++) {
//            allNodesGrid.setText(i + 1, 0, String.valueOf(nodes.get(i).getId()));
//            if (nodes.get(i).getParentId() != -1) 
//                allNodesGrid.setText(i + 1, 1, String.valueOf(nodes.get(i).getParentId()));
//            allNodesGrid.setText(i + 1, 2, nodes.get(i).getName());
//            allNodesGrid.setText(i + 1, 3, nodes.get(i).getIp());
//            allNodesGrid.setText(i + 1, 4, nodes.get(i).getPort());
//        }
//    }
//    
//    /**
//     * Func that saves changes and update db and nodes
//     */
//    private void saveState() {
//        if (!changedNodes.isEmpty()) {
//            service.saveChanges(changedNodes, new AsyncCallback<List<Node>>() {
//                
//                @Override
//                public void onSuccess(List<Node> result) {
//                    refreshViewers(result);
//                }
//                
//                @Override
//                public void onFailure(Throwable caught) {
//                    Window.alert(ERROR);
//                }
//            });
//        } else {
//            // Popup window: nothing has been changed
//            simplePopup.setWidget(new HTML(EVERYTHING_UP_TO_DATE));
//            simplePopup.show();
//        }
//    }
//    
//    private void refreshState(TextBox box, int name) {
//        TreeItem selectedItem = mainTree.getSelectedItem();
//        
//        Node curNode = treeItemToNodeMap.get(selectedItem);
//        if (changedNodes.contains(curNode))
//            changedNodes.remove(curNode);
//        
//        switch (name) {
//            case 1:
//                curNode.setName(box.getText());
//                selectedItem.setHTML(curNode.getName());
//                break;
//            case 2:
//                curNode.setIp(box.getText());
//                break;
//            case 3:
//                curNode.setPort(box.getText());
//        }
//        changedNodes.add(curNode);
//    }
//    
//    private void refreshViewers(List<Node> newNodes) {
//        changedNodes.clear();
//        mainTree.clear();
//        treeItemToNodeMap.clear();
//        nodes.clear();
//        
//        nodes.addAll(newNodes);
//        for (Node n : nodes) {
//            if (n.getParentId() == -1) {
//                TreeItem item = new TreeItem(new HTML(n.getName()));
//                treeItemToNodeMap.put(item, n);
//                mainTree.addItem(item);
//            } else {
//                TreeItem item = new TreeItem(new HTML(n.getName()));
//                treeItemToNodeMap.put(item, n);
//                TreeItem parentItem = null;
//                for (Node node : nodes) {
//                    if (node.getId().equals(n.getParentId())) {
//                        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemToNodeMap.entrySet();
//                    
//                        for (Map.Entry<TreeItem, Node> pair : entrySet) {
//                            if(node.equals(pair.getValue())) 
//                                parentItem = pair.getKey();
//                        }
//                    }
//                }
//                parentItem.addItem(item);
//            }
//        }
//        redrawAllNodesTable();
//    }
//}
//
