package com.zjlanyun.lyapp.factory;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.zjlanyun.lyapp.fragment.FirstFragment;
import com.zjlanyun.lyapp.fragment.SecondFragment;
import com.zjlanyun.lyapp.fragment.ThirdFragment;

import java.util.HashMap;

/**
 * Created by MDZZ on 2019-02-26.
 */

public class MainFragmentFactory {
    private static final String TAG = "MainFragmentFactory";
    private static HashMap<Integer, Fragment> mSavedFragment = new HashMap<Integer, Fragment>();

    /**
     * 放入页面
     */
    private void initFragement(){

    }

    public static Fragment getFragment(int position) {
        Fragment fragment = mSavedFragment.get(position);
        Log.d(TAG,"传入的postion:"+ position);
        if(fragment == null) {
            switch (position) {
                case 0:
                    fragment = FirstFragment.newInstance(0);
                    break;
                case 1:
                    fragment = SecondFragment.newInstance(0);
                    break;
                case 2:
                    fragment = ThirdFragment.newInstance(0);
                    break;
                default:
                    fragment = SecondFragment.newInstance(0);

                    break;
            }
            mSavedFragment.put(position, fragment);
        }
        return fragment;
    }
}
