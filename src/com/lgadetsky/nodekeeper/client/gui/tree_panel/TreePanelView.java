package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private FlowPanel treePanel;
    private Label treeText;
    private ScrollPanel treeScroll;
    private final Tree mainTree;

    TreePanelActionHandler handler;

    public TreePanelView() {

        treeItemtoNodeMap = new HashMap<TreeItem, Node>();

        mainTree = new Tree();

        treePanel = new FlowPanel();
        treePanel.setStyleName(StylesNames.TREE_PANEL);
        initWidget(treePanel);
        treeScroll = new ScrollPanel();
        treeScroll.setStyleName(StylesNames.TREE_SCROLL);
        treeText = new Label(StringConstants.TREE);
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
        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemtoNodeMap.entrySet();

        for (Map.Entry<TreeItem, Node> pair : entrySet) {
            if (node.equals(pair.getValue()))
                mainTree.setSelectedItem(pair.getKey());
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

        if (newNode.getParentId().equals(-1))
            mainTree.addItem(newTreeItem);
        else
            mainTree.getSelectedItem().addItem(newTreeItem);
    }

    @Override
    public void buildTree(List<Node> nodes) {
        mainTree.clear();

        Collections.sort(nodes, Node.COMPARE_BY_ID);

        treeItemtoNodeMap = new HashMap<TreeItem, Node>();

        for (Node n : nodes) {
            TreeItem item = new TreeItem(new HTML(n.getName()));
            treeItemtoNodeMap.put(item, n);
            if (n.getParentId() == -1) {
                mainTree.addItem(item);
            } else {
                TreeItem parentItem = null;
                for (Node node : nodes) {
                    if (node.getId().equals(n.getParentId())) {
                        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemtoNodeMap.entrySet();

                        for (Map.Entry<TreeItem, Node> pair : entrySet) {
                            if (node.equals(pair.getValue()))
                                parentItem = pair.getKey();
                        }
                    }
                }
                parentItem.addItem(item);
            }
        }
    }

    @Override
    public void onNameBoxChange(String name) {

    }
}
