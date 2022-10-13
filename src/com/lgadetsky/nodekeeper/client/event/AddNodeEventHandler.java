package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddNodeEventHandler extends EventHandler{
    void onAdd(AddNodeEvent event);
}
