package com.lgadetsky.nodekeeper.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lgadetsky.nodekeeper.shared.Node;

@RemoteServiceRelativePath("nodekeeper")
public interface NodeKeeperService extends RemoteService {
    List<Node> getAllNodes();
    Node getNode(int id);
    Node create(Node node);
    boolean update(Node node);
    boolean delete(int id);
    List<Node> saveChanges(List<Node> changes);
}
