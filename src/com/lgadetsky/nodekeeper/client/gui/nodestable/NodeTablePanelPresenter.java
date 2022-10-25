package com.lgadetsky.nodekeeper.client.gui.nodestable;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.event.RefreshEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEventHandler;
import com.lgadetsky.nodekeeper.client.gui.Presenter;
import com.lgadetsky.nodekeeper.client.gui.nodestable.NodesTablePanelDisplay.NodesTablePanelActionHandler;

public class NodeTablePanelPresenter extends Presenter {

    private final HandlerManager eventBus;
    private final NodesTablePanelDisplay display;

    public NodeTablePanelPresenter(HandlerManager eventBus, NodesTablePanelDisplay display) {
        this.eventBus = eventBus;
        this.display = display;

        bind();
        setUpLocalEventBus();
    }

    public void bind() {
        display.setNodesTablePanelHandler(new NodesTablePanelActionHandler() {
            @Override
            public void onRefreshClick() {
                eventBus.fireEvent(new RefreshEvent());
            }
        });
    }

    public void setUpLocalEventBus() {
        eventBus.addHandler(UpdateStateEvent.TYPE,
                new UpdateStateEventHandler() {
                    @Override
                    public void onUpdate(UpdateStateEvent event) {
                        display.setData(event.getNodes());
                    }
                });
    }

    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }
}
