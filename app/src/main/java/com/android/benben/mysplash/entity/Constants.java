package com.android.benben.mysplash.entity;

import com.android.benben.mysplash.ColumnApplication;

/**
 * Time      2017/6/26 16:51 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public interface Constants {
    String API_DEBUG_SERVER_URL = "http://beta.goldenalpha.com.cn/";

    String EXTRA_KEY_EXIT = "extra_key_exit";

    String DOWNLOAD_SPLASH = "download_splash";
    String EXTRA_DOWNLOAD = "extra_download";

    //动态闪屏序列化地址
    String SPLASH_PATH = ColumnApplication.getContext().getFilesDir().getAbsolutePath() + "/alpha/splash";

    String SPLASH_FILE_NAME = "splash.srr";
}
