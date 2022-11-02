package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddEvent extends GwtEvent<AddEventHandler>{
    public enum ItemType {
        ROOT, CHILD
    }
    
    public static Type<AddEventHandler> TYPE = new Type<AddEventHandler>();
    private final ItemType itemType;
    
    public AddEvent(ItemType type) {
        this.itemType = type;
    }
    
    public ItemType getItemType() {
        return itemType;
    }
    
    @Override
    public Type<AddEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddEventHandler handler) {
        handler.onAdd(this);
    }

}
