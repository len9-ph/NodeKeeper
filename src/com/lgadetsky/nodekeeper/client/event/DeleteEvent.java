package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DeleteEvent extends GwtEvent<DeleteEventHandler> {
    public static final Type<DeleteEventHandler> TYPE = new Type<DeleteEventHandler>();

    public DeleteEvent() {
        super();
    }

    @Override
    public Type<DeleteEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteEventHandler handler) {
        handler.onDelete(this);
    }

}
