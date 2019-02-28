package com.zjlanyun.lyapp.utils;

import android.content.Context;
import android.text.TextUtils;

import com.zjlanyun.lyapp.MainApplication;
import com.zjlanyun.lyapp.greendao.DaoMaster;
import com.zjlanyun.lyapp.greendao.DaoSession;
import com.zjlanyun.lyapp.greendao.IrConfig;
import com.zjlanyun.lyapp.greendao.IrConfigDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * @author: shun
 * @date: 2017-03-17 11:10
 * @Desc:
 */

public class DBHelper {
    private static DBHelper instance;
    private static Context mContext;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, "lanyun.db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    private DBHelper() {
    }

    public static void init(Context context) {
        mContext = context;
        instance = new DBHelper();
        // 数据库对象
        DaoSession daoSession = getDaoSession(mContext);
    }

    public static DBHelper getInstance() {
        return instance;
    }

    /**
     * 获取配置表中的某个值
     * @param str
     * @return
     */
    public static String getIrConfigString(String str){
        QueryBuilder<IrConfig> queryBuilder = DBHelper.getDaoSession(MainApplication.getContext()).getIrConfigDao().queryBuilder();
        queryBuilder.where(IrConfigDao.Properties.Name.eq(str));
        List<IrConfig> list = queryBuilder.list();
        if (list.size() > 0) {
            return queryBuilder.list().get(0).getVal();
        } else {
            return "";
        }
    }

    public static int getIrConfigInt(String str, int defval){
        QueryBuilder<IrConfig> queryBuilder = DBHelper.getDaoSession(MainApplication.getContext()).getIrConfigDao().queryBuilder();
        queryBuilder.where(IrConfigDao.Properties.Name.eq(str));
        List<IrConfig> list = queryBuilder.list();
        if (list.size() > 0) {
            if (TextUtils.isEmpty(queryBuilder.list().get(0).getVal())){
                return defval;
            }
            return Integer.parseInt(queryBuilder.list().get(0).getVal());
        } else {
            return defval;
        }
    }
}
