package com.lgadetsky.nodekeeper.client.mytree;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;

public class TreeTable extends Composite {
    private FlexTable treeTable;
    
//    private TreeRow selectedRow;
    
    public TreeTable() {
        treeTable = new FlexTable();
        treeTable.setStyleName("treeTable");
        
//        treeTable.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                Cell cell = treeTable.getCellForEvent(event);
//                selectedRow = (TreeRow) treeTable.getWidget(cell.getRowIndex(), 0);
//            }
//        });
        
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
//    public void addChildRow(TreeRow row) {
//        selectedRow.addChild(row);
//        for (int i = 0; i < treeTable.getRowCount(); i++) {
//            TreeRow curRow = (TreeRow) treeTable.getWidget(i, 0);
//            if (curRow.equals(selectedRow)) {
//                
//                treeTable.insertCell(i+1, 0);
//                treeTable.setWidget(i+1, 0, row);
//            }
//        }
//    }
    
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
    
//    public TreeRow getSelectedRow() {
//        return selectedRow;
//    }
    
    public void clear() {
        treeTable.clear();
    }
}
