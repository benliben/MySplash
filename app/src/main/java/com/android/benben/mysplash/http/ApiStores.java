package com.android.benben.mysplash.http;

import com.android.benben.mysplash.entity.Common;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Time      2017/6/26 16:51 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public interface ApiStores {
    @GET("fundworks/media/getFlashScreen")
    Observable<Common> getSplashImage(@Query("type") int type);

}
