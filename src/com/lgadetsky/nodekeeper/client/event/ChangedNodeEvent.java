package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class ChangedNodeEvent extends GwtEvent<ChangedNodeEventHandler>{
    public static Type<ChangedNodeEventHandler> TYPE = new Type<ChangedNodeEventHandler>();
    private final Node oldNode;
    private final Node newNode;
    
    public ChangedNodeEvent(Node oldNode, Node changedNode) {
        this.oldNode = oldNode;
        this.newNode = changedNode;
    }
    
    public Node getOldNode() {  return oldNode;  }
    
    public Node getChangedNode() {  return newNode;  }

    @Override
    public Type<ChangedNodeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangedNodeEventHandler handler) {
        handler.onChange(this);
    }
    
    
}
