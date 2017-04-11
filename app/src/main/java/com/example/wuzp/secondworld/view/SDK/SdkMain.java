package com.example.wuzp.secondworld.view.SDK;

import android.util.Log;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;

import java.io.IOException;
import java.util.Map;

/**
 * Created by wuzp on 2017/4/5.
 */

public class SdkMain {

    public static void main(String[] args){
        String paramurl = "https://api.jd.com/routerjson";
        String paramtoken = "775f5238-a519-4965-9c9c-65cf9fa82116";
        String paramkey = "49E02674580710F88932334B6FC6A2EE";
        String paramSecret = "afd717fd23c34376a86a2fe0e89cd6b5";
        DefaultJdClient client = new DefaultJdClient(
                paramurl,
                paramtoken,
                paramkey,
                paramSecret
        );
        try{
            AbstractResponse response =  client.execute(new JdRequest<AbstractResponse>() {
                @Override
                public String getApiMethod() {
                    return "jingdong.ware.write.add";
                }

                @Override
                public Map<String, String> getSysParams() {
                    return null;
                }

                @Override
                public String getAppJsonParams() throws IOException {
                    return null;
                }

                @Override
                public String getOtherParams() throws IOException {
                    return null;
                }

                @Override
                public Class<AbstractResponse> getResponseClass() {
                    return null;
                }
            });
            Log.e("wzp","response:" + response.toString());
        }catch (Exception e){}
    }
    String a = "{\n" +
            "    \"skus\": [\n" +
            "        {\n" +
            "            \"venderId\": \"654876\",\n" +
            "            \"wareId\": 0,\n" +
            "            \"skuId\": 0,\n" +
            "            \"status\": 1,\n" +
            "            \"saleAttrs\": [\n" +
            "                {\n" +
            "                    \"attrId\": \"1000026942\",\n" +
            "                    \"attrValueAlias\": [\n" +
            "                        \"浅蓝色\"\n" +
            "                    ],\n" +
            "                    \"attrValues\": [\n" +
            "                        \"1638017604\"\n" +
            "                    ],\n" +
            "                    \"index\": 1\n" +
            "                }\n" +
            "            ],\n" +
            "            \"features\": [\n" +
            "                {\n" +
            "                    \"featureCn\": \"测试\",\n" +
            "                    \"featureKey\": \"test\",\n" +
            "                    \"featureValue\": \"1\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"jdPrice\": 10000,\n" +
            "            \"outerId\": \"taobao544706828671\",\n" +
            "            \"stockNum\": 0,\n" +
            "            \"imgTag\": \"\",\n" +
            "            \"wareTitle\": \"2017春款厚底乐福鞋女平底真皮单鞋休闲板鞋编织一脚蹬女鞋牛皮\",\n" +
            "            \"categoryId\": 12348,\n" +
            "            \"logo\": \"jfs/t3094/275/7953148662/172156/838b396a/58bea3ecN9bb4be2f.jpg\",\n" +
            "            \"skuName\": \"2017春款厚底乐福鞋女平底真皮单鞋休闲板鞋编织一脚蹬女鞋牛皮\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"ware\": {\n" +
            "        \"title\": \"2017春款厚底乐福鞋女平底真皮单鞋休闲板鞋编织一脚蹬女鞋牛皮\",\n" +
            "        \"venderId\": \"654876\",\n" +
            "        \"categoryId\": 700,\n" +
            "        \"brandId\": \"222294\",\n" +
            "        \"templateId\": \"\",\n" +
            "        \"transportId\": \"\",\n" +
            "        \"wareStatus\": 1,\n" +
            "        \"outerId\": \"taobao544706828671\",\n" +
            "        \"itemNum\": \"taobao544706828671\",\n" +
            "        \"barCode\": \"123\",\n" +
            "        \"wareLocation\": 1,\n" +
            "        \"colType\": 0,\n" +
            "        \"delivery\": 1,\n" +
            "        \"adWords\": {\n" +
            "            \"url\": \"http://jd.com/index.html\",\n" +
            "            \"urlWords\": \"京东\",\n" +
            "            \"words\": \"京东万岁\"\n" +
            "        },\n" +
            "        \"wrap\": \"1\",\n" +
            "        \"packListing\": \"11\",\n" +
            "        \"length\": 1,\n" +
            "        \"width\": 1,\n" +
            "        \"height\": 1,\n" +
            "        \"weight\": 0.1,\n" +
            "        \"props\": [\n" +
            "            {\n" +
            "                \"attrId\": 581,\n" +
            "                \"attrValues\": [\n" +
            "                    2137\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 586,\n" +
            "                \"attrValues\": [\n" +
            "                    2216\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 11759,\n" +
            "                \"attrValues\": [\n" +
            "                    695619\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 129769,\n" +
            "                \"attrValues\": [\n" +
            "                    695615\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 129768,\n" +
            "                \"attrValues\": [\n" +
            "                    695602\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 10129739,\n" +
            "                \"attrValues\": [\n" +
            "                    10695539\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 10129736,\n" +
            "                \"attrValues\": [\n" +
            "                    10695533\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 10129732,\n" +
            "                \"attrValues\": [\n" +
            "                    10695511\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 10129731,\n" +
            "                \"attrValues\": [\n" +
            "                    1\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attrId\": 10129730,\n" +
            "                \"attrValues\": [\n" +
            "                    10695625\n" +
            "                ]\n" +
            "            }\n" +
            "        ],\n" +
            "        \"images\": [\n" +
            "            {\n" +
            "                \"colorId\": \"0000000000\",\n" +
            "                \"imgId\": \"6646686738\",\n" +
            "                \"imgIndex\": \"1\",\n" +
            "                \"imgUrl\": \"jfs/t4672/247/1414401484/500736/c7ba20e/58de171eN3ced5e4f.jpg\",\n" +
            "                \"imgZoneId\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"colorId\": \"0000000000\",\n" +
            "                \"imgId\": \"6646686738\",\n" +
            "                \"imgIndex\": \"2\",\n" +
            "                \"imgUrl\": \"jfs/t4672/247/1414401484/500736/c7ba20e/58de171eN3ced5e4f.jpg\",\n" +
            "                \"imgZoneId\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"colorId\": \"0000000000\",\n" +
            "                \"imgId\": \"6646686738\",\n" +
            "                \"imgIndex\": \"3\",\n" +
            "                \"imgUrl\": \"jfs/t4672/247/1414401484/500736/c7ba20e/58de171eN3ced5e4f.jpg\",\n" +
            "                \"imgZoneId\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"colorId\": \"0000000000\",\n" +
            "                \"imgId\": \"6646686738\",\n" +
            "                \"imgIndex\": \"4\",\n" +
            "                \"imgUrl\": \"jfs/t4672/247/1414401484/500736/c7ba20e/58de171eN3ced5e4f.jpg\",\n" +
            "                \"imgZoneId\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"colorId\": \"0000000000\",\n" +
            "                \"imgId\": \"6646686738\",\n" +
            "                \"imgIndex\": \"5\",\n" +
            "                \"imgUrl\": \"jfs/t4672/247/1414401484/500736/c7ba20e/58de171eN3ced5e4f.jpg\",\n" +
            "                \"imgZoneId\": \"1\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"mobileDesc\": \"test\",\n" +
            "        \"introduction\": \"test\",\n" +
            "        \"afterSales\": \"ceshi\",\n" +
            "        \"logo\": \"\",\n" +
            "        \"marketPrice\": \"100\",\n" +
            "        \"costPrice\": 100,\n" +
            "        \"jdPrice\": \"100\",\n" +
            "        \"brandName\": \"荣耀\",\n" +
            "        \"stockNum\": 0,\n" +
            "        \"categorySecId\": 0,\n" +
            "        \"saleAttrs\": \"1,1\",\n" +
            "        \"shopId\": 650536,\n" +
            "        \"promiseId\": 0\n" +
            "    }\n" +
            "}";
}
