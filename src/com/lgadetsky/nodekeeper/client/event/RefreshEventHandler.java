package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface RefreshEventHandler extends EventHandler{
    void onRefresh(RefreshEvent event);
}
