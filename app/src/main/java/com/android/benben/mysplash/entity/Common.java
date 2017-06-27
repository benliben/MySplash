package com.android.benben.mysplash.entity;

/**
 * Time      2017/6/26 16:51 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class Common {
    // 返回码，0成功
    public String message;
    //
    public String debug;
    // 返回状态 200代表成功
    public int status;
    // 具体内容
    public Attachment attachment;

    public boolean isValid() {
        return status == 200;

    }

    public boolean isServiceBlock() {
        return status == 500;

    }

    public boolean isNeedOut() {
        return status == 1000;

    }

    @Override
    public String toString() {
        return "Common{" +
                "message='" + message + '\'' +
                ", debug='" + debug + '\'' +
                ", status=" + status +
                ", attachment=" + attachment +
                '}';
    }
}
