package com.lgadetsky.nodekeeper.client.gui.selected_panel;

import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.shared.Node;

public interface SelectedPanelDisplay {
    interface SelectedPanelActionHandler {
        void onBoxChange(String field, String value);
        
        void onError(String message);
    }

    void setNode(Node node);
    
    void clearPanel();
    
    void setEditState();

    void setSelectedPanelActionHandler(SelectedPanelActionHandler handler);

    Widget asWidget();
}
