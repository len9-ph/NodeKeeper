package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UpdateStateEventHandler extends EventHandler {
    void onUpdate(UpdateStateEvent event);
}
