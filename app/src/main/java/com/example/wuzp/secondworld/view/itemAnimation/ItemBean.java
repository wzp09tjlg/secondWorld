package com.example.wuzp.secondworld.view.itemAnimation;

/**
 * Created by wuzp on 2017/6/16.
 */

public class ItemBean {
    private int id;
    private int icon;
    private String name;
    private int sort;

    public  ItemBean(){}

    public ItemBean(int id,int icon,String name){
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.sort = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "{icon:" + icon + ";name:" + name + "}";
    }
}
