package com.example.wuzp.secondworld.view.VarDb.ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wuzp on 2017/6/29.
 * 1.添加两个 jar 一个是Android 一个 是core
 * 2.注解生成一张表
 */
@DatabaseTable(tableName = "TABLE_USER")
public class User {
    //自动注解解释成 自增的 id列
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "age")
    private int age;

    public User(){}

    public User(String name,int age){
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "{_id:" + id + ";name:"+ name +";age:" + age + "}\n";
    }
}
