package com.zjlanyun.lyapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.adapter.MenuAdapter;
import com.zjlanyun.lyapp.bean.MenuBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MDZZ on 2019-02-25.
 */

public class FirstFragment extends Fragment {

    private ArrayList<MenuBean> mDataList;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;


    public static FirstFragment newInstance(int arg0) {
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("arg0", arg0);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initAdapter();


        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initAdapter() {
        BaseQuickAdapter homeAdapter = new MenuAdapter(R.layout.menu_item, mDataList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setNotDoAnimationCount(3);
        homeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        rvList.setAdapter(homeAdapter);
        homeAdapter.isFirstOnly(false);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MenuBean item = new MenuBean();
            item.setDate("test");
            item.setCode("test");
            mDataList.add(item);
        }
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
    }
}
