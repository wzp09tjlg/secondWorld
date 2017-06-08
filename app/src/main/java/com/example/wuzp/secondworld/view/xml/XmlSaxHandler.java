package com.example.wuzp.secondworld.view.xml;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/6/7.
 * 使用sax 解析xml 需要实现解析内容的方法
 *
 DefaultHandler类
 这是安卓中内置的用于SAX处理XML的类，但是大多情况下我们都需要继承该类重写部分方法，才能达到处理XML数据的功能。

 startDocument方法
 这是第一个需要重写的方法，每处理一个XML文档都会响应一次。所以这个方法里可以写需要初始化的代码。

 startElement方法
 这是处理每个节点所触发的方法，通过这个方法你可以直接当前处理的节点的名称以及属性。

 characters方法
 当处理一个节点之间的文本时候触发该方法，但是该方法并不会告诉你当前文本的所属标签，而仅仅是告诉你文本内容。

 endElement方法
 遇到一个节点的结束标签时，将会出发这个方法，并且会传递结束标签的名称。

 endDocument方法
 如果当前的XML文档处理完毕后，将会触发该方法，在此方法内你可以将最终的结果保存并且销毁不需要使用的变量。
 *
 */
//准备解析一个list<Note>的数据
//xml中针对出现的标签 不能添加换行，因为出现换行之后 就会出现导致解析标签 多次调用charater
public class XmlSaxHandler extends DefaultHandler {
    private static final String TAG = "XmlSaxHandler";
    private List<NodeBean> nodes ;
    private NodeBean tempNode;
    private String tempElement = "";

    private ParseEndCallback callback;

    //初始化一些重要的处理
    public XmlSaxHandler(ParseEndCallback callback){
       nodes = new ArrayList<>();//这是Java7 中针对泛型的 推断的技术哟
        this.callback = callback;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.e("wzp","startDocument");
    }

    //在解析时会调用很多次
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);//空的方法 没有任何实现  可以不调用
        tempElement = qName;
        switch (qName){//在Java7 中可以使用String来组判断 之前只能使用char short int来做判断和处理
            case "root":
                break;
            case "node":
                tempNode = new NodeBean();
                break;
            case "node_id":
                break;
            case "node_name":
                break;
            case "node_address":
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String tempStr = new String(ch,start,length);
        //int len = tempStr.length();// \n 长度是1 \n\t 长度是2
        switch (tempElement){
            case "node":
                break;
            case "node_id":
                tempNode.setNode_id(tempStr);
                break;
            case "node_name":
                tempNode.setNode_name(tempStr);
                break;
            case "node_address":
                tempNode.setNode_address(tempStr);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        switch (qName){
            case "root":
                break;
            case "node":
                nodes.add(tempNode);
                break;
            case "node_id":
                break;
            case "node_name":
                break;
            case "node_address":
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.e("wzp","endDocument");
        if(callback != null && nodes != null)
          callback.parseCallback(nodes);
    }
}
