package com.example.wuzp.secondworld.view.wangdou.model.parse.task;


import com.example.wuzp.secondworld.view.wangdou.model.parse.Common;

/**
 * Created by zhouyibo on 2017/4/25.
 */

public class TaskConditionComplete extends Common {

    /**
     * data : {"task_id":1,"task_name":"新手福利","task_status":1,"condition_id":1,"condition_status":1}
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
         * condition_id : 1
         * condition_status : 1
         */
        private int task_id;
        private String task_name;
        private int task_status;
        private int condition_id;
        private int condition_status;

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

        public int getCondition_id() {
            return condition_id;
        }

        public void setCondition_id(int condition_id) {
            this.condition_id = condition_id;
        }

        public int getCondition_status() {
            return condition_status;
        }

        public void setCondition_status(int condition_status) {
            this.condition_status = condition_status;
        }
    }
}
