package com.lgadetsky.nodekeeper.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RefreshEvent extends GwtEvent<RefreshEventHandler> {
	public static Type<RefreshEventHandler> TYPE = new Type<RefreshEventHandler>();
	
	@Override
	public Type<RefreshEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshEventHandler handler) {
		handler.onRefresh(this);
	}

}
