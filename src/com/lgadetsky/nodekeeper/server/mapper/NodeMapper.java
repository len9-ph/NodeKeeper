package com.lgadetsky.nodekeeper.server.mapper;

import java.util.List;

import com.lgadetsky.nodekeeper.shared.Node;


public interface NodeMapper {
    void insert(Node node);
    Node findById(int id);
    List<Node> findAll();
    void update(Node node);
    void deleteById(int id);
}
