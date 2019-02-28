package com.zjlanyun.lyapp.http;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aihook.alertview.AlertView;
import com.aihook.progressview.library.ProgressViewDialog;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.zjlanyun.lyapp.R;

import org.json.JSONObject;

import java.net.ProtocolException;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


/**
 * @author: shun
 * @date: 2016-06-29 15:51
 * @desc: 接受回调结果
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Context mContext;
    private ProgressViewDialog mProgressViewDialog;
    private Activity mActivity;
    private boolean mAutoShowError;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * @param context       context用来实例化dialog.
     * @param request       请求对象.
     * @param httpCallback  回调对象.
     * @param canCancel     是否允许用户取消请求.
     * @param isShowLoading 是否显示加载进度dialog.
     */
    public HttpResponseListener(Context context, Request<?> request, HttpListener<T> httpCallback, boolean canCancel, boolean isShowLoading, boolean autoShowError) {
        this.mContext = context;
        this.mRequest = request;
        this.mAutoShowError = autoShowError;
        this.mActivity = (Activity) context;
        if (mContext != null && isShowLoading) {
            mProgressViewDialog = new ProgressViewDialog(mContext);
        }
        this.callback = httpCallback;
    }

    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        if (mProgressViewDialog != null && !mActivity.isFinishing() && !mProgressViewDialog.isShowing())
            mProgressViewDialog.showWithMaskType(ProgressViewDialog.ProgressViewDialogMaskType.None);
    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (mProgressViewDialog != null && mProgressViewDialog.isShowing())
            mProgressViewDialog.dismiss();
        if (callback != null) {
            callback.onFinish(what);
        }
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
//        Logger.i(response.get().toString());
        int responseCode = response.getHeaders().getResponseCode();
        Log.d("HttpResponseListener",response.getHeaders()+"////"+mAutoShowError);

        if (responseCode > 400 && mContext != null) {
            if (responseCode == 405) {// 405表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。
                new AlertView(mContext.getString(R.string.request_succeed), mContext.getString(R.string.request_method_not_allow), "确定", null, null, mContext, AlertView.Style.Alert, null).show();
            }
            else {// 但是其它400+的响应码服务器一般会有流输出。
                String title = String.format(Locale.getDefault(), mContext.getString(R.string.error_response_code), responseCode);
                String content = String.format(Locale.getDefault(), mContext.getString(R.string.error_response_code_dex), responseCode);
                new AlertView(title, content, "确定", null, null, mContext, AlertView.Style.Alert, null)
                        .show();
            }
        }
        if (callback != null) {
            if (mAutoShowError) {
                JSONObject jsonObject = (JSONObject) response.get();
                if (jsonObject.optInt("code") == 0) {
                    callback.onSucceed(what, response);
                }
                else if (jsonObject.optInt("code") == 1){
                    Toasty.error(mContext, jsonObject.optString("msg"), Toast.LENGTH_SHORT, true).show();
                    callback.onFailed(what, response);
                }
            }
            else {
                callback.onSucceed(what, response);
            }
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好
//            ToastUtils.showShortToast(R.string.error_please_check_network);
            Toasty.error(mContext, mContext.getString(R.string.error_please_check_network), Toast.LENGTH_SHORT, true).show();
        } else if (exception instanceof TimeoutError) {// 请求超时
//            ToastUtils.showShortToast(R.string.error_timeout);
            Toasty.error(mContext, mContext.getString(R.string.error_timeout), Toast.LENGTH_SHORT, true).show();
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
//            ToastUtils.showShortToast(R.string.error_not_found_server);
            Toasty.error(mContext, mContext.getString(R.string.error_not_found_server), Toast.LENGTH_SHORT, true).show();
        } else if (exception instanceof URLError) {// URL是错的
//            ToastUtils.showShortToast(R.string.error_url_error);
            Toasty.error(mContext, mContext.getString(R.string.error_url_error), Toast.LENGTH_SHORT, true).show();
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
//            ToastUtils.showShortToast(R.string.error_not_found_cache);
            Toasty.error(mContext, mContext.getString(R.string.error_not_found_cache), Toast.LENGTH_SHORT, true).show();
        } else if (exception instanceof ProtocolException) {
//            ToastUtils.showShortToast(R.string.error_system_unsupport_method);
            Toasty.error(mContext, mContext.getString(R.string.error_system_unsupport_method), Toast.LENGTH_SHORT, true).show();
        } else {
//            ToastUtils.showShortToast(R.string.error_unknow);
            Toasty.error(mContext, mContext.getString(R.string.error_unknow), Toast.LENGTH_SHORT, true).show();
        }
        Logger.e("错误：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, response);
    }

}
