package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class BoxChangeEvent extends GwtEvent<BoxChangeEventHandler> {
    public static Type<BoxChangeEventHandler> TYPE = new Type<BoxChangeEventHandler>();

    private Node node;
    private String field;
    private String value;

    public BoxChangeEvent(Node node, String field, String value) {
        this.node = node;
        this.field = field;
        this.value = value;
    }

    public Node getNode() {
        return node;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Type<BoxChangeEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BoxChangeEventHandler handler) {
        handler.onBoxChange(this);
    }
}
