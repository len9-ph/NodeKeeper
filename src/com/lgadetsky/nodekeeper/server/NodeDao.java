package com.lgadetsky.nodekeeper.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.lgadetsky.nodekeeper.shared.Node;

public class NodeDao implements DAO<Node>{
    
    private List<Node> nodes = new ArrayList<>();
    
    @Override
    public Node get(int id) {
        // TODO make with iterator
        return nodes.get(id);
    }

    @Override
    public List<Node> getAll() {
        // TODO Auto-generated method stub
        return nodes;
    }

    @Override
    public void save(Node node) {
        // TODO Auto-generated method stub
        nodes.add(node);
    }

    @Override
    public void update(Node t) {
        // TODO update finc
        
    }

    @Override
    public void delete(int id) {
        // TODO make it with iterator
        
    }

}
