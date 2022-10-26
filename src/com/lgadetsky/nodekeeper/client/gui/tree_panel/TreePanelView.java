package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.shared.Node;

public class TreePanelView extends Composite implements TreePanelDisplay {
    private HashMap<TreeItem, Node> treeItemtoNodeMap;
    private FlowPanel treePanel;
    private Label treeText;
    private ScrollPanel treeScroll;
    private final Tree mainTree;
    
    public TreePanelView() {
        mainTree = new Tree();
        
        mainTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                // fire event set selected item
            }
        });
    }
    
    @Override
    public void setSelectedNode(Node node) {
        
    }

    @Override
    public void setTreePanelActionHandler(TreePanelActionHandler handler) {
        
    }

    @Override
    public void updateTree(Node newNode) {
        
    }

    @Override
    public void buildTree(List<Node> nodes) {
        
    }

    @Override
    public void onNameBoxChange(String name) {
        
    }

}
