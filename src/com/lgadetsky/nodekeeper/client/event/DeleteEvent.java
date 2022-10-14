package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class DeleteEvent extends GwtEvent<DeleteEventHandler>{
    public static Type<DeleteEventHandler> TYPE = new Type<DeleteEventHandler>();
    private Node node;
    
    public DeleteEvent(Node node) {
        this.node = node;
    }
    
    public Node getNode() {
        return node;
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
