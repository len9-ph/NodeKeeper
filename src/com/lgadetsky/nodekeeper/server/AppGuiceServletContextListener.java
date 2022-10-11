package com.lgadetsky.nodekeeper.server;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.lgadetsky.nodekeeper.MyAppModule;
import com.lgadetsky.nodekeeper.MyBatisModule;


//
//import java.util.Properties;
//
//import org.mybatis.guice.XMLMyBatisModule;
//
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import com.google.inject.Module;
//import com.google.inject.servlet.GuiceServletContextListener;
//import com.google.inject.servlet.ServletModule;
//import com.lgadetsky.nodekeeper.MyAppModule;
//
//public class AppGuiceServletContextListener extends GuiceServletContextListener {
//    static final String NODEKEEPER_PATH_MODULE = "/nodekeeper";
//    
//    @Override
//    protected Injector getInjector() {
//        return Guice.createInjector(new Module[] { new MyAppModule(), new XMLMyBatisModule() {
//
//            @Override
//            protected void initialize() {
//                // TODO Auto-generated method stub
//                Properties props = new Properties();
//                props.setProperty("JDBC.driver", "org.postgresql.Driver");
//                props.setProperty("JDBC.url", "jdbc:postgresql://localhost:5432/Node");
//                props.setProperty("JDBC.username", "root");
//                props.setProperty("JDBC.password", "123");
//                
//                setClassPathResource("mybatis-config.xml");
//                addProperties(props);
//                
//            }
//            
//        }, new ServletModule() {
//            @Override
//            protected void configureServlets() {
//                super.configureServlets();
//                serve(NODEKEEPER_PATH_MODULE).with(NodeKeeperServiceImpl.class);
//            }
//        } });
//    }
//}

public class AppGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new MyAppModule(),
                new MyBatisModule()
                );
    }
    
}



