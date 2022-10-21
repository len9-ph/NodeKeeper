package com.lgadetsky.nodekeeper.client.mytree;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;

public class TreeTable extends Composite {
    private FlexTable treeTable;
    
    public TreeTable() {
        treeTable = new FlexTable();
        treeTable.setStyleName("treeTable");
        
        FlowPanel panel = new FlowPanel();
        initWidget(panel);
        panel.add(treeTable);
    }
    
    // if we insert root row
    public void addRootRow(TreeRow row) {
        treeTable.insertRow(treeTable.getRowCount());
        treeTable.setWidget(treeTable.getRowCount(), 0, row);
    }
    
    // if we insert child row
    public void addChildRow(TreeRow row, Integer index) {
        row.getElement().getStyle().setPaddingLeft(row.getLevel() * 15, Unit.PX);
        treeTable.insertRow(index + 1);
        treeTable.setWidget(index + 1, 0, row);
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
        row.getElement().getStyle().setPaddingLeft(row.getLevel() * 15, Unit.PX);
        
        treeTable.setWidget(treeTable.getRowCount(), 0, row);
        if (row.isParent()) {
            for (TreeRow child : row.getChilds()) {
                child.addStyleName("hide");
                initializeUtil(child);
            }
        }
    }
    
    public void remove(Integer indexOfSelected ) {
        treeTable.removeRow(indexOfSelected);
    }
    
    public void clear() {
        treeTable.removeAllRows();
    }
}
