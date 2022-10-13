package com.lgadetsky.nodekeeper.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class RefreshEvent extends GwtEvent<RefreshEventHandler>{
    public static Type<RefreshEventHandler> TYPE = new Type<RefreshEventHandler>();
    private final List<Node> changedNodes;
    
    public RefreshEvent(List<Node> changedNodes) {
        this.changedNodes = changedNodes;
    }
    
    public List<Node> getChangedNodes(){    return changedNodes;    }
    
    @Override
    public Type<RefreshEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RefreshEventHandler handler) {
        handler.onRefresh(this);
    }

}
