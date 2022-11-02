package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddEventHandler extends EventHandler{
    void onAdd(AddEvent event);
}
