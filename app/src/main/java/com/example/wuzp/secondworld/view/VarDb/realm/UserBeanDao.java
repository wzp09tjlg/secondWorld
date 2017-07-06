package com.example.wuzp.secondworld.view.VarDb.realm;

/**
 * Created by wuzp on 2017/7/5.
 * 继承BaseDao来管理定义的表结构
 * realm 对数据表的操作 都是通过工具类的Dao来操作的
 */
public class UserBeanDao extends BaseDao {
/*
    private Realm realm;

    public UserBeanDao(Realm realm){
        super(realm);
        this.realm = realm;
    }

    */
/**
     * 单条保存demo
     *//*

    public boolean addOneTest() {
        boolean bl = false;
        try{
            realm.beginTransaction();
            //在数据库中创建一个对象，主键默认值为0
            UserBean user = realm.createObject(UserBean.class);//(类，主键)

            //更新数据库各自段的值
            user.setName("admin");
            //主键字段的值由0更新为55。而不是直接创建了一个id为55的对象
            user.setAge(55);
            //...
            realm.commitTransaction();
            bl = true;
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }

        */
/*try{
            realm.beginTransaction();
            UserBean user2 = new UserBean("hibrid", 26,"nv" ,"赣州");
            //不给id，会被默认为0
            //user2.setAge(102);
            UserBean userWithId = realm.copyToRealm(user2);
            realm.commitTransaction();
            bl = true;
        }catch (Exception e){
            e.printStackTrace();
            realm.cancelTransaction();
        }*//*

        return bl;

        */
/**
         * try{
         * realm.beginTranscation();
         *  UserBean bean  = new UserBean();
         *  bean....
         *
         * realm.commitTranscation();
         * //执行在一个事务中 所以就能保证操作再一个原子动作中国完成。但是如果这个动作执行的次数非常多，
         * }catch(Exception e){}
         *
         * *//*

    }

    public boolean init() {
        */
/**
         * 此处要注意，方法最后调用的是添加或者修改的方法。
         * 如果list的数据都不给id，则第一条记录添加成功后的id为0，后面的都在此基础上修改。
         * 最后的效果是，数据库只有一条记录，id为0，其他字段被更新为了最后一个对象的数据
         *//*

        List<UserBean> list = new ArrayList<>();
        list.add(new UserBean(0,"android", 20, "河南常德", "传菜员"));
        list.add(new UserBean(1,"angel", 21, "云南西双版纳", "飞行员"));
        list.add(new UserBean(2,"adidass", 28, "云南德克萨斯州", "海员"));
        list.add(new UserBean(3,"hijack", 39, "加州电厂", "厨师"));
        list.add(new UserBean(4,"hibrid", 26, "赣州", "贼"));
        list.add(new UserBean(5,"admin", 20, "湖北汉城", "程序员"));
        return saveOrUpdateBatch(list);
    }

    */
/**
     * 条件查询
     *
     * @return 返回结果集合
     *//*

    public RealmResults<UserBean> findByAnyParams(HashMap<Object, Object> params) {
        //realm.where(TestUser.class)
        //可跟查询条件
        //.or()                      或者
        //.beginsWith()              以xxx开头
        //.endsWith()                以xxx结尾
        //.greaterThan()             大于
        //.greaterThanOrEqualTo()    大于或等于
        //.lessThan()                小于
        //.lessThanOrEqualTo()       小于或等于
        //.equalTo()                 等于
        //.notEqualTo()              不等于
        //.findAll()                 查询所有
        //.average()                 平均值
        //.beginGroup()              开始分组
        //.endGroup()                结束分组
        //.between()                 在a和b之间
        //.contains()                包含xxx
        //.count()                   统计数量
        //.distinct()                去除重复
        //.findFirst()               返回结果集的第一行记录
        //.isNotEmpty()              非空串
        //.isEmpty()                 为空串
        //.isNotNull()               非空对象
        //.isNull()                  为空对象
        //.max()                     最大值
        //.maximumDate()             最大日期
        //.min()                     最小值
        //.minimumDate()             最小日期
        //.sum()                     求和
        return realm.where(UserBean.class).findAll();
    }
*/


}
