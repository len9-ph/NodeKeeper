package com.lgadetsky.nodekeeper.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface CustomTreePanelDisplay {
    interface CustomTreePanelActionHanlder {
        void onSelect(Node selectedNode);
    }
    void setCustomTreePanelActionHanlder(CustomTreePanelActionHanlder handler);
    void buildTree(List<Node> nodes);
    void updateTree(Node newNode);
    Widget asWidget();
}