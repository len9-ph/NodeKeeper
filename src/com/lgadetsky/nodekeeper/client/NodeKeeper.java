package com.lgadetsky.nodekeeper.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.lgadetsky.nodekeeper.shared.Node;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NodeKeeper implements EntryPoint {
    // Stub array for data
    private ArrayList<Node> nodes = new ArrayList<>();
    // Two big blocks of layout
    private FlowPanel upperPanel = new FlowPanel();
    private FlowPanel lowerPanel = new FlowPanel();
    
    // Blocks in upperPanel creating
    private ScrollPanel treePanel = new ScrollPanel();
    private FlowPanel selectedPanel = new FlowPanel();
    
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
    
    private FlowPanel allNodesPanel = new FlowPanel();
    private Button refreshButton = new Button("Refresh");
    private Grid allNodesGrid = new Grid(1, 5);
    
    @Override
    public void onModuleLoad() {
        nodes.add(new Node(1, "Jora", "192.180.1.1", "4114"));
        nodes.add(new Node(2, "Dasha", "192.141.2.2", "2222"));
        
        // Upper panel assembly
        // TODO add tree implementation
        TextBox textStub = new TextBox();
        textStub.setText("TEXTSTUB");
        treePanel.add(textStub);
        treePanel.setStyleName("treePanel");
        upperPanel.add(treePanel);
        
        selectedGrid.setText(0, 0, "id");
        selectedGrid.setText(1, 0, "parentId");
        selectedGrid.setText(2, 0, "name");
        selectedGrid.setText(3, 0, "ip");
        selectedGrid.setText(4, 0, "port");
        selectedGrid.setWidget(0, 1, idBox);
        selectedGrid.setWidget(1, 1, parentBox);
        selectedGrid.setWidget(2, 1, nameBox);
        selectedGrid.setWidget(3, 1, ipBox);
        selectedGrid.setWidget(4, 1, portBox);
        selectedNodeLabel.setStyleName("selectedNodePanel");
        
        
        selectedGrid.setStyleName("selectedGrid");
        
        upperPanel.add(selectedGrid);
        upperPanel.setStyleName("panel");
        
        // Lower panel assembly
        buttonPanel.add(addRootButton);
        buttonPanel.add(addChildButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectedNodeLabel);
        buttonPanel.add(selectedNodeTextBox);
        
        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addRootNode();
            }
        });
        
        buttonPanel.setStyleName("buttonPanel");
        
        lowerPanel.add(buttonPanel);
        
        allNodesPanel.add(refreshButton);
        allNodesGrid.setText(0, 0, "ip");
        allNodesGrid.setText(0, 1, "parentId");
        allNodesGrid.setText(0, 2, "name");
        allNodesGrid.setText(0, 3, "ip");
        allNodesGrid.setText(0, 4, "port");
        allNodesPanel.add(allNodesGrid);
        allNodesGrid.setStyleName("allNodesGrid");
        allNodesPanel.setStyleName("allNodesPanel");
        
        // Refresh button assembly
        refreshButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                refreshAllNodesPanel();
                
            }
        });
        
//        refreshButton.addKeyDownHandler(new KeyDownHandler() {
//            @Override
//            public void onKeyDown(KeyDownEvent event) {
//                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
//                    refreshAllNodesPanel();
//            }
//        });
        
        lowerPanel.add(allNodesPanel);
        lowerPanel.setStyleName("panel");
        
        RootPanel.get("page").add(upperPanel);
        RootPanel.get("page").add(lowerPanel);
        
        // Refresh allNodesPanel on loading
        refreshAllNodesPanel();
    }
    
    private void addRootNode() {
        String id = idBox.getText();
        String name = nameBox.getText();
        String ip = ipBox.getText();
        String port = portBox.getText();
        
        Node newNode = new Node(Integer.valueOf(id), name, ip, port);
        nodes.add(newNode);
    }
    
    private void refreshAllNodesPanel() {
        allNodesGrid.resize(nodes.size() + 1, 5);
        allNodesGrid.setText(0, 0, "ip");
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

}
