package com.lgadetsky.nodekeeper.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.lgadetsky.nodekeeper.client.presenter.AllNodesDisplay;
import com.lgadetsky.nodekeeper.shared.Node;

public class AllNodesView extends Composite implements AllNodesDisplay{
    private final String ID = "id";
    private final String PARENT_ID = "parentId";
    private final String NAME = "name";
    private final String IP = "ip";
    private final String PORT = "port";
    
    private final Label allNodesText;
    private final FlowPanel allNodesPanel;
    private final FlowPanel allNodesTable;
    private final Button refreshButton;
    private Grid allNodesGrid;
    private AllNodesDisplayActionHandler actionHandler;
    
    
    public AllNodesView() {
        allNodesPanel = new FlowPanel();
        initWidget(allNodesPanel);
        
        allNodesText = new Label("All nodes");
        allNodesPanel.add(allNodesText);
        
        allNodesTable = new FlowPanel();
        allNodesGrid = new Grid(1, 5);
        allNodesGrid.setText(0, 0, ID);
        allNodesGrid.setText(0, 1, PARENT_ID);
        allNodesGrid.setText(0, 2, NAME);
        allNodesGrid.setText(0, 3, IP);
        allNodesGrid.setText(0, 4, PORT);
        allNodesTable.add(allNodesGrid);
        allNodesPanel.add(allNodesTable);
        
        allNodesGrid.setStyleName("allNodesGrid");
        allNodesTable.setStyleName("allNodesPanel");
        
        refreshButton = new Button("Refresh");
        allNodesPanel.add(refreshButton);
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                actionHandler.onRefreshButtonClick(event);
            }
        });
    }
    
    
    @Override
    public void setDisplayActionHandler(AllNodesDisplayActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    @Override
    public void setData(List<Node> data) {
        allNodesGrid.resize(data.size() + 1, 5);
        for(int i = 0; i < data.size(); i++) {
            allNodesGrid.setText(i + 1, 0, String.valueOf(data.get(i).getId()));
            if (data.get(i).getParentId() != -1) 
                allNodesGrid.setText(i + 1, 1, String.valueOf(data.get(i).getParentId()));
            allNodesGrid.setText(i + 1, 2, data.get(i).getName());
            allNodesGrid.setText(i + 1, 3, data.get(i).getIp());
            allNodesGrid.setText(i + 1, 4, data.get(i).getPort());
        }
    }

}
