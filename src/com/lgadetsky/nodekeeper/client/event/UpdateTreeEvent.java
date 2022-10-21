package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class UpdateTreeEvent extends GwtEvent<UpdateTreeEventHandler>{
    public static final Type<UpdateTreeEventHandler> TYPE = new Type<UpdateTreeEventHandler>();
    private final Node newNode;
    
    public UpdateTreeEvent(Node node) {
        this.newNode = node;
    }
    
    public Node getNewNode() {
        return newNode;
    }
    
    @Override
    public Type<UpdateTreeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateTreeEventHandler handler) {
        handler.onUpdateTree(this);
    }

}
