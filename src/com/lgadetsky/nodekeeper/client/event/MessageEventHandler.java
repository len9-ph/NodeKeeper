package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface MessageEventHandler extends EventHandler {
    void onMessageSend(MessageEvent event);
}
