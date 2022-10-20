package com.lgadetsky.nodekeeper.client.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.lgadetsky.nodekeeper.client.mytree.TreeRow;
import com.lgadetsky.nodekeeper.client.mytree.TreeTable;
import com.lgadetsky.nodekeeper.shared.Node;

public class CustomTreePanelView extends Composite implements CustomTreePanelDisplay{
    
    private TreeTable table;
    private HashMap<TreeRow, Node> treeRowToNodeMap;
    
    private CustomTreePanelActionHanlder handler;
    
    public CustomTreePanelView() {
        table = new TreeTable();
        treeRowToNodeMap = new HashMap<TreeRow, Node>();
        ScrollPanel panel = new ScrollPanel();
        panel.setStyleName("customTree");
        panel.add(table);
        initWidget(panel);
        
        table.getTreeTable().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = table.getTreeTable().getCellForEvent(event);
                TreeRow selectedRow = (TreeRow) table.getTreeTable().getWidget(cell.getRowIndex(), 0);
                handler.onSelect(treeRowToNodeMap.get(selectedRow));
            }
        });
    }
    
    @Override
    public void setCustomTreePanelActionHanlder(CustomTreePanelActionHanlder handler) {
        this.handler = handler;
    }

    @Override
    public void buildTree(List<Node> nodes) {
        table.clear();
        
        List<TreeRow> rootNodes = new LinkedList<TreeRow>();
        for (Node n : nodes) {
            TreeRow newRow = new TreeRow(n.getName());
            treeRowToNodeMap.put(newRow, n);
            
            if (n.getParentId().equals(-1))
                rootNodes.add(newRow);
            else {
                // найти родителя и положить в него
                Set<Map.Entry<TreeRow, Node>> entrySet = treeRowToNodeMap.entrySet();
                for (Map.Entry<TreeRow, Node> pair : entrySet) {
                    if (pair.getValue().getId().equals(n.getParentId())) {
                        newRow.increaseLevel(pair.getKey().getLevel());
                        pair.getKey().addChild(newRow);
                    }
                }
            }
            
        }
//        for (Node n : nodes) {
//            // build communication within treeRow and node
//            TreeRow newRow = new TreeRow(n.getName());
//            treeRowToNodeMap.put(newRow, n);
//            
//            // sort parents and childs 
//            parentToChilds.put(newRow, new LinkedList<TreeRow>());
//            
//            if (n.getParentId() > 0) {
//                Set<Map.Entry<TreeRow, Node>> entrySet = treeRowToNodeMap.entrySet();
//                TreeRow parentRow;
//                // Find parent row
//                for (Map.Entry<TreeRow, Node> pair : entrySet) {
//                    if (n.getParentId().equals(pair.getValue().getParentId())) {
//                        parentRow = pair.getKey();
//                        newRow.increaseLevel(parentRow.getLevel());
//                        parentToChilds.get(parentRow).add(newRow);
//                    }
//                }
//            }
//        }
        
        table.initialize(rootNodes);
    }

    @Override
    public void updateTree(Node newNode) {
        TreeRow newRow = new TreeRow(newNode.getName());
        treeRowToNodeMap.put(newRow, newNode);
        
        if (newNode.getParentId().equals(-1))
            table.addRootRow(newRow);
//        else
//            table.addChildRow(newRow);
    }
    
}
