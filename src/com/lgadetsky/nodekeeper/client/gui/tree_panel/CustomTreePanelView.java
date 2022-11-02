package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree.TreeRow;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_tree.TreeTable;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class CustomTreePanelView extends Composite implements TreePanelDisplay {

    private TreeTable table;
    private HashMap<TreeRow, Node> treeRowToNodeMap;

    private TreePanelActionHandler handler;

    public CustomTreePanelView() {
        table = new TreeTable(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                handler.onSelect(treeRowToNodeMap.get(table.getSelectedRow()));
            }
        });
        treeRowToNodeMap = new HashMap<TreeRow, Node>();

        ScrollPanel panel = new ScrollPanel();
        panel.setStyleName(StylesNames.CUSTOM_TREE);
        panel.add(table);

        FlowPanel mainPanel = new FlowPanel();
        Label customTreeLabel = new Label(StringConstants.CUSTOM_TREE);
        mainPanel.add(customTreeLabel);
        mainPanel.add(panel);
        mainPanel.setStyleName(StylesNames.CUSTOM_TREE_PANEL);
        initWidget(mainPanel);
    }

    @Override
    public void setSelectedItem(Node node) {
        Set<Entry<TreeRow, Node>> entrySet = treeRowToNodeMap.entrySet();

        for (Entry<TreeRow, Node> pair : entrySet) {
            if (node.equals(pair.getValue())) {
                table.setSelectedRow(pair.getKey());
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

        if (newNode.getParentId() == null) {
            table.addRootRow(newRow);
        } else {
            newRow.increaseLevel(table.getSelectedRow().getLevel());
            table.getSelectedRow().addChild(newRow);
            table.addChildRow(newRow, table.getIndexOfSelectedRow());
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

            if (n.getParentId() == null) {
                rootNodes.add(newRow);
            } else {
                Set<Entry<TreeRow, Node>> entrySet = treeRowToNodeMap.entrySet();
                for (Entry<TreeRow, Node> pair : entrySet) {
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
        if (!name.isEmpty()) {
            table.getSelectedRow().setName(name);
        } else {
            table.getSelectedRow().setName(StringConstants.EMPTY_ITEM);
        }
    }

    @Override
    public void onDelete() {
        table.delete();
    }

}
