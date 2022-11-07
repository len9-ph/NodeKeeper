package com.lgadetsky.nodekeeper.client.gui.manager_panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class ManagerPanelView extends Composite implements ManagerPanelDisplay {
    private FlowPanel treeContainer;
    private FlowPanel customTreeContainer;
    
//    private Button addRootButton;
//    private Button addChildButton;
//    private Button editButton;
//    private Button deleteButton;
//
//    private Label selectedNodeLabel;
    private Label selectedNodeTextLabel;

    private ManagerPanelActionHandler handler;

    public ManagerPanelView() {
        FlowPanel managerPanel = new FlowPanel();
        treeContainer = new FlowPanel();
        
        customTreeContainer = new FlowPanel();
        FlowPanel buttonPanel = new FlowPanel();
        treeContainer.setStyleName(StylesNames.MANAGER_CONTAINER);
        customTreeContainer.setStyleName(StylesNames.MANAGER_CONTAINER);
        
        managerPanel.add(treeContainer);
        managerPanel.add(customTreeContainer);
        managerPanel.add(buttonPanel);
        
        initWidget(managerPanel);

        Button addRootButton = new Button(StringConstants.ADD_ROOT);
        Button addChildButton = new Button(StringConstants.ADD_CHILD);
        Button editButton = new Button(StringConstants.EDIT);
        Button deleteButton = new Button(StringConstants.DELETE);

        Label selectedNodeLabel = new Label(StringConstants.SELECTED_NODE);
        selectedNodeTextLabel = new Label(StringConstants.EMPTY_STRING);
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
        if (id != 0) {
            selectedNodeTextLabel.setText(id.toString());
        }
        else {
            selectedNodeTextLabel.setText(StringConstants.EMPTY_STRING);
        }
    }

    @Override
    public HasWidgets getTreeContainer() {
        return this.treeContainer;
    }

    @Override
    public HasWidgets getCustomTreeContainer() {
        return this.customTreeContainer;
    }
}
