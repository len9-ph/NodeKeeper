package com.lgadetsky.nodekeeper.client.gui.widgets.custom_notification;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.lgadetsky.nodekeeper.client.util.NumberConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class NotificationWidget extends Composite {
    private Label popUpMessage;

    public NotificationWidget() {
        FlowPanel popUpPanel = new FlowPanel();
        popUpPanel.setStyleName(StylesNames.NOTIFICATION_WIDGET);

        popUpMessage = new Label();
        popUpPanel.add(popUpMessage);

        initWidget(popUpPanel);
    }

    public void showPopUp(String message, NotificationType type) {
        popUpMessage.setText(message);
        switch (type) {
            case DEFAULT:
                popUpMessage.setStyleName(StylesNames.DEFAULT_MESSAGE);
                break;
            case ERROR:
                popUpMessage.setStyleName(StylesNames.ERROR_MESSAGE);
                break;
            default:
                break;
        }

        show();
        Timer timer = new Timer() {
            @Override
            public void run() {
                hide();
            }
        };
        timer.schedule(NumberConstants.FIVE_SECONDS);
    }

    private void show() {
        RootPanel.get().add(this);
    }

    private void hide() {
        RootPanel.get().remove(this);
    }
}
