package com.lgadetsky.nodekeeper.client.gui.node_keeper;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification.NotificationType;

public interface NodeKeeperDisplay {
    Widget asWidget();

    HasWidgets getContainer();

    void showPopUpMessage(String mes, NotificationType type);
}
