package com.lgadetsky.nodekeeper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NodeKeeper implements EntryPoint {
    private FlowPanel upperBlock = new FlowPanel();
    private FlowPanel lowerBlock = new FlowPanel();
    private FlowPanel selectedPanel = new FlowPanel();
    private FlowPanel allNodesPanel = new FlowPanel();
    private FlowPanel buttonPanel = new FlowPanel();
    
    private FlowPanel treePanel = new FlowPanel();
    
    private FlexTable selectedTable = new FlexTable();
    private FlexTable allNodesTable = new FlexTable();
    
    
    @Override
    public void onModuleLoad() {
        // Scroll panel 
        HTML content = new HTML("Scroll panel for nodes");
        treePanel.add(content);
        treePanel.setSize("200px", "100px");
        treePanel.addStyleName("treePanel");
        // TODO build tree panel
        
        // Table for selected item
        selectedTable.setText(0, 0, "id");
        selectedTable.setText(1, 0, "parentId");
        selectedTable.setText(2, 0, "name");
        selectedTable.setText(3, 0, "ip");
        selectedTable.setText(4, 0, "port");
        
        // Button table
        buttonPanel.add(new Button("Add root node"));
        buttonPanel.add(new Button("Add child"));
        buttonPanel.add(new Button("Edit"));
        buttonPanel.add(new Button("Delete"));
        // TODO add selected item indicator
        
        // All nodes Table
        allNodesTable.setText(0, 0, "id");
        allNodesTable.setText(0, 1, "parentId");
        allNodesTable.setText(0, 2, "name");
        allNodesTable.setText(0, 3, "ip");
        allNodesTable.setText(0, 4, "port");
        
        // UpperPanelAssembly
        selectedPanel.addStyleName("selectedPanel");
        selectedPanel.add(selectedTable);
        upperBlock.add(treePanel);
        upperBlock.add(selectedPanel);
        upperBlock.addStyleName("upperPanel");
        
        // LowerPanelAssembly
        allNodesPanel.add(allNodesTable);
        lowerBlock.add(buttonPanel);
        lowerBlock.add(allNodesPanel);
        lowerBlock.addStyleName("lowerPanel");
        
        // Root panel assembly
        RootPanel.get("nodeKeeper").add(upperBlock);
        RootPanel.get("nodeKeeper").add(lowerBlock);
        
        
        
    }

}
