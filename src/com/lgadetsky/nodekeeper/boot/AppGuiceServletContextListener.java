package com.lgadetsky.nodekeeper.boot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.lgadetsky.nodekeeper.AppModule;
import com.lgadetsky.nodekeeper.MyBatisModule;

public class AppGuiceServletContextListener extends GuiceServletContextListener{

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new AppModule(), new MyBatisModule(), new ServletModule() {

            @Override
            protected void configureServlets() {
                
            }
        });
    }
    
    
//void test {
//    Properties myBatisProperties = new 
//}
}
