package com.lgadetsky.nodekeeper.client.gui.node_keeper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.lgadetsky.nodekeeper.client.gui.widgets.NotificationWidget;
import com.lgadetsky.nodekeeper.client.util.StringConstants;

public class NodeKeeperView extends Composite implements NodeKeeperDisplay{
    //private DecoratedPopupPanel simplePopup;
    private NotificationWidget popUp;
    public NodeKeeperView() {
//        simplePopup = new DecoratedPopupPanel(true);
        popUp = new NotificationWidget(StringConstants.ERROR, 1);
        FlowPanel mainpanel = new FlowPanel();
        initWidget(mainpanel);
        RootPanel.get().add(popUp);
    }

    @Override
    public void showPopUpMessage(String mes) {
        
//        simplePopup.setWidget(new HTML(mes));
//        simplePopup.show();
    }

    @Override
    public HasWidgets getContainer() {
        return (HasWidgets) super.getWidget();
    }
}
