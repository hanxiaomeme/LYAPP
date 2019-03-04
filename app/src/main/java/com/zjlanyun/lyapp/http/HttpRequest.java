package com.zjlanyun.lyapp.http;

import android.content.Context;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.zjlanyun.lyapp.config.SPConfig;
import com.zjlanyun.lyapp.utils.SPData;

import org.json.JSONObject;

import static com.zjlanyun.lyapp.utils.UtilConstants.USER_TYPE_INTERIOR;

/**
 * @author: shun
 * @date: 2016-09-26 18:14
 * @Desc: 请求方法
 */
public class HttpRequest {

    private Request<JSONObject> request;
    private Context mContext;
    private int mWhat = 0;
    private boolean mCanCancel;
    private boolean mIsLoading; //显示loading
    private int mConnectTimeout = 15 * 1000; // 设置连接超时。
    private int mReadTimeout = 15 * 1000; // 设置读取超时时间，也就是服务器的响应超时。
    private boolean mAutoShowError = true;
    private String mAction = "";
    private String mParam = "";
    private boolean hasDefault = false;

    public int getmConnectTimeout() {
        return mConnectTimeout;
    }

    public void setmConnectTimeout(int mConnectTimeout) {
        this.mConnectTimeout = mConnectTimeout;
    }

    public int getmWhat() {
        return mWhat;
    }

    public void setmWhat(int mWhat) {
        this.mWhat = mWhat;
    }

    public boolean ismIsLoading() {
        return mIsLoading;
    }

    public void setmIsLoading(boolean mIsLoading) {
        this.mIsLoading = mIsLoading;
    }

    public HttpRequest(Context context, String url) {
        this(context, url, RequestMethod.POST);
    }

    public HttpRequest(Context context, String url, String action, String param, boolean hasDefault) {
        this(context, url, RequestMethod.POST);
        this.mAction = action;
        this.mParam = param;
        this.hasDefault = hasDefault;
    }

    public HttpRequest(Context context, String url, String action, String param, boolean hasDefault, boolean showLoading) {
        this(context, url, RequestMethod.POST);
        this.mAction = action;
        this.mParam = param;
        this.hasDefault = hasDefault;
        this.mIsLoading = showLoading;
    }

    public HttpRequest(Context context, String url, boolean isLoading) {
        this(context, url, RequestMethod.POST, 0, false, isLoading);
    }

    public HttpRequest(Context context, String url, RequestMethod requestMethod) {
        this(context, url, requestMethod, 0);
    }

    public HttpRequest(Context context, String url, int what) {
        this(context, url, RequestMethod.POST, what, false, true);
    }

    public HttpRequest(Context context, String url, RequestMethod requestMethod, int what) {
        this(context, url, requestMethod, what, false, true);
    }

    public HttpRequest(Context context, String url, RequestMethod requestMethod, int what, boolean canCancel, boolean isLoading) {
        this.mContext = context;
        this.request = NoHttp.createJsonObjectRequest(url, requestMethod);
        this.mWhat = what;
        this.mCanCancel = canCancel;
        this.mIsLoading = isLoading;
    }
    public HttpRequest(Context context, String url, RequestMethod requestMethod, boolean isLoading) {
        this(context, url, requestMethod, 0,false,isLoading);
    }

    public void send(HttpListener<JSONObject> callback) {
        this.send(callback, mWhat);
    }

    public void send(HttpListener<JSONObject> callback, int what) {
        this.mWhat = what;
        this.send(callback, mWhat, mCanCancel, mIsLoading);
    }

    public void send(HttpListener<JSONObject> callback, int what, boolean canCancel, boolean isLoading) {
        if (!mAction.equals("")) this.request.add("action", mAction);
        if (!mParam.equals("")) this.request.add("param", mParam);
        if (hasDefault) setDefault();
        this.mWhat = what;
        this.mCanCancel = canCancel;
        this.mIsLoading = isLoading;
        request.setConnectTimeout(mConnectTimeout); // 设置连接超时。
        request.setReadTimeout(mReadTimeout); // 设置读取超时时间，也就是服务器的响应超时。
        request.setCancelSign(mContext);

        CallServer.getRequestInstance().add(mContext, mWhat, request, callback, mCanCancel, mIsLoading, mAutoShowError);
    }

    public Request<JSONObject> getRequest() {
        return request;
    }

    public void setRequest(Request<JSONObject> request) {
        this.request = request;
    }

    public void setDefault() {
        request.add("username", SPData.getUserinfo().getString(SPConfig.SP_UID, ""));
        request.add("password", SPData.getUserinfo().getString(SPConfig.SP_PWD, ""));
        request.add("usertype", SPData.getUserinfo().getString("usertype",USER_TYPE_INTERIOR));
    }

    public boolean ismAutoShowError() {
        return mAutoShowError;
    }

    public void setmAutoShowError(boolean mAutoShowError) {
        this.mAutoShowError = mAutoShowError;
    }
}
