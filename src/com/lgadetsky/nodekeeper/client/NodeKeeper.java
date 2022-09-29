package com.lgadetsky.nodekeeper.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.javascript.host.svg.SVGEllipseElement;
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
import com.google.gwt.user.client.ui.PopupPanel;
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
    
    private LinkedList<Node> changedNodes = new LinkedList<>();
    
    // Map that keeps data for tree
    //private HashMap<Integer, Node> treeMap = new HashMap<>();
    // Map for
    private HashMap<Integer, TreeItem> idItemMap = new HashMap<>();
    
    // Map that connect node items with their treeItem's
    private HashMap<Node, TreeItem> nodeToTreeItemMap = new HashMap<>();
    
    
    // Two big blocks of layout
    private FlowPanel upperPanel = new FlowPanel();
    private FlowPanel lowerPanel = new FlowPanel();
    
    // Blocks in upperPanel creating
    private FlowPanel treePanel = new FlowPanel();
    private ScrollPanel treeScroll = new ScrollPanel();
    private FlowPanel selectedPanel = new FlowPanel();
    private Label treeText = new Label("Tree"); 
    
    private Label selectedText = new Label("Selected");
    private Grid selectedGrid = new Grid(5, 2);
    private TextBox idBox = new TextBox();
    private TextBox parentBox = new TextBox();
    private TextBox nameBox = new TextBox();
    private TextBox ipBox = new TextBox();
    private TextBox portBox = new TextBox();
    
    // Blocks in lowerPanel creating
    private FlowPanel buttonPanel = new FlowPanel();
    private Button addRootButton = new Button("Add root node");
    private Button addChildButton = new Button("Add child");
    private Button editButton = new Button("Edit");
    private Button deleteButton = new Button("Delete");
    private Label selectedNodeLabel = new Label("Selected node: ");
    private TextBox selectedNodeTextBox = new TextBox();
    
    private Label allNodesText = new Label("All nodes");
    private FlowPanel allNodesPanel = new FlowPanel();
    private FlowPanel allNodesTable = new FlowPanel();
    private Button refreshButton = new Button("Refresh");
    private Grid allNodesGrid = new Grid(1, 5);
    private Tree mainTree;
    private FlowPanel popupPanel = new FlowPanel();
    
    @Override
    public void onModuleLoad() {
        // Upper panel assembly
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
                        nameBox.setText(pair.getKey().getName());
                        ipBox.setText(pair.getKey().getIp());
                        portBox.setText(pair.getKey().getPort());
                    }
                }
            }
        });
        
        treeScroll.add(mainTree);
        treeScroll.setStyleName("treeScroll");
        treePanel.add(treeText);
        treePanel.add(treeScroll);
        treePanel.setStyleName("treePanel");
        
        upperPanel.add(treePanel);
        
        selectedGrid.setText(0, 0, "id");
        selectedGrid.setText(1, 0, "parentId");
        selectedGrid.setText(2, 0, "name");
        selectedGrid.setText(3, 0, "ip");
        selectedGrid.setText(4, 0, "port");
        idBox.setReadOnly(true);
        parentBox.setReadOnly(true);
        selectedGrid.setWidget(0, 1, idBox);
        selectedGrid.setWidget(1, 1, parentBox);
        selectedGrid.setWidget(2, 1, nameBox);
        selectedGrid.setWidget(3, 1, ipBox);
        selectedGrid.setWidget(4, 1, portBox);
        selectedNodeLabel.setStyleName("selectedNodePanel");
//        idBox.setStyleName("textBox");
//        parentBox.setStyleName("textBox");
//        nameBox.setStyleName("textBox");
//        ipBox.setStyleName("textBox");
//        portBox.setStyleName("textBox");
        
        selectedPanel.add(selectedText);
        selectedPanel.add(selectedGrid);
        
        selectedGrid.setStyleName("selectedGrid");
        
        upperPanel.add(selectedPanel);
        upperPanel.setStyleName("panel");
        
        // Lower panel assembly
        buttonPanel.add(addRootButton);
        buttonPanel.add(addChildButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectedNodeLabel);
        buttonPanel.add(selectedNodeTextBox);
        
        selectedNodeTextBox.setStyleName("textBox");
        
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
        
        buttonPanel.setStyleName("buttonPanel");
        
        lowerPanel.add(buttonPanel);
        
        allNodesPanel.add(allNodesText);
        allNodesTable.add(refreshButton);
        allNodesGrid.setText(0, 0, "id");
        allNodesGrid.setText(0, 1, "parentId");
        allNodesGrid.setText(0, 2, "name");
        allNodesGrid.setText(0, 3, "ip");
        allNodesGrid.setText(0, 4, "port");
        allNodesTable.add(allNodesGrid);
        allNodesPanel.add(allNodesTable);
        
        //allNodesGrid.getRowFormatter().addStyleName(0, "firstRowStyle");
        allNodesGrid.setStyleName("allNodesGrid");
        allNodesTable.setStyleName("allNodesPanel");
        
        // Refresh button assembly
        refreshButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                refreshAllNodesPanel();
                
            }
        });
        
        lowerPanel.add(allNodesPanel);
        
