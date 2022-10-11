package com.lgadetsky.nodekeeper.server;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lgadetsky.nodekeeper.server.mapper.NodeMapper;
import com.lgadetsky.nodekeeper.shared.Node;

@Singleton
public class DAOImpl implements DAO<Node> {

    @Inject
    private NodeMapper nodeMapper;

    @Override
    public Node get(Integer id) {
        return nodeMapper.findById(id);
    }

    @Override
    public List<Node> getAll() {
        return nodeMapper.findAll();
    }

    @Override
    public Node save(Node node) {
        nodeMapper.insert(node);
        return node;
    }

    @Override
    public Node update(Node node) {
        nodeMapper.update(node);
        return node;
    }

    @Override
    public Integer delete(Integer id) {
        nodeMapper.deleteById(id);
        return id;
    }
    
}
