package com.lgadetsky.nodekeeper.client.view;

import java.util.Collections;
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
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.shared.Node;

public class TreeEditPanelView extends Composite implements TreeEditPanelDisplay{
    // Panel that keeps tree panel and selected panel
    private FlowPanel upperPanel;
    
    // Tree panel elements 
    private HashMap<TreeItem, Node> treeItemtoNodeMap;
    private FlowPanel treePanel;
    private Label treeText;
    private ScrollPanel treeScroll;
    private final Tree mainTree;
    
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
        treeEditPanel.addStyleName("upperPanel");
        
        treeItemtoNodeMap = new HashMap<TreeItem, Node>();
        
        upperPanel = new FlowPanel();
        
        mainTree = new Tree();
        
        mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem selectedItem = mainTree.getSelectedItem();
                
                setSelectedItem(treeItemtoNodeMap.get(selectedItem));
            }
        });
        
        // Tree assembly
        treePanel = new FlowPanel();
        treeText = new Label("Tree");
        treePanel.add(treeText);
        treeScroll = new ScrollPanel();
        treeScroll.add(mainTree);
        treeScroll.setStyleName("treeScroll");
        treePanel.add(treeScroll);
        treePanel.setStyleName("treePanel");
        upperPanel.add(treePanel);
        
        // Selected panel
        selectedPanel = new FlowPanel();
        selectedText = new Label("Selected");
        selectedPanel.add(selectedText);
        selectedTable = new FlowPanel();
        selectedGrid = new Grid(5, 2);
        selectedTable.add(selectedGrid);
        selectedPanel.add(selectedTable);
        selectedGrid.setStyleName("selectedGrid");
        selectedTable.setStyleName("selectedNodeTable");
        selectedPanel.setStyleName("selectedNodePanel");
        
        selectedGrid.setText(0, 0, StringConstants.ID);
        selectedGrid.setText(1, 0, StringConstants.PARENT_ID);
        selectedGrid.setText(2, 0, StringConstants.NAME);
        selectedGrid.setText(3, 0, StringConstants.IP);
        selectedGrid.setText(4, 0, StringConstants.PORT);
        
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
        
        addRootButton = new Button(StringConstants.ADD_ROOT);
        addChildButton = new Button(StringConstants.ADD_CHILD);
        editButton = new Button(StringConstants.EDIT);
        deleteButton = new Button(StringConstants.DELETE);
        selectedNodeLabel = new Label(StringConstants.SELECTED);
        selectedNodeTextLabel = new Label("");
        buttonPanel.setStyleName("buttonPanel");
        selectedNodeTextLabel.setStyleName("selectedLabel");
        selectedNodeLabel.setStyleName("selectedLabel");
        
        
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
                    if (treeItemtoNodeMap.get(mainTree.getSelectedItem()).getId() > 0)
                        handler.onAddChildClick(treeItemtoNodeMap.get(mainTree.getSelectedItem()));
                    else
                        handler.onError(StringConstants.PARENT_ITEM_NOT_VALID);
                } else 
                    handler.onError(StringConstants.PARENT_ITEM_WAS_NOT_SELECTED);
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
                    handler.onError(StringConstants.ITEM_WAS_NOT_SELECTED);
            }
        });
        
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (mainTree.getSelectedItem() != null) {
                    TreeItem selectedItem = mainTree.getSelectedItem();
                    Node curNode = treeItemtoNodeMap.get(selectedItem);
                    // Delete elem from nodes in presenter
                    handler.onDeleteClick(curNode);
                    // Remove elem from tree
                    if(curNode.getParentId().equals(-1))
                        mainTree.removeItem(selectedItem);
                    else
                        selectedItem.getParentItem().removeItem(selectedItem);
                    
                } else 
                    handler.onError(StringConstants.ITEM_WAS_NOT_SELECTED);
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
        
        Collections.sort(nodes, Node.COMPARE_BY_ID);
        
        treeItemtoNodeMap = new HashMap<TreeItem, Node>();
        
        for (Node n : nodes) {
            TreeItem item = new TreeItem(new HTML(n.getName()));
            treeItemtoNodeMap.put(item, n);
            if (n.getParentId() == -1) {
                mainTree.addItem(item);
            } else {
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

    @Override
    public void setNode(Node node) {
        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemtoNodeMap.entrySet();
        
        for (Map.Entry<TreeItem, Node> pair : entrySet) {
            if(node.equals(pair.getValue()))
                mainTree.setSelectedItem(pair.getKey());
        }
        
        setSelectedItem(node);
    }
    
    private void setSelectedItem(Node selectedNode) {
        selectedNodeTextLabel.setText(selectedNode.getId().toString());
        idLabel.setText(selectedNode.getId().toString());
        nameLabel.setText(selectedNode.getName());
        ipLabel.setText(selectedNode.getIp());
        portLabel.setText(selectedNode.getPort());
        
        if (selectedNode.getParentId() > -1)
            parentLabel.setText(selectedNode.getParentId().toString());
        else
            parentLabel.setText("");
        
        selectedGrid.setWidget(0, 1, idLabel);
        selectedGrid.setWidget(1, 1, parentLabel);
        selectedGrid.setWidget(2, 1, nameLabel);
        selectedGrid.setWidget(3, 1, ipLabel);
        selectedGrid.setWidget(4, 1, portLabel);
    }
}
