package com.lgadetsky.nodekeeper.client.gui.selected_panel;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.lgadetsky.nodekeeper.client.util.NumberConstants;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class SelectedPanelView extends Composite implements SelectedPanelDisplay {
    private Grid selectedGrid;

    private TextBox nameBox;
    private TextBox ipBox;
    private TextBox portBox;

    private SelectedPanelActionHandler handler;

    public SelectedPanelView() {
        FlowPanel selectedPanel = new FlowPanel();
        initWidget(selectedPanel);

        Label selectedText = new Label(StringConstants.SELECTED);
        selectedPanel.add(selectedText);
        FlowPanel selectedTable = new FlowPanel();
        selectedGrid = new Grid(NumberConstants.FIVE_ROW_COUNT, NumberConstants.TWO_COLUMN_COUNT);
        selectedTable.add(selectedGrid);
        selectedPanel.add(selectedTable);
        selectedGrid.setStyleName(StylesNames.SELECTED_GRID);
        selectedTable.setStyleName(StylesNames.SELECTED_NODE_TABLE);
        selectedPanel.setStyleName(StylesNames.SELECTED_NODE_PANEL);

        selectedGrid.setText(NumberConstants.ID_ROW, NumberConstants.FIRST_COLUMN, StringConstants.ID);
        selectedGrid.setText(NumberConstants.PARENT_ID_ROW, NumberConstants.FIRST_COLUMN, StringConstants.PARENT_ID);
        selectedGrid.setText(NumberConstants.NAME_ROW, NumberConstants.FIRST_COLUMN, StringConstants.NAME);
        selectedGrid.setText(NumberConstants.IP_ROW, NumberConstants.FIRST_COLUMN, StringConstants.IP);
        selectedGrid.setText(NumberConstants.PORT_ROW, NumberConstants.FIRST_COLUMN, StringConstants.PORT);

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
        selectedGrid.setText(NumberConstants.ID_ROW, NumberConstants.PARENT_ID_COLUMN, node.getId().toString());
        if (node.getParentId() != null) {
            selectedGrid.setText(NumberConstants.PARENT_ID_ROW, NumberConstants.PARENT_ID_COLUMN, node.getParentId().toString());
        } else {
            selectedGrid.setText(NumberConstants.PARENT_ID_ROW, NumberConstants.PARENT_ID_COLUMN, StringConstants.EMPTY_STRING);
        }
        selectedGrid.setText(NumberConstants.NAME_ROW, NumberConstants.PARENT_ID_COLUMN, node.getName());
        selectedGrid.setText(NumberConstants.IP_ROW, NumberConstants.PARENT_ID_COLUMN, node.getIp());
        selectedGrid.setText(NumberConstants.PORT_ROW, NumberConstants.PARENT_ID_COLUMN, node.getPort());
    }

    @Override
    public void setSelectedPanelActionHandler(SelectedPanelActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setEditState() {
        if (!selectedGrid.getText(NumberConstants.ID_ROW, NumberConstants.SECOND_COLUMN).equals(" ")) {
            nameBox.setText(StringConstants.EMPTY_STRING);
            if (selectedGrid.getText(NumberConstants.NAME_ROW, NumberConstants.SECOND_COLUMN).equals(StringConstants.NEW_ROOT)) {
                nameBox.getElement().setPropertyString(StylesNames.PLACEHOLDER, StringConstants.NEW_ROOT);
            } else if (selectedGrid.getText(NumberConstants.NAME_ROW, NumberConstants.SECOND_COLUMN).equals(StringConstants.NEW_CHILD)) {
                nameBox.getElement().setPropertyString(StylesNames.PLACEHOLDER, StringConstants.NEW_CHILD);
            } else {
                nameBox.getElement().setPropertyString(StylesNames.PLACEHOLDER, StringConstants.EMPTY_STRING);
                nameBox.setText(selectedGrid.getText(NumberConstants.NAME_ROW, NumberConstants.SECOND_COLUMN));
            }
            ipBox.setText(selectedGrid.getText(NumberConstants.IP_ROW, NumberConstants.SECOND_COLUMN));
            portBox.setText(selectedGrid.getText(NumberConstants.PORT_ROW, NumberConstants.SECOND_COLUMN));
            selectedGrid.setWidget(NumberConstants.NAME_ROW, NumberConstants.SECOND_COLUMN, nameBox);
            selectedGrid.setWidget(NumberConstants.IP_ROW, NumberConstants.SECOND_COLUMN, ipBox);
            selectedGrid.setWidget(NumberConstants.PORT_ROW, NumberConstants.SECOND_COLUMN, portBox);
            nameBox.setFocus(true);
        } else {
            handler.onError(StringConstants.ITEM_WAS_NOT_SELECTED);
        }
    }

    @Override
    public void clearPanel() {
        selectedGrid.setText(NumberConstants.ID_ROW, NumberConstants.SECOND_COLUMN, StringConstants.EMPTY_STRING);
        selectedGrid.setText(NumberConstants.PARENT_ID_ROW, NumberConstants.SECOND_COLUMN, StringConstants.EMPTY_STRING);
        selectedGrid.setText(NumberConstants.NAME_ROW, NumberConstants.SECOND_COLUMN, StringConstants.EMPTY_STRING);
        selectedGrid.setText(NumberConstants.IP_ROW, NumberConstants.SECOND_COLUMN, StringConstants.EMPTY_STRING);
        selectedGrid.setText(NumberConstants.PORT_ROW, NumberConstants.SECOND_COLUMN, StringConstants.EMPTY_STRING);
    }

}
