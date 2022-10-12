package com.lgadetsky.nodekeeper.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.shared.Node;

public interface EditNodesDisplay {
    interface EditNodesDisplayActionHadler {
        void onTreeItemSelection(SelectionEvent<TreeItem> event);
        void onAddRootButton(ClickEvent event);
        void onAddChildButton(ClickEvent event);
        void onEditButton(ClickEvent event);
        void onDeleteButton(ClickEvent event);
        void nameBoxPrinting(KeyUpEvent event);
        void ipBoxPrinting(KeyUpEvent event);
        void portBoxPrinting(KeyUpEvent event);
    }
    
    void setDisplayActionhandler(EditNodesDisplayActionHadler actionHadler);
    void setData(List<Node> data);
    void setSelectedItem(Node selectedNode);
    void setTreeItem(TreeItem item);
    void setTreeItem(TreeItem parent, TreeItem item);
    void editTreeItem(TreeItem item);
}
