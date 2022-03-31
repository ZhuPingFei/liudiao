package com.example.Liudiao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.Liudiao.ui.notifications.RequestUserThread;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends AppCompatActivity {

    private SharedPreferences preferences ;
    private SharedPreferences.Editor editor;
    private String current_banliName;
    private int current_banliId;
    private int user_id;
    private int transactor_num;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();

            int code = bd.getInt("code",0);
            transactor_num = bd.getInt("transactor_num",0);


            if (code==0 && transactor_num!=0){
                int trans_id = bd.getInt("transactor_id",0);
                String name = bd.getString("name","");
                current_banliId = trans_id;
                current_banliName = name;

            }else {
                current_banliId = 100000;
                current_banliName = "未绑定";
            }
//            preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
//            editor = preferences.edit();
//            editor.putInt("current_banliId",current_banliId);
//            editor.putString("current_banliName",current_banliName);
//            editor.commit();

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("user",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        user_id = preferences.getInt("user_id",0);

        String url = "http://175.23.169.100:9000/transactor/getTransactor";
        RequestUserThread rdt = new RequestUserThread(url,user_id,handler);
        rdt.start();



//        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
//        editor = preferences.edit();

//        Intent intent = getIntent();
//        int come_where = intent.getIntExtra("from_where",0);
//        if (come_where == 1){
//            editor.putInt("current_banliId",current_banliId);
//            editor.putString("current_banliName",current_banliName);
//            editor.commit();
//        }
//        Intent intent = getIntent();
//        if (intent != null){
//            Bundle bd = intent.getExtras();
//            current_banliName = bd.getString("current_banliName");
//            current_banliId = bd.getInt("current_banliId");
//            editor.putInt("current_banliId",current_banliId);
//            editor.putString("current_banliName",current_banliName);
//            editor.commit();
//        }



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_xingcheng, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
