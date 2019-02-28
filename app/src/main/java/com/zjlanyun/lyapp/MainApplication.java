package com.zjlanyun.lyapp;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.zjlanyun.lyapp.greendao.DaoSession;

import dev.DevUtils;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.logger.LogConfig;
import dev.utils.app.logger.LogLevel;

/**
 * Created by MDZZ on 2019-02-20.
 */

public class MainApplication extends Application{
    // 日志TAG
    private final String TAG = "MainApplication";
    private static Context mContext;
    private static Application _instance;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        _instance = this;

        //日志打印工具初始化
        //初始化日志打印
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());

        //Http请求初始化
        NoHttp.initialize(this, new NoHttp.Config().setNetworkExecutor(new OkHttpNetworkExecutor()));
        Logger.setTag("NoHttp_LY");
        Logger.setDebug(true);

        // 初始化工具类
        DevUtils.init(this.getApplicationContext());
        Utils.init(this);


    }

    public static Context getContext() {
        return mContext;
    }
    public static Application getInstance() {
        return _instance;
    }
}
