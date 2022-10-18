package com.lgadetsky.nodekeeper.client.mypopup;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MyPopUp extends Composite {
    private Label popUpMessage;
    
    public MyPopUp(String message) {
        FlowPanel panel = new FlowPanel();
        initWidget(panel);
        
        popUpMessage = new Label(message);
        panel = new FlowPanel();
        panel.setStyleName("");
        panel.add(popUpMessage);
        RootPanel.get().add(panel);
    }
}
