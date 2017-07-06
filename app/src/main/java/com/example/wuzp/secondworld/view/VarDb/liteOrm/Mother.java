package com.example.wuzp.secondworld.view.VarDb.liteOrm;

import android.os.Parcel;
import android.os.Parcelable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by wuzp on 2017/7/4.
 *
 @Table("test_model") 表名
 @PrimaryKey(AssignType.AUTO_INCREMENT) 主键自增长
 @PrimaryKey(AssignType.BY_MYSELF) 自己设置主键
 @Ignore 忽略该字段，不存入数据库
 @Column("login") 指定列名
 @Collate("NOCASE") 大小写无关
 *
 *
 */
//为了bean 能方便在网络的时候 序列化 继承parcebale 接口
@Table("MOTHER")
public class Mother  implements Parcelable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long _id;
    @Column("name")
    private String name;
    @Column("age")
    private int age;
    @Column("hobby")
    private String hobby;
    @Column("address")
    private String address;

    public Mother(){}

    public Mother(String name,int age,String hobby,String address){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.address = address;
    }

    public Mother(Parcel source){
        name = source.readString();
        age  = source.readInt();
        hobby = source.readString();
        address = source.readString();
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
        dest.writeString(address);
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "{name:" + name + ",age:" + age + ",hobby:" + hobby + ",address:" + address + "}";
    }

    public static final Creator<Mother> CREATOR = new Creator<Mother>() {
        @Override
        public Mother createFromParcel(Parcel source) {
            return new Mother(source);
        }

        @Override
        public Mother[] newArray(int size) {
            return new Mother[0];
        }
    };
}
