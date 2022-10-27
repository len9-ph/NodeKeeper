package com.lgadetsky.nodekeeper.client.gui.manager_panel;

import com.google.gwt.user.client.ui.Widget;

public interface ManagerPanelDisplay {
    interface ManagerPanelActionHandler {
        void onAddRootClick();

        void onAddChildClick();

        void onEditClick();

        void onDeleteClick();
    }

    void setSelectedId(Integer id);

    void setManagerPanelActionHandler(ManagerPanelActionHandler handler);

    Widget asWidget();
}
