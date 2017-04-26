package com.example.wuzp.secondworld.network.parse;

import java.util.List;

/**
 * Created by wuzp on 2017/4/26.
 * 使用GsonFormat 的插件来实现bean的构建
 */
public class ShowBean {
    /**
     * error_code : 0
     * tip_msg : 操作成功
     * data : [{"id":"48","tabs_name":"热门"},{"id":"12","tabs_name":"世间情"},{"id":"4","tabs_name":"烧脑族"},{"id":"34","tabs_name":"幻想"},{"id":"14","tabs_name":"青春派"},{"id":"26","tabs_name":"怪谈"},{"id":"16022","tabs_name":"同人"}]
     */
    private int error_code;
    private String tip_msg;
    private List<DataBean> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getTip_msg() {
        return tip_msg;
    }

    public void setTip_msg(String tip_msg) {
        this.tip_msg = tip_msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 48
         * tabs_name : 热门
         */

        private String id;
        private String tabs_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTabs_name() {
            return tabs_name;
        }

        public void setTabs_name(String tabs_name) {
            this.tabs_name = tabs_name;
        }
    }
}
