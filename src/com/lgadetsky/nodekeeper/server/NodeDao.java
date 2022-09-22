package com.lgadetsky.nodekeeper.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lgadetsky.nodekeeper.shared.Node;

public class NodeDao implements DAO<Node>{
    
    private List<Node> nodes = new ArrayList<>();
    
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
    public void save(Node node) {
        nodes.add(node);
    }

    @Override
    public void update(Node node) {
        Iterator<Node> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            Node nextNode = nodeIterator.next();
            if (nextNode.getId() == node.getId()) {
                nextNode.setIp(node.getIp());
                nextNode.setParentId(node.getParentId());
                nextNode.setName(node.getName());
                nextNode.setPort(node.getPort());
            }
        }
    }

    @Override
    public void delete(int id) {
        Iterator<Node> nodeIterator = nodes.iterator();
        while (nodeIterator.hasNext()) {
            Node nextNode = nodeIterator.next();
            if (nextNode.getId() == id)
                nodeIterator.remove();
        }
    }

}
