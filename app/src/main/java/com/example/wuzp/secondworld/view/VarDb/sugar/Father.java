package com.example.wuzp.secondworld.view.VarDb.sugar;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

/**
 * Created by wuzp on 2017/7/4.
 * sugar对应的bean 是需要继承sugarRecord 并实现Parcebale的接口
 * 需要创建一个static final 的CREATOR  的类
 *
 * 1.创建的表中 没有主键  在Greendao Ormlite 中都提供的主键注解，但是现在却是没有主键,Sugar 自己去创建一个主键
 */
//这里的序列化 应该不是 使用sugar的必须的操作可能是在网络数据转换时 使用到这个bean了 所以就实现了这个接口
    //使用sugar 时 在AS上 应该关闭instant run 关闭，不然创建表不成功 不知道啥原因
public class Father extends SugarRecord implements Parcelable {

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "hobby")
    private String hobby;

    public Father(){}

    public Father(String name,int age,String hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    //提供这样的方法
    //按照定义的顺序来实现 对属性的获取
    public Father(Parcel in){
        name = in.readString();
        age  = in.readInt();
        hobby = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(hobby);
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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "{name:" + name +",age:" + age + ",hobby:" + hobby + "}";
    }

    //实现Parcebale的接口 就需要提供这样一个静态的final的creator 内部静态类 并且需要再bean中 提供读和写的两个方法
    public static final Creator<Father> CREATOR = new Creator<Father>() {
        @Override
        public Father createFromParcel(Parcel in) {
            return new Father(in);
        }

        @Override
        public Father[] newArray(int size) {
            return new Father[size];
        }
    };
}
