package com.lgadetsky.nodekeeper.shared;

/**
 * Bean 
 * @author Leonid Gadetsky
 *
 */
public class Node {
    private static int count = 0;
    private int id;
    private int parentId;
    private String name;
    private String ip;
    private String port;
    
    public Node(int parentId, String name, String ip, String port) {
        this.id = count++;
        this.parentId = parentId;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
    
    public Node(String name, String ip, String port) {
        this.id = count++;
        this.parentId = -1;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
    
    public int getId() {
        return id;
    }
    
    public int getParentId() {
        return parentId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIp() {
        return ip;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
}
