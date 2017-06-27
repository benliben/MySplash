package com.android.benben.mysplash.http.interceptor;

import android.text.TextUtils;

import com.android.benben.mysplash.UserCenter;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Time      2017/6/26 16:53 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class AddQueryParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        //String method = originalRequest.method();
        // Headers headers = originalRequest.headers();
        String token = UserCenter.getInstance().getToken();
        String uid = String.valueOf(UserCenter.getInstance().getCurrentUser().uid);
        HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                .addQueryParameter("token", TextUtils.isEmpty(UserCenter.getInstance().getToken()) ? "" : token)
                .addQueryParameter("uid", UserCenter.getInstance().getCurrentUser().uid == 0 ? "" : uid)
                .build();
        request = originalRequest.newBuilder().url(modifiedUrl).build();

        return chain.proceed(request);
    }
}
