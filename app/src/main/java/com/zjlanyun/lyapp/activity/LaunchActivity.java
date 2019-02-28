package com.zjlanyun.lyapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zjlanyun.lyapp.BuildConfig;
import com.zjlanyun.lyapp.R;

import static com.zjlanyun.lyapp.utils.UtilConstants.USER_TYPE_CUSTOMER;
import static com.zjlanyun.lyapp.utils.UtilConstants.USER_TYPE_INTERIOR;
import static com.zjlanyun.lyapp.utils.UtilConstants.USER_TYPE_SUPPLIER;

public class LaunchActivity extends AppCompatActivity {
    public static final String LogTag = "LaunchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        into();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 跳转登陆界面
     */
    private void into() {
        if (BuildConfig.DEBUG){
            Intent intent;
            intent = new Intent(LaunchActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            //等待两秒再进入
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent;
                    switch ("") {
                        case USER_TYPE_INTERIOR:

                            break;
                        case USER_TYPE_CUSTOMER:
                            break;
                        case USER_TYPE_SUPPLIER:
                            break;
                    }
                    intent = new Intent(LaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 3000);
        }


    }

}
