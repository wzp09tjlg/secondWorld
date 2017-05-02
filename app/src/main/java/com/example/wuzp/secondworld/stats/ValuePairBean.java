package com.example.wuzp.secondworld.stats;

/**
 * Created by wuzp on 2017/5/2.
 */
public class ValuePairBean {
    private String name;
    private String value;

    public ValuePairBean(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{name:" + name + ";value:" + value + "}";
    }
}
