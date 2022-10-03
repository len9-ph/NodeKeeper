package com.lgadetsky.nodekeeper.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.shared.Node;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NodeKeeper implements EntryPoint {
    private final NodeKeeperServiceAsync service = GWT.create(NodeKeeperService.class);
    
    // List with data for tree
    private LinkedList<Node> nodes = new LinkedList<>();
    
    // List for changed data
    private LinkedList<Node> changedNodes = new LinkedList<>();
    
    // Map that connect node items with their treeItem's
    private HashMap<Node, TreeItem> nodeToTreeItemMap = new HashMap<>();
    
    // Tree for tree panel
    private Tree mainTree;
    
    // Two big blocks of layout
    private FlowPanel upperPanel = new FlowPanel();
    private FlowPanel lowerPanel = new FlowPanel();
    
    // Blocks in upperPanel creating
    
    // Tree panel 
    private FlowPanel treePanel = new FlowPanel();
    private ScrollPanel treeScroll = new ScrollPanel();
    private FlowPanel selectedPanel = new FlowPanel();
    private Label treeText = new Label("Tree"); 
    
    // Selected node panel
    private Label selectedText = new Label("Selected");
    private Grid selectedGrid = new Grid(5, 2);
    private TextBox idBox = new TextBox();
    private TextBox parentBox = new TextBox();
    private TextBox nameBox = new TextBox();
    private TextBox ipBox = new TextBox();
    private TextBox portBox = new TextBox();
    
    // Blocks in lowerPanel creating
    
    // Button panel
    private FlowPanel buttonPanel = new FlowPanel();
    private Button addRootButton = new Button("Add root node");
    private Button addChildButton = new Button("Add child");
    private Button editButton = new Button("Edit");
    private Button deleteButton = new Button("Delete");
    private Label selectedNodeLabel = new Label("Selected node: ");
    private TextBox selectedNodeTextBox = new TextBox();
    
    // All nodes panel
    private Label allNodesText = new Label("All nodes");
    private FlowPanel allNodesPanel = new FlowPanel();
    private FlowPanel allNodesTable = new FlowPanel();
    private Button refreshButton = new Button("Refresh");
    private Grid allNodesGrid = new Grid(1, 5);
    
    
    @Override
    public void onModuleLoad() {
        
        // Main panel styles setting
        upperPanel.setStyleName("panel");
        lowerPanel.setStyleName("panel");
        
        // Tree panel draw
        treePanel.add(treeText);
        treeScroll.setStyleName("treeScroll");
        treePanel.add(treeScroll);
        treePanel.setStyleName("treePanel");
        
        upperPanel.add(treePanel);
        
        // Selected panel draw
        selectedGrid.setText(0, 0, "id");
        selectedGrid.setText(1, 0, "parentId");
        selectedGrid.setText(2, 0, "name");
        selectedGrid.setText(3, 0, "ip");
        selectedGrid.setText(4, 0, "port");
        
        selectedGrid.setWidget(0, 1, idBox);
        idBox.setReadOnly(true);
        selectedGrid.setWidget(1, 1, parentBox);
        parentBox.setReadOnly(true);
        selectedGrid.setWidget(2, 1, nameBox);
        selectedGrid.setWidget(3, 1, ipBox);
        selectedGrid.setWidget(4, 1, portBox);
        selectedGrid.setStyleName("selectedGrid");
        selectedNodeLabel.setStyleName("selectedNodePanel");
        
        selectedPanel.add(selectedText);
        selectedPanel.add(selectedGrid);
        
        upperPanel.add(selectedPanel);
        
        // Lower panel assembly
        // Button panel draw
        buttonPanel.add(addRootButton);
        buttonPanel.add(addChildButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectedNodeLabel);
        buttonPanel.add(selectedNodeTextBox);
        selectedNodeTextBox.setStyleName("textBox");
        buttonPanel.setStyleName("buttonPanel");
        
        // Buttons handling
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addRootNode();
            }
        });
        
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addChildNode();
            }
        });
        
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editNode();
            }
        });
        
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                deleteNode();
            }
        });
        
        lowerPanel.add(buttonPanel);
        
        // All nodes panel draw 
        allNodesPanel.add(allNodesText);
        allNodesTable.add(refreshButton);
     // Refresh button assembly
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                saveState();
            }
        });
        
        allNodesGrid.setText(0, 0, "id");
        allNodesGrid.setText(0, 1, "parentId");
        allNodesGrid.setText(0, 2, "name");
        allNodesGrid.setText(0, 3, "ip");
        allNodesGrid.setText(0, 4, "port");
        allNodesTable.add(allNodesGrid);
        allNodesPanel.add(allNodesTable);
        
        allNodesGrid.setStyleName("allNodesGrid");
        allNodesTable.setStyleName("allNodesPanel");
        
        lowerPanel.add(allNodesPanel);
        
        // Add panel to main panel
        RootPanel.get("page").add(upperPanel);
        RootPanel.get("page").add(lowerPanel);
        
        // Initialize page with data
        initialize();
    }
    

    /**
     * Get data from db and if request success init tree 
     */
    private void initialize() {
        nodes.clear();
        service.getAllNodes(new AsyncCallback<List<Node>>() {
            
            @Override
            public void onSuccess(List<Node> result) {
                nodes.addAll(result);
                mainTree = createTree();
               
                mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
                    @Override
                    public void onSelection(SelectionEvent<TreeItem> event) {
                        TreeItem item = mainTree.getSelectedItem();
                        Set<Map.Entry<Node, TreeItem>> entrySet = nodeToTreeItemMap.entrySet();
                        
                        for (Map.Entry<Node, TreeItem> pair : entrySet) {
                            if(item.equals(pair.getValue())) {
                                selectedNodeTextBox.setText(pair.getKey().getId().toString());
                                if (pair.getKey().getParentId() > -1)
                                    parentBox.setText(pair.getKey().getParentId().toString());
                                else
                                    parentBox.setText("");
                                idBox.setText(pair.getKey().getId().toString());
                                nameBox.setText(pair.getKey().getName());
                                ipBox.setText(pair.getKey().getIp());
                                portBox.setText(pair.getKey().getPort());
                            }
                        }
                    }
                });
                treeScroll.add(mainTree);
                redrawAllNodesTable();
            }
            
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Internal error");
            }
        });
    }
    
    /**
     * Function that create tree from nodes list
     * @return - tree that contains all nodes from list
     */
    private Tree createTree() {
        final Tree t = new Tree();
        
        for (Node n : nodes) {
            if (n.getParentId() == -1) {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                nodeToTreeItemMap.put(n, item);
                t.addItem(item);
            } else {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                nodeToTreeItemMap.put(n, item);
                TreeItem parentItem = null;
                for (Node node : nodes) {
                    if (node.getId().equals(n.getParentId()))
                        parentItem = nodeToTreeItemMap.get(node);
                }
                parentItem.addItem(item);
            }
        }
        return t;
    }
    
    /**
     * Function that create empty node, add it to tree and add it to changedNodes list
     */
    private void addRootNode() {
        Node newNode = new Node();
        changedNodes.add(newNode);
        TreeItem newItem = new TreeItem(new HTML(newNode.getName()));
        nodeToTreeItemMap.put(newNode, newItem);
        mainTree.addItem(newItem);
    }
    
    /**
     * Function that create empty child node, dependes on selected item
     */
    private void addChildNode() {
        if (mainTree.getSelectedItem() != null) {
            TreeItem parentItem = mainTree.getSelectedItem();
            Node newNode = new Node(Integer.valueOf(selectedNodeTextBox.getText()));
            TreeItem newItem = new TreeItem(new HTML(newNode.getName()));
            changedNodes.add(newNode);
            nodeToTreeItemMap.put(newNode, newItem);
            parentItem.addItem(newItem);
        } else {
            // PopUp window: parent item not found
        }
    }
    
    /**
     * update node
     */
    private void editNode() {
        if (mainTree.getSelectedItem() != null) {
            TreeItem selectedItem = mainTree.getSelectedItem();
            
            // Find node by treeItem and edit it
            Set<Map.Entry<Node, TreeItem>> entrySet = nodeToTreeItemMap.entrySet();
            
            for (Map.Entry<Node, TreeItem> pair : entrySet) {
                if(selectedItem.equals(pair.getValue())) {
                    pair.getKey().setName(nameBox.getText());
                    pair.getKey().setIp(ipBox.getText());
                    pair.getKey().setPort(portBox.getText());
                    pair.getValue().setHTML(pair.getKey().getName());
                    changedNodes.add(pair.getKey());
                    mainTree.setFocus(true);
                }
            }
            
        } else {
            // PopUp window: item was not selected
        }
        
    }
    
    /**
     * delete node
     */
    private void deleteNode() {
        if (mainTree.getSelectedItem() != null) {
            TreeItem selectedItem = mainTree.getSelectedItem();
            Set<Map.Entry<Node, TreeItem>> entrySet = nodeToTreeItemMap.entrySet();

            for (Map.Entry<Node, TreeItem> pair : entrySet) {
                if (selectedItem.equals(pair.getValue())) {
                    if (pair.getKey().getId().equals(-1)) {
                        changedNodes.remove(pair.getKey());
                        nodeToTreeItemMap.remove(pair.getKey());
                    } else {
                        pair.getKey().setDeleted(true);
                        changedNodes.add(pair.getKey());
                        nodeToTreeItemMap.remove(pair.getKey());
                    }
                }
            }
            mainTree.removeItem(selectedItem);
        } else {
            // Popup window: item was not selected
        }
    }
    
    /**
     * Util func that redraw table with all nodes depend on nodes
     */
    private void redrawAllNodesTable() {
        allNodesGrid.resize(nodes.size() + 1, 5);
        allNodesGrid.setText(0, 0, "id");
        allNodesGrid.setText(0, 1, "parentId");
        allNodesGrid.setText(0, 2, "name");
        allNodesGrid.setText(0, 3, "ip");
        allNodesGrid.setText(0, 4, "port");
        for(int i = 0; i < nodes.size(); i++) {
            allNodesGrid.setText(i + 1, 0, String.valueOf(nodes.get(i).getId()));
            if (nodes.get(i).getParentId() != -1) 
                allNodesGrid.setText(i + 1, 1, String.valueOf(nodes.get(i).getParentId()));
            allNodesGrid.setText(i + 1, 2, nodes.get(i).getName());
            allNodesGrid.setText(i + 1, 3, nodes.get(i).getIp());
            allNodesGrid.setText(i + 1, 4, nodes.get(i).getPort());
        }
    }
    
    /**
     * Func that saves changes and update db and nodes
     */
    private void saveState() {
        if (!changedNodes.isEmpty()) {
            service.saveChanges(changedNodes, new AsyncCallback<List<Node>>() {
                
                @Override
                public void onSuccess(List<Node> result) {
                    // TODO Auto-generated method stub
                    refreshViewers(result);
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    // TODO Auto-generated method stub
                    Window.alert("Error");
                }
            });
        } else {
            // Popup window: nothing has been changed
        }
    }
    
    private void refreshViewers(List<Node> newNodes) {
        changedNodes.clear();
        mainTree.clear();
        nodeToTreeItemMap.clear();
        nodes.clear();
        
        nodes.addAll(newNodes);
        for (Node n : nodes) {
            if (n.getParentId() == -1) {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                nodeToTreeItemMap.put(n, item);
                mainTree.addItem(item);
            } else {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                nodeToTreeItemMap.put(n, item);
                TreeItem parentItem = null;
                for (Node node : nodes) {
                    if (node.getId().equals(n.getParentId()))
                        parentItem = nodeToTreeItemMap.get(node);
                }
                parentItem.addItem(item);
            }
        }
        redrawAllNodesTable();
    }
}

