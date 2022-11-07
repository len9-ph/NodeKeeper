package com.lgadetsky.nodekeeper.client.gui.nodes_table;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.lgadetsky.nodekeeper.client.util.NumberConstants;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeTablePanelView extends Composite implements NodesTablePanelDisplay {
    private Grid allNodesGrid;

    private NodesTablePanelActionHandler handler;

    public NodeTablePanelView() {
        FlowPanel allNodesPanel = new FlowPanel();
        initWidget(allNodesPanel);
        allNodesPanel.addStyleName(StylesNames.LOWER_PANEL);

        Label allNodesText = new Label(StringConstants.ALL_NODES);
        allNodesPanel.add(allNodesText);

        // Button assembly
        Button refreshButton = new Button(StringConstants.REFRESH);
        allNodesPanel.add(refreshButton);
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onRefreshClick();
            }
        });

        // Table assembly
        FlowPanel allNodesTable = new FlowPanel();
        allNodesGrid = new Grid(NumberConstants.HEADER_ROW, NumberConstants.FIVE_COLUMN_COUNT);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.ID_COLUMN, StringConstants.ID);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.PARENT_ID_COLUMN, StringConstants.PARENT_ID);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.NAME_COLUMN, StringConstants.NAME);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.IP_COLUMN, StringConstants.IP);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.PORT_COLUMN, StringConstants.PORT);
        allNodesGrid.setStyleName(StylesNames.ALL_NODES_GRID);
        allNodesTable.setStyleName(StylesNames.ALL_NODES_PANEL);

        allNodesTable.add(allNodesGrid);
        allNodesPanel.add(allNodesTable);
    }

    @Override
    public void setNodesTablePanelHandler(NodesTablePanelActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void refreshData(List<Node> nodes) {
        allNodesGrid.clear(true);
        allNodesGrid.resize(nodes.size() + NumberConstants.HEADER_ROW, NumberConstants.FIVE_COLUMN_COUNT);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.ID_COLUMN, StringConstants.ID);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.PARENT_ID_COLUMN, StringConstants.PARENT_ID);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.NAME_COLUMN, StringConstants.NAME);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.IP_COLUMN, StringConstants.IP);
        allNodesGrid.setText(NumberConstants.FIRST_ROW, NumberConstants.PORT_COLUMN, StringConstants.PORT);
        
        
        for (int i = 0; i < nodes.size(); i++) {
            allNodesGrid.setText(i + NumberConstants.HEADER_ROW, NumberConstants.ID_COLUMN, nodes.get(i).getId().toString());
            if (nodes.get(i).getParentId() != null) {
                allNodesGrid.setText(i + NumberConstants.HEADER_ROW, NumberConstants.PARENT_ID_COLUMN, nodes.get(i).getParentId().toString());
            }
            allNodesGrid.setText(i + NumberConstants.HEADER_ROW, NumberConstants.NAME_COLUMN, nodes.get(i).getName());
            allNodesGrid.setText(i + NumberConstants.HEADER_ROW, NumberConstants.IP_COLUMN, nodes.get(i).getIp());
            allNodesGrid.setText(i + NumberConstants.HEADER_ROW, NumberConstants.PORT_COLUMN, nodes.get(i).getPort());
        }
    }
}
