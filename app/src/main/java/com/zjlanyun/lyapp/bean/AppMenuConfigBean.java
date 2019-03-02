package com.zjlanyun.lyapp.bean;

import java.util.List;

/**
 * Created by MDZZ on 2019-02-28.
 */

public class AppMenuConfigBean {
    private int code;
    private String msg;
    private List<ChildrenBean> data;

    public int getCode() {return code;}
    public void setCode(int code) {this.code = code;}
    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}
    public List<ChildrenBean> getData() {return data;}
    public void setData(List<ChildrenBean> data) {this.data = data;}
    public static class ChildrenBean {
        private int id;
        private String menu_code;
        private int iGrade;
        private String name;
        private int act_id;
        private boolean bEnd;
        private List<ChildrenBean> Children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMenu_code() {
            return menu_code;
        }

        public void setMenu_code(String menu_code) {
            this.menu_code = menu_code;
        }

        public int getIGrade() {
            return iGrade;
        }

        public void setIGrade(int iGrade) {
            this.iGrade = iGrade;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAct_id() {
            return act_id;
        }

        public void setAct_id(int act_id) {
            this.act_id = act_id;
        }

        public boolean isBEnd() {
            return bEnd;
        }

        public void setBEnd(boolean bEnd) {
            this.bEnd = bEnd;
        }

        public List<ChildrenBean> getChildren() {
            return Children;
        }

        public void setChildren(List<ChildrenBean> Children) {
            this.Children = Children;
        }
    }
}
