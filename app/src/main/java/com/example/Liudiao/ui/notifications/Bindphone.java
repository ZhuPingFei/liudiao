package com.example.Liudiao.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class Bindphone extends AppCompatActivity {

    private ImageView r_back;
    private EditText phone;
    private EditText yanzhengma;
    private Button send;
    private Button confirm;

    public EventHandler eh; //事件接收器
    private TimeCount mTimeCount;//计时器

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int uid = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_phone);

        SMSSDK.initSDK(this, "3592cec9d4378", "3fbeea03daf56cfaa5f3137ef20803b3");

        phone = (EditText) findViewById(R.id.phone);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        send = (Button) findViewById(R.id.send_yanzhengma);
        confirm = (Button) findViewById(R.id.bindphone_confirm);

        mTimeCount = new TimeCount(60000, 1000);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        uid = sharedPreferences.getInt("user_id",0);

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        eh = new EventHandler(){
            @Override
            public void afterEvent(final int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) { //回调完成

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("user_phone",phone.getText().toString());
                                    jsonObject.put("user_id",uid);
                                    String url = "http://175.23.169.100:9030/user/set_phone";
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

                                    int code = jsonObj1.getInt("code");
                                    if (code == 0){
                                        editor.putString("user_phone",phone.getText().toString());
                                        editor.commit();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(Bindphone.this,"绑定成功！",Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                            }
                                        });
                                    }else {
                                        Toast.makeText(Bindphone.this,"绑定失败！",Toast.LENGTH_SHORT).show();
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
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){ //获取验证码成功

                    } else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){

                    }
                } else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = phone.getText().toString();
                if (!TextUtils.isEmpty(phone.getText().toString().trim())) {
                    if (isMobilPhone(phone.getText().toString().trim())) {
                        if (!yanzhengma.getText().toString().trim().equals("")) {
                            SMSSDK.submitVerificationCode("+86",phone.getText().toString().trim(),yanzhengma.getText().toString().trim());//提交验证
                        }else{
                            Toast.makeText(Bindphone.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Bindphone.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Bindphone.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!phone.getText().toString().trim().equals("")){
                    if (isMobilPhone(phone.getText().toString().trim())) {

                        SMSSDK.getVerificationCode("+86",phone.getText().toString().trim());//获取验证码
                        Toast.makeText(Bindphone.this, "已对手机号"+phone.getText().toString()+"发送验证码", Toast.LENGTH_SHORT).show();
                        mTimeCount.start();
                    }else{
                        Toast.makeText(Bindphone.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Bindphone.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
}
