package com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.lgadetsky.nodekeeper.client.util.NumberConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class TreeTable extends Composite {
    private FlexTable treeTable;

    private Integer indexOfSelected;
    private TreeRow selectedRow;

    private final ClickHandler handler;

    public TreeTable(ClickHandler handler) {
        treeTable = new FlexTable();
        treeTable.setStyleName(StylesNames.TREE_TABLE);
        initWidget(treeTable);
        this.handler = handler;

        treeTable.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (selectedRow != null) {
                    selectedRow.removeSelected();
                }

                Cell cell = treeTable.getCellForEvent(event);
                indexOfSelected = cell.getRowIndex();

                selectedRow = (TreeRow) treeTable.getWidget(indexOfSelected, 0);
                selectedRow.setSelected();

                TreeTable.this.handler.onClick(event);
            }
        });
    }

    public TreeRow getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(TreeRow selectedRow) {
        if (this.selectedRow != null) {
            this.selectedRow.removeSelected();
        }
        this.selectedRow = selectedRow;
        this.selectedRow.setSelected();
        if (selectedRow.getParentRow() != null) {
            showFromTop(selectedRow.getParentRow());
        }
        this.indexOfSelected = getIndexOfRow(selectedRow);
    }

    public Integer getIndexOfSelectedRow() {
        return indexOfSelected;
    }

    public void addRootRow(TreeRow row) {
        treeTable.setWidget(treeTable.getRowCount(), 0, row);
    }

    public void addChildRow(TreeRow row, Integer index) {
        row.getElement().getStyle().setPaddingLeft(row.getLevel() * NumberConstants.PADDING_VALUE, Unit.PX);
        TreeRow parentRow = (TreeRow) treeTable.getWidget(index, 0);
        parentRow.pressButton();
        row.setParentRow(parentRow);
        if (index + parentRow.countChilds() > treeTable.getRowCount()) {
            treeTable.setWidget(treeTable.getRowCount(), 0, row);
        }
        else {
            treeTable.insertRow(index + parentRow.countChilds());
            treeTable.setWidget(index + parentRow.countChilds(), 0, row);
        }
    }

    public void initialize(List<TreeRow> rootElements) {
        for (TreeRow row : rootElements) {
            initializeUtil(row);
        }
    }

    public FlexTable getTreeTable() {
        return treeTable;
    }

    private void initializeUtil(TreeRow row) {
        row.getElement().getStyle().setPaddingLeft(row.getLevel() * NumberConstants.PADDING_VALUE, Unit.PX);

        treeTable.setWidget(treeTable.getRowCount(), 0, row);
        if (row.isParent()) {
            for (TreeRow child : row.getChilds()) {
                child.addStyleName(StylesNames.HIDE);
                child.setParentRow(row);
                initializeUtil(child);
            }
        }
    }

    public void delete() {
        if (selectedRow.isParent()) {
            treeTable.removeRow(indexOfSelected);
            for (int i = 0; i < selectedRow.countChilds(); i++) {
                treeTable.removeRow(indexOfSelected);
            }
        } else {
            treeTable.removeRow(indexOfSelected);
        }
    }

    public void clear() {
        treeTable.removeAllRows();
    }
    
    private void showFromTop(TreeRow parent) {
        if (parent.getParentRow() != null) {
            showFromTop(parent.getParentRow());
        }
        parent.pressButton();
    }
    
    private Integer getIndexOfRow(TreeRow row) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            if (treeTable.getWidget(i, 0).equals(row)) {
                return i;
            }
        }
        return null;
    }
}
