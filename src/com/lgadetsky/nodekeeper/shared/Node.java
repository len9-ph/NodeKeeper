package com.lgadetsky.nodekeeper.shared;

import java.io.Serializable;

/**
 * Bean 
 * @author Leonid Gadetsky
 *
 */
public class Node implements Serializable{
    private static final long serialVersionUID = 3927932427045891397L;
    
    private static int count = 0;
    private Integer id;
    private Integer parentId;
    private String name;
    private String ip;
    private String port;
    private boolean isDeleted;
    
    // Constructor for root node
    public Node() {
        this.id = -1;
        this.parentId = -1;
        this.name = "new root";
    }
    
    // Constructor for child node
    public Node(int parentId) {
        this.id = -1;
        this.parentId = parentId;
        this.name = "new child";
    }
    
    public void setDeleted(boolean flag) {
        this.isDeleted = flag;
    }
    
    public boolean isDeleted() {
        return this.isDeleted;
    }
    
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
    
    public void setId() {
        this.id = count++;
    }
    
    public Integer getId() {
        return id;
    }
    
    public Integer getParentId() {
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
