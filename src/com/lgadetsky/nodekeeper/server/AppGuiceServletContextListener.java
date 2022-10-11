package com.lgadetsky.nodekeeper.server;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.lgadetsky.nodekeeper.MyAppModule;
import com.lgadetsky.nodekeeper.MyBatisModule;

public class AppGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector( new Module[] {
                new MyAppModule(),
                new MyBatisModule() }
                );
    }
    
}



