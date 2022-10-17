package com.lgadetsky.nodekeeper.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.event.AddChildEvent;
import com.lgadetsky.nodekeeper.client.event.AddRootEvent;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEvent;
import com.lgadetsky.nodekeeper.client.event.MessageEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEventHandler;
import com.lgadetsky.nodekeeper.client.event.UpdateTreeEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateTreeEventHandler;
import com.lgadetsky.nodekeeper.client.view.TreeEditPanelDisplay;
import com.lgadetsky.nodekeeper.client.view.TreeEditPanelDisplay.TreeEditPanelActionHandler;
import com.lgadetsky.nodekeeper.shared.Node;

public class TreeEditPanelPresenter extends Presenter {
    private final HandlerManager eventBus;
    private final TreeEditPanelDisplay display;

    public TreeEditPanelPresenter(HandlerManager eventBus, TreeEditPanelDisplay display) {
        this.eventBus = eventBus;
        this.display = display;

        bind();
        setUpLocalEventBus();
    }

    public void bind() {
        display.setTreeEditPanelActionHanlder(new TreeEditPanelActionHandler() {

            @Override
            public void onSelectError(String message) {
                // Send event to nodeKeeper to show popUp
                eventBus.fireEvent(new MessageEvent(message));
            }

            @Override
            public void onBoxChange(Node node, String field, String value) {
                eventBus.fireEvent(new BoxChangeEvent(node, field, value));
            }

            @Override
            public void onDeleteClick(Node node) {
                // send event to nodekeeper to update changenodes
                eventBus.fireEvent(new DeleteEvent(node));
            }

            @Override
            public void onAddRootClick() {
                // send event to nodekeeper to update changenodes
                eventBus.fireEvent(new AddRootEvent());
            }

            @Override
            public void onAddChildClick(Node parentNode) {
                // send event to nodekeeper to update changenodes
                eventBus.fireEvent(new AddChildEvent(parentNode));
            }
        });
    }

    public void setUpLocalEventBus() {
        eventBus.addHandler(UpdateStateEvent.TYPE,
                new UpdateStateEventHandler() {
                    @Override
                    public void onUpdate(UpdateStateEvent event) {
                        display.buildTree(event.getNodes());
                    }
                });

        eventBus.addHandler(UpdateTreeEvent.TYPE,
                new UpdateTreeEventHandler() {
                    @Override
                    public void onUpdateTree(UpdateTreeEvent event) {
                        display.updateTree(event.getNewNode());
                    }
                });
    }

    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }
}
