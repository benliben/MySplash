package com.android.benben.mysplash.http.callback;

import android.content.Context;
import android.content.Intent;

import com.android.benben.mysplash.ColumnApplication;
import com.android.benben.mysplash.UserCenter;
import com.android.benben.mysplash.activity.LoginActivity;
import com.android.benben.mysplash.entity.Attachment;
import com.android.benben.mysplash.entity.Common;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.benben.mysplash.entity.Constants.EXTRA_KEY_EXIT;

/**
 * Time      2017/6/26 16:52 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public abstract class CommonCallback<T extends Common> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.raw().code() == 200) {
            T body = response.body();
            if (body != null) {
                if (body.isValid()) {
                    if (body.attachment != null) {
                        onSuccess(body.attachment);
                    } else {
                        onError("Attachment 对象为空");
                    }
                } else {
                    if (body.isNeedOut()) {//token过期重新登录
                        gotoLogInActivity();

                    } else if (body.isServiceBlock()) {
                    } else {
                        onError(body.message);
                    }
                }
            } else {
                onError(response.message());
            }
        } else {
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }
    }

    private void gotoLogInActivity() {
        Context context = ColumnApplication.getContext();
        UserCenter.getInstance().setToken(null);//清楚token
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_KEY_EXIT, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            return;
        } else if (t instanceof ConnectException) {
            onError(t.getMessage());
        } else if (t instanceof RuntimeException) {
            onError(t.getMessage());
        } else {
            onError(t.getMessage());
        }
        call.cancel();
    }

    public abstract void onSuccess(Attachment attachment);

    public void onError(String message) {
    }
}
