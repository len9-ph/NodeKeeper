package com.lgadetsky.nodekeeper.shared;

import java.io.Serializable;
import java.util.Comparator;

import com.lgadetsky.nodekeeper.client.util.StringConstants;

/**
 * Bean
 * 
 * @author Leonid Gadetsky
 *
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 3927932427045891397L;

    private Integer id;
    private Integer parentId;
    private String name;
    private String ip;
    private String port;
    private boolean isDeleted;

    public Node() {
        this.id = -1;
        this.name = StringConstants.NEW_ROOT;
    }

    public Node(int parentId) {
        this.id = -1;
        this.parentId = parentId;
        this.name = StringConstants.NEW_CHILD;
    }

    public void setDeleted(boolean flag) {
        this.isDeleted = flag;
    }

    public boolean isDeleted() {
        return this.isDeleted;
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

    public static final Comparator<Node> COMPARE_BY_ID = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };
}
