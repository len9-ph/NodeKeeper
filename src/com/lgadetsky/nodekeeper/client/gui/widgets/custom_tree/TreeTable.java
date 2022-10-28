package com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.lgadetsky.nodekeeper.client.util.NumberConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class TreeTable extends Composite {
    private FlexTable treeTable;

        private Integer indexOfSelected;
        private TreeRow selectedRow;

    public TreeTable() {
        treeTable = new FlexTable();
        treeTable.setStyleName(StylesNames.TREE_TABLE);
        initWidget(treeTable);

        //        treeTable.addClickHandler(new ClickHandler() {
        //
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                if (selectedRow != null)
        //                    selectedRow.removeSelected();
        //
        //                Cell cell = treeTable.getCellForEvent(event);
        //                indexOfSelected = cell.getRowIndex();
        //
        //                selectedRow = (TreeRow) treeTable.getWidget(indexOfSelected, 0);
        //                selectedRow.setSelected();
        //
        //                handler.onClick(event);
        //            }
        //        });
        //        this.treeTable.addClickHandler(new ClickHandler() {
        //
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                if (selectedRow != null)
        //                    selectedRow.removeSelected();
        //
        //                Cell cell = treeTable.getCellForEvent(event);
        //                indexOfSelected = cell.getRowIndex();
        //
        //                selectedRow = (TreeRow) treeTable.getWidget(indexOfSelected, 0);
        //                selectedRow.setSelected();
        //            }
        //        });
    }

//    public void addHandler(ClickHandler handler) {
//        this.treeTable.addClickHandler(handler);
//    }
    
    //    public TreeRow getSelectedRow() {
    //        return selectedRow;
    //    }
    //
    //    public Integer getIndexOfSelectedRow() {
    //        return indexOfSelected;
    //    }

    public void addRootRow(TreeRow row) {
        treeTable.insertRow(treeTable.getRowCount());
        treeTable.setWidget(treeTable.getRowCount(), 0, row);
    }

    public void addChildRow(TreeRow row, Integer index) {
        row.getElement().getStyle().setPaddingLeft(row.getLevel() * NumberConstants.PADDING_VALUE, Unit.PX);
        TreeRow parentRow = (TreeRow) treeTable.getWidget(index, 0);
        parentRow.pressButton();
        treeTable.insertRow(index + parentRow.countChilds());
        treeTable.setWidget(index + parentRow.countChilds(), 0, row);
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
        row.getElement().getStyle().setPaddingLeft(row.getLevel() * NumberConstants.PADDING_VALUE,
                Unit.PX);

        treeTable.setWidget(treeTable.getRowCount(), 0, row);
        if (row.isParent()) {
            for (TreeRow child : row.getChilds()) {
                child.addStyleName(StylesNames.HIDE);
                initializeUtil(child);
            }
        }
    }

    public void remove(Integer indexOfSelected) {
        treeTable.removeRow(indexOfSelected);
    }

    public void clear() {
        treeTable.removeAllRows();
    }

    public Integer getIndexOfRow(TreeRow row) {
        for (int i = 0; i < treeTable.getRowCount(); i++) {
            if (treeTable.getWidget(i, 0).equals(row))
                return i;
        }
        return null;
    }
}
