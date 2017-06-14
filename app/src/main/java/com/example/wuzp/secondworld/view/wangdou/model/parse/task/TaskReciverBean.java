package com.example.wuzp.secondworld.view.wangdou.model.parse.task;


import com.example.wuzp.secondworld.view.wangdou.model.parse.Common;

/**
 * Created by zhouyibo on 2017/4/24.
 */

public class TaskReciverBean extends Common {

    /**
     * data : {"task_id":1,"task_name":"新手福利","task_status":1,"vouchers_price":100}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * task_id : 1
         * task_name : 新手福利
         * task_status : 1
         * vouchers_price : 100
         */

        private int task_id;
        private String task_name;
        private int task_status;
        private double vouchers_price;

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

        public int getTask_status() {
            return task_status;
        }

        public void setTask_status(int task_status) {
            this.task_status = task_status;
        }

        public double getVouchers_price() {
            return vouchers_price;
        }

        public void setVouchers_price(int vouchers_price) {
            this.vouchers_price = vouchers_price;
        }
    }
}
