package com.lgadetsky.nodekeeper.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lgadetsky.nodekeeper.client.NodeKeeperService;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeKeeperServiceImpl extends RemoteServiceServlet implements NodeKeeperService{

    private static final long serialVersionUID = 1L;
    
    
    private NodeDao nodeDao;
    
    public NodeKeeperServiceImpl() {
        nodeDao = new NodeDao();
    }
    
    @Override
    public List<Node> getAllNodes() {
        return nodeDao.getAll();
    }

    @Override
    public Node getNode(int id) {
        return nodeDao.get(id);
    }
    
    @Override
    public Node create(Node node) {
        node.setId();
        nodeDao.save(node);
        return node;
    }
    
    @Override
    public boolean update(Node node) {
        Node res = nodeDao.update(node);
        if (res == null)
            return false;
        else
            return true;
    }

    @Override
    public boolean delete(int id) {
        int res = nodeDao.delete(id);
        if (res > 0)
            return true;
        else 
            return false;
    }
    
    @Override
    public List<Node> saveChanges(List<Node> changes) {
        for (Node n : changes) {
            if (n.getId().equals(-1)) 
                create(n);
            else if (n.isDeleted()) {
                delete(n.getId());
            }
            else {
                update(n);
            }
        }
        return getAllNodes();
    }
    
}
