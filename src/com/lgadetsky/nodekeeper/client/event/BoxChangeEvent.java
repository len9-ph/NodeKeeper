package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BoxChangeEvent extends GwtEvent<BoxChangeEventHandler> {
    public static Type<BoxChangeEventHandler> TYPE = new Type<BoxChangeEventHandler>();

    private String field;
    private String value;

    public BoxChangeEvent(String field, String value) {
        this.field = field;
        this.value = value;
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
