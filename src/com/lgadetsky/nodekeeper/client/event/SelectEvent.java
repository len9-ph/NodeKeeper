package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class SelectEvent extends GwtEvent<SelectEventHandler> {
    public static final Type<SelectEventHandler> TYPE = new Type<SelectEventHandler>();
    private final Node selectedNode;

    public SelectEvent(Node selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Node getSelectedNode() {
        return selectedNode;
    }

    @Override
    public Type<SelectEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SelectEventHandler handler) {
        handler.onSelect(this);
    }

}
