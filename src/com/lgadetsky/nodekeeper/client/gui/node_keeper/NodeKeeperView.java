package com.lgadetsky.nodekeeper.client.gui.node_keeper;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.gui.widgets.ChoiceDialogBox;
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

    @Override
    public boolean showChoiceDialogBox(String mes) {
        
        ChoiceDialogBox choiceBox = new ChoiceDialogBox(mes, new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                String src;
                src = event.getSource().toString();
                System.out.print(src);
            }
        });
        return false;
    }
}
