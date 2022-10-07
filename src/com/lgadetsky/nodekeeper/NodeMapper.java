package com.lgadetsky.nodekeeper;

import com.lgadetsky.nodekeeper.shared.Node;

public interface NodeMapper {
    void insert(Node node);
    Node findById(int id);
    void update(Node node);
    void deleteById(int id);
}
