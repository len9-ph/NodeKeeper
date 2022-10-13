package com.lgadetsky.nodekeeper.client.view;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;

public interface TreeEditPanelDisplay {
	interface TreeEditPanelActionHandler{
		
	}
	
	void setTree(Tree tree);
	Widget asWidget();
}
