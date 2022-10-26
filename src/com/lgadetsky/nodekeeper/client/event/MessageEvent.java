package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MessageEvent extends GwtEvent<MessageEventHandler> {
    public static final Type<MessageEventHandler> TYPE = new Type<MessageEventHandler>();
    private final String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Type<MessageEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MessageEventHandler handler) {
        handler.onMessageSend(this);
    }
}
