package com.zjlanyun.lyapp.http;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * @author: shun
 * @date: 2016-12-19 9:41
 * @Desc:
 */

public abstract class SimpleHttpListener<T> implements HttpListener<T> {
    @Override
    public void onSucceed(int what, Response<T> response) {

    }

    @Override
    public void onFailed(int what, Response<T> response) {

    }

    @Override
    public void onFinish(int what) {

    }
}
