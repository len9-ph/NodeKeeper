package com.lgadetsky.nodekeeper.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class UpdateStateEvent extends GwtEvent<UpdateStateEventHandler> {
    public static final Type<UpdateStateEventHandler> TYPE = new Type<UpdateStateEventHandler>();  
    private final List<Node> nodes;
    
    public UpdateStateEvent(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    
    @Override
    public Type<UpdateStateEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UpdateStateEventHandler handler) {
        handler.onUpdate(this);
    }
}
