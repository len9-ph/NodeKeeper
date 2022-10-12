package com.lgadetsky.nodekeeper.client.view;

import java.util.List;

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
import com.lgadetsky.nodekeeper.client.presenter.EditNodesDisplay;
import com.lgadetsky.nodekeeper.shared.Node;

public class EditNodesView extends Composite implements EditNodesDisplay{
    private final String ID = "id";
    private final String PARENT_ID = "parentId";
    private final String NAME = "name";
    private final String IP = "ip";
    private final String PORT = "port";
    
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
                actionHandler.nameBoxPrinting(event);
            }
        });
        
        ipBox.addKeyUpHandler(new KeyUpHandler() {
            
            @Override
            public void onKeyUp(KeyUpEvent event) {
                actionHandler.ipBoxPrinting(event);
            }
        });
        
        portBox.addKeyUpHandler(new KeyUpHandler() {
            
            @Override
            public void onKeyUp(KeyUpEvent event) {
                actionHandler.portBoxPrinting(event);
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
                actionHandler.onAddRootButton(event);
            }
        });
        
        addChildButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                actionHandler.onAddChildButton(event);
            }
        });
        
        editButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
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
    public void setData(List<Node> data) {
        // initialize tree
    }

}

























