package com.lgadetsky.nodekeeper.client.gui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.client.util.StylesNames;

public class ChoiceDialogBox extends Composite {
    private final ClickHandler handler;

    private final Label messageLabel;

    public ChoiceDialogBox(ClickHandler handler) {
        this.handler = handler;
        FlowPanel choicePanel = new FlowPanel();
        choicePanel.setStyleName(StylesNames.CHOICE_BOX);
        initWidget(choicePanel);

        messageLabel = new Label();
        choicePanel.add(messageLabel);

        Button yesButton = new Button(StringConstants.YES);
        yesButton.addStyleName(StylesNames.YES_BUTTON);
        Button noButton = new Button(StringConstants.NO);
        noButton.addStyleName(StylesNames.NO_BUTTON);

        choicePanel.add(yesButton);
        choicePanel.add(noButton);

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
    }

    public void showDialog(String message) {
        messageLabel.setText(message);
        show();
    }

    private void show() {
        RootPanel.get().add(this);
    }

    private void hide() {
        RootPanel.get().remove(this);
    }
}
