package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class AddChildEvent extends GwtEvent<AddChildEventHandler> {
    public static Type<AddChildEventHandler> TYPE = new Type<AddChildEventHandler>();

    private final Node parentNode;

    public AddChildEvent(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getParentNode() {
        return parentNode;
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
