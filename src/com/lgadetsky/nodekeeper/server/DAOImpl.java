package com.lgadetsky.nodekeeper.server;

import java.util.List;

import com.google.inject.Inject;
import com.lgadetsky.nodekeeper.server.mapper.NodeMapper;
import com.lgadetsky.nodekeeper.shared.Node;

public class DAOImpl implements DAO<Node> {
    
    @Inject
    private NodeMapper mapper;

    @Override
    public Node get(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public List<Node> getAll() {
        return mapper.findAll();
    }

    @Override
    public Node save(Node node) {
        mapper.insert(node);
        return node;
    }

    @Override
    public Node update(Node node) {
        mapper.update(node);
        return node;
    }

    @Override
    public Integer delete(Integer id) {
        mapper.deleteById(id);
        return id;
    }
    
}
