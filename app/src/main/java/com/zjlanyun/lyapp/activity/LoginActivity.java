package com.zjlanyun.lyapp.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.zjlanyun.lyapp.MainActivity;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.common.Common;
import com.zjlanyun.lyapp.config.SPConfig;
import com.zjlanyun.lyapp.http.HttpRequest;
import com.zjlanyun.lyapp.http.SimpleHttpListener;
import com.zjlanyun.lyapp.utils.SPData;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.zjlanyun.lyapp.config.WebConfig.URL_LOGIN;

public class LoginActivity extends BaseActivity {
    public static final String TAG = "LoginActivity_LY";
    //
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    private Context mContext = this;
    private String str_user = "";
    private String str_pass = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getPermission();
        initToolbar();

    }


    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }


    /**
     * 获取权限
     */
    public void getPermission() {
        final String[] permissionList = new String[]{
                Permission.READ_PHONE_STATE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.CAMERA
        };
        AndPermission.with(this)
                .runtime()
                .permission(permissionList)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissionList)) {
//                            setPermission();
                        }
                    }
                })
                .start();

    }

    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                    }
                })
                .start();
    }


    /**
     * 登录
     */
    private void login() {
        str_user = etUsername.getText().toString().trim();
        str_pass = etPassword.getText().toString().trim();
        if (str_user.isEmpty()){
            Toasty.error(mContext,getString(R.string.input_username), Toast.LENGTH_LONG,true).show();
            return;
        }
        if (str_pass.isEmpty()){
            Toasty.error(mContext,getString(R.string.input_password), Toast.LENGTH_LONG,true).show();
            return;
        }



        HttpRequest client = new HttpRequest(mContext, URL_LOGIN, RequestMethod.GET);
        client.getRequest().add("userid", str_user);
        client.getRequest().add("pwd", str_pass);
        client.send(new SimpleHttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                super.onSucceed(what, response);
                Log.d(TAG,response.get().toString());
                Toasty.success(mContext,getString(R.string.login_success), Toast.LENGTH_LONG,true).show();
                SPData.getUserinfo().put(SPConfig.SP_UID,str_user);
                SPData.getUserinfo().put(SPConfig.SP_PWD,str_pass);
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
            }
            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                super.onFailed(what, response);
            }
        });





    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_go:

                login();
                break;
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
                break;
        }
    }


}
