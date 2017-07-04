package com.example.wuzp.secondworld.view.VarDb.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wuzp on 2017/7/3.
 * 自定义一个bean之后 自动那个会生成 这个bean中的其他数据及方法
 */
@Entity
public class ReaderBean {
    public static final int TYPE_STUDENT = 0X01;
    public static final int TYPE_TEACHER = 0X02;

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;
    private String address;
    private int type;

    @Generated(hash = 1422838430)
    public ReaderBean(Long id, String name, int age, String address, int type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.type = type;
    }

    @Generated(hash = 1365287976)
    public ReaderBean() {
    }

    @Override
    public String toString() {
        return "{id:" + id + ",name:" + name + ",age:" + age + "address:" + address + "}";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
