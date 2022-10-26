package com.lgadetsky.nodekeeper.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.lgadetsky.nodekeeper.client.gui.node_keeper.NodeKeeperPresenter;
import com.lgadetsky.nodekeeper.client.gui.node_keeper.NodeKeeperView;
import com.lgadetsky.nodekeeper.client.util.StringConstants;

public class NodeKeeper implements EntryPoint {
    private static NodeKeeperServiceAsync rpcService = GWT.create(NodeKeeperService.class);
    
    @Override
    public void onModuleLoad() {
        NodeKeeperView display = new NodeKeeperView();
        NodeKeeperPresenter keeperPresenter = new NodeKeeperPresenter(display);
        keeperPresenter.go(RootPanel.get(StringConstants.PAGE));
    }

    public static NodeKeeperServiceAsync getRpc() {
        return rpcService;
    }
}
