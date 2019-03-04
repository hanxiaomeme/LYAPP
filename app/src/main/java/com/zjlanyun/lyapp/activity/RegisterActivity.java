package com.zjlanyun.lyapp.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.zjlanyun.lyapp.R;
import com.zjlanyun.lyapp.common.Common;
import com.zjlanyun.lyapp.config.SPConfig;
import com.zjlanyun.lyapp.http.HttpRequest;
import com.zjlanyun.lyapp.http.SimpleHttpListener;
import com.zjlanyun.lyapp.utils.SPData;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zjlanyun.lyapp.config.WebConfig.URL_UPDATESETTING_TEST;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.cv_add)
    CardView cvAdd;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.bt_update)
    Button btUpdate;
    @BindView(R.id.et_server_ip)
    EditText etServerIp;
    @BindView(R.id.server_port)
    EditText etServerPort;

    private Context mContext = this;
    private String server_ip = "";
    private String server_port = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ShowEnterAnimation();
        setDefaultValue();
    }

    private void setDefaultValue() {
        server_ip = SPData.getConfig().getString(SPConfig.SP_SERVER_IP);
        server_port = SPData.getConfig().getString(SPConfig.SP_SERVER_PORT);
        etServerIp.setText(server_ip);
        etServerPort.setText(server_port);
    }

    /**
     * 更新配置
     */
    public void updateSetting() {

        btUpdate.setText(getString(R.string.update_msg));
        progressBar.setVisibility(View.VISIBLE);
        btUpdate.setEnabled(false);
        HttpRequest client = new HttpRequest(mContext, URL_UPDATESETTING_TEST, RequestMethod.GET, false);
        client.send(new SimpleHttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, final Response<JSONObject> response) {
                super.onSucceed(what, response);
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Common.updateSetting(response.get(), mContext);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toasty.success(mContext, getString(R.string.update_success), Toast.LENGTH_SHORT, true).show();
                        btUpdate.setText(getString(R.string.update_config));
                        progressBar.setVisibility(View.GONE);
                        btUpdate.setEnabled(true);
                        SPData.getConfig().put(SPConfig.SP_SERVER_IP,etServerIp.getText().toString());
                        SPData.getConfig().put(SPConfig.SP_SERVER_PORT,etServerPort.getText().toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                super.onFailed(what, response);
                btUpdate.setText(getString(R.string.update_config));
                progressBar.setVisibility(View.GONE);
                btUpdate.setEnabled(true);
            }
        });
    }


    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.update_icon_48dp);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


    @OnClick({R.id.fab, R.id.bt_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_update:
                updateSetting();
                break;
            case R.id.fab:
                animateRevealClose();
                break;
        }
    }


}
