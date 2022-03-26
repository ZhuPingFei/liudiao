package com.example.Liudiao;

import android.app.Application;

import cn.smssdk.SMSSDK;

public class LiudiaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //SMSSDK.initSDK(this, "359332cf6a49e", "ddb1717a68ce7083d8f9c1aa58667d69");
    }
}
