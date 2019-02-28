package com.zjlanyun.lyapp.utils;


import com.blankj.utilcode.util.SPUtils;

import static com.blankj.utilcode.util.SPUtils.getInstance;

/**
 * @author: shun
 * @date: 2017-03-31 18:09
 * @Desc:
 */

public class SPData {

    /**
     * spUtilsUserinfo保存用户信息
     * spUtilsConfig保存配置信息
     */
    private static SPUtils spUtilsUserinfo, spUtilsConfig;

    public static SPUtils getUserinfo(){
        if (spUtilsUserinfo == null) {
            spUtilsUserinfo = getInstance("userInfo");
        }
        return spUtilsUserinfo;
    }

    public static SPUtils getConfig(){
        if (spUtilsConfig == null) {
            spUtilsConfig = getInstance("ly_config");
        }
        return spUtilsConfig;
    }
}