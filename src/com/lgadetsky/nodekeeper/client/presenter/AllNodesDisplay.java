package com.lgadetsky.nodekeeper.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface AllNodesDisplay {
    interface AllNodesDisplayActionHandler {
        void onRefreshButtonClick(ClickEvent event);
    }
    
    void setDisplayActionHandler(AllNodesDisplayActionHandler actionHandler);
    void setData(List<Node> data);
    
    Widget asWidget();
}
