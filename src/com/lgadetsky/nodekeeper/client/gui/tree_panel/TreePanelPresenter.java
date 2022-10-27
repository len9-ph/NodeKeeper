package com.lgadetsky.nodekeeper.client.gui.tree_panel;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEvent;
import com.lgadetsky.nodekeeper.client.event.BoxChangeEventHandler;
import com.lgadetsky.nodekeeper.client.event.DeleteEvent;
import com.lgadetsky.nodekeeper.client.event.DeleteEventHandler;
import com.lgadetsky.nodekeeper.client.event.MessageEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEvent;
import com.lgadetsky.nodekeeper.client.event.SelectEventHandler;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEventHandler;
import com.lgadetsky.nodekeeper.client.event.UpdateTreeEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateTreeEventHandler;
import com.lgadetsky.nodekeeper.client.gui.Presenter;
import com.lgadetsky.nodekeeper.client.gui.tree_panel.TreePanelDisplay.TreePanelActionHandler;
import com.lgadetsky.nodekeeper.client.util.StringConstants;
import com.lgadetsky.nodekeeper.shared.Node;

public class TreePanelPresenter extends Presenter{
    private final HandlerManager eventBus;
    private final TreePanelDisplay display;
    
    public TreePanelPresenter(HandlerManager eventBus, TreePanelDisplay display) {
        this.eventBus = eventBus;
        this.display = display;
        
        bind();
        setUpLocalEventBus();
    }
    
    public void bind() {
        display.setTreePanelActionHandler(new TreePanelActionHandler() {
            
            @Override
            public void onSelect(Node node) {
                eventBus.fireEvent(new SelectEvent(node));
            }
            
            @Override
            public void onMessage(String message) {
                eventBus.fireEvent(new MessageEvent(message));
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
        
        eventBus.addHandler(SelectEvent.TYPE, 
                new SelectEventHandler() {
                    
                    @Override
                    public void onSelect(SelectEvent event) {
                        display.setSelectedItem(event.getSelectedNode());
                    }
                });
        
        eventBus.addHandler(BoxChangeEvent.TYPE, new BoxChangeEventHandler() {
            
            @Override
            public void onBoxChange(BoxChangeEvent event) {
                if (event.getField().equals(StringConstants.NAME))
                    display.onNameBoxChange(event.getValue());
            }
        });
        
        eventBus.addHandler(DeleteEvent.TYPE, new DeleteEventHandler() {
            
            @Override
            public void onDelete(DeleteEvent event) {
                display.onDelete();
            }
        });
    }
    
    @Override
    public void go(HasWidgets container) {
        container.add(display.asWidget());
    }

}
