package com.lgadetsky.nodekeeper.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.shared.Node;

public class TreeEditPanelView extends Composite implements TreeEditPanelDisplay{
	private final String ID = "id";
    private final String PARENT_ID = "parentId";
    private final String NAME = "name";
    private final String IP = "ip";
    private final String PORT = "port";
    
    // Panel that keeps tree panel and selected panel
    private FlowPanel upperPanel;
    
    // Tree panel elements 
    private HashMap<TreeItem, Node> treeItemtoNodeMap;
    private FlowPanel treePanel;
    private Label treeText;
    private ScrollPanel treeScroll;
    private Tree mainTree;
    
    // Selected panel elements
    private FlowPanel selectedPanel;
    private Label selectedText;
    private FlowPanel selectedTable;
    private Grid selectedGrid;
    
    private Label idLabel;
    private Label parentLabel;
    private Label nameLabel;
    private Label ipLabel;
    private Label portLabel;
    
    private TextBox nameBox;
    private TextBox ipBox;
    private TextBox portBox;
    
    // Button panel elements
    private FlowPanel buttonPanel;
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;
    
    private Label selectedNodeLabel;
    private Label selectedNodeTextLabel;
    
    private TreeEditPanelActionHandler handler;
    
    public TreeEditPanelView() {
        FlowPanel treeEditPanel = new FlowPanel();
        initWidget(treeEditPanel);
        
        upperPanel = new FlowPanel();
        
        mainTree = new Tree();
        
        mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem selectedItem = mainTree.getSelectedItem();
                
                selectedNodeTextLabel.setText(treeItemtoNodeMap.get(selectedItem).getId().toString());
                idLabel.setText(treeItemtoNodeMap.get(selectedItem).getId().toString());
                parentLabel.setText(treeItemtoNodeMap.get(selectedItem).getParentId().toString());
                nameLabel.setText(treeItemtoNodeMap.get(selectedItem).getName());
                ipLabel.setText(treeItemtoNodeMap.get(selectedItem).getIp());
                portLabel.setText(treeItemtoNodeMap.get(selectedItem).getPort());
                
                if (treeItemtoNodeMap.get(selectedItem).getParentId() > -1)
                    parentLabel.setText(treeItemtoNodeMap.get(selectedItem).getParentId().toString());
                else
                    parentLabel.setText("");
                selectedGrid.setWidget(0, 1, idLabel);
                selectedGrid.setWidget(1, 1, parentLabel);
                selectedGrid.setWidget(2, 1, nameLabel);
                selectedGrid.setWidget(3, 1, ipLabel);
                selectedGrid.setWidget(4, 1, portLabel);
            }
        });
        
        // Tree assembly
        treePanel = new FlowPanel();
        treeText = new Label("Tree");
        treePanel.add(treeText);
        treeScroll = new ScrollPanel();
        treeScroll.add(mainTree);
        treePanel.add(treeScroll);
        upperPanel.add(treePanel);
        
        // Selected panel
        selectedPanel = new FlowPanel();
        selectedText = new Label("Selected");
        selectedPanel.add(selectedText);
        selectedTable = new FlowPanel();
        selectedGrid = new Grid(5, 2);
        selectedTable.add(selectedGrid);
        selectedPanel.add(selectedTable);
        
        selectedGrid.setText(0, 0, ID);
        selectedGrid.setText(1, 0, PARENT_ID);
        selectedGrid.setText(2, 0, NAME);
        selectedGrid.setText(3, 0, IP);
        selectedGrid.setText(4, 0, PORT);
        
        idLabel = new Label();
        parentLabel = new Label();
        nameLabel = new Label();
        ipLabel = new Label();
        portLabel = new Label();
        
        nameBox = new TextBox();
        ipBox = new TextBox();
        portBox = new TextBox();
        
        // Selected text boxes handlers assembly
        nameBox = new TextBox();
        ipBox = new TextBox();
        portBox = new TextBox();
        
        nameBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                mainTree.getSelectedItem().setHTML(nameBox.getText());
                handler.onBoxChange(treeItemtoNodeMap.get(mainTree.getSelectedItem()),
                        "name",
                        nameBox.getText());
            }
        });
        
        ipBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                handler.onBoxChange(treeItemtoNodeMap.get(mainTree.getSelectedItem()),
                        "ip",
                        ipBox.getText());
            }
        });
        
        portBox.addKeyUpHandler(new KeyUpHandler() {
            
            @Override
            public void onKeyUp(KeyUpEvent event) {
                handler.onBoxChange(treeItemtoNodeMap.get(mainTree.getSelectedItem()),
                        "port",
                        portBox.getText());
            }
        });
        
        upperPanel.add(selectedPanel);
        
        treeEditPanel.add(upperPanel);
        
        // Button Panel
        buttonPanel = new FlowPanel();
        
        addRootButton = new Button("Add root");
        addChildButton = new Button("Add child");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        selectedNodeLabel = new Label("Selected node:");
        selectedNodeTextLabel = new Label("");
        
        buttonPanel.add(addRootButton);
        buttonPanel.add(addChildButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectedNodeLabel);
        buttonPanel.add(selectedNodeTextLabel);
        
        treeEditPanel.add(buttonPanel);
        // Buttons handlers assembly
        
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onAddRootClick();
            }
        });
        
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (mainTree.getSelectedItem() != null) {
                    handler.onAddChildClick(treeItemtoNodeMap.get(mainTree.getSelectedItem()));
                } else 
                    handler.onSelectError("Item was not selected");
            }
        });
        
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (mainTree.getSelectedItem() != null) {
                    nameBox.setText(nameLabel.getText());
                    ipBox.setText(ipLabel.getText());
                    portBox.setText(portLabel.getText());
                    selectedGrid.setWidget(2, 1, nameBox);
                    selectedGrid.setWidget(3, 1, ipBox);
                    selectedGrid.setWidget(4, 1, portBox);
                } else 
                    handler.onSelectError("Item was not selected");
            }
        });
        
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(mainTree.getSelectedItem() != null) {
                    
                } else 
                    handler.onSelectError("Item was not selected");
            }
        });
    }
    
    @Override
    public void setTreeEditPanelActionHanlder(TreeEditPanelActionHandler handler) {
        this.handler = handler;
    }

    
    @Override
    public void buildTree(List<Node> nodes) {
        mainTree.clear();
        
        for (Node n : nodes) {
            if (n.getParentId() == -1) {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                treeItemtoNodeMap.put(item, n);
                mainTree.addItem(item);
            } else {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                treeItemtoNodeMap.put(item, n);
                TreeItem parentItem = null;
                for (Node node : nodes) {
                    if (node.getId().equals(n.getParentId())) {
                        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemtoNodeMap.entrySet();
                        
                        for (Map.Entry<TreeItem, Node> pair : entrySet) {
                            if(node.equals(pair.getValue())) 
                                parentItem = pair.getKey();
                        }
                    }
                }
                parentItem.addItem(item);
            }
        }
    }

    @Override
    public void updateTree(Node newNode) {
        TreeItem newTreeItem = new TreeItem(new HTML(newNode.getName()));
        treeItemtoNodeMap.put(newTreeItem, newNode);
        if(newNode.getParentId().equals(-1))
            mainTree.addItem(newTreeItem);
        else 
            mainTree.getSelectedItem().addItem(newTreeItem);
    }
}
