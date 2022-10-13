package com.lgadetsky.nodekeeper.client.view;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface NodesTablePanelDisplay {
	interface NodesTablePanelActionHandler {
		void onRefreshEvent();
	}
	
	void setNodesTablePanelHandler(NodesTablePanelActionHandler handler);
	void setData(List<Node> nodes);
	Widget asWidget();
}
