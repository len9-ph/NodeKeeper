package com.lgadetsky.nodekeeper.client.gui.nodes_table;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeTablePanelView extends Composite implements NodesTablePanelDisplay {
    private Label allNodesText;
    private FlowPanel allNodesTable;
    private Button refreshButton;
    private Grid allNodesGrid;

    private NodesTablePanelActionHandler handler;

    public NodeTablePanelView() {
        FlowPanel allNodesPanel = new FlowPanel();
        initWidget(allNodesPanel);
        allNodesPanel.addStyleName(StylesNames.LOWER_PANEL);

        allNodesText = new Label(StringConstants.ALL_NODES);
        allNodesPanel.add(allNodesText);

        // Button assembly
        refreshButton = new Button(StringConstants.REFRESH);
        allNodesPanel.add(refreshButton);
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onRefreshClick();
            }
        });

        // Table assembly
        allNodesTable = new FlowPanel();
        allNodesGrid = new Grid(1, 5);
        allNodesGrid.setText(0, 0, StringConstants.ID);
        allNodesGrid.setText(0, 1, StringConstants.PARENT_ID);
        allNodesGrid.setText(0, 2, StringConstants.NAME);
        allNodesGrid.setText(0, 3, StringConstants.IP);
        allNodesGrid.setText(0, 4, StringConstants.PORT);
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
    public void setData(List<Node> nodes) {
        allNodesGrid.resize(nodes.size() + 1, 5);

        for (int i = 0; i < nodes.size(); i++) {
            allNodesGrid.setText(i + 1, 0, nodes.get(i).getId().toString());
            if (nodes.get(i).getParentId() != -1)
                allNodesGrid.setText(i + 1, 1, nodes.get(i).getParentId().toString());
            allNodesGrid.setText(i + 1, 2, nodes.get(i).getName());
            allNodesGrid.setText(i + 1, 3, nodes.get(i).getIp());
            allNodesGrid.setText(i + 1, 4, nodes.get(i).getPort());
        }
    }
}
