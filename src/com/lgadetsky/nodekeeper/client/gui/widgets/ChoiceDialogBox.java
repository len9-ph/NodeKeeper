package com.lgadetsky.nodekeeper.client.gui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;

public class ChoiceDialogBox extends DialogBox {
    private final ClickHandler handler;
    
    public ChoiceDialogBox(String message, ClickHandler handler) {
        this.handler = handler;
        setText(message);
        this.center();
        
        Button yesButton = new Button("Yes"); 
        Button noButton = new Button("No");
        this.add(yesButton);
        //this.add(noButton);
        
        yesButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                ChoiceDialogBox.this.handler.onClick(event);
                hide();
            }
        });
        
        noButton.addClickHandler(new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                ChoiceDialogBox.this.handler.onClick(event);
                hide();
            }
        });
        show();
    }
    
}
