package com.android.benben.mysplash.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.benben.mysplash.R;

/**
 * Time      2017/6/26 16:50 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class LoginActivity  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录界面");
    }
}
