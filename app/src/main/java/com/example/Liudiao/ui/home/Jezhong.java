package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
import java.util.ArrayList;

public class Jezhong extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private boolean isMe;

    private ArrayList<String> jiezhong_danwei = new ArrayList<String>();

    private ArrayAdapter<String> jiezhong1;
    private ArrayAdapter<String> jiezhong2;
    private ArrayAdapter<String> jiezhong3;

    private Spinner jiezhong_spinner1;
    private int jiezhong1Position;
    private Spinner jiezhong_spinner2;
    private int jiezhong2Position;
    private Spinner jiezhong_spinner3;
    private int jiezhong3Position;

    private boolean isFirstSpinner1 = true;
    private boolean isFirstSpinner2 = true;
    private boolean isFirstSpinner3 = true;

    private Button jiezhong_tijiao;

    private ImageView r_back;
    private TextView okey;

    private TextView selectJiezhong1;
    private TextView selectJiezhong2;
    private TextView selectJiezhong3;

    private DatePicker dp = null;
    private Calendar calendar = null;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    private AlertDialog.Builder builder;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();

            int code = bd.getInt("code");

            if (code == 0) {
                String time1 = bd.getString("firsttime");
                String time2 = bd.getString("secondtime");
                String time3 = bd.getString("thirdtime");

                int type1 = bd.getInt("type1");
                int type2 = bd.getInt("type2");
                int type3 = bd.getInt("type3");

                jiezhong_spinner1 = (Spinner) findViewById(R.id.jiezhong1_spinner);
                jiezhong_spinner2 = (Spinner) findViewById(R.id.jiezhong2_spinner);
                jiezhong_spinner3 = (Spinner) findViewById(R.id.jiezhong3_spinner);

                selectJiezhong1 = (TextView) findViewById(R.id.select_jiezhongDate1);
                selectJiezhong2 = (TextView) findViewById(R.id.select_jiezhongDate2);
                selectJiezhong3 = (TextView) findViewById(R.id.select_jiezhongDate3);


                selectJiezhong1.setText(time1);
                selectJiezhong2.setText(time2);
                selectJiezhong3.setText(time3);
                jiezhong_spinner1.setSelection(type1);
                jiezhong_spinner2.setSelection(type2);
                jiezhong_spinner3.setSelection(type3);
            }

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jiezhong);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);

