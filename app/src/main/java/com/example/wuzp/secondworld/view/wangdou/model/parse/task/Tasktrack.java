package com.example.wuzp.secondworld.view.wangdou.model.parse.task;


import com.example.wuzp.secondworld.view.wangdou.model.parse.Common;

import java.util.List;

/**
 * Created by zhouyibo on 2017/4/25.
 */

public class Tasktrack extends Common {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * task_id : 1
         * task_type : 1
         * condition_id : 1
         * complete_condition : 1
         * complete_progress : 1
         * condition_status : 1
         */

        private String task_id;
        private int condition_type;
        private String condition_id;
        private int complete_condition;
        private int complete_progress;
        private int condition_status;
        private boolean receive = false;

        public boolean isReceive() {
            return receive;
        }

        public void setReceive(boolean receive) {
            this.receive = receive;
        }

        public String getTask_id() {
            return task_id;
        }

        public void setTask_id(String task_id) {
            this.task_id = task_id;
        }

        public int getTask_type() {
            return condition_type;
        }

        public void setTask_type(int task_type) {
            this.condition_type = task_type;
        }

        public String getCondition_id() {
            return condition_id;
        }

        public void setCondition_id(String condition_id) {
            this.condition_id = condition_id;
        }

        public int getComplete_condition() {
            return complete_condition;
        }

        public void setComplete_condition(int complete_condition) {
            this.complete_condition = complete_condition;
        }

        public int getComplete_progress() {
            return complete_progress;
        }

        public void setComplete_progress(int complete_progress) {
            this.complete_progress = complete_progress;
        }

        public int getCondition_status() {
            return condition_status;
        }

        public void setCondition_status(int condition_status) {
            this.condition_status = condition_status;
        }
    }
}
