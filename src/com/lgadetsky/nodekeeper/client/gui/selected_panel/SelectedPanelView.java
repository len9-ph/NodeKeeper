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
        selectedGrid.setText(0, 1, node.getId().toString());
        if (node.getParentId() != null)
            selectedGrid.setText(1, 1, node.getParentId().toString());
        else 
            selectedGrid.setText(1, 1, StringConstants.EMPTY_STRING);
        selectedGrid.setText(2, 1, node.getName());
        selectedGrid.setText(3, 1, node.getIp());
        selectedGrid.setText(4, 1, node.getPort());
    }

    @Override
    public void setSelectedPanelActionHandler(SelectedPanelActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setEditState() {
        if (!selectedGrid.getText(0, 1).isEmpty()) {
            nameBox.setText(StringConstants.EMPTY_STRING);
            if (selectedGrid.getText(2, 1).equals(StringConstants.NEW_ROOT)) 
                nameBox.getElement().setPropertyString(StylesNames.PLACEHOLDER, StringConstants.NEW_ROOT);
            else if (selectedGrid.getText(2, 1).equals(StringConstants.NEW_CHILD))
                nameBox.getElement().setPropertyString(StylesNames.PLACEHOLDER, StringConstants.NEW_CHILD);
            else 
                nameBox.setText(selectedGrid.getText(2, 1));
            ipBox.setText(selectedGrid.getText(3, 1));
            portBox.setText(selectedGrid.getText(4, 1));
            selectedGrid.setWidget(2, 1, nameBox);
            selectedGrid.setWidget(3, 1, ipBox);
            selectedGrid.setWidget(4, 1, portBox);
            nameBox.setFocus(true);
        } else 
            handler.onError(StringConstants.ITEM_WAS_NOT_SELECTED);
    }

    @Override
    public void clearPanel() {
        selectedGrid.setText(0, 1, StringConstants.EMPTY_STRING);
        selectedGrid.setText(1, 1, StringConstants.EMPTY_STRING);
        selectedGrid.setText(2, 1, StringConstants.EMPTY_STRING);
        selectedGrid.setText(3, 1, StringConstants.EMPTY_STRING);
        selectedGrid.setText(4, 1, StringConstants.EMPTY_STRING);
        
    }

}