//        preferences = getSharedPreferences("user_jiezhong", Activity.MODE_PRIVATE);
//        editor = preferences.edit();//获取编辑器
//        String getJiezhongtime1 = preferences.getString("jiezhong_time1","");
//        int getJiezhong1 = preferences.getInt("jiezhong_danwei1",0);
//        String getJiezhongtime2 = preferences.getString("jiezhong_time2","");
//        int getJiezhong2 = preferences.getInt("jiezhong_danwei2",0);
//        String getJiezhongtime3 = preferences.getString("jiezhong_time3","");
//        int getJiezhong3 = preferences.getInt("jiezhong_danwei3",0);

        jiezhong_danwei.add("北京生物 BeiJing Institute of Biological Products Co.,Ltd.");
        jiezhong_danwei.add("武汉生物 WuHan Institute of Biological Product Co.,Ltd.");
        jiezhong_danwei.add("北京科兴中维 SINOVAC");
        jiezhong_danwei.add("康希诺 CanSinoBIO");
        jiezhong_danwei.add("安徽智飞龙科马 Anhui Zhifei Longcom Biopharmaceutical Co.,Ltd.");
        jiezhong_danwei.add("美国辉瑞 Pfizer");
        jiezhong_danwei.add("美国莫德纳 Modern");
        jiezhong_danwei.add("美国强生 Johnson");
        jiezhong_danwei.add("英国牛津/阿斯利康 Oxford University and AstraZeneca");
        jiezhong_danwei.add("俄罗斯 Gamaleya");
        jiezhong_danwei.add("其他");
        jiezhong_danwei.add("不详");

        String url = "http://175.23.169.100:9000/case-vaccination/get";
        RequestJiezhongThread rdt = new RequestJiezhongThread(url,current_transId,handler);
        rdt.start();

        jiezhong_spinner1 = (Spinner) findViewById(R.id.jiezhong1_spinner);
        jiezhong_spinner2 = (Spinner) findViewById(R.id.jiezhong2_spinner);
        jiezhong_spinner3 = (Spinner) findViewById(R.id.jiezhong3_spinner);

        selectJiezhong1 = (TextView) findViewById(R.id.select_jiezhongDate1);
        selectJiezhong2 = (TextView) findViewById(R.id.select_jiezhongDate2);
        selectJiezhong3 = (TextView) findViewById(R.id.select_jiezhongDate3);

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        okey = (TextView) findViewById(R.id.okey);
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuffer sb1 = new StringBuffer(selectJiezhong1.getText().toString());
                final StringBuffer sb2 = new StringBuffer(selectJiezhong2.getText().toString());
                final StringBuffer sb3 = new StringBuffer(selectJiezhong3.getText().toString());
                if (selectJiezhong1.getText().toString().length()==8){
                    sb1.insert(5,"0");
                    sb1.insert(8,"0");
                }else if (selectJiezhong1.getText().toString().length()==9){
                    String[] split = selectJiezhong1.getText().toString().split("-");
                    if (split[1].length() == 2){
                        sb1.insert(8,"0");
                    }else {
                        sb1.insert(5,"0");
                    }
                }
                if (selectJiezhong2.getText().toString().length()==8){

                    sb2.insert(5,"0");
                    sb2.insert(8,"0");
                }else if (selectJiezhong2.getText().toString().length()==9){
                    String[] split = selectJiezhong2.getText().toString().split("-");
                    if (split[1].length() == 2){
                        sb2.insert(8,"0");
                    }else {
                        sb2.insert(5,"0");
                    }
                }
                if (selectJiezhong3.getText().toString().length()==8){

                    sb3.insert(5,"0");
                    sb3.insert(8,"0");
                }else if (selectJiezhong3.getText().toString().length()==9){
                    String[] split = selectJiezhong3.getText().toString().split("-");
                    if (split[1].length() == 2){
                        sb3.insert(8,"0");
                    }else {
                        sb3.insert(5,"0");
                    }
                }
                if (!isMe){
                    builder = new AlertDialog.Builder(Jezhong.this).setTitle("重要提醒")
                            .setMessage("当前为代办模式，是否确认提交？")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String postUrl = "http://175.23.169.100:9000/case-vaccination/set";
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("transactor_id",current_transId);
                                                jsonObject.put("firsttime",sb1.toString());
                                                jsonObject.put("type1",jiezhong1Position);
                                                jsonObject.put("secondtime",sb2.toString());
                                                jsonObject.put("type2",jiezhong2Position);
                                                jsonObject.put("thirdtime",sb3.toString());
                                                jsonObject.put("type3",jiezhong3Position);

                                                URL httpUrl = new URL(postUrl);
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
                                                int isUpadteSeccess = jsonObj1.getInt("code");
                                                if (isUpadteSeccess == 0){
                                                    editor.putBoolean(current_transId+"hasJezhong",true);
                                                    editor.commit();
                                                    Looper.prepare();
                                                    Toast.makeText(Jezhong.this,"提交成功",Toast.LENGTH_SHORT).show();
                                                    Looper.loop();
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
                                    onBackPressed();
                                    dialogInterface.dismiss();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String postUrl = "http://175.23.169.100:9000/case-vaccination/set";
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("transactor_id",current_transId);
                                jsonObject.put("firsttime",sb1.toString());
                                jsonObject.put("type1",jiezhong1Position);
                                jsonObject.put("secondtime",sb2.toString());
                                jsonObject.put("type2",jiezhong2Position);
                                jsonObject.put("thirdtime",sb3.toString());
                                jsonObject.put("type3",jiezhong3Position);

                                URL httpUrl = new URL(postUrl);
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
                                int isUpadteSeccess = jsonObj1.getInt("code");
                                if (isUpadteSeccess == 0){
                                    Looper.prepare();
                                    Toast.makeText(Jezhong.this,"提交成功",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
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
                    //onBackPressed();
                }
                //dataCommit();

            }
        });





        //初始化日期选择器
        dp = (DatePicker) findViewById(R.id.date_picker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        jiezhong1 = new ArrayAdapter<String>(Jezhong.this, R.layout.simple_spinner_item, jiezhong_danwei);
        jiezhong2 = new ArrayAdapter<String>(Jezhong.this, R.layout.simple_spinner_item, jiezhong_danwei);
        jiezhong3 = new ArrayAdapter<String>(Jezhong.this, R.layout.simple_spinner_item, jiezhong_danwei);
        //设置样式
        jiezhong1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        jiezhong2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        jiezhong3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        //加载适配器
        jiezhong_spinner1.setAdapter(jiezhong1);
        jiezhong_spinner2.setAdapter(jiezhong2);
        jiezhong_spinner3.setAdapter(jiezhong3);

//        selectJiezhong1.setText(getJiezhongtime1);
//        jiezhong_spinner1.setSelection(getJiezhong1);
//        selectJiezhong2.setText(getJiezhongtime2);
//        jiezhong_spinner2.setSelection(getJiezhong2);
//        selectJiezhong3.setText(getJiezhongtime3);
//        jiezhong_spinner3.setSelection(getJiezhong3);

        jiezhong_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSpinner1 = true){
                    view.setVisibility(View.INVISIBLE);
                }
                isFirstSpinner1 = false;
                jiezhong1Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jiezhong_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSpinner2 = true){
                    view.setVisibility(View.INVISIBLE);
                }
                isFirstSpinner2 = false;
                jiezhong2Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jiezhong_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstSpinner3 = true){
                    view.setVisibility(View.INVISIBLE);
                }
                isFirstSpinner3 = false;
                jiezhong3Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectJiezhong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jezhong.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong1.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        selectJiezhong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jezhong.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong2.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        selectJiezhong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jezhong.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong3.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences("user_jiezhong", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        editor.putString("jiezhong_time1",selectJiezhong1.getText().toString());
        editor.putString("jiezhong_time2",selectJiezhong2.getText().toString());
        editor.putString("jiezhong_time3",selectJiezhong3.getText().toString());
        editor.putInt("jiezhong_danwei1",jiezhong1Position);
        editor.putInt("jiezhong_danwei2",jiezhong2Position);
        editor.putInt("jiezhong_danwei3",jiezhong3Position);
       // Toast.makeText(Jezhong.this,""+jiezhong1Position+"=="+jiezhong2Position+"=="+jiezhong3Position,Toast.LENGTH_SHORT).show();
        editor.commit();
    }
}
