package com.lgadetsky.nodekeeper.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface TreeEditPanelDisplay {
    interface TreeEditPanelActionHandler{
        void onError(String message);
        void onBoxChange(Node node, String field, String value);
        void onAddRootClick();
        void onAddChildClick(Node parentNode);
        void onDeleteClick(Node node);
    }
    
    void setNode(Node node);
    void setTreeEditPanelActionHanlder(TreeEditPanelActionHandler handler);
    void updateTree(Node newNode);
    void buildTree(List<Node> nodes);
    Widget asWidget();
}
