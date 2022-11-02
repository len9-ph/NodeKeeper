package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;
import com.lgadetsky.nodekeeper.shared.Node;

public class TreePanelView extends Composite implements TreePanelDisplay {
    private HashMap<TreeItem, Node> treeItemtoNodeMap;
    private final Tree mainTree;

    private TreePanelActionHandler handler;

    public TreePanelView() {

        treeItemtoNodeMap = new HashMap<TreeItem, Node>();

        mainTree = new Tree();

        FlowPanel treePanel = new FlowPanel();
        treePanel.setStyleName(StylesNames.TREE_PANEL);
        initWidget(treePanel);
        ScrollPanel treeScroll = new ScrollPanel();
        treeScroll.setStyleName(StylesNames.TREE_SCROLL);
        Label treeText = new Label(StringConstants.TREE);
        treePanel.add(treeText);
        treePanel.add(treeScroll);
        treeScroll.add(mainTree);

        mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                handler.onSelect(treeItemtoNodeMap.get(mainTree.getSelectedItem()));
            }
        });
    }

    @Override
    public void setSelectedItem(Node node) {

        Set<Entry<TreeItem, Node>> entrySet = treeItemtoNodeMap.entrySet();

        for (Entry<TreeItem, Node> pair : entrySet) {
            if (node.equals(pair.getValue())) {
                mainTree.setSelectedItem(null);

                if (pair.getKey().getParentItem() != null) {
                    showFromTop(pair.getKey().getParentItem());
                }

                mainTree.setSelectedItem(pair.getKey(), false);
                break;
            }
        }
    }

    @Override
    public void setTreePanelActionHandler(TreePanelActionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void updateTree(Node newNode) {
        TreeItem newTreeItem = new TreeItem(new HTML(newNode.getName()));
        treeItemtoNodeMap.put(newTreeItem, newNode);

        if (newNode.getParentId() == null) {
            mainTree.addItem(newTreeItem);
        } else {
            mainTree.getSelectedItem().setState(true);
            mainTree.getSelectedItem().addItem(newTreeItem);
        }
        mainTree.setSelectedItem(newTreeItem);
    }

    @Override
    public void buildTree(List<Node> nodes) {
        mainTree.clear();

        treeItemtoNodeMap = new HashMap<TreeItem, Node>();

        for (Node n : nodes) {
            TreeItem item = new TreeItem(new HTML(n.getName()));
            treeItemtoNodeMap.put(item, n);
            if (n.getParentId() == null) {
                mainTree.addItem(item);
            } else {
                TreeItem parentItem = null;
                for (Node node : nodes) {
                    if (n.getParentId() != null && node.getId().equals(n.getParentId())) {
                        Set<Entry<TreeItem, Node>> entrySet = treeItemtoNodeMap.entrySet();

                        for (Entry<TreeItem, Node> pair : entrySet) {
                            if (node.equals(pair.getValue())) {
                                parentItem = pair.getKey();
                            }
                        }
                    }
                }
                parentItem.addItem(item);
            }
        }
    }

    @Override
    public void onNameBoxChange(String name) {
        if (!name.isEmpty()) {
            mainTree.getSelectedItem().setHTML(name);
        } else {
            mainTree.getSelectedItem().setHTML(StringConstants.EMPTY_ITEM);
        }
    }

    @Override
    public void onDelete() {
        if (treeItemtoNodeMap.get(mainTree.getSelectedItem()).getParentId() == null) {
            mainTree.removeItem(mainTree.getSelectedItem());
        } else {
            mainTree.getSelectedItem().getParentItem().removeItem(mainTree.getSelectedItem());
        }
    }

    private void showFromTop(TreeItem parent) {
        if (parent.getParentItem() != null) {
            showFromTop(parent.getParentItem());
        }
        parent.setState(true);
    }
}
