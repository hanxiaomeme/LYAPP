package com.zjlanyun.lyapp.common;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.config.SPConfig;
import com.zjlanyun.lyapp.greendao.IrActWindow;
import com.zjlanyun.lyapp.greendao.IrConfig;
import com.zjlanyun.lyapp.greendao.IrExtend;
import com.zjlanyun.lyapp.greendao.IrModel;
import com.zjlanyun.lyapp.greendao.IrModelFields;
import com.zjlanyun.lyapp.greendao.IrSearchFields;
import com.zjlanyun.lyapp.greendao.IrUiMenu;
import com.zjlanyun.lyapp.greendao.IrUiMenuDao;
import com.zjlanyun.lyapp.utils.DBHelper;
import com.zjlanyun.lyapp.utils.SPData;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by MDZZ on 2019-02-27.
 */

public class Common {
    /**
     * 更新本地配置
     *
     * @param jsonObject
     * @param context
     */
    public static void updateSetting(JSONObject jsonObject, Context context) {
        try {
            cleanSetting(context);

            JSONArray ir_ui_menu = jsonObject.getJSONObject("data").getJSONArray("ir_ui_menu");
            ArrayList<IrUiMenu> ir_ui_menuList = new ArrayList<>(JSON.parseArray(ir_ui_menu.toString(), IrUiMenu.class));
            for (int i = 0; i < ir_ui_menuList.size(); i++) {
                DBHelper.getDaoSession(context).getIrUiMenuDao().insertOrReplace(ir_ui_menuList.get(i));
            }

            JSONArray ir_act_window = jsonObject.getJSONObject("data").getJSONArray("ir_act_window");
            ArrayList<IrActWindow> ir_act_windowList = new ArrayList<>(JSON.parseArray(ir_act_window.toString(), IrActWindow.class));
            for (int i = 0; i < ir_act_windowList.size(); i++) {
                DBHelper.getDaoSession(context).getIrActWindowDao().insertOrReplace(ir_act_windowList.get(i));
            }

            JSONArray ir_model_fields = jsonObject.getJSONObject("data").getJSONArray("ir_model_fields");
            ArrayList<IrModelFields> ir_model_fieldsList = new ArrayList<>(JSON.parseArray(ir_model_fields.toString(), IrModelFields.class));
            for (int i = 0; i < ir_model_fieldsList.size(); i++) {
                DBHelper.getDaoSession(context).getIrModelFieldsDao().insertOrReplace(ir_model_fieldsList.get(i));
            }

            JSONArray ir_model = jsonObject.getJSONObject("data").getJSONArray("ir_model");
            ArrayList<IrModel> irModelArrayList = new ArrayList<>(JSON.parseArray(ir_model.toString(), IrModel.class));
            for (int i = 0; i < irModelArrayList.size(); i++) {
                DBHelper.getDaoSession(context).getIrModelDao().insertOrReplace(irModelArrayList.get(i));
            }

            JSONArray ir_search_fields = jsonObject.getJSONObject("data").getJSONArray("ir_search_fields");
            ArrayList<IrSearchFields> irSearchFieldsArrayList = new ArrayList<>(JSON.parseArray(ir_search_fields.toString(), IrSearchFields.class));
            for (int i = 0; i < irSearchFieldsArrayList.size(); i++) {
                DBHelper.getDaoSession(context).getIrSearchFieldsDao().insertOrReplace(irSearchFieldsArrayList.get(i));
            }

            JSONArray ir_extend = jsonObject.getJSONObject("data").getJSONArray("ir_extend");
            ArrayList<IrExtend> IrExtendArrayList = new ArrayList<>(JSON.parseArray(ir_extend.toString(), IrExtend.class));
            for (int i = 0; i < IrExtendArrayList.size(); i++) {
                DBHelper.getDaoSession(context).getIrExtendDao().insertOrReplace(IrExtendArrayList.get(i));
            }

            JSONArray ir_config = jsonObject.getJSONObject("data").getJSONArray("ir_config");
            ArrayList<IrConfig> IrConfigArrayList = new ArrayList<>(JSON.parseArray(ir_config.toString(), IrConfig.class));
            for (int i = 0; i < IrConfigArrayList.size(); i++) {
                DBHelper.getDaoSession(context).getIrConfigDao().insertOrReplace(IrConfigArrayList.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toasty.error(context, context.getString(R.string.error_update),Toast.LENGTH_LONG,true).show();
            return;
        }
    }

    /**
     * 清空本地配置的缓存信息和用户登录信息
     */
    public static void cleanSetting(Context context) {
        DBHelper.getDaoSession(context).getIrUiMenuDao().deleteAll();
        DBHelper.getDaoSession(context).getIrActWindowDao().deleteAll();
        DBHelper.getDaoSession(context).getIrModelFieldsDao().deleteAll();
        DBHelper.getDaoSession(context).getIrModelDao().deleteAll();
        DBHelper.getDaoSession(context).getIrSearchFieldsDao().deleteAll();
        DBHelper.getDaoSession(context).getIrExtendDao().deleteAll();
        DBHelper.getDaoSession(context).getIrConfigDao().deleteAll();


        SPData.getUserinfo().put(SPConfig.SP_PWD, "");
        SPData.getUserinfo().put(SPConfig.SP_UID, "");
    }


    /**
     * 获取菜单
     * @param mContext
     * @return
     */
    public static List<IrUiMenu> getApiIrMenuTable(Context mContext,String where){
//        String where = "active = 1 " ;
        IrUiMenuDao irUiMenuDao = DBHelper.getDaoSession(mContext).getIrUiMenuDao();
        Query<IrUiMenu> query = irUiMenuDao.queryBuilder().where(new WhereCondition.StringCondition(where))
                .orderAsc(IrUiMenuDao.Properties.Sequence).build();
        return query.list();

    }

    /**
     * 获取菜单种类,数量
     * @return
     */
    public static List<Integer> getMenuType(Context mContext,String where){
        List<IrUiMenu> menuList = getApiIrMenuTable(mContext,where);
        List<Integer> typeList = new ArrayList<>();
        if (menuList == null || menuList.size() == 0)
            return null;
        for (int i = 0; i < menuList.size(); i++) {
            IrUiMenu item = menuList.get(i);
            typeList.add(item.getPacket_id());
        }
        return new ArrayList(new HashSet(typeList));
    }



}