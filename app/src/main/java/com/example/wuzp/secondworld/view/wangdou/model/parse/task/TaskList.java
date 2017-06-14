package com.example.wuzp.secondworld.view.wangdou.model.parse.task;


import com.example.wuzp.secondworld.view.wangdou.model.parse.Common;

import java.util.List;

/**
 * Created by zhouyibo on 2017/4/25.
 */

public class TaskList extends Common {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Cloneable{
        /**
         * task_id : 1
         * task_name : 新手福利
         * task_cate_id : 1
         * task_cate_name : 成长|日常
         * task_intro : 任务说明
         * task_status : 1
         * task_status_name : 未完成|去完成|已领取|未领取
         * url_redirect_code : 1
         * vouchers_price : 100
         * complete_progress : 1
         * complete_complete : 1
         * create_time : 0
         * tips : {"task_name":"新手福利","task_intro":"任务说明"}
         */

        private int task_id;
        private int task_type;
        private String task_name;
        private int task_cate_id;
        private String task_cate_name;
        private String task_intro;
        private int task_status;
        private String task_status_name;
        private int url_redirect_code = 0;
        private String url_redirect = "";
        private String vouchers_price;
        private int complete_progress;
        private int complete_condition;
        private String create_time;
        private TipsBean tips;
        private int count = 1;

        public int getTask_type() {
            return task_type;
        }

        public void setTask_type(int task_type) {
            this.task_type = task_type;
        }

        public String getUrl_redirect() {
            return url_redirect;
        }

        public void setUrl_redirect(String url_redirect) {
            this.url_redirect = url_redirect;
        }

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }

        public String getTask_name() {
            return task_name;
        }

        public void setTask_name(String task_name) {
            this.task_name = task_name;
        }

        public int getTask_cate_id() {
            return task_cate_id;
        }

        public void setTask_cate_id(int task_cate_id) {
            this.task_cate_id = task_cate_id;
        }

        public String getTask_cate_name() {
            return task_cate_name;
        }

        public void setTask_cate_name(String task_cate_name) {
            this.task_cate_name = task_cate_name;
        }

        public String getTask_intro() {
            return task_intro;
        }

        public void setTask_intro(String task_intro) {
            this.task_intro = task_intro;
        }

        public int getTask_status() {
            return task_status;
        }

        public void setTask_status(int task_status) {
            this.task_status = task_status;
        }

        public String getTask_status_name() {
            return task_status_name;
        }

        public void setTask_status_name(String task_status_name) {
            this.task_status_name = task_status_name;
        }

        public int getUrl_redirect_code() {
            return url_redirect_code;
        }

        public void setUrl_redirect_code(int url_redirect_code) {
            this.url_redirect_code = url_redirect_code;
        }

        public String getVouchers_price() {
            return vouchers_price;
        }

        public void setVouchers_price(String vouchers_price) {
            this.vouchers_price = vouchers_price;
        }

        public int getComplete_progress() {
            return complete_progress;
        }

        public void setComplete_progress(int complete_progress) {
            this.complete_progress = complete_progress;
        }

        public int getComplete_condition() {
            return complete_condition;
        }

        public void setComplete_condition(int complete_condition) {
            this.complete_condition = complete_condition;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public TipsBean getTips() {
            return tips;
        }

        public void setTips(TipsBean tips) {
            this.tips = tips;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public static class TipsBean {
            /**
             * task_name : 新手福利
             * task_intro : 任务说明
             */

            private String task_name;
            private String task_intro;

            public String getTask_name() {
                return task_name;
            }

            public void setTask_name(String task_name) {
                this.task_name = task_name;
            }

            public String getTask_intro() {
                return task_intro;
            }

            public void setTask_intro(String task_intro) {
                this.task_intro = task_intro;
            }
        }
        public static int compare(Object o1, Object o2, int tag) {
            if (((DataBean) o1).getTask_cate_id() != ((DataBean) o2).getTask_cate_id() ) {
                return (((DataBean) o1).getTask_cate_id()  - ((DataBean) o2).getTask_cate_id() ) * tag > 0 ? -1 : 1;
            }
            if (((DataBean) o1).getTask_status() != 1 && ((DataBean) o2).getTask_status() == 1) {
                return -1;
            }
            if (((DataBean) o1).getTask_status() == 1 && ((DataBean) o2).getTask_status() != 1) {
                return 1;
            }
            return ((DataBean) o1).getTask_id() - ((DataBean) o2).getTask_id() > 0 ? 1 : -1;
        }

        @Override
        public Object clone() {
            DataBean bean = null;
            try {
                bean = (DataBean) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return bean;
        }
        @Override
        public boolean equals(Object obj) {
            return (this.task_id == ((DataBean) obj).getTask_id()
                    && this.task_status == ((DataBean) obj).getTask_status()
                    && this.complete_progress == ((DataBean) obj).getComplete_progress()
                    && this.count == ((DataBean) obj).getCount());
        }

    }
}
