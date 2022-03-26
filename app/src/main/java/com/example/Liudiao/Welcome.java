package com.example.Liudiao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        startMainActivity();
    }

    private void startMainActivity(){

        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
                boolean isLogin = preferences.getBoolean("is_login", Boolean.parseBoolean(""));
                if (isLogin == false){
                    Intent mainIntent = new Intent(Welcome.this,MainActivity.class);
                    startActivity(mainIntent);
                }else {
                    Intent mainIntent = new Intent(Welcome.this,Main.class);
                    startActivity(mainIntent);
                }

                Welcome.this.finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask,2000);//延时两秒执行 run 里面的操作
    }
}
