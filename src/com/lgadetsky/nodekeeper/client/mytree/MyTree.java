package com.lgadetsky.nodekeeper.client.mytree;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.lgadetsky.nodekeeper.shared.Node;

public class MyTree extends Composite {
    private FlexTable mainTree;
    
    //private HashMap<TableRow, LinkedList<TableRow>> parentToChildMap;
    private HashMap<TableRow, Node> rowToNodeMap;
    
    
    public MyTree(List<Node> nodes) {
        rowToNodeMap = new HashMap<TableRow, Node>();
        for (Node n : nodes) {
            TableRow newRow = new TableRow(n.getName(), mainTree);
            
        }
    }
    
    private void populateMap(List<Node> nodes) {
        for (Node n : nodes) {
            TableRow newRow = new TableRow(n.getName(), mainTree);
            
            rowToNodeMap.put(newRow, n);
        }
    }
}
