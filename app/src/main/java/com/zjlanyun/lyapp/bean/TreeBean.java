package com.zjlanyun.lyapp.bean;

import java.util.List;

/**
 * Created by MDZZ on 2019-03-02.
 */

public class TreeBean {

    /**
     * code : 0
     * data : {"list":[],"total":1,"pageCount":1,"data_sql":"exec api_pagination 'ICInStockBill ICInStockBill left join t_Checkstatus t292 on  t292.fvalue = ICInStockBill.FStatus  left join t_Confirm t1646 on  t1646.fvalue = ICInStockBill.Isconfirm','FInterID','ICInStockBill.FInterID,ICInStockBill.FBillNo,ICInStockBill.FDate,ICInStockBill.FBiller,ICInStockBill.FCheckDate, t292.fdisplay FStatus, t1646.fdisplay Isconfirm','FStatus asc,Isconfirm asc,FInterID desc',10,1,1,'FTranType =221 and ICInStockBill.FBillNo like ''%00025308%'''"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : []
         * total : 1
         * pageCount : 1
         * data_sql : exec api_pagination 'ICInStockBill ICInStockBill left join t_Checkstatus t292 on  t292.fvalue = ICInStockBill.FStatus  left join t_Confirm t1646 on  t1646.fvalue = ICInStockBill.Isconfirm','FInterID','ICInStockBill.FInterID,ICInStockBill.FBillNo,ICInStockBill.FDate,ICInStockBill.FBiller,ICInStockBill.FCheckDate, t292.fdisplay FStatus, t1646.fdisplay Isconfirm','FStatus asc,Isconfirm asc,FInterID desc',10,1,1,'FTranType =221 and ICInStockBill.FBillNo like ''%00025308%'''
         */

        private int total;
        private int pageCount;
        private String data_sql;
        private List<?> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public String getData_sql() {
            return data_sql;
        }

        public void setData_sql(String data_sql) {
            this.data_sql = data_sql;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
    }
}
