package com.zjlanyun.lyapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.orhanobut.logger.Logger;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.adapter.ExpandableItemAdapter;
import com.zjlanyun.lyapp.common.Common;
import com.zjlanyun.lyapp.entity.Level0Item;
import com.zjlanyun.lyapp.entity.Level1Item;
import com.zjlanyun.lyapp.greendao.IrUiMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

        list = generateData();
        adapter = new ExpandableItemAdapter(list);
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_LEVEL_1 ? 1 : manager.getSpanCount();
            }
        });
        rvList.setAdapter(adapter);
        // important! setLayoutManager should be called after setAdapter
        rvList.setLayoutManager(manager);
        adapter.expandAll();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    private ArrayList<MultiItemEntity> generateData() {
        //获取菜单

        List<Integer> typeList = Common.getMenuType(getContext()," active = 1 ");
        Logger.t(TAG).d("typeList:---"+typeList);
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < typeList.size(); i++) {
            Level0Item lv0 = new Level0Item("当前分类：" + typeList.get(i), "subtitle of " + i);
            List<IrUiMenu> menuList = Common.getApiIrMenuTable(getContext(),"packet_id = "+typeList.get(i) + " and active = 1 ");
            for (int j = 0; j < menuList.size(); j++) {
                IrUiMenu item = menuList.get(j);
                lv0.addSubItem(new Level1Item(item.getId()+"", item.getName()));
            }
            res.add(lv0);
        }

        return res;
    }
}
