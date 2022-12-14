package com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.lgadetsky.nodekeeper.client.util.NumberConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class TreeRow extends Composite {
    private ToggleButton button;
    private Label treeItemText;
    private Integer level;
    private List<TreeRow> childs;
    private TreeRow parentRow;

    public TreeRow(String name) {
        this.level = 0;
        this.childs = new LinkedList<>();
        button = new ToggleButton();
        button.setStyleName(StylesNames.ROW_TOGGLE_BUTTON);

        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (button.getValue()) {
                    showChilds();
                } else {
                    hideChilds();
                }
            }
        });

        button.addStyleName(StylesNames.HIDDEN);
        treeItemText = new Label(name);
        treeItemText.setStyleName(StylesNames.ROW_TEXT);

        FlowPanel panel = new FlowPanel();
        panel.add(button);
        panel.add(treeItemText);

        initWidget(panel);
    }

    public void setSelected() {
        treeItemText.addStyleName(StylesNames.SELECTED_ITEM);
    }

    public TreeRow getParentRow() {
        return parentRow;
    }

    public void setParentRow(TreeRow parent) {
        this.parentRow = parent;
    }

    public void removeSelected() {
        treeItemText.removeStyleName(StylesNames.SELECTED_ITEM);
    }

    public void setName(String name) {
        this.treeItemText.setText(name);
    }

    public Integer getLevel() {
        return this.level;
    }

    public void increaseLevel(Integer parentLevel) {
        this.level = parentLevel + 1;
        if (NumberConstants.DEFAULT_WIDTH - this.level * NumberConstants.WIDTH_SHIFT > NumberConstants.MIN_ROW_WIDTH) {
            treeItemText.getElement().getStyle().setWidth(NumberConstants.DEFAULT_WIDTH - this.level * NumberConstants.WIDTH_SHIFT, Unit.PX);
        } else {
            treeItemText.getElement().getStyle().setWidth(NumberConstants.MIN_ROW_WIDTH, Unit.PX);
        }
    }

    public void addChild(TreeRow row) {
        button.removeStyleName(StylesNames.HIDDEN);
        childs.add(row);
    }

    public boolean isParent() {
        if (childs.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public Integer countChilds() {
        return countUtil(getChilds());
    }

    private Integer countUtil(List<TreeRow> list) {
        for (TreeRow row : list) {
            if (row.isParent()) {
                return list.size() + countUtil(row.getChilds());
            }
        }
        return list.size();
    }

    public void pressButton() {
        if (button.getValue() == false) {
            button.setValue(true);
            showChilds();
        }
    }

    public List<TreeRow> getChilds() {
        return this.childs;
    }

    private void setButtonHide() {
        button.setValue(false);
    }

    private void hideChilds() {
        for (TreeRow row : childs) {
            if (row.isParent()) {
                row.hideChilds();
            }
            row.hide();
        }
    }

    private void showChilds() {
        for (TreeRow row : childs) {
            row.show();
            if (row.isParent()) {
                row.setButtonHide();
            }
        }
    }

    private void hide() {
        this.setStyleName(StylesNames.HIDE);
    }

    private void show() {
        this.removeStyleName(StylesNames.HIDE);
    }
}
