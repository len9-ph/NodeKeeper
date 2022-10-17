package com.lgadetsky.nodekeeper.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;

public class NodeKeeperView extends Composite implements NodeKeeperDisplay{
    private DecoratedPopupPanel simplePopup;
    public NodeKeeperView() {
        simplePopup = new DecoratedPopupPanel(true);
        FlowPanel mainpanel = new FlowPanel();
        initWidget(mainpanel);
    }

    @Override
    public void showPopUpMessage(String mes) {
        simplePopup.setWidget(new HTML(mes));
        simplePopup.show();
    }

    @Override
    public HasWidgets getContainer() {
        return (HasWidgets) super.getWidget();
    }
}
