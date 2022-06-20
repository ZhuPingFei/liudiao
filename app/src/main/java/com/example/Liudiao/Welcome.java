package com.example.Liudiao;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.example.Liudiao.login.ZhanghaoLogin;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends Activity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String last_user_phone;
    private boolean is_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();//获取编辑器
//
//        editor.putInt("user_id",113);
//        editor.putString("user_phone","18888880000");
//        editor.putBoolean("is_first_login",false);
//        editor.putBoolean("is_login",true);
//        editor.commit();

        startMainActivity();
    }

    private void startMainActivity(){

        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {

                if (isConnect(Welcome.this) == false) {
                    new AlertDialog.Builder(Welcome.this)
                            .setTitle("网络错误")
                            .setMessage("网络连接失败，请开启GPRS或者WIFI连接")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(0);
                                }
                            }).show();
                }

                SharedPreferences preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                int user_id = preferences.getInt("user_id",0);
                is_login = preferences.getBoolean("is_login",false);
                Intent mainIntent;
                if (is_login && user_id!=0){
                    mainIntent = new Intent(Welcome.this, Main.class);
                }else {
                    mainIntent = new Intent(Welcome.this, ZhanghaoLogin.class);
                }

                startActivity(mainIntent);
                Welcome.this.finish();

            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask,2000);//延时两秒执行 run 里面的操作
    }
    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.v("error", e.toString());
        }
        return false;
    }

}
