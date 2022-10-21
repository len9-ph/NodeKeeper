package com.lgadetsky.nodekeeper.client.mytree;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;

public class TreeRow extends Composite{
    private ToggleButton button;
    private Label treeItemText;
    private Integer level;
    private List<TreeRow> childs;
    
    public TreeRow(String name) {
        this.level = 0;
        this.childs = new LinkedList<>();
        button = new ToggleButton();
        button.setStyleName("rowToggleButton");
        
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               if (button.getValue())
                   showChilds();
               else
                   hideChilds();
            }
        });
        
        button.addStyleName("hidden");
        treeItemText = new Label(name);
        treeItemText.setStyleName("rowText");
        
        FlowPanel panel = new FlowPanel();  
        panel.add(button);
        panel.add(treeItemText);
        
        initWidget(panel);
    }
    
    public void setSelected() {
        treeItemText.addStyleName("gwt-TreeItem-selected");
    }
    
    public void removeSelected() {
        treeItemText.removeStyleName("gwt-TreeItem-selected");
    }
    
    public void setName(String name) {
        this.treeItemText.setText(name);
    }
    
    public Integer getLevel() {
        return this.level;
    }
    
    public void increaseLevel(Integer parentLevel) {
        this.level = parentLevel + 1;
    }
    
    public void addChild(TreeRow row) {
        button.removeStyleName("hidden");
        childs.add(row);
    }
    
    public boolean isParent() {
        if (childs.isEmpty())
            return false;
        else 
            return true;
    }
    
    public Integer countChilds() {
        return countUtil(getChilds());
    }
    
    private Integer countUtil(List<TreeRow> list) {
        for (TreeRow row : list) 
            if (row.isParent())
                return 1 + row.getChilds().size() + countUtil(row.getChilds());
        
        return list.size();
        
    }
    
    public List<TreeRow> getChilds() {
        return this.childs;
    }
    
    private void setButtonHide() {
        button.setValue(false);
    }
    
    private void hideChilds() {
        for (TreeRow row : childs) {
            if(row.isParent()) 
                row.hideChilds();
            row.hide();
        }
    }
    
    private void showChilds() {
        for (TreeRow row : childs) {
            row.show();
            if(row.isParent())
                row.setButtonHide();
        }
    }
    
    private void hide() {
        this.removeStyleName("show");
        this.setStyleName("hide");
    }
    
    private void show() {
        this.removeStyleName("hide");
        this.setStyleName("show");
    }
}

