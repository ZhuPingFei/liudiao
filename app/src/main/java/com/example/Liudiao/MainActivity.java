package com.example.Liudiao;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.login.QRLogin;
import com.example.Liudiao.login.ZhanghaoLogin;
//import com.yzq.zxinglibrary.android.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity {

    private boolean isLogin;
    private Button login;
    private Button zhijielogin;
    private EditText phone;
    private EditText yanzhengma;
    private Button send;

    private TextView login2;
    private TextView login3;

    private String last_user_phone;
    private RelativeLayout yanzheng;

    public EventHandler eh; //事件接收器
    private TimeCount mTimeCount;//计时器

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int uid = 0;
    private int authority = 1;
    int loginType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SMSSDK.initSDK(this, "3592cec9d4378", "3fbeea03daf56cfaa5f3137ef20803b3");

        phone = (EditText)findViewById(R.id.login_edit_account);
        phone.setInputType(InputType.TYPE_CLASS_PHONE);
        login2 = (TextView) findViewById(R.id.login2_btn);
        yanzheng = (RelativeLayout) findViewById(R.id.yanzheng);

        login3 = (TextView) findViewById(R.id.login3_btn);
        send = (Button)findViewById(R.id.send_yanzhengma);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);

        mTimeCount = new TimeCount(60000, 1000);
        login = (Button) findViewById(R.id.login_btn_login);
        isLogin = false;
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        last_user_phone = sharedPreferences.getString("user_phone","");
        final String user_phone = sharedPreferences.getString("user_phone","");
        boolean is_first = sharedPreferences.getBoolean("is_first_login",true);

        phone.setFocusable(true);
//        if (is_first==true){
//            zidongLogin.setVisibility(View.GONE);
//            yanzhengLogin.setVisibility(View.GONE);
//        }else {
//            zidongLogin.setVisibility(View.VISIBLE);
//        }
        //init();

        eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) { //回调完成

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
                        Login();
//                        editor.putString("user_phone",phone.getText().toString());
//                        editor.putBoolean("is_login",true);
//                        editor.commit();//提交修改
//                        Intent intent = new Intent(MainActivity.this,Main.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();

                        //startActivity(new Intent(MainActivity.this, Main.class)); //页面跳转

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){ //获取验证码成功

                    } else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){ //返回支持发送验证码的国家列表
                    }
                } else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = phone.getText().toString();
                char[] arr = s.toCharArray();
                //Login();

                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (isMobilPhone(phone.getText().toString().trim())) {
                        if (!yanzhengma.getText().toString().trim().equals("")) {
                            SMSSDK.submitVerificationCode("+86",phone.getText().toString().trim(),yanzhengma.getText().toString().trim());//提交验证
                        }else{
                            Toast.makeText(MainActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }



            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phone.getText().toString().trim().equals("")){
                    if (isMobilPhone(phone.getText().toString().trim())) {

                        SMSSDK.getVerificationCode("+86",phone.getText().toString().trim());//获取验证码
                        Toast.makeText(MainActivity.this, "已对手机号"+phone.getText().toString()+"发送验证码", Toast.LENGTH_SHORT).show();
                        mTimeCount.start();

                        new Thread(){
                            @Override
                            public void run() {
                                try{
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("user_phone",phone.getText().toString());
                                    String url = "http://175.23.169.100:9030/user/register/";
                                    URL httpUrl = new URL(url);
                                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                                    PrintWriter out = null;
                                    conn.setRequestMethod("POST");
                                    conn.setReadTimeout(5000);
                                    conn.setRequestProperty("Content-type", "application/json");
                                    conn.setDoInput(true);
                                    conn.setDoOutput(true);
                                    out = new PrintWriter(conn.getOutputStream());
                                    //OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                                    //发送请求的参数
                                    //String content ="{mobile:"+phone.getText().toString()+"}";
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
                                    //conn.disconnect();

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }else{
                        Toast.makeText(MainActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ZhanghaoLogin.class);
                startActivity(intent);
                finish();
            }
        });

        login3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
//                    startActivityForResult(intent,1111);
//                }else {
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},100);
//                }
                Intent intent = new Intent(MainActivity.this, QRLogin.class);
                startActivity(intent);
                finish();
            }
        });


        zhijielogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnect(MainActivity.this) == false) {
                    new AlertDialog.Builder(MainActivity.this)
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
                Intent intent = new Intent(MainActivity.this,Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("from_where", 1);
                startActivity(intent);
                finish();
            }



        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static boolean isMobilPhone(String phone) {
        if (phone.isEmpty()) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18([0-4]|[6-9]))|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }


    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            send.setClickable(false);
            send.setText(l/1000 + "重新获取");
        }

        @Override
        public void onFinish() {
            send.setClickable(true);
            send.setText("发送验证码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

//    private void init(){
//        eh = new EventHandler(){
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//
//                if (result == SMSSDK.RESULT_COMPLETE) { //回调完成
//
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
//
//                        startActivity(new Intent(MainActivity.this, Main.class)); //页面跳转
//
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){ //获取验证码成功
//
//                    } else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){ //返回支持发送验证码的国家列表
//
//                    }
//                } else{
//                    ((Throwable)data).printStackTrace();
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调
//    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.send_yanzhengma:
////                SMSSDK.getSupportedCountries();//获取短信目前支持的国家列表
//                if(!phone.getText().toString().trim().equals("")){
//                    if (checkTel(phone.getText().toString().trim())) {
//                        SMSSDK.getVerificationCode("86",phone.getText().toString());//获取验证码
//                        mTimeCount.start();
//                    }else{
//                        Toast.makeText(MainActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.login_btn_login:
//                if (!phone.getText().toString().trim().equals("")) {
//                    if (checkTel(phone.getText().toString().trim())) {
//                        if (!yanzhengma.getText().toString().trim().equals("")) {
//                            SMSSDK.submitVerificationCode("86",phone.getText().toString().trim(),yanzhengma.getText().toString().trim());//提交验证
//                        }else{
//                            Toast.makeText(MainActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(MainActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

//    public boolean checkTel(String tel){
//        Pattern p = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18([0-4]|[6-9]))|(19[8|9]))\\d{8}$");
//        Matcher matcher = p.matcher(tel);
//        return matcher.matches();
//    }


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
        new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("mobile",phone.getText().toString());
                    String url = "http://175.23.169.100:9030/user/login/phone";
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

                        // editor.putInt(""+phone.getText().toString(),uid);
                        if (!(jsonObj1.getString("user_name")+"").equals("") && !(jsonObj1.getString("user_name")+"").equals("null") ){
                            editor.putString("user_name",jsonObj1.getString("user_name"));
                        }
                        if (!(jsonObj1.getString("user_phone")+"").equals("") && !(jsonObj1.getString("user_phone")+"").equals("null") ){
                            editor.putString("user_phone",jsonObj1.getString("user_phone"));
                        }
                        editor.putBoolean("is_first_login",false);
                        editor.putBoolean("is_login",true);
                        editor.putInt("authority",authority);
                        editor.commit();//提交修改
                        if (!phone.equals(last_user_phone)){
                            sharedPreferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putInt("current_banliId",0);
                            editor.putString("current_banliName","未绑定");
                            editor.putInt("authority",authority);
                            editor.commit();
                        }
                        Intent intent = new Intent(MainActivity.this,Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("from_where", 1);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
