package com.example.wuzp.secondworld.view.xml;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wuzp.secondworld.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/6/7.
 * 使用sax 解析xml
 * 使用pull 解析xml
 */
public class XmlActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnParse;
    private List<NodeBean> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        initView();
    }

    private void initView(){
        btnParse = (Button)findViewById(R.id.btn_parse);
        initData();
    }

    private void initData(){
        btnParse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_parse:
                //parseXml();
               //List<studentBean> list = pullParseXml();
                //Log.e("wzp","size:" + list.size());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<studentBean> list = doPullParseXml();
                        Log.e("wzp","size:" + list.size());
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    private void parseXml(){
        try{
            //这是解析assete 中json的方法
          /*  AssetManager assetManager = getResources().getAssets();
            InputStream inputStream = assetManager.open("jsonBean.json");
            Gson gson = new Gson();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            JsonReader reader = new JsonReader(isReader);*/
            //这是解析assete中xml的解析方法
            /*AssetManager assetManager = getResources().getAssets();
            InputStream inputStream = assetManager.open("nodes.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XmlSaxHandler dh = new XmlSaxHandler(callback);//解析结束后存入数据库
            parser.parse(inputStream, dh);*/

            //这是使用pull的方式来解析xml （晚上继续学习pull解析xml）

        }catch (Exception e){
            Log.e("wzp","e:"+e.getMessage());

        }
    }

    private ParseEndCallback callback = new ParseEndCallback(){
        //内部的接口 也会隐式的获取上下文，因为要获取得到上下文中的 一些资源，所以就必须在获取上下文之后才能得到
        @Override
        public void parseCallback(List<NodeBean> datas) {
            data = datas;
            int count = datas.size();
            for(int i=0;i<count;i++){
                Log.e("wzp","bean:" + data.get(i).toString()) ;
            }
        }
    };

    //pull解析xml 基于标签的事件(每一个标签都有开始和结束(一般情况下正常如此 不正常就待测试))
    //然后获取标签的值(如果标签中有属性，那么再者标签下 就应该获取相应的属性)
    public List<studentBean> pullParseXml(){
        List<studentBean> lists=null;
        studentBean studentBean=null;

        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            //获取XmlPullParser实例
            XmlPullParser pullParser=factory.newPullParser();
            AssetManager assetManager = getResources().getAssets();

            InputStream in=assetManager.open("students.xml");
            pullParser.setInput(in, "UTF-8");
            //开始
            int eventType=pullParser.getEventType();

            //基于轮训的事件
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=pullParser.getName();
                switch (eventType) {
                    //文档开始
                    case XmlPullParser.START_DOCUMENT:
                        lists=new ArrayList<studentBean>();
                        break;

                    //开始节点
                    case XmlPullParser.START_TAG:
                        if("student".equals(nodeName)){
                            studentBean=new studentBean();
                            studentBean.setId(pullParser.getAttributeValue(0));
                            studentBean.setGroup(pullParser.getAttributeValue(1));
                        }else if("name".equals(nodeName)){
                            studentBean.setName(pullParser.nextText());
                        }else if("sex".equals(nodeName)){
                            studentBean.setSex(pullParser.nextText());
                        }else if("age".equals(nodeName)){
                            studentBean.setAge((pullParser.nextText()));
                        }else if("email".equals(nodeName)){
                            studentBean.setEmail(pullParser.nextText());
                        }else if("birthday".equals(nodeName)){
                            studentBean.setBirthday(pullParser.nextText());
                        }else if("memo".equals(nodeName)){
                            studentBean.setMemo(pullParser.nextText());
                        }
                        break;
                    //结束节点
                    case XmlPullParser.END_TAG:
                        if("student".equals(nodeName)){
                            lists.add(studentBean);
                            studentBean=null;
                        }
                        break;
                    default:
                        break;
                }
                // 手动的触发下一个事件
                eventType=pullParser.next();//轮训的查找下一个事件 然后进行处理，知道最后的一个标签
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }

    private List<studentBean> doPullParseXml(){
        List<studentBean> nodeBeanList = new ArrayList<>();//java7 中的泛型推断
        studentBean tempBean = null;
       try{
           XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
           XmlPullParser pullParser = factory.newPullParser();
           AssetManager assetManager = getAssets();
           InputStream inputStream = assetManager.open("students.xml");

           pullParser.setInput(inputStream,"UTF-8");

           int eventType = pullParser.getEventType();
           while(eventType != XmlPullParser.END_DOCUMENT){
               switch (eventType){
                   case XmlPullParser.START_DOCUMENT:
                       break;
                   case XmlPullParser.START_TAG:
                       String tagName = pullParser.getName();
                       switch (tagName){
                           case "student":
                               tempBean = new studentBean();
                               tempBean.setId(pullParser.getAttributeValue(0));
                               tempBean.setGroup(pullParser.getAttributeName(1));
                               break;
                           case "name":
                               if(tempBean != null)
                                   tempBean.setName(pullParser.getText());
                               break;
                           case "sex":
                               if(tempBean != null)
                                   tempBean.setSex(pullParser.getText());
                               break;
                           case "age":
                               if(tempBean != null)
                                   tempBean.setAge(pullParser.getText());
                               break;
                           case "email":
                               if(tempBean != null)
                                   tempBean.setEmail(pullParser.getText());
                               break;
                           case "birthday":
                               if(tempBean != null)
                                   tempBean.setBirthday(pullParser.getText());
                               break;
                           case "memo":
                               if(tempBean != null)
                                   tempBean.setMemo(pullParser.getText());
                               break;
                       }
                       break;
                   case XmlPullParser.END_TAG:
                       String name = pullParser.getName();
                       switch(name){
                           case "student":
                               if(tempBean != null)
                               nodeBeanList.add(tempBean);
                               break;
                       }
                       break;
               }

               eventType =  eventType=pullParser.next();///调用的是next的方法 来获取当前的状态
           }
       }catch (Exception e){}
        return  nodeBeanList;
    }

}
