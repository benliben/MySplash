package com.android.benben.mysplash.entity;

import java.io.Serializable;

/**
 * Time      2017/6/26 16:40 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class Splash implements Serializable {
    private static final long serialVersionUID = 7382351359868556980L;//这里必须写死，序列化id
    public int id;
    public String burl;//大图url
    public String surl;//小图url
    public int type;//图片类型，Android 1 Ios 2
    public String click_url; // 点击跳转 URl
    public String savePath;//图片的存储地址
    public String title;//图片的存储地址

    public Splash(String burl, String surl, String click_url, String savePath) {
        this.burl = burl;
        this.surl = surl;
        this.click_url = click_url;
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "Splash{" +
                "id=" + id +
                ", burl='" + burl + '\'' +
                ", surl='" + surl + '\'' +
                ", type=" + type +
                ", click_url='" + click_url + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';

    }
}
