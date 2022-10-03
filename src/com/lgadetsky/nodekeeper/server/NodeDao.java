package com.lgadetsky.nodekeeper.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.lgadetsky.nodekeeper.shared.Node;

public class NodeDao implements DAO<Node>{
    private HashMap<Integer, Node> data = new HashMap<Integer, Node>();
    
    @Override
    public Node get(Integer id) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        data.put(node.getId(), node);
        return node;
    }

    @Override
    public Integer delete(Integer id) {
        data.remove(id);
        return id;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
//    private static List<Node> nodes = new LinkedList<>();
//    
//    public void fill() {
//        nodes.add(new Node("Jora", "192.180.1.1", "4114"));
//        nodes.add(new Node("Dasha", "192.141.2.2", "2222"));
//        nodes.add(new Node(1, "Zhenya", "192.141.2.2", "2222"));
//        nodes.add(new Node(0, "Dima", "192.141.2.2", "2222"));
//        nodes.add(new Node("Kolya", "192.141.2.2", "2222"));
//        nodes.add(new Node(4,  "Liza", "192.141.2.2", "11232"));
//        nodes.add(new Node(3, "Ksenia", "192.141.2.2", "2525"));
//        nodes.add(new Node(4, "Ira", "192.141.2.2", "2442"));
//    }
//    
//    @Override
//    public Node get(int id) {
//        Iterator<Node> nodeIterator = nodes.iterator();
//        while (nodeIterator.hasNext()) {
//            Node nextNode = nodeIterator.next();
//            if(nextNode.getId() == id)
//                return nextNode;
//        }
//        return null;
//    }
//    
//    
//    
//    @Override
//    public List<Node> getAll() {
//        return nodes;
//    }
//
//    @Override
//    public Node save(Node node) {
//        nodes.add(node);
//        return node;
//    }
//
//    @Override
//    public Node update(Node node) {
//        Iterator<Node> nodeIterator = nodes.iterator();
//        while (nodeIterator.hasNext()) {
//            Node nextNode = nodeIterator.next();
//            if (nextNode.getId() == node.getId()) {
//                nextNode.setIp(node.getIp());
//                nextNode.setParentId(node.getParentId());
//                nextNode.setName(node.getName());
//                nextNode.setPort(node.getPort());
//                return node;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public int delete(int id) {
//        if (nodes.removeIf(n -> n.getId().equals(id)))
//            return id;
//        else 
//            return -1;
//
//    }

}
