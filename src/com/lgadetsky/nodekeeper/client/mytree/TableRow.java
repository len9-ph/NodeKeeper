package com.lgadetsky.nodekeeper.client.mytree;

import java.util.LinkedList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;

public class TableRow extends Composite{
    private FlowPanel row;
    
    private FlexTable container;
    
    private ToggleButton rowButton;
    
    private Label ItemText;
    
    private LinkedList<TableRow> listOfchilds;
    
    public boolean isParent() { return !listOfchilds.isEmpty(); }
    
    public TableRow(String itemText, FlexTable tree) {
        rowButton = new ToggleButton();
        
        rowButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if(rowButton.getValue()) 
                    if (isParent()) 
                        showChilds();
                else 
                    if (isParent()) {
                        
                    }
            }
        });
        this.container = tree;
        
        ItemText = new Label(itemText);
        
        row.add(rowButton);
        row.add(ItemText);
        
        initWidget(row);
    }
    
    public void showChilds() {
        
    }
    
    public void hideChilds() {
        
    }
    
    public ToggleButton getRowButton() {
        return rowButton;
    }
}
