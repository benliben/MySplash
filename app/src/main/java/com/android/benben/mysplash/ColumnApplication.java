package com.android.benben.mysplash;

import android.app.Application;
import android.content.Context;

/**
 * Time      2017/6/26 16:49 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class ColumnApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        UserCenter.initInstance(this);
    }
}
