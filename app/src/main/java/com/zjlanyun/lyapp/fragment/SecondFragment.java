package com.zjlanyun.lyapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.zjlanyun.lyapp.MainActivity;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.activity.LoginActivity;
import com.zjlanyun.lyapp.adapter.ExpandableItemAdapter;
import com.zjlanyun.lyapp.bean.AppMenuConfigBean;
import com.zjlanyun.lyapp.common.Common;
import com.zjlanyun.lyapp.config.SPConfig;
import com.zjlanyun.lyapp.entity.Level0Item;
import com.zjlanyun.lyapp.entity.Level1Item;
import com.zjlanyun.lyapp.entity.LevelEndItem;
import com.zjlanyun.lyapp.greendao.IrUiMenu;
import com.zjlanyun.lyapp.http.HttpRequest;
import com.zjlanyun.lyapp.http.SimpleHttpListener;
import com.zjlanyun.lyapp.utils.SPData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.zjlanyun.lyapp.config.WebConfig.URL_GETAPPMENU;
import static com.zjlanyun.lyapp.config.WebConfig.URL_LOGIN;

/**
 * Created by MDZZ on 2019-02-26.
 */

public class SecondFragment extends Fragment {
    private static final String TAG = "SecondFragment_ly";
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;
    private Context mContext = getActivity();
    Unbinder unbinder;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    HashMap<String,Level0Item> map_lv0;
    HashMap<String,Level1Item> map_lv1;
    HashMap<String,LevelEndItem> map_lv2;
    List<Level0Item> list_lv0 ;
    List<Level1Item> list_lv1 ;
    List<LevelEndItem> list_lv2 ;
    public static SecondFragment newInstance(int arg0) {
        SecondFragment fragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("arg0", arg0);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();

        getAppMenuConfig();
        return view;

    }

    private void initData(){
        list_lv0 = new ArrayList<>();
        list_lv1 = new ArrayList<>();
        list_lv2 = new ArrayList<>();
        map_lv0 = new HashMap<>();
        map_lv1 = new HashMap<>();
        map_lv2 = new HashMap<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private ArrayList<MultiItemEntity> generateData() {
        //获取菜单
//        List<Integer> typeList = Common.getMenuType(getContext(), " active = 1 ");
//        Logger.t(TAG).d("typeList:---" + typeList);
        ArrayList<MultiItemEntity> res = new ArrayList<>();
//        for (int i = 0; i < typeList.size(); i++) {
//            Level0Item lv0 = new Level0Item(typeList.get(i));
//            List<IrUiMenu> menuList = Common.getApiIrMenuTable(getContext(), "packet_id = " + typeList.get(i) + " and active = 1 ");
//            for (int j = 0; j < menuList.size(); j++) {
//                IrUiMenu item = menuList.get(j);
//                Level1Item lv1 = new Level1Item(item.getName());
//                for (int k = 0; k < 7; k++) {
//                    lv1.addSubItem(new LevelEndItem(item.getName(), item.getName()));
//                }
//                lv0.addSubItem(lv1);
//            }
//            res.add(lv0);
//        }
        return res;
    }

    private void getAppMenuConfig() {
        HttpRequest client = new HttpRequest(mContext, URL_GETAPPMENU, RequestMethod.GET);
        client.send(new SimpleHttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                super.onSucceed(what, response);
                Logger.t(TAG).d(response.get().toString());
                String json = response.get().toString();
                Gson gson = new Gson();
                AppMenuConfigBean appMenuConfigBean = gson.fromJson(json, AppMenuConfigBean.class);
                analyzeDate(appMenuConfigBean.getData());
                initRcList();
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                super.onFailed(what, response);
            }
        });
    }

    /**
     * 递归解析菜单
     * @param list
     */
    private void analyzeDate(List<AppMenuConfigBean.ChildrenBean> list){

        //然后递归
        for (int i = 0; i < list.size(); i++) {
            AppMenuConfigBean.ChildrenBean item = list.get(i);
            switch (item.getIGrade()-1){
                case 0 ://1级
                    Level0Item lv0 = new Level0Item(item.getName(),item.getMenu_code(),item.getAct_id(),item.isBEnd());
                    map_lv0.put(item.getMenu_code(),lv0);
                    break;
                case 1 ://2级
                    Level1Item lv1 = new Level1Item(item.getName(),item.getMenu_code(),item.getAct_id(),item.isBEnd());
                    map_lv1.put(item.getMenu_code(),lv1);
                    break;
                case 2 ://3级
                    LevelEndItem lv2 = new LevelEndItem(item.getName(),item.getMenu_code(),item.getAct_id());
                    map_lv2.put(item.getMenu_code(),lv2);
                    break;
            }

            //非末级
            if (item.getChildren() != null && !item.isBEnd()){
                analyzeDate(item.getChildren());
            }
        }
    }

    private ArrayList<MultiItemEntity> loadMenu(){
        //01.01.03
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (Map.Entry<String, Level0Item> entry0 : map_lv0.entrySet()) {
            String key0 = entry0.getKey().toString();
            Level0Item value0 = entry0.getValue();
            for (Map.Entry<String, Level1Item> entry01 : map_lv1.entrySet()) {
                String key01 = entry01.getKey().toString().split("\\.")[0];
                Level1Item value01 = entry01.getValue();
                for (Map.Entry<String, LevelEndItem> entry02 : map_lv2.entrySet()) {
                    String key02 = entry02.getKey().toString().split("\\.")[0]+"."+entry02.getKey().toString().split("\\.")[1];
                    LevelEndItem value02 = entry02.getValue();
                    if (key02.equals(entry01.getKey().toString())) {
                        if (!value01.contains(value02)){
                            value01.addSubItem(value02);
                        }
                    }
                }
                if (key01.equals(key0)){
                    value0.addSubItem(value01);
                }
            }
            res.add(value0);

        }
        return res;
    }

    private void initRcList(){
//        list = generateData();
        list = loadMenu();
        adapter = new ExpandableItemAdapter(list);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_LEVEL_2 ? 2 : manager.getSpanCount();
//            }
//        });
        rvList.setAdapter(adapter);
        // important! setLayoutManager should be called after setAdapter
        rvList.setLayoutManager(manager);
    }
}
