package com.example.wuzp.secondworld.view.xml;

/**
 * Created by wuzp on 2017/6/7.
 */

public class NodeBean {
    public String node_id;
    public String node_name;
    public String node_address;

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public String getNode_address() {
        return node_address;
    }

    public void setNode_address(String node_address) {
        this.node_address = node_address;
    }

    @Override
    public String toString() {
        return "{node_id:" + node_id + ";node_name:" + node_name
                + ";node_address:" + node_address + "}";
    }
}
