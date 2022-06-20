package com.example.Liudiao;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import cn.smssdk.SMSSDK;

public class LiudiaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //SMSSDK.initSDK(this, "359332cf6a49e", "ddb1717a68ce7083d8f9c1aa58667d69");
    }
    @Override
    protected void attachBaseContext(Context context){
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
