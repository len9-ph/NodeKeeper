package com.lgadetsky.nodekeeper.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;

public class TreeEditPanelView extends Composite implements TreeEditPanelDisplay{
	private final String ID = "id";
    private final String PARENT_ID = "parentId";
    private final String NAME = "name";
    private final String IP = "ip";
    private final String PORT = "port";
    
    // Panel that keeps tree panel and selected panel
    private FlowPanel upperPanel;
    
    // Tree panel elements 
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
    
    public TreeEditPanelView() {
    	FlowPanel treeEditPanel = new FlowPanel();
    	initWidget(treeEditPanel);
    	
    	upperPanel = new FlowPanel();
    	
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
        
        // Selected text boxes handlers assembly
        nameBox = new TextBox();
        ipBox = new TextBox();
        portBox = new TextBox();
        
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
        
        // Buttons handlers assembly
        
    	
    }
    
    
	@Override
	public void setTree(Tree tree) {
		// TODO Auto-generated method stub
		
	}

}
