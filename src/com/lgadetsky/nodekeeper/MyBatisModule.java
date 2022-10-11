package com.lgadetsky.nodekeeper;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//import org.mybatis.guice.XMLMyBatisModule;
//
//public class MyBatisModule extends XMLMyBatisModule{
//    @Override
//    protected void initialize() {
//        try (FileInputStream input = new FileInputStream("com/lgadetsky/nodekeeper/resources/db.txt")) {
//            Properties properties = new Properties();
//            properties.load(input);
//            setClassPathResource("NodeKeeper/mybatis-config.xml");
//            addProperties(properties);
//        } catch (IOException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }
//
//}

import java.util.Properties;

import org.mybatis.guice.XMLMyBatisModule;


public class MyBatisModule extends XMLMyBatisModule {
    private static Properties props = new Properties();
    
    static {
        props.setProperty("JDBC.username", "root");
        props.setProperty("JDBC.password", "123");
    }
    
    @Override
    protected void initialize() {
        setClassPathResource("com/lgadetsky/nodekeeper/mybatis-config.xml");
        addProperties(props);
    }
    
}
