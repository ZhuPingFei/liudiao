package com.example.Liudiao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.login.QRLogin;
//import com.yzq.zxinglibrary.android.CaptureActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity {

    private boolean isLogin;
    private Button login;
    private EditText phone;
    private EditText yanzhengma;
    private Button send;

    private TextView login2;
    private TextView login3;

    public EventHandler eh; //事件接收器
    private TimeCount mTimeCount;//计时器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SMSSDK.initSDK(this, "3592cec9d4378", "3fbeea03daf56cfaa5f3137ef20803b3");

        phone = (EditText)findViewById(R.id.login_edit_account);
        phone.setInputType(InputType.TYPE_CLASS_PHONE);
        login2 = (TextView) findViewById(R.id.login2_btn);
        login3 = (TextView) findViewById(R.id.login3_btn);
        send = (Button)findViewById(R.id.send_yanzhengma);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);

        mTimeCount = new TimeCount(60000, 1000);
        login = (Button) findViewById(R.id.login_btn_login);
        isLogin = false;
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        //init();

        eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) { //回调完成

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
                        editor.putString("user_phone",phone.getText().toString());
                        editor.putBoolean("is_login",true);
                        editor.commit();//提交修改
                        Intent intent = new Intent(MainActivity.this,Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

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

//                if(TextUtils.isEmpty(phone.getText().toString().trim())){
//                    Toast.makeText(MainActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
//                }else if(!isMobilPhone(phone.getText().toString().trim())){
//                    Toast.makeText(MainActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
//                }else {
//                    editor.putString("user_phone",s);
//                    editor.putBoolean("is_login",true);
//                    editor.commit();//提交修改
//                    Intent intent = new Intent(MainActivity.this,Main.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                }

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
                Intent intent = new Intent(MainActivity.this,ZhanghaoLogin.class);
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
}
