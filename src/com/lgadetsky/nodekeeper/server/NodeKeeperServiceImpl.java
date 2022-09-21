package com.lgadetsky.nodekeeper.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.lgadetsky.nodekeeper.client.NodeKeeperService;
import com.lgadetsky.nodekeeper.shared.Node;

public class NodeKeeperServiceImpl extends RemoteServiceServlet implements NodeKeeperService{

    @Override
    public List<Node> getAllNodes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node getNode(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
