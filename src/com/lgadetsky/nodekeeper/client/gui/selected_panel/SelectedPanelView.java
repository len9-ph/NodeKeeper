package com.lgadetsky.nodekeeper.client.gui.selected_panel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class SelectedPanelView extends Composite implements SelectedPanelDisplay{
    
    private FlowPanel selectedPanel;
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

}
