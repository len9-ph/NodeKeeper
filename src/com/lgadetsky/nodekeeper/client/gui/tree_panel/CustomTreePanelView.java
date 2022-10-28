package com.lgadetsky.nodekeeper.client.gui.tree_panel;

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
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree.TreeRow;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree.TreeTable;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class CustomTreePanelView extends Composite implements TreePanelDisplay {

    private TreeTable table;
    private HashMap<TreeRow, Node> treeRowToNodeMap;
    private ScrollPanel panel;

    private Integer indexOfSelected;
    private TreeRow selectedRow;

    private TreePanelActionHandler handler;

    public CustomTreePanelView() {
        table = new TreeTable();
        treeRowToNodeMap = new HashMap<TreeRow, Node>();

        panel = new ScrollPanel();
        panel.setStyleName(StylesNames.CUSTOM_TREE);
        panel.add(table);
        initWidget(panel);

        //        table.addHandler(new ClickHandler() {
        //            
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                // TODO Auto-generated method stub
        //                
        //            }
        //        });
        table.getTreeTable().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (selectedRow != null)
                    selectedRow.removeSelected();

                Cell cell = table.getTreeTable().getCellForEvent(event);
                indexOfSelected = cell.getRowIndex();

                selectedRow = (TreeRow) table.getTreeTable().getWidget(indexOfSelected, 0);
                selectedRow.setSelected();

                handler.onSelect(treeRowToNodeMap.get(selectedRow));
            }
        });

    }

    @Override
    public void setSelectedItem(Node node) {
        Set<Map.Entry<TreeRow, Node>> entrySet = treeRowToNodeMap.entrySet();

        for (Map.Entry<TreeRow, Node> pair : entrySet) {
            if (node.equals(pair.getValue())) {
                if (selectedRow != null)
                    selectedRow.removeSelected();
                selectedRow = pair.getKey();
                selectedRow.setSelected();
                //                if (node.getParentId() != -1)
                //                    indexOfSelected = table.getIndexOfRow(selectedRow);

                // TODO verticalScroll position on selected element
                //                panel.setVerticalScrollPosition(selectedRow.getAbsoluteTop());
            }
        }

    }

    @Override
    public void setTreePanelActionHandler(TreePanelActionHandler handler) {
        this.handler = handler;
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
        setSelectedItem(newNode);
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
                // Find root and put this in it
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
    public void onNameBoxChange(String name) {
        selectedRow.setName(name);
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

}
