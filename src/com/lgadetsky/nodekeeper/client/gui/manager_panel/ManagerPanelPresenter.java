package com.lgadetsky.nodekeeper.client.gui.manager_panel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.event.AddChildEvent;
import com.lgadetsky.nodekeeper.client.event.AddRootEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEventHandler;
import com.lgadetsky.nodekeeper.client.event.EditEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEventHandler;
import com.lgadetsky.nodekeeper.client.gui.Presenter;
import com.lgadetsky.nodekeeper.client.gui.manager_panel.ManagerPanelDisplay.ManagerPanelActionHandler;

public class ManagerPanelPresenter extends Presenter {
    private final HandlerManager eventBus;
    private final ManagerPanelDisplay display;

    public ManagerPanelPresenter(HandlerManager eventBus, ManagerPanelDisplay display) {
        this.eventBus = eventBus;
        this.display = display;

        bind();
        setUpLocalEventBus();
    }

    public void bind() {
        display.setManagerPanelActionHandler(new ManagerPanelActionHandler() {
            @Override
            public void onEditClick() {
                eventBus.fireEvent(new EditEvent());
            }

            @Override
            public void onDeleteClick() {
                eventBus.fireEvent(new DeleteEvent());
            }

            @Override
            public void onAddRootClick() {
                eventBus.fireEvent(new AddRootEvent());
            }

            @Override
            public void onAddChildClick() {
                eventBus.fireEvent(new AddChildEvent());
            }
        });
    }

    public void setUpLocalEventBus() {
        eventBus.addHandler(SelectEvent.TYPE,
                new SelectEventHandler() {
                    @Override
                    public void onSelect(SelectEvent event) {
                        display.setSelectedId(event.getSelectedNode().getId());
                    }
                });

        eventBus.addHandler(DeleteEvent.TYPE, new DeleteEventHandler() {

            @Override
            public void onDelete(DeleteEvent event) {
                display.setSelectedId(0);
            }
        });

    }

    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }

}
