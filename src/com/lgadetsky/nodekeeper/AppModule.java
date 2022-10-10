package com.lgadetsky.nodekeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;

import com.google.inject.servlet.ServletModule;
import com.lgadetsky.nodekeeper.server.NodeKeeperServiceImpl;

public class AppModule extends ServletModule{
    static final String NODEKEEPER_PATH_MODULE = "/nodekeeper/nodekeeper";
    
    @Override
    protected void configureServlets() {
        bind(Log.class).to(Log4JLogger.class);
        
        serve(NODEKEEPER_PATH_MODULE).with(NodeKeeperServiceImpl.class);
    }
}
