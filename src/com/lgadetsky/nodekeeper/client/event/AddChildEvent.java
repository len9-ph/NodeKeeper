package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddChildEvent extends GwtEvent<AddChildEventHandler> {
    public static Type<AddChildEventHandler> TYPE = new Type<AddChildEventHandler>();

    public AddChildEvent() {
        super();
    }

    @Override
    public Type<AddChildEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddChildEventHandler handler) {
        handler.onAddChild(this);
    }

}
