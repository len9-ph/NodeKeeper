package com.lgadetsky.nodekeeper;

import com.google.inject.servlet.ServletModule;
import com.lgadetsky.nodekeeper.server.NodeKeeperServiceImpl;

//public class MyAppModule extends AbstractModule{
//
//    @Override
//    protected void configure() {
//        requestStaticInjection(Server.class);
//        bind(DAO.class).to(DAOImpl.class).in(Singleton.class);
//    }
//
//}

public class MyAppModule extends ServletModule {
    static final String CONTEXXT_PATH_APPMODULE = "/nodekeeper/nodekeeper";
    
    protected void configureServlets() {
        serve(CONTEXXT_PATH_APPMODULE).with(NodeKeeperServiceImpl.class);
    }
}
