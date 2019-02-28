package com.zjlanyun.lyapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.zjlanyun.lyapp.factory.MainFragmentFactory;


/**
 * Created by MDZZ on 2019-02-26.
 */

public class MainViewPageAdapter extends FragmentPagerAdapter {

    private int pageSize = 0;
    private Fragment mCurrentFragment;
    public MainViewPageAdapter(FragmentManager fm , int size) {
        super(fm);
        this.pageSize = size;
    }

    @Override
    public Fragment getItem(int i) {
        return MainFragmentFactory.getFragment(i);
    }

    @Override
    public int getCount() {
        return pageSize;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
