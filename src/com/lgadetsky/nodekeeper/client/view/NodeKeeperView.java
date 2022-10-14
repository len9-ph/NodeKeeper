package com.lgadetsky.nodekeeper.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class NodeKeeperView extends Composite implements NodeKeeperDisplay{
    //private DecoratedPopupPanel simplePopup;
    public NodeKeeperView() {
        FlowPanel mainpanel = new FlowPanel();
        initWidget(mainpanel);
    }
}
