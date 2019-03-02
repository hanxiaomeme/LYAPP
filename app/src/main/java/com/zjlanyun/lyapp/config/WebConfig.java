package com.zjlanyun.lyapp.config;

import com.zjlanyun.lyapp.utils.SPData;

/**
 * Created by MDZZ on 2019-02-22.
 */

public class WebConfig {
//    public static final String SERVER_IP = SPData.getConfig().getString("serverUrl", "");

    public static final String SERVER_IP = "192.168.1.99:53556";
    //用户登录
    public static final String URL_LOGIN = "http://"+ SERVER_IP +"/api/User/Login";

    //更新配置,测试使用
    public static final String URL_UPDATESETTING_TEST = "http://192.168.2.190:91/api.aspx?action=connectConfig";

    //APP获取菜单
    public static final String URL_GETAPPMENU = "http://192.168.1.99:53556/api/SysConfig/GetAppMenu";


}
