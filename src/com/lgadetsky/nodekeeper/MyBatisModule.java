package com.lgadetsky.nodekeeper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mybatis.guice.XMLMyBatisModule;

public class MyBatisModule extends XMLMyBatisModule {

    private final static Properties getConnectionPropeties(String path) {
        Properties myBatisProp = new Properties();

        try {
            InputStream input = new FileInputStream(path);
            myBatisProp.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return myBatisProp;
    }

    @Override
    protected void initialize() {
        setClassPathResource("com/lgadetsky/nodekeeper/mybatis-config.xml");
        addProperties(getConnectionPropeties("C:\\Users\\leoni\\git\\NodeKeeper\\src\\com\\lgadetsky\\nodekeeper\\db.properties"));
        setEnvironmentId("development");
    }

}
