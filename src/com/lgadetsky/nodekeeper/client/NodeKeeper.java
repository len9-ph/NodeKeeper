package com.lgadetsky.nodekeeper.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
    // Stub array for data
    private ArrayList<Node> treeNodes = new ArrayList<>();
    HashMap<Integer, TreeItem> idItemMap = new HashMap<>();
    
    
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
    
    @Override
    public void onModuleLoad() {
        treeNodes.add(new Node("Jora", "192.180.1.1", "4114"));
        treeNodes.add(new Node("Dasha", "192.141.2.2", "2222"));
        treeNodes.add(new Node(1, "Zhenya", "192.141.2.2", "2222"));
        treeNodes.add(new Node(0, "Dima", "192.141.2.2", "2222"));
        treeNodes.add(new Node("Kolya", "192.141.2.2", "2222"));
        treeNodes.add(new Node(4,  "Liza", "192.141.2.2", "11232"));
        treeNodes.add(new Node(3, "Ksenia", "192.141.2.2", "2525"));
        treeNodes.add(new Node(4, "Ira", "192.141.2.2", "2442"));
        
        // Upper panel assembly
        mainTree = createTree();
        
        mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem item = mainTree.getSelectedItem();
                Set<Map.Entry<Integer, TreeItem>> entrySet = idItemMap.entrySet();
                
                //Integer id = null;
                
                for (Map.Entry<Integer, TreeItem> pair : entrySet) {
                    if (item.equals(pair.getValue())) {
                        Integer id = pair.getKey();
                        selectedNodeTextBox.setText(id.toString());
                        Node curNode = new Node();
                        for (Node n : treeNodes)
                            if (n.getId() == id)
                                curNode = n;
                        idBox.setText(String.valueOf(curNode.getId()));
                        if (curNode.getParentId() > -1)
                            parentBox.setText(String.valueOf(curNode.getParentId()));
                        else
                            parentBox.setText("");
                        nameBox.setText(String.valueOf(curNode.getName()));
                        ipBox.setText(String.valueOf(curNode.getIp()));
                        portBox.setText(String.valueOf(curNode.getPort()));
                        
                        
                    }
                }
                //selectedNodeTextBox.setText(id.toString());
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
        lowerPanel.setStyleName("panel");
        
        RootPanel.get("page").add(upperPanel);
        RootPanel.get("page").add(lowerPanel);
        
        // Refresh allNodesPanel on loading
        refreshAllNodesPanel();
    }
    
    // Create tree from db data
    private Tree createTree() {
        Tree t = new Tree();
        for (Node n : treeNodes) {
            if(n.getParentId() == -1) {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                idItemMap.put(n.getId(), item);
                t.addItem(item);
            } else {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                idItemMap.put(n.getId(), item);
                TreeItem parent = idItemMap.get(n.getParentId());
                parent.addItem(item);
            }
        }
        
        return t;
    }
    
    
    private void addRootNode() {
        Node newNode = new Node();
        treeNodes.add(newNode);
        TreeItem newItem = new TreeItem(new HTML(newNode.getName()));
        idItemMap.put(newNode.getId(), newItem);
        mainTree.addItem(newItem);
    }
    
    private void addChildNode() {
        // TODO add selector validator
        
        int parentId = Integer.valueOf(selectedNodeTextBox.getText());
        Node newNode = new Node(parentId);
        treeNodes.add(newNode);
        TreeItem item = new TreeItem(new HTML(newNode.getName()));
        idItemMap.put(newNode.getId(), item);
        idItemMap.get(parentId).addItem(item);
    }
    
    private void editNode() {
        // TODO implement editing of selected element
     // TODO add input validation
        
        
    }
    
    private void deleteNode() {
        // TODO implement deleting node by id
        // TODO add input validation
        int id = Integer.valueOf(selectedNodeTextBox.getText());
        
        for (Node node : treeNodes) {
            if (node.getId() == id)
                treeNodes.remove(node);
        }
    }
    
    private void refreshAllNodesPanel() {
        Node newNode = treeNodes.get(0);
        
        
        allNodesGrid.resize(treeNodes.size() + 1, 5);
        allNodesGrid.setText(0, 0, "id");
        allNodesGrid.setText(0, 1, "parentId");
        allNodesGrid.setText(0, 2, "name");
        allNodesGrid.setText(0, 3, "ip");
        allNodesGrid.setText(0, 4, "port");
        for(int i = 0; i < treeNodes.size(); i++) {
            allNodesGrid.setText(i + 1, 0, String.valueOf(treeNodes.get(i).getId()));
            if (treeNodes.get(i).getParentId() != -1) 
                allNodesGrid.setText(i + 1, 1, String.valueOf(treeNodes.get(i).getParentId()));
            allNodesGrid.setText(i + 1, 2, treeNodes.get(i).getName());
            allNodesGrid.setText(i + 1, 3, treeNodes.get(i).getIp());
            allNodesGrid.setText(i + 1, 4, treeNodes.get(i).getPort());
        }
    }

}
