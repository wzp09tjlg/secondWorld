package com.example.wuzp.secondworld.view.huasheng.builder;

/**
 * Created by wuzp on 2017/3/30.
 * 从网上下载的User这个类 建造者模式
 * User user =
  new User.UserBuilder("Jack","10086")
 .age(25)
 .address("GuangZhou")
 .phone("13800138000")
 .build();

 UserBuilder 是一个静态的类 专门用于存放各个属性
 在创建User的时候 只需要将UserBuilder中的各个属性复制过来就可以了
 静态的类 创建的对象也是静态变量，这种builder的方式 只会存在这个静态类，不会创建这个静态类的对象(理解一下)。
 在类中声明的final 变量可以不在声明的时候 赋值,在构造函数中再赋值 也是可以的。
 */
public class User {
    private final String name;         //必选
    private final String cardID;       //必选
    private final int age;             //可选
    private final String address;      //可选
    private final String phone;        //可选

    private User(UserBuilder userBuilder){
        this.name=userBuilder.name;
        this.cardID=userBuilder.cardID;
        this.age=userBuilder.age;
        this.address=userBuilder.address;
        this.phone=userBuilder.phone;
    }

    public String getName() {
        return name;
    }

    public String getCardID() {
        return cardID;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    //静态的类 在User类中只会保存一个 在创建User的时候，无论创建多少回个User
    // 都会使用UserBuilder静态类 来赋值。
    public static class UserBuilder{
        private final String name;
        private final String cardID;
        private int age;
        private String address;
        private String phone;

        public UserBuilder(String name,String cardID){
            this.name=name;
            this.cardID=cardID;
        }

        public UserBuilder age(int age){
            this.age=age;
            return this;
        }

        public UserBuilder address(String address){
            this.address=address;
            return this;
        }

        public UserBuilder phone(String phone){
            this.phone=phone;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
