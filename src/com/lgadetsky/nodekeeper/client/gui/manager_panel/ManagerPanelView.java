package com.lgadetsky.nodekeeper.client.gui.manager_panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class ManagerPanelView extends Composite implements ManagerPanelDisplay {
    private Button addRootButton;
    private Button addChildButton;
    private Button editButton;
    private Button deleteButton;

    private Label selectedNodeLabel;
    private Label selectedNodeTextLabel;

    private ManagerPanelActionHandler handler;

    public ManagerPanelView() {
        FlowPanel buttonPanel = new FlowPanel();
        initWidget(buttonPanel);

        addRootButton = new Button(StringConstants.ADD_ROOT);
        addChildButton = new Button(StringConstants.ADD_CHILD);
        editButton = new Button(StringConstants.EDIT);
        deleteButton = new Button(StringConstants.DELETE);

        selectedNodeLabel = new Label(StringConstants.SELECTED);
        selectedNodeTextLabel = new Label("");
        buttonPanel.setStyleName(StylesNames.BUTTON_PANEL);
        selectedNodeLabel.setStyleName(StylesNames.SELECTED_LABEL);
        selectedNodeTextLabel.setStyleName(StylesNames.SELECTED_LABEL);

        buttonPanel.add(addRootButton);
        buttonPanel.add(addChildButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectedNodeLabel);
        buttonPanel.add(selectedNodeTextLabel);

        addRootButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onAddRootClick();
            }
        });

        addChildButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onAddChildClick();
            }
        });

        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onEditClick();
            }
        });

        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.onDeleteClick();
            }
        });
    }

    @Override
    public void setManagerPanelActionHandler(ManagerPanelActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setSelectedId(Integer id) {
        selectedNodeTextLabel.setText(id.toString());
    }

}
