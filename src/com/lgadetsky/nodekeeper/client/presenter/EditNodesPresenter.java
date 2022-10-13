package com.lgadetsky.nodekeeper.client.presenter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.lgadetsky.nodekeeper.client.event.AddNodeEvent;
import com.lgadetsky.nodekeeper.client.event.ChangedNodeEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEvent;
import com.lgadetsky.nodekeeper.client.event.UpdateStateEventHandler;
import com.lgadetsky.nodekeeper.client.presenter.EditNodesDisplay.EditNodesDisplayActionHadler;
import com.lgadetsky.nodekeeper.shared.Node;

public class EditNodesPresenter implements Presenter {
    private LinkedList<Node> nodes = new LinkedList<>();
    private LinkedList<Node> changedNodes = new LinkedList<>();
    private HashMap<TreeItem, Node> treeItemToNodeMap = new HashMap<>();
    private TreeItem selectedItem;
    
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
                        // Build tree and send it to editNodesDisplay
                        nodes.clear();
                        changedNodes.clear();
                        nodes.addAll(event.getNodes());
                        
                        editNodesDisplay.setTree(createTree());
                    }
                });
    }
    
    public void bind() {
        editNodesDisplay.setDisplayActionhandler(new EditNodesDisplayActionHadler() {
            @Override
            public void nameBoxPrinting(KeyUpEvent event, String box) {
                selectedItem.setText(box);
                Node oldNode = treeItemToNodeMap.get(selectedItem);
                if(changedNodes.contains(oldNode))
                    changedNodes.remove(oldNode);
                Node newNode = treeItemToNodeMap.get(selectedItem);
                newNode.setName(box);
                changedNodes.add(newNode);
                eventBus.fireEvent(new ChangedNodeEvent(oldNode, newNode));
            }
            
            @Override
            public void ipBoxPrinting(KeyUpEvent event, String box) {
                Node oldNode = treeItemToNodeMap.get(selectedItem);
                if(changedNodes.contains(oldNode))
                    changedNodes.remove(oldNode);
                Node newNode = treeItemToNodeMap.get(selectedItem);
                newNode.setIp(box);
                changedNodes.add(newNode);
                eventBus.fireEvent(new ChangedNodeEvent(oldNode, newNode));
                
            }
            
            @Override
            public void portBoxPrinting(KeyUpEvent event, String box) {
                Node oldNode = treeItemToNodeMap.get(selectedItem);
                if(changedNodes.contains(oldNode))
                    changedNodes.remove(oldNode);
                Node newNode = treeItemToNodeMap.get(selectedItem);
                newNode.setPort(box);
                changedNodes.add(newNode);
                eventBus.fireEvent(new ChangedNodeEvent(oldNode, newNode));
            }
            
            @Override
            public void onTreeItemSelection(SelectionEvent<TreeItem> event) {
                selectedItem = event.getSelectedItem();
                editNodesDisplay.setSelectedItem(treeItemToNodeMap.get(selectedItem));
            }
            
            @Override
            public void onEditButton(ClickEvent event) {
                editNodesDisplay.editButtonPress(treeItemToNodeMap.get(selectedItem));
            }
            
            @Override
            public void onDeleteButton(ClickEvent event) {
                if(selectedItem == null)
                    Window.alert("aaa");
                else {
                    Node curNode = treeItemToNodeMap.get(selectedItem);
                    if (curNode.getId().equals(-1)) {
                        changedNodes.remove(curNode);
                        treeItemToNodeMap.remove(selectedItem);
                        // Add deleteNode event eventBus.fireEvent(new );
                    } else {
                        Node oldNode = curNode;
                        curNode.setDeleted(true);
                        for (Node n : nodes) {
                            if (n.getParentId().equals(curNode.getId())){
                                Node oldNodeInner = n;
                                n.setDeleted(true);
                                changedNodes.add(n);
                                eventBus.fireEvent(new ChangedNodeEvent(oldNodeInner, n));
                            }
                        }
                        changedNodes.add(curNode);
                        eventBus.fireEvent(new ChangedNodeEvent(oldNode, curNode));
                        treeItemToNodeMap.remove(selectedItem);
                    }
                    if (curNode.getId().equals(-1))
                        editNodesDisplay.deleteTreeItem(selectedItem);
                    else 
                        editNodesDisplay.deleteTreeItem(selectedItem.getParentItem(), selectedItem);
                }
            }
            
            @Override
            public void onAddRootButton(ClickEvent event, TreeItem item) {
                Node newNode = new Node();
                treeItemToNodeMap.put(item, newNode);
                changedNodes.add(newNode);
                eventBus.fireEvent(new AddNodeEvent(newNode));
            }
            
            @Override
            public void onAddChildButton(ClickEvent event, TreeItem parentItem, TreeItem item) {
                Node parentNode = treeItemToNodeMap.get(parentItem);
                if (parentNode.getId().equals(-1)) {
                    //popup
                } else {
                    Node newNode = new Node(parentNode.getId());
                    changedNodes.add(newNode);
                    eventBus.fireEvent(new AddNodeEvent(newNode));
                    treeItemToNodeMap.put(item, newNode);
                }
            }
        });
    }
    
    @Override
    public void go(HasWidgets container) {
        
    }
    
    private Tree createTree() {
        final Tree t = new Tree();
        
        for (Node n : nodes) {
            if (n.getParentId() == -1) {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                treeItemToNodeMap.put(item, n);
                t.addItem(item);
            } else {
                TreeItem item = new TreeItem(new HTML(n.getName()));
                treeItemToNodeMap.put(item, n);
                TreeItem parentItem = null;
                for (Node node : nodes) {
                    if (node.getId().equals(n.getParentId())) {
                        Set<Map.Entry<TreeItem, Node>> entrySet = treeItemToNodeMap.entrySet();
                        
                        for (Map.Entry<TreeItem, Node> pair : entrySet) {
                            if(node.equals(pair.getValue())) 
                                parentItem = pair.getKey();
                        }
                    }
                }
                parentItem.addItem(item);
            }
        }
        return t;
    }

}
