package com.android.benben.mysplash.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Time      2017/6/26 16:54 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class NetWorkUtils {
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        return !(info == null || info.getState() != NetworkInfo.State.CONNECTED);
    }
}
