package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditEvent extends GwtEvent<EditEventHandler>{
    public static final Type<EditEventHandler> TYPE = new Type<EditEventHandler>();
    
    @Override
    public Type<EditEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditEventHandler handler) {
        handler.onEdit(this);
    }

}
