package com.example.Liudiao.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.Main;
import com.example.Liudiao.R;
import com.example.Liudiao.Xieyi;
import com.example.Liudiao.login.QRLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.login.LoginException;

public class ZhanghaoLogin extends AppCompatActivity {

    private TextView login1;
    private Button login;
    private TextView login2;
    private EditText user_name;
    private EditText password;

    private CheckBox sure;
    private TextView xieyi;

    private int uid = 0;
    private int authority = 1;
    private int last_uid;
    private String last_userName;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        login1 = (TextView) findViewById(R.id.login1_btn);
        login = (Button) findViewById(R.id.login);
        login2 = (TextView) findViewById(R.id.login2_btn);
        user_name = (EditText) findViewById(R.id.login_edit_account);
        password = (EditText) findViewById(R.id.login_edit_key);
        sure = (CheckBox) findViewById(R.id.xieyi);
        xieyi = (TextView) findViewById(R.id.xieyi_text);

        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();//获取编辑器
        last_uid = preferences.getInt("user_id",0);
        last_userName = preferences.getString("user_name","");

        user_name.setText(last_userName);


        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int uid = preferences.getInt("user_id",0);
                Toast.makeText(ZhanghaoLogin.this,"此功能暂不开放",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ZhanghaoLogin.this,MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int uid = preferences.getInt("user_id",0);
                Intent intent = new Intent(ZhanghaoLogin.this, QRLogin.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sure.isChecked()){
                    Login();
                }else {
                    Toast.makeText(ZhanghaoLogin.this,"请您阅读并同意《流调APP用户隐私权政策》",Toast.LENGTH_LONG).show();
                }

            }
        });

        xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhanghaoLogin.this, Xieyi.class);
                startActivity(intent);
            }
        });

    }

    public void Login(){
        //进行网络检查
        if (isConnect(this) == false) {
            new AlertDialog.Builder(this)
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
        if (user_name.getText().toString().length()==0){
            Toast.makeText(ZhanghaoLogin.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
        }else {
            if (password.getText().toString().length()==0){
                Toast.makeText(ZhanghaoLogin.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("user_name",user_name.getText().toString());
                            jsonObject.put("password",password.getText().toString());
                            String url = "http://175.23.169.100:9030/user/login/username";
                            URL httpUrl = new URL(url);
                            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                            PrintWriter out = null;
                            conn.setRequestMethod("POST");
                            conn.setReadTimeout(5000);
                            conn.setRequestProperty("Content-type", "application/json");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            out = new PrintWriter(conn.getOutputStream());
                            out.print(jsonObject);
                            out.flush();
                            InputStream is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                            StringBuffer sb = new StringBuffer();
                            String str;
                            while ((str = reader.readLine()) != null) {
                                sb.append(str);
                            }
                            JSONObject jsonObj1 = new JSONObject(sb.toString());
                            Message message = new Message();
                            int code = jsonObj1.getInt("code");
                            if (code == 0){
                                uid = jsonObj1.getInt("user_id");
                                authority = jsonObj1.getInt("authority");
                                editor.putInt("user_id",uid);
                                editor.putBoolean("is_login",true);
                                if (!(jsonObj1.getString("user_name")+"").equals("") && !(jsonObj1.getString("user_name")+"").equals("null") ){
                                    editor.putString("user_name",jsonObj1.getString("user_name"));
                                }
                                editor.putInt("authority",authority);
                                editor.commit();
                                if (uid != last_uid){
                                    preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
                                    editor = preferences.edit();
                                    editor.putInt("current_banliId",0);
                                    editor.putString("current_banliName","未绑定");
                                    editor.putInt("authority",authority);
                                    editor.commit();
                                }

                                Log.e("123", String.valueOf(authority));

                                Intent intent = new Intent(ZhanghaoLogin.this, Main.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ZhanghaoLogin.this,"用户名或密码错误！",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

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
