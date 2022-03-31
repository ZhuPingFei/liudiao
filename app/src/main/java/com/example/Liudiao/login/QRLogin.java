package com.example.Liudiao.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.Liudiao.Main;
import com.example.Liudiao.MainActivity;
import com.example.Liudiao.R;
import com.example.Liudiao.ZhanghaoLogin;
import com.example.Liudiao.ui.notifications.Erweima;
import com.example.Liudiao.zxing.android.CaptureActivity;

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


public class QRLogin extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int uid = 0;

    private Button login;
    private TextView login1;
    private TextView login2;

    private String last_user_phone;

    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_login);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        last_user_phone = sharedPreferences.getString("user_phone","");

        login = (Button) findViewById(R.id.qr_login);
        login1 = (TextView) findViewById(R.id.login1_btn);
        login2 = (TextView) findViewById(R.id.login2_btn);

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRLogin.this, ZhanghaoLogin.class);
                startActivity(intent);
                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(QRLogin.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED ){
// 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                    ActivityCompat.requestPermissions(QRLogin.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    goScan();
                }
            }
        });
    }

    private void goScan(){
        Intent intent = new Intent(QRLogin.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScan();
                } else {
                    Toast.makeText(this, "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                //Toast.makeText(QRLogin.this,content,Toast.LENGTH_SHORT).show();
                //tv_scanResult.setText("你扫描到的内容是：" + content);
                Login(content);
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


    public void Login(final String phone){
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
                    jsonObject.put("mobile",phone);
                    String url = "http://175.23.169.100:9000/user/login/phone";
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
                    if (code == 0){
                        uid = jsonObj1.getInt("user_id");
                        editor.putInt("user_id",uid);
                        // editor.putInt(""+phone.getText().toString(),uid);
                        editor.putString("user_phone",phone);
                        editor.putBoolean("is_first_login",false);
                        editor.putBoolean("is_login",true);
                        editor.commit();//提交修改

                        if (!phone.equals(last_user_phone)){
                            sharedPreferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putInt("current_banliId",0);
                            editor.putString("current_banliName","未绑定");
                            editor.commit();
                        }


                        Intent intent = new Intent(QRLogin.this, Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("from_where", 1);
                        startActivity(intent);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(QRLogin.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(QRLogin.this,"登录失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
//                        uid = sharedPreferences.getInt(""+phone.getText().toString(),0);
//                        editor.putInt("user_id",uid);
//                        editor.putString("user_phone",phone.getText().toString());
//                        editor.putBoolean("is_first_login",false);
//                        editor.putBoolean("is_login",true);
//                        editor.commit();//提交修改
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
