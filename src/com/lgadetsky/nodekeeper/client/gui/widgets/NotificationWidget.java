package com.lgadetsky.nodekeeper.client.gui.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class NotificationWidget extends Composite{
    private FlowPanel popUpPanel;
    private Label popUpMessage;
    
    public NotificationWidget() {
        popUpPanel = new FlowPanel();
        popUpPanel.setStyleName("customPopUp");
        
        popUpMessage = new Label();
        popUpPanel.add(popUpMessage);
        
        initWidget(popUpPanel);
    }
    
    public void showPopUp(String message, boolean type) {
        popUpMessage.setText(message);
        
        if(type == false)
            popUpMessage.setStyleName("defaultMessage");
        else 
            popUpMessage.setStyleName("errorMessage");
    }
}
