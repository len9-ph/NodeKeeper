package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ChangedNodeEventHandler extends EventHandler {
    void onChange(ChangedNodeEvent event);
}
