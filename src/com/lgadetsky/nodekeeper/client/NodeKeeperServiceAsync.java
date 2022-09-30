package com.lgadetsky.nodekeeper.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lgadetsky.nodekeeper.shared.Node;

public interface NodeKeeperServiceAsync {
    void getAllNodes(AsyncCallback<List<Node>> callback);
    void getNode(int id, AsyncCallback<Node> callback);
    void create(Node node, AsyncCallback<Node> callback);
    void update(Node node, AsyncCallback<Boolean> callback);
    void delete(int id, AsyncCallback<Boolean> callback);
    void saveChanges(List<Node> changes, AsyncCallback<Boolean> callback);
}
