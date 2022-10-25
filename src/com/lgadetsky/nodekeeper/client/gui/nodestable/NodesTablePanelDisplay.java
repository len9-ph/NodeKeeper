package com.lgadetsky.nodekeeper.client.gui.nodestable;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface NodesTablePanelDisplay {
    interface NodesTablePanelActionHandler {
        void onRefreshClick();
    }
    
    void setNodesTablePanelHandler(NodesTablePanelActionHandler handler);
    void setData(List<Node> nodes);
    Widget asWidget();
}
