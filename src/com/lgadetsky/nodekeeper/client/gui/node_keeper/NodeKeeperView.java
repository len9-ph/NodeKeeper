package com.lgadetsky.nodekeeper.client.gui.node_keeper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification.NotificationType;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification.NotificationWidget;

public class NodeKeeperView extends Composite implements NodeKeeperDisplay {
    private NotificationWidget popUp;

    public NodeKeeperView() {
        popUp = new NotificationWidget();
        FlowPanel mainpanel = new FlowPanel();
        initWidget(mainpanel);
    }

    @Override
    public void showPopUpMessage(String mes, NotificationType type) {
        popUp.showPopUp(mes, type);
    }

    @Override
    public HasWidgets getContainer() {
        return (HasWidgets) super.getWidget();
    }
}
