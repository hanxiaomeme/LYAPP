package com.zjlanyun.lyapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.zjlanyun.lyapp.http.CallServer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by MDZZ on 2019-02-22.
 */

public class BaseActivity extends AppCompatActivity {
    public Context mContext = this;
    public Activity mActivity = this;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            BarUtils.setStatusBarVisibility(this,false);
        }

    }

    /**
     * 基础初始化，继承后必须调用
     */
    public void initBaseView() {
        unbinder = ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        CallServer.getRequestInstance().cancelBySign(mContext);
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
