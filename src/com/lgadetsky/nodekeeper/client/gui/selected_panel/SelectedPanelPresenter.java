package com.lgadetsky.nodekeeper.client.gui.selected_panel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEventHandler;
import com.lgadetsky.nodekeeper.client.event.EditEvent;
import com.lgadetsky.nodekeeper.client.event.EditEventHandler;
import com.lgadetsky.nodekeeper.client.event.MessageEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEventHandler;
import com.lgadetsky.nodekeeper.client.gui.Presenter;
import com.lgadetsky.nodekeeper.client.gui.selected_panel.SelectedPanelDisplay.SelectedPanelActionHandler;

public class SelectedPanelPresenter extends Presenter {
    private final HandlerManager eventBus;
    private final SelectedPanelDisplay display;

    public SelectedPanelPresenter(HandlerManager eventBus, SelectedPanelDisplay display) {
        this.eventBus = eventBus;
        this.display = display;

        bind();
        setUpLocalEventBus();
    }

    public void bind() {
        display.setSelectedPanelActionHandler(new SelectedPanelActionHandler() {
            @Override
            public void onBoxChange(String field, String value) {
                eventBus.fireEvent(new BoxChangeEvent(field, value));
            }

            @Override
            public void onError(String message) {
                eventBus.fireEvent(new MessageEvent(message));
            }
        });
    }

    public void setUpLocalEventBus() {
        eventBus.addHandler(SelectEvent.TYPE, new SelectEventHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                display.setNode(event.getSelectedNode());
            }
        });

        eventBus.addHandler(EditEvent.TYPE, new EditEventHandler() {

            @Override
            public void onEdit(EditEvent event) {
                display.setEditState();
            }
        });

        eventBus.addHandler(DeleteEvent.TYPE, new DeleteEventHandler() {

            @Override
            public void onDelete(DeleteEvent event) {
                display.clearPanel();
            }
        });
    }

    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }

}
