package com.sanqian.d6.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    protected Handler handler = new Handler();

    public static SplashActivity splashActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = this;
        setContentView(R.layout.activity_splash);
        handler.postDelayed(new Runnable() {    //匿名内部类  创建线程
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, PrivacyActivity.class));      //界面转跳
                splashActivity.finish();
            }
        }, 1500);         //第二个参数是停留的时间
    }


}


