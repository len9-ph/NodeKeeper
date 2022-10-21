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
    
    private Integer indexOfSelected;
    private TreeRow selectedRow;
    
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
                indexOfSelected = cell.getRowIndex();
                selectedRow = (TreeRow) table.getTreeTable().getWidget(indexOfSelected, 0);
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
        
        table.initialize(rootNodes);
    }

    @Override
    public void updateTree(Node newNode) {
        TreeRow newRow = new TreeRow(newNode.getName());
        treeRowToNodeMap.put(newRow, newNode);
        
        if (newNode.getParentId().equals(-1))
            table.addRootRow(newRow);
        else {
            newRow.increaseLevel(selectedRow.getLevel());
            selectedRow.addChild(newRow);
            table.addChildRow(newRow, indexOfSelected);
        }
    }

    @Override
    public void onDelete() {
        if (selectedRow.isParent()) {
            table.remove(indexOfSelected);
            for (int i = 0; i < selectedRow.countChilds(); i++)
                table.remove(indexOfSelected);
        } else 
            table.remove(indexOfSelected);
    }

    @Override
    public void onBoxChange(String name) {
        selectedRow.setName(name);
    }
    
}
