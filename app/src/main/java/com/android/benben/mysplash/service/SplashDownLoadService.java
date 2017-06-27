package com.android.benben.mysplash.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.android.benben.mysplash.entity.Common;
import com.android.benben.mysplash.entity.Constants;
import com.android.benben.mysplash.entity.Splash;
import com.android.benben.mysplash.http.HttpClient;
import com.android.benben.mysplash.utils.DownLoadUtils;
import com.android.benben.mysplash.utils.SerializableUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.android.benben.mysplash.utils.SerializableUtils.readObject;

/**
 * Time      2017/6/26 16:54 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class SplashDownLoadService extends IntentService {

    private Splash mScreen;
    public static final int TYPE_ANDROID = 1;
    private static final String SPLASH_FILE_NAME = "splash.srr";
    public SplashDownLoadService() {
        super("SplashDownLoad");
    }

    public static void startDownLoadSplashImage(Context context, String action) {
        Intent intent = new Intent(context, SplashDownLoadService.class);
        intent.putExtra(Constants.EXTRA_DOWNLOAD, action);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra(Constants.EXTRA_DOWNLOAD);
            if (action.equals(Constants.DOWNLOAD_SPLASH)) {
                loadSplashNetDate();
            }
        }
    }

    private void loadSplashNetDate() {
        HttpClient.getInstance()
                .getSplashImage(TYPE_ANDROID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Common>() {
                    @Override
                    public void accept(@NonNull Common common) throws Exception {
                        if (common.isValid() && common.attachment != null) {
                            mScreen = common.attachment.flashScreen;
                            Splash splashLocal = getSplashLocal();
                            if (mScreen != null) {
                                if (splashLocal == null) {
                                    Log.d("SplashDemo","splashLocal 为空导致下载");
                                    startDownLoadSplash(Constants.SPLASH_PATH, mScreen.burl);
                                } else if (isNeedDownLoad(splashLocal.savePath, mScreen.burl)) {
                                    Log.d("SplashDemo","isNeedDownLoad 导致下载");
                                    startDownLoadSplash(Constants.SPLASH_PATH, mScreen.burl);
                                }
                            } else {
                                if (splashLocal != null) {
                                    File splashFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH, Constants.SPLASH_FILE_NAME);
                                    if (splashFile.exists()) {
                                        splashFile.delete();
                                        Log.d("SplashDemo","mScreen为空删除本地文件");
                                    }
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
    }

    private Splash getSplashLocal() {
        Splash splash = null;
        try {
            File splashFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH, SPLASH_FILE_NAME);
            splash = (Splash) readObject(splashFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splash;
    }

    /**
     * @param path 本地存储的图片绝对路径
     * @param url  网络获取url
     * @return 比较储存的 图片名称的哈希值与 网络获取的哈希值是否相同
     */
    private boolean isNeedDownLoad(String path, String url) {
        if (TextUtils.isEmpty(path)) {
            Log.d("SplashDemo","本地url " + TextUtils.isEmpty(path));
            Log.d("SplashDemo","本地url " + TextUtils.isEmpty(url));
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            Log.d("SplashDemo","本地file " + file.exists());
            return true;
        }
        if (getImageName(path).hashCode() != getImageName(url).hashCode()) {
            Log.d("SplashDemo","path hashcode " + getImageName(path) + " " + getImageName(path).hashCode());
            Log.d("SplashDemo","url hashcode " + getImageName(url) + " " + getImageName(url).hashCode());
            return true;
        }
        return false;
    }


    private String getImageName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] split = url.split("/");
        String nameWith_ = split[split.length - 1];
        String[] split1 = nameWith_.split("\\.");
        return split1[0];
    }

    private void startDownLoadSplash(String splashPath, String burl) {
        DownLoadUtils.downLoad(splashPath, new DownLoadUtils.DownLoadInterFace() {
            @Override
            public void afterDownLoad(ArrayList<String> savePaths) {
                if (savePaths.size() == 1) {
                    Log.d("SplashDemo","闪屏页面下载完成" + savePaths);
                    if (mScreen != null) {
                        mScreen.savePath = savePaths.get(0);
                    }
                    SerializableUtils.writeObject(mScreen, Constants.SPLASH_PATH + "/" + SPLASH_FILE_NAME);
                } else {
                    Log.d("SplashDemo","闪屏页面下载失败" + savePaths);
                }
            }
        }, burl);
    }
}
