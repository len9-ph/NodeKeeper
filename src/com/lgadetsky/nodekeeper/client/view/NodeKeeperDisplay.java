package com.lgadetsky.nodekeeper.client.view;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public interface NodeKeeperDisplay {
    Widget asWidget();
    HasWidgets getContainer();
    void showPopUpMessage(String mes);
}
