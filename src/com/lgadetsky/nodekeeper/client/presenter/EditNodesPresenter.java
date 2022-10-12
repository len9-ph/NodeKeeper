package com.lgadetsky.nodekeeper.client.presenter;

import java.util.HashMap;
import java.util.LinkedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEventHandler;
import com.lgadetsky.nodekeeper.client.presenter.EditNodesDisplay.EditNodesDisplayActionHadler;
import com.lgadetsky.nodekeeper.shared.Node;

public class EditNodesPresenter implements Presenter {
    private LinkedList<Node> nodes = new LinkedList<>();
    private LinkedList<Node> changedNodes = new LinkedList<>();
    private HashMap<TreeItem, Node> treeItemToNodeMap = new HashMap<>();
    
    private final HandlerManager eventBus;
    private final EditNodesDisplay editNodesDisplay;
    
    public EditNodesPresenter(HandlerManager eventBus, EditNodesDisplay dispay) {
        this.eventBus = eventBus;
        this.editNodesDisplay = dispay;
        
        bind();
        setUpLocalEventBus();
    }
    
    public void setUpLocalEventBus() {
        eventBus.addHandler(UpdateStateEvent.TYPE,
                new UpdateStateEventHandler() {
                    @Override
                    public void onUpdate(UpdateStateEvent event) {
                        // updateNodes(event.getNodes);
                        editNodesDisplay.setData(nodes);
                    }
                });
    }
    
    public void bind() {
        editNodesDisplay.setDisplayActionhandler(new EditNodesDisplayActionHadler() {
            
            @Override
            public void portBoxPrinting(KeyUpEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onTreeItemSelection(SelectionEvent<TreeItem> event) {
                // TODO Auto-generated method stub
                event.getSelectedItem();
            }
            
            @Override
            public void onEditButton(ClickEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onDeleteButton(ClickEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAddRootButton(ClickEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAddChildButton(ClickEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void nameBoxPrinting(KeyUpEvent event) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void ipBoxPrinting(KeyUpEvent event) {
                // TODO Auto-generated method stub
                
            }
        });
    }
     
    @Override
    public void go(HasWidgets container) {
        
    }

}
