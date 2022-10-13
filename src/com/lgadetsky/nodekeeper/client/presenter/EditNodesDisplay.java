package com.lgadetsky.nodekeeper.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.shared.Node;

public interface EditNodesDisplay {
    interface EditNodesDisplayActionHadler {
        void onTreeItemSelection(SelectionEvent<TreeItem> event);
        void onAddRootButton(ClickEvent event, TreeItem item);
        void onAddChildButton(ClickEvent event,TreeItem parentItem, TreeItem item);
        void onEditButton(ClickEvent event);
        void onDeleteButton(ClickEvent event);
        void nameBoxPrinting(KeyUpEvent event, String box);
        void ipBoxPrinting(KeyUpEvent event, String box);
        void portBoxPrinting(KeyUpEvent event, String box);
    }
    
    void setDisplayActionhandler(EditNodesDisplayActionHadler actionHadler);
    void setTree(Tree tree);
    void deleteTreeItem(TreeItem item);
    void deleteTreeItem(TreeItem parent, TreeItem item);
    void setSelectedItem(Node selectedNode);
    void editButtonPress(Node selectedNode);
}
