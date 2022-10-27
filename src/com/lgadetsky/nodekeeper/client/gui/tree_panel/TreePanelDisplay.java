package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface TreePanelDisplay {
    interface TreePanelActionHandler {
        void onMessage(String message);

        void onSelect(Node node);
    }

    void setSelectedItem(Node node);

    void setTreePanelActionHandler(TreePanelActionHandler handler);

    void updateTree(Node newNode);

    void buildTree(List<Node> nodes);

    void onNameBoxChange(String name);

    void onDelete();

    Widget asWidget();
}
