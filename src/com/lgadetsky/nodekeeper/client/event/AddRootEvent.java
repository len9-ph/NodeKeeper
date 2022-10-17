package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddRootEvent extends GwtEvent<AddRootEventHandler>{
    public static Type<AddRootEventHandler> TYPE = new Type<AddRootEventHandler>();
    
    public AddRootEvent() {
        super();
    }
    
    @Override
    public Type<AddRootEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddRootEventHandler handler) {
        handler.onAddRoot(this);
    }

}
