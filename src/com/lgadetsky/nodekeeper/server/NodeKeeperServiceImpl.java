package com.lgadetsky.nodekeeper.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lgadetsky.nodekeeper.client.NodeKeeperService;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeKeeperServiceImpl extends RemoteServiceServlet implements NodeKeeperService{

    private static final long serialVersionUID = 1L;
    
    private NodeDao nodeDao;
    
    @Override
    public List<Node> getAllNodes() {
        return nodeDao.getAll();
    }

    @Override
    public Node getNode(int id) {
        return nodeDao.get(id);
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
        if (res < 0)
            return false;
        else 
            return true;
    }
    
}
