package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DeleteEventHandler extends EventHandler {
    void onDelete(DeleteEvent event);
}
