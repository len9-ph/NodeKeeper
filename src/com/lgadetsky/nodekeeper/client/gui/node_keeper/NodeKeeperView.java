package com.lgadetsky.nodekeeper.client.gui.node_keeper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.gui.widgets.ChoiceDialogBox;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification.NotificationType;
import com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification.NotificationWidget;
import com.lgadetsky.nodekeeper.client.util.StringConstants;

public class NodeKeeperView extends Composite implements NodeKeeperDisplay {
    private NotificationWidget popUp;
    private ChoiceDialogBox dialogBox;
    private NodeKeeperActionHandler handler;

    public NodeKeeperView() {
        popUp = new NotificationWidget();
        dialogBox = new ChoiceDialogBox(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (event.getSource().toString().equals(StringConstants.YES_SOURCE)) {
                    handler.onChoice();
                }
            }
        });
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

    @Override
    public void showChoiceDialogBox(String mes) {
        dialogBox.showDialog(mes);
    }

    @Override
    public void setNodeKeeperActionHandler(NodeKeeperActionHandler handler) {
        this.handler = handler;
    }
}
