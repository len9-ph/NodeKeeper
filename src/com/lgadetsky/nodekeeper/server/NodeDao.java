package com.lgadetsky.nodekeeper.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lgadetsky.nodekeeper.shared.Node;

public class NodeDao implements DAO<Node>{
    
    private static List<Node> nodes = new ArrayList<>();
    
    @Override
    public Node get(int id) {
        Iterator<Node> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            Node nextNode = nodeIterator.next();
            if(nextNode.getId() == id)
                return nextNode;
        }
        return null;
    }

    @Override
    public List<Node> getAll() {
        return nodes;
    }

    @Override
    public Node save(Node node) {
        nodes.add(node);
        return node;
    }

    @Override
    public Node update(Node node) {
        Iterator<Node> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            Node nextNode = nodeIterator.next();
            if (nextNode.getId() == node.getId()) {
                nextNode.setIp(node.getIp());
                nextNode.setParentId(node.getParentId());
                nextNode.setName(node.getName());
                nextNode.setPort(node.getPort());
                return node;
            }
        }
        return null;
    }

    @Override
    public int delete(int id) {
        Iterator<Node> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            Node nextNode = nodeIterator.next();
            if (nextNode.getId() == id)
                nodeIterator.remove();
            return id;
        }
        return -1;
    }

}
