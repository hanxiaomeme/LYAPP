package com.zjlanyun.lyapp.http;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * @author:
 * @date:
 * @desc: 接受回调结果
 '──────── ▌▒█───────────▄▀▒▌
 ' ────────▌▒▒▀▄───────▄▀▒▒▒▐
 ' ───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
 ' ─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
 ' ───▄▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐
 ' ──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌
 ' ──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
 ' ─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌
 ' ─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌
 ' ─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐
 ' ▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌
 ' ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐
 ' ─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌
 ' ─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐
 ' ──▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌
 ' ────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀
 ' ───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀
 ' ──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀
 */
public interface HttpListener<T> {
    void onSucceed(int what, Response<T> response);
    void onFailed(int what, Response<T> response);
    void onFinish(int what);

}
