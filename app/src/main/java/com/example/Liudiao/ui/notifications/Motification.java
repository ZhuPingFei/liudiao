package com.example.Liudiao.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.Main;
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

public class Motification extends AppCompatActivity {

    private ImageView r_back;
    private EditText userName;
    private EditText password;
    private EditText passwordConfirm;
    private Button confirm;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int user_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motification);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        user_id = sharedPreferences.getInt("user_id",0);

        userName = (EditText) findViewById(R.id.motification_user_name);
        password = (EditText) findViewById(R.id.motification_user_password);
        passwordConfirm = (EditText) findViewById(R.id.motification_confirm_password);
        confirm = (Button) findViewById(R.id.motification_confirm);

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")){
                    Toast.makeText(Motification.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    userName.setFocusable(true);
                }else {
                    if (password.getText().toString().equals("")){
                        Toast.makeText(Motification.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                        password.setFocusable(true);
                    }else {
                        if (passwordConfirm.getText().toString().equals("")){
                            Toast.makeText(Motification.this,"请确认密码！",Toast.LENGTH_SHORT).show();
                            passwordConfirm.setFocusable(true);
                        }else {
                            if (!password.getText().toString().equals(passwordConfirm.getText().toString())){
                                Toast.makeText(Motification.this,"两次输入密码必须相同！",Toast.LENGTH_SHORT).show();
                            }else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("user_name",userName.getText().toString());
                                            jsonObject.put("password",password.getText().toString());
                                            jsonObject.put("user_id",user_id);
                                            String url = "http://175.23.169.100:9030/user/set_usrname_and_psswrd";
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
                                            if (code == 0) {
                                                editor.putString("user_name",userName.getText().toString());
                                                editor.commit();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Motification.this,"设置成功！",Toast.LENGTH_SHORT).show();
                                                        onBackPressed();
                                                    }
                                                });


                                            }else if (code == 504){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Motification.this,"用户名已存在！",Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Motification.this,"设置失败！",Toast.LENGTH_SHORT).show();
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
                }
            }
        });

    }
}
