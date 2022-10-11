package com.lgadetsky.nodekeeper.server;

import com.google.inject.Inject;
import com.lgadetsky.nodekeeper.shared.Node;

public class Server {
    @Inject
    private static Server server;

    @Inject
    private DAOImpl dao = new DAOImpl();

    public static Server getInstance() {
        if (server == null) {
            return new Server();
        }
        return server;
    }

    public DAO<Node> getDaoImpl() {
        return dao;
    }
}
