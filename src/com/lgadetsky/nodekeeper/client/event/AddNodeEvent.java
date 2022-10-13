package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class AddNodeEvent extends GwtEvent<AddNodeEventHandler>{
    public static Type<AddNodeEventHandler> TYPE = new Type<AddNodeEventHandler>();
    
    private final Node newNode;
    
    public AddNodeEvent(Node node) {
        this.newNode = node;
    }
    
    public Node getNode() {  return newNode;  }
    
    @Override
    public Type<AddNodeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddNodeEventHandler handler) {
        handler.onAdd(this);
    }
    
}
