package com.lgadetsky.nodekeeper.client.gui.widgets.customnotification;


import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class NotificationWidget extends Composite{
    private static FlowPanel popUpPanel;
    private Label popUpMessage;
    
    public NotificationWidget() {
        popUpPanel = new FlowPanel();
        popUpPanel.setStyleName("customPopUp");
        
        popUpMessage = new Label();
        popUpPanel.add(popUpMessage);
        
        initWidget(popUpPanel);
    }
    
    public void showPopUp(String message, NotificationType type) {
        popUpMessage.setText(message);
        switch (type) {
            case DEFAULT:
                popUpMessage.setStyleName("defaultMessage");
                break;
            case ERROR:
                popUpMessage.setStyleName("errorMessage");
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
        timer.schedule(5000);
    }
    private void show() {
        RootPanel.get().add(this);
    }
    
    private void hide() {
        RootPanel.get().remove(this);
    }
}
