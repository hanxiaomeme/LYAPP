package com.zjlanyun.lyapp.config;


/**
 * 作者：韩笑 on 2017/6/27 0027 08:39
 * <p>
 * 作用：常量类
 */


public class UtilConstants {


    public static final String USER_TYPE_INTERIOR = "1";
    public static final String USER_TYPE_CUSTOMER = "2";
    public static final String USER_TYPE_SUPPLIER = "3";


    public static final String ACTIVITY_INTENT_BILLSNAME = "bills_name";
    public static final String ACTIVITY_INTENT_MODELID = "model_id";
    public static final String ACTIVITY_INTENT_ACTID = "act_id";
    public static final String ACTIVITY_INTENT_ACTION = "action";

    public static final int ACTIVITY_PHONE_SCANQRCODE = 1;
    public static final int REQUESTCODE_CREATE = 2;


    public static final String FIELD_NOTCLEAN = "notclean";//字段点击保存之后不清空
    public static final String FIELD_AUTOCHOISEBILL = "autochoisebill";//字段带有选单功能
    public static final String FIELD_MANY2ONECONTRAST = "many2one_contrast";//many2one字段扫描新的值会进行对比提示
    public static final String FIELD_STOPMANY2ONECONTRAST = "many2one_contrast_stop";//many2one字段扫描新的值会进行对比提示,不同则停止
    public static final String FIELD_CANINPUT = "input";//如果字段配置了任何情况下可输入，那么就在任何情况下都可输入，审核除外
    public static final String MULIT_SELECTION = "multi_selection";//String类型的字段可以进行多选
    public static final String SINGLE_SELECTION = "single_selection";//
    public static final String NOT_DUPLICATED  = "not_duplicated";//该字段在该单据不可重复

}
