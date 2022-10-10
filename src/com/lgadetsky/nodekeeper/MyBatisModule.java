package com.lgadetsky.nodekeeper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mybatis.guice.XMLMyBatisModule;

public class MyBatisModule extends XMLMyBatisModule{
    @Override
    protected void initialize() {
        try (InputStream input = new FileInputStream("/NodeKeeper/db.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            setClassPathResource("/NodeKeeper/mybatis-config.xml");
            addProperties(properties);
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