//        lowerPanel.add(popupPanel);
//        popupPanel.setStyleName("popupPanel");
        
        lowerPanel.setStyleName("panel");
        
        RootPanel.get("page").add(upperPanel);
        RootPanel.get("page").add(lowerPanel);
        
        // Refresh allNodesPanel on loading
        // refreshAllNodesPanel();
    }
    
    // Create tree from db data
    private Tree createTree() {
        final Tree t = new Tree();
        service.getAllNodes(new AsyncCallback<List<Node>>() {
            
            @Override
            public void onSuccess(List<Node> result) {
                nodes.addAll(result);
                
                for (Node n : nodes) {
                    if(n.getParentId() == -1) {
                        TreeItem item = new TreeItem(new HTML(n.getName()));
                        nodeToTreeItemMap.put(n, item);
                        idItemMap.put(n.getId(), item);
                        t.addItem(item);
                    } else {
                        TreeItem item = new TreeItem(new HTML(n.getName()));
                        nodeToTreeItemMap.put(n, item);
                        idItemMap.put(n.getId(), item);
                        TreeItem parent = idItemMap.get(n.getParentId());
                        parent.addItem(item);
                    }
                }
                PopupPanel panel = new PopupPanel();
                panel.setWidget(new Label("Tree initialization was successful"));
                panel.setPopupPosition(0, Window.getClientHeight() - 40);
                panel.show();
                
            }
            @Override
            public void onFailure(Throwable caught) {
                PopupPanel panel = new PopupPanel();
                panel.setWidget(new Label("Tree initialization failed"));
                panel.setPopupPosition(0, Window.getClientHeight() - 40);
                panel.show();
                
            }
        });
        return t;
    }
    
    
    private void addRootNode() {
        Node newNode = new Node();
        nodes.add(newNode);
        TreeItem newItem = new TreeItem(new HTML(newNode.getName()));
        nodeToTreeItemMap.put(newNode, newItem);
        mainTree.addItem(newItem);
    }
    
    private void addChildNode() {
        if (mainTree.getSelectedItem() != null) {
            TreeItem parentItem = mainTree.getSelectedItem();
            Node newNode = new Node(Integer.valueOf(selectedNodeTextBox.getText()));
            TreeItem newItem = new TreeItem();
            nodes.add(newNode);
            nodeToTreeItemMap.put(newNode, newItem);
            parentItem.addItem(newItem);
        } else {
            // PopUp window: parent item not found
        }
    }
    
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
                }
            }
            
        } else {
            // PopUp window: item was not selected
        }
        
    }
    
    private void deleteNode() {
        // TODO implement deleting node by id
        // TODO add input validation
        if (mainTree.getSelectedItem() != null) {
            TreeItem selectedItem = mainTree.getSelectedItem();
            Set<Map.Entry<Node, TreeItem>> entrySet = nodeToTreeItemMap.entrySet();
            
            for (Map.Entry<Node, TreeItem> pair : entrySet) {
                if (selectedItem.equals(pair.getValue())) {
                    // Set id equal -2 that means element should be deleted than user press refresh
                    pair.getKey().setId(-2);
                }
            }
            selectedItem.remove();
        } else {
            // Popup window: item was not selected
        }
    }
    
    private void refreshAllNodesPanel() {
        saveState();
        
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
    
    private void saveState() {
        //TreeItem selectedItem = mainTree.getSelectedItem();
        // TODO find node by selected tree item
        Node curNode = new Node(); // here should be node from map finded by nodeToItemMap
        //curNode.setId(); // Initialize node formally add it to db
        curNode.setName(nameBox.getText());
        curNode.setIp(ipBox.getText());
        curNode.setPort(portBox.getText());
        service.create(curNode, new AsyncCallback<Node>() {

            @Override
            public void onFailure(Throwable caught) {
                PopupPanel panel = new PopupPanel();
                panel.setWidget(new Label("Node add failed"));
                panel.setPopupPosition(0, Window.getClientHeight() - 40);
                panel.show();
            }

            @Override
            public void onSuccess(Node result) {
                PopupPanel panel = new PopupPanel();
                panel.setWidget(new Label("Node add success"));
                panel.setPopupPosition(0, Window.getClientHeight() - 40);
                panel.show();
            }
            
        });
    }
    
    
}
