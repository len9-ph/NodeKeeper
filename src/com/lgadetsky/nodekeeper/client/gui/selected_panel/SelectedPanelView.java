package com.lgadetsky.nodekeeper.client.gui.selected_panel;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class SelectedPanelView extends Composite implements SelectedPanelDisplay {
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

    private SelectedPanelActionHandler handler;

    public SelectedPanelView() {
        FlowPanel selectedPanel = new FlowPanel();
        initWidget(selectedPanel);
        
        selectedText = new Label(StringConstants.SELECTED);
        selectedPanel.add(selectedText);
        selectedTable = new FlowPanel();
        selectedGrid = new Grid(5, 2);
        selectedTable.add(selectedGrid);
        selectedPanel.add(selectedTable);
        selectedGrid.setStyleName(StylesNames.SELECTED_GRID);
        selectedTable.setStyleName(StylesNames.SELECTED_NODE_TABLE);
        selectedPanel.setStyleName(StylesNames.SELECTED_NODE_PANEL);

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
        
        nameBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                handler.onBoxChange(StringConstants.NAME, nameBox.getText());
            }
        });

        ipBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                handler.onBoxChange(StringConstants.IP, ipBox.getText());
            }
        });

        portBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                handler.onBoxChange(StringConstants.PORT, portBox.getText());
            }
        });
    }

    @Override
    public void setNode(Node node) {
        idLabel.setText(node.getId().toString());
        nameLabel.setText(node.getName());
        ipLabel.setText(node.getIp());
        portLabel.setText(node.getPort());

        if (node.getParentId() > -1)
            parentLabel.setText(node.getParentId().toString());
        else
            parentLabel.setText("");

        selectedGrid.setWidget(0, 1, idLabel);
        selectedGrid.setWidget(1, 1, parentLabel);
        selectedGrid.setWidget(2, 1, nameLabel);
        selectedGrid.setWidget(3, 1, ipLabel);
        selectedGrid.setWidget(4, 1, portLabel);
    }

    @Override
    public void setSelectedPanelActionHandler(SelectedPanelActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setEditState() {
        if (!idLabel.getText().isEmpty()) {
            nameBox.setText(nameLabel.getText());
            ipBox.setText(ipLabel.getText());
            portBox.setText(portLabel.getText());
            selectedGrid.setWidget(2, 1, nameBox);
            selectedGrid.setWidget(3, 1, ipBox);
            selectedGrid.setWidget(4, 1, portBox);
            nameBox.setFocus(true);
        } else 
            handler.onError(StringConstants.ITEM_WAS_NOT_SELECTED);
    }

    @Override
    public void clearPanel() {
        idLabel.setText("");
        parentLabel.setText("");
        nameLabel.setText("");
        ipLabel.setText("");
        portLabel.setText("");

        selectedGrid.setWidget(0, 1, idLabel);
        selectedGrid.setWidget(1, 1, parentLabel);
        selectedGrid.setWidget(2, 1, nameLabel);
        selectedGrid.setWidget(3, 1, ipLabel);
        selectedGrid.setWidget(4, 1, portLabel);
        
    }

}
