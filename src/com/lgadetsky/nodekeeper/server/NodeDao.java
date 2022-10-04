package com.lgadetsky.nodekeeper.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.lgadetsky.nodekeeper.shared.Node;

public class NodeDao implements DAO<Node>{
    private HashMap<Integer, Node> data = new HashMap<Integer, Node>();
    
    @Override
    public Node get(Integer id) {
        return data.get(id);
    }

    @Override
    public List<Node> getAll() {
        return new LinkedList<>(data.values());
    }

    @Override
    public Node save(Node node) {
        data.put(node.getId(), node);
        return node;
    }

    @Override
    public Node update(Node node) {
        data.put(node.getId(), node);
        return node;
    }

    @Override
    public Integer delete(Integer id) {
        data.remove(id);
        return id;
    }
}
