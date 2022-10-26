package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface TreePanelDisplay {
    interface TreePanelActionHandler {
        void onMessage(String message);

        void onBoxChange(Node node, String field, String value);

        void onAddRootClick();

        void onAddChildClick(Node parentNode);

        void onDeleteClick(Node node);
    }

    void setSelectedNode(Node node);

    void setTreePanelActionHandler(TreePanelActionHandler handler);

    void updateTree(Node newNode);

    void buildTree(List<Node> nodes);

    void onNameBoxChange(String name);

    Widget asWidget();
}
