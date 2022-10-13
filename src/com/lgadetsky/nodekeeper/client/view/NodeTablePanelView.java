package com.lgadetsky.nodekeeper.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeTablePanelView extends Composite implements NodesTablePanelDisplay{
	private final String ID = "id";
    private final String PARENT_ID = "parentId";
    private final String NAME = "name";
    private final String IP = "ip";
    private final String PORT = "port";
	
	private Label allNodesText;
	private FlowPanel allNodesTable;
	private Button refreshButton;
	private Grid allNodesGrid;
	
	private NodesTablePanelActionHandler handler;
	
	public NodeTablePanelView() {
		FlowPanel allNodesPanel = new FlowPanel();
		initWidget(allNodesPanel);
		
		allNodesText = new Label("All nodes");
		allNodesPanel.add(allNodesText);
		
		// Button assembly
		refreshButton = new Button("Refresh");
		allNodesPanel.add(refreshButton);
		refreshButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handler.onRefreshEvent();
			}
		});
		
		// Table assembly
		allNodesTable = new FlowPanel();
		allNodesGrid = new Grid(1, 5);
		allNodesGrid.setText(0, 0, ID);
        allNodesGrid.setText(0, 1, PARENT_ID);
        allNodesGrid.setText(0, 2, NAME);
        allNodesGrid.setText(0, 3, IP);
        allNodesGrid.setText(0, 4, PORT);
        
        allNodesTable.add(allNodesGrid);
        allNodesPanel.add(allNodesTable);	
	}
	
	@Override
	public void setNodesTablePanelHandler(NodesTablePanelActionHandler handler) {
		this.handler = handler;
	}

	@Override
	public void setData(List<Node> nodes) {
		allNodesGrid.resize(nodes.size() + 1, 5);
		
		for(int i = 0; i < nodes.size(); i++) {
            allNodesGrid.setText(i + 1, 0, String.valueOf(nodes.get(i).getId()));
            if (nodes.get(i).getParentId() != -1) 
                allNodesGrid.setText(i + 1, 1, nodes.get(i).getParentId().toString());
            allNodesGrid.setText(i + 1, 2, nodes.get(i).getName());
            allNodesGrid.setText(i + 1, 3, nodes.get(i).getIp());
            allNodesGrid.setText(i + 1, 4, nodes.get(i).getPort());
        }
		
	}

}
