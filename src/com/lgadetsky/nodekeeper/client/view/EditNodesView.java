package com.lgadetsky.nodekeeper.client.view;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.client.presenter.EditNodesDisplay;
import com.lgadetsky.nodekeeper.shared.Node;

public class EditNodesView extends Composite implements EditNodesDisplay{
    private final String ID = "id";
    private final String PARENT_ID = "parentId";
    private final String NAME = "name";
    private final String IP = "ip";
    private final String PORT = "port";
    
    private HashMap<Integer, TreeItem> nodeIdToTreeItemMap;
    
    private Tree mainTree;
    private FlowPanel treePanel;
    private ScrollPanel treeScroll;
    private FlowPanel selectedPanel;
    private FlowPanel selectedTable;
    private Label treeText;
    
    private Label selectedText;
    private Grid selectedGrid;
    
    private Label idLabel;
    private Label parentLabel;
    private Label nameLabel;
    private Label ipLabel;
    private Label portLabel;
    
    private TextBox nameBox;
    private TextBox ipBox;
    private TextBox portBox;
    
    private FlowPanel buttonPanel;
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;
    private Label selectedNodeLabel;
    private Label selectedNodeTextLabel;
    
    private EditNodesDisplayActionHadler actionHandler;
    
    public EditNodesView() {
        DecoratorPanel contentTableDecorator  = new DecoratorPanel();
        
        mainTree = new Tree();
        treePanel = new FlowPanel();
        treeText = new Label("Tree");
        treePanel.add(treeText);
        treeScroll = new ScrollPanel();
        treeScroll.setStyleName("treeScroll");
        treePanel.setStyleName("treePanel");
        
        selectedText = new Label("Selected");
        selectedGrid = new Grid(1, 5);
        selectedGrid.setText(0, 0, ID);
        selectedGrid.setText(1, 0, PARENT_ID);
        selectedGrid.setText(2, 0, NAME);
        selectedGrid.setText(3, 0, IP);
        selectedGrid.setText(4, 0, PORT);
        
        selectedGrid.setStyleName("selectedGrid");
        selectedTable = new FlowPanel();
        selectedPanel = new FlowPanel();
        
        selectedPanel.add(selectedText);
        selectedTable.add(selectedGrid);
        selectedPanel.add(selectedTable);
        
        selectedGrid.setStyleName("selectedGrid");
        selectedTable.setStyleName("selectedNodeTable");
        selectedPanel.setStyleName("selectedNodePanel");
        
        nameBox = new TextBox();
        ipBox = new TextBox();
        portBox = new TextBox();
        
        nameBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                actionHandler.nameBoxPrinting(event, nameBox.getText());
            }
        });
        
        ipBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                actionHandler.ipBoxPrinting(event, ipBox.getText());
            }
        });
        
        portBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                actionHandler.portBoxPrinting(event, portBox.getText());
            }
        });
        
        buttonPanel = new FlowPanel();
        addRootButton = new Button("Add root node");
        addChildButton = new Button("Add child");
        editButton = new Button("Edit");
        deleteButton = new Button("Delete");
        selectedNodeLabel = new Label("Selected node: ");
        selectedNodeTextLabel = new Label("");
        
        buttonPanel.add(addRootButton);
        buttonPanel.add(addChildButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectedNodeLabel);
        buttonPanel.add(selectedNodeTextLabel);
        buttonPanel.setStyleName("buttonPanel");
        selectedNodeTextLabel.setStyleName("selectedLabel");
        selectedNodeLabel.setStyleName("selectedLabel");
        
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                TreeItem item = new TreeItem();
                item.setText("new root");
                mainTree.addItem(item);
                actionHandler.onAddRootButton(event, item);
            }
        });
        
        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                TreeItem item = new TreeItem();
                item.setText("new child");
                mainTree.getSelectedItem().addItem(item);
                actionHandler.onAddChildButton(event, mainTree.getSelectedItem(), item);
            }
        });
        
        editButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                // Refactor (not sending req in presenter)
                actionHandler.onEditButton(event);
            }
        });
        
        deleteButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                actionHandler.onDeleteButton(event);
            }
        });
        
    }
    
    @Override
    public void setDisplayActionhandler(EditNodesDisplayActionHadler actionHadler) {
        this.actionHandler = actionHadler;
    }
    
    
    
    @Override
    public void setTree(Tree tree) {
        // initialize tree
        // Set tree? setTree(Tree tree) 
        mainTree.clear();
        mainTree = tree;
    }

    @Override
    public void setSelectedItem(Node selectedNode) {
        idLabel.setText(selectedNode.getId().toString());
        if(selectedNode.getParentId() > -1)
            parentLabel.setText(selectedNode.getParentId().toString());
        else 
            parentLabel.setText("");
        nameLabel.setText(selectedNode.getName());
        ipLabel.setText(selectedNode.getIp());
        portLabel.setText(selectedNode.getPort());
        
        selectedGrid.setWidget(0, 1, idLabel);
        selectedGrid.setWidget(1, 1, parentLabel);
        selectedGrid.setWidget(2, 1, nameLabel);
        selectedGrid.setWidget(3, 1, ipLabel);
        selectedGrid.setWidget(4, 1, portLabel);
    }

    @Override
    public void editButtonPress(Node selectedNode) {
        nameBox.setText(selectedNode.getName());
        ipBox.setText(selectedNode.getIp());
        portBox.setText(selectedNode.getPort());
        
        selectedGrid.setWidget(2, 1, nameBox);
        selectedGrid.setWidget(3, 1, ipBox);
        selectedGrid.setWidget(4, 1, portBox);
    }

    @Override
    public void deleteTreeItem(TreeItem item) {
        mainTree.removeItem(item);
    }

    @Override
    public void deleteTreeItem(TreeItem parent, TreeItem item) {
        parent.removeItem(item);
    }

}

























