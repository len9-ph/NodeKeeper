package com.lgadetsky.nodekeeper.client.presenter;

import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lgadetsky.nodekeeper.client.NodeKeeperServiceAsync;
import com.lgadetsky.nodekeeper.client.event.RefreshEvent;
import com.lgadetsky.nodekeeper.client.event.RefreshEventHandler;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeKeeperPresenter implements Presenter {
    private final NodeKeeperServiceAsync rpcService;
    private final HandlerManager eventBus;
    //private final NodeKeeperDisplay nodeKeeperDisplay;
    
    public NodeKeeperPresenter(NodeKeeperServiceAsync rpcService, HandlerManager eventBus /*,NodeKeeperDisplay display*/) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        //this.nodeKeeperDisplay = display;
    }
    
    public void setUpLocalEventBus() {
        eventBus.addHandler(RefreshEvent.TYPE, 
                new RefreshEventHandler() {
                    
                    @Override
                    public void onRefresh(RefreshEvent event) {
                        onRefreshEvent(event.getChangedNodes());
                    }
                });
    }
    
    
    @Override
    public void go(HasWidgets container) {
        // TODO Auto-generated method stub
        
    }
    
    private void onRefreshEvent(List<Node> changedNodes) {
        rpcService.saveChanges(changedNodes, new AsyncCallback<List<Node>>() {
            @Override
            public void onSuccess(List<Node> result) {
                eventBus.fireEvent(new UpdateStateEvent(result));
            }
            
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("AAAAAA");
            }
        });
    }

}
