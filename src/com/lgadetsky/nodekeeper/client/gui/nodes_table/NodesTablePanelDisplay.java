package com.lgadetsky.nodekeeper.client.gui.nodes_table;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface NodesTablePanelDisplay {
    interface NodesTablePanelActionHandler {
        void onRefreshClick();
    }

    void setNodesTablePanelHandler(NodesTablePanelActionHandler handler);

    void refreshData(List<Node> nodes);

    Widget asWidget();
}
