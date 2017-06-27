package com.android.benben.mysplash.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.benben.mysplash.R;
import com.android.benben.mysplash.UserCenter;
import com.android.benben.mysplash.entity.Constants;
import com.android.benben.mysplash.entity.Splash;
import com.android.benben.mysplash.service.SplashDownLoadService;
import com.android.benben.mysplash.utils.SerializableUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time      2017/6/26 14:51 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.sp_bg)
    ImageView mSpBg;
    @BindView(R.id.sp_jump_btn)
    Button mSpJumpBtn;

    private Splash mSplash;


    /**
     * 由于countDownTimer有一定的延迟，所以这里设置3400
     */
    private CountDownTimer countDownTimer = new CountDownTimer(3400, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSpJumpBtn.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            mSpJumpBtn.setText("跳过(" + 0 + "s)");
            gotoLoginOrMainActivity();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        showAndDownSplash();
    }

    @OnClick({R.id.sp_jump_btn, R.id.sp_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sp_bg:
                gotoWebActivity();
                break;
            case R.id.sp_jump_btn:
                gotoLoginOrMainActivity();
                break;
        }
    }

    private void gotoWebActivity() {
        if (mSplash != null && mSplash.click_url != null) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", mSplash.click_url);
            intent.putExtra("title", mSplash.title);
            intent.putExtra("fromSplash", true);
            startActivity(intent);
            finish();
        }
    }

    private void showAndDownSplash() {
        showSplash();
        startImageDownLoad();
    }

    private void showSplash() {
        mSplash = getLocalSplash();
        if (mSplash != null && !TextUtils.isEmpty(mSplash.savePath)) {
            Log.d("SplashDemo", "SplashActivity 获取本地序列化成功 " + mSplash);
            Glide.with(this).load(mSplash.savePath).dontAnimate().into(mSpBg);
            startClock();
        } else {
            mSpJumpBtn.setVisibility(View.INVISIBLE);
            mSpJumpBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoLoginOrMainActivity();
                }
            }, 1000);
        }
    }

    private void startImageDownLoad() {
        SplashDownLoadService.startDownLoadSplashImage(this, Constants.DOWNLOAD_SPLASH);
    }

    private Splash getLocalSplash() {
        Splash splash = null;
        try {

            Log.d("存储路径", Constants.SPLASH_PATH);//修改为存储到内存卡中，不需要动态申请权限
            // /data/user/0/com.example.wsj.splashdemo/files/alpha/splash
            File serializableFile = SerializableUtils.getSerializableFile(Constants.SPLASH_PATH,
                    Constants.SPLASH_FILE_NAME);
            splash = (Splash) SerializableUtils.readObject(serializableFile);
        } catch (IOException e) {
            Log.d("SplashDemo", "SplashActivity 获取本地序列化闪屏失败" + e.getMessage());
        }
        return splash;
    }

    private void startClock() {
        mSpJumpBtn.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    private void gotoLoginOrMainActivity() {
        countDownTimer.cancel();
        if (UserCenter.getInstance().getToken() != null) {
            gotoLoginActivity();
        } else {
            gotoMainActivity();
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}
