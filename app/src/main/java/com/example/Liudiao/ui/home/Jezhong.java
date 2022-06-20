package com.example.Liudiao.ui.home;

import static com.example.Liudiao.R.color.red;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;

import org.apache.poi.hssf.record.chart.FontIndexRecord;
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
    private SharedPreferences preferences1;
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
    private int times = 0;

    private LinearLayout first_date;
    private LinearLayout second_date;
    private LinearLayout third_date;
    private RelativeLayout first_unit;
    private RelativeLayout second_unit;
    private RelativeLayout third_unit;
    private LinearLayout jz1;
    private LinearLayout jz2;
    private LinearLayout jz3;

    private RadioGroup radioGroup;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;

//    private RadioButton radioButton11;
//    private RadioButton radioButton12;
//    private RadioButton radioButton21;
//    private RadioButton radioButton22;
//    private RadioButton radioButton31;
//    private RadioButton radioButton32;
    private Switch Switch1;
    private Switch Switch2;
    private Switch Switch3;

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

                if (!time1.equals("") && !time1.equals("null")){
                    selectJiezhong1.setText(time1);
                }else {
                    selectJiezhong1.setText("");
                }

                if (!time2.equals("") && !time2.equals("null")){
                    selectJiezhong2.setText(time2);
                }else {
                    selectJiezhong2.setText("");
                }

                if (!time3.equals("") && !time3.equals("null")){
                    selectJiezhong3.setText(time3);
                }else {
                    selectJiezhong3.setText("");
                }

                jiezhong_spinner1.setSelection(type1);
                jiezhong_spinner2.setSelection(type2);
                jiezhong_spinner3.setSelection(type3);
                preferences1 = getSharedPreferences(current_transId+"jiezhong",Activity.MODE_PRIVATE);
                Switch1.setChecked(preferences1.getBoolean("switch1",false));
                Switch2.setChecked(preferences1.getBoolean("switch2",false));
                Switch3.setChecked(preferences1.getBoolean("switch3",false));
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


//        String getJiezhongtime1 = preferences.getString("jiezhong_time1","");
//        int getJiezhong1 = preferences.getInt("jiezhong_danwei1",0);
//        String getJiezhongtime2 = preferences.getString("jiezhong_time2","");
//        int getJiezhong2 = preferences.getInt("jiezhong_danwei2",0);
//        String getJiezhongtime3 = preferences.getString("jiezhong_time3","");
//        int getJiezhong3 = preferences.getInt("jiezhong_danwei3",0);
        jiezhong_danwei.add("");
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

        String url = "http://175.23.169.100:9030/case-vaccination/get";
        RequestJiezhongThread rdt = new RequestJiezhongThread(url,current_transId,handler);
        rdt.start();

        jiezhong_spinner1 = (Spinner) findViewById(R.id.jiezhong1_spinner);
        jiezhong_spinner2 = (Spinner) findViewById(R.id.jiezhong2_spinner);
        jiezhong_spinner3 = (Spinner) findViewById(R.id.jiezhong3_spinner);

        selectJiezhong1 = (TextView) findViewById(R.id.select_jiezhongDate1);
        selectJiezhong2 = (TextView) findViewById(R.id.select_jiezhongDate2);
        selectJiezhong3 = (TextView) findViewById(R.id.select_jiezhongDate3);

//        radioButton11 = (RadioButton) findViewById(R.id.jezhong_radioButton1);
//        radioButton12 = (RadioButton) findViewById(R.id.jezhong_radioButton2);
//        radioButton21 = (RadioButton) findViewById(R.id.jezhong_radioButton21);
//        radioButton22 = (RadioButton) findViewById(R.id.jezhong_radioButton22);
//        radioButton31 = (RadioButton) findViewById(R.id.jezhong_radioButton31);
//        radioButton32 = (RadioButton) findViewById(R.id.jezhong_radioButton32);
        Switch1 = (Switch) findViewById(R.id.switch1);
        Switch2 = (Switch) findViewById(R.id.switch2);
        Switch3 = (Switch) findViewById(R.id.switch3);

        first_date = (LinearLayout) findViewById(R.id.first_date);
        second_date = (LinearLayout) findViewById(R.id.second_date);
        third_date = (LinearLayout) findViewById(R.id.third_date);
        first_unit = (RelativeLayout) findViewById(R.id.first_unit);
        second_unit = (RelativeLayout) findViewById(R.id.second_unit);
        third_unit = (RelativeLayout) findViewById(R.id.third_unit);
        radioGroup = (RadioGroup) findViewById(R.id.jezhong_radiogroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.jezhong_radiogroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.jezhong_radiogroup3);
        jz1=(LinearLayout) findViewById(R.id.jezhong1);
        jz2=(LinearLayout) findViewById(R.id.jezhong2);
        jz3=(LinearLayout) findViewById(R.id.jezhong3);
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = findViewById(checkedId);
//                String s = radioButton.getText().toString();
//                if (s.equals("否")){
//                    first_date.setVisibility(View.GONE);
//                    first_unit.setVisibility(View.GONE);
//                }else{
//                    first_date.setVisibility(View.VISIBLE);
//                    first_unit.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = findViewById(checkedId);
//                String s = radioButton.getText().toString();
//                if (s.equals("否")){
//                    second_date.setVisibility(View.GONE);
//                    second_unit.setVisibility(View.GONE);
//                }else{
//                    second_date.setVisibility(View.VISIBLE);
//                    second_unit.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = findViewById(checkedId);
//                String s = radioButton.getText().toString();
//                if (s.equals("否")){
//                    third_date.setVisibility(View.GONE);
//                    third_unit.setVisibility(View.GONE);
//                }else{
//                    third_date.setVisibility(View.VISIBLE);
//                    third_unit.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        preferences1 = getSharedPreferences(current_transId+"jiezhong",Activity.MODE_PRIVATE);
        Switch1.setChecked(preferences1.getBoolean("switch1",false));
        Switch2.setChecked(preferences1.getBoolean("switch2",false));
        Switch3.setChecked(preferences1.getBoolean("switch3",false));
        if (Switch1.isChecked()){
            first_date.setVisibility(View.VISIBLE);
            first_unit.setVisibility(View.VISIBLE);
            jz2.setVisibility(View.VISIBLE);

        }else{
            first_date.setVisibility(View.GONE);
            first_unit.setVisibility(View.GONE);
            jz2.setVisibility(View.GONE);
            second_date.setVisibility(View.GONE);
            second_unit.setVisibility(View.GONE);
            jz3.setVisibility(View.GONE);
            third_date.setVisibility(View.GONE);
            third_unit.setVisibility(View.GONE);
            Switch2.setChecked(false);
            Switch3.setChecked(false);
        }
        if (Switch2.isChecked()){
            second_date.setVisibility(View.VISIBLE);
            second_unit.setVisibility(View.VISIBLE);
            jz3.setVisibility(View.VISIBLE);
        }else{
            second_date.setVisibility(View.GONE);
            second_unit.setVisibility(View.GONE);
            jz3.setVisibility(View.GONE);
            third_date.setVisibility(View.GONE);
            third_unit.setVisibility(View.GONE);
            Switch3.setChecked(false);
        }
        if (Switch3.isChecked()){

            third_date.setVisibility(View.VISIBLE);
            third_unit.setVisibility(View.VISIBLE);

        }else{
            third_date.setVisibility(View.GONE);
            third_unit.setVisibility(View.GONE);
        }
        Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Switch1.isChecked()){
                    first_date.setVisibility(View.VISIBLE);
                    first_unit.setVisibility(View.VISIBLE);
                    jz2.setVisibility(View.VISIBLE);
                    second_date.setVisibility(View.GONE);
                    second_unit.setVisibility(View.GONE);
                    jz3.setVisibility(View.GONE);
                    third_date.setVisibility(View.GONE);
                    third_unit.setVisibility(View.GONE);
                    Switch2.setChecked(false);
                    Switch3.setChecked(false);
                }else{
                    first_date.setVisibility(View.GONE);
                    first_unit.setVisibility(View.GONE);
                    jz2.setVisibility(View.GONE);
                    second_date.setVisibility(View.GONE);
                    second_unit.setVisibility(View.GONE);
                    jz3.setVisibility(View.GONE);
                    third_date.setVisibility(View.GONE);
                    third_unit.setVisibility(View.GONE);
                    Switch2.setChecked(false);
                    Switch3.setChecked(false);
                }
            }
        });
        Switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Switch2.isChecked()){

                    second_date.setVisibility(View.VISIBLE);
                    second_unit.setVisibility(View.VISIBLE);
                    jz3.setVisibility(View.VISIBLE);
                    Switch3.setChecked(false);
                    third_date.setVisibility(View.GONE);
                    third_unit.setVisibility(View.GONE);
                }else{
                    second_date.setVisibility(View.GONE);
                    second_unit.setVisibility(View.GONE);
                    jz3.setVisibility(View.GONE);
                    third_date.setVisibility(View.GONE);
                    third_unit.setVisibility(View.GONE);
                    Switch3.setChecked(false);
                }
            }
        });
        Switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Switch3.isChecked()){

                    third_date.setVisibility(View.VISIBLE);
                    third_unit.setVisibility(View.VISIBLE);

                }else{
                    third_date.setVisibility(View.GONE);
                    third_unit.setVisibility(View.GONE);
                }
            }
        });



//        radioButton11.setChecked(preferences.getBoolean("radiobutton11",false));
//        radioButton12.setChecked(preferences.getBoolean("radiobutton12",false));
//        radioButton21.setChecked(preferences.getBoolean("radiobutton21",false));
//        radioButton22.setChecked(preferences.getBoolean("radiobutton22",false));
//        radioButton31.setChecked(preferences.getBoolean("radiobutton31",false));
//        radioButton32.setChecked(preferences.getBoolean("radiobutton32",false));



        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                dataCommit();
            }
        });

        okey = (TextView) findViewById(R.id.okey);
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int times = -1;
                if (Switch1.isChecked() && Switch2.isChecked() && Switch3.isChecked()){
                    times = 3;
                }else if (Switch1.isChecked() && Switch2.isChecked() && Switch3.isChecked()){
                    times = 0;
                } else {
                    if (Switch1.isChecked()){
                        times++;
                    }
                    if (Switch2.isChecked()){
                        times++;
                    }
                    if (Switch3.isChecked()){
                        times++;
                    }
                    if( times != -1){
                        times++;
                    }
                }
//                if (radioButton11.isChecked() && radioButton21.isChecked() && radioButton31.isChecked()){
//                    times = 3;
//                }else if (radioButton12.isChecked() && radioButton22.isChecked() && radioButton32.isChecked()){
//                    times = 0;
//                } else {
//                    if (radioButton11.isChecked()){
//                        times++;
//                    }
//                    if (radioButton21.isChecked()){
//                        times++;
//                    }
//                    if (radioButton31.isChecked()){
//                        times++;
//                    }
//                    if( times != -1){
//                        times++;
//                    }
//                }
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
                String date1 = sb1.toString();
//                if (date1.equals("")) {
//                    date1 = null;
//                }

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
                String date2 = sb2.toString();
//                if (date2.equals("")) {
//                    date2 = null;
//                }

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
                String date3 = sb3.toString();
//                if (date3.equals("")) {
//                    date3 = null;
//                }

                dataCommit();
                if (!isMe){
                    final int finalTimes = times;
                    final String finalDate = date1;
                    final String finalDate1 = date2;
                    final String finalDate2 = date3;
                    int year1=10000;
                    int month1=10000;
                    int day1=10000;
                    int year2=10001;
                    int month2=10001;
                    int day2=10001;
                    int year3=10002;
                    int month3=10002;
                    int day3=10002;
                    if (!date1.equals("")){
                        if (date1.contains("-")) {
                            String [] parts1 = date1.split("-");
                            year1 = Integer.parseInt(parts1[0]);
                            month1 = Integer.parseInt(parts1[1]);
                            day1 = Integer.parseInt(parts1[2]);
                        }
                    }
                    if (!date2.equals("")){
                        if (date2.contains("-")) {
                            String [] parts2 = date2.split("-");
                            year2 = Integer.parseInt(parts2[0]);
                            month2 = Integer.parseInt(parts2[1]);
                            day2 = Integer.parseInt(parts2[2]);
                        }
                    }
                    if (!date3.equals("")){
                        if (date3.contains("-")) {
                            String [] parts3 = date3.split("-");
                            year3 = Integer.parseInt(parts3[0]);
                            month3 = Integer.parseInt(parts3[1]);
                            day3 = Integer.parseInt(parts3[2]);
                        }
                    }
                    if(year1>year2 || year2>year3 || year1>year3){
                        Toast.makeText(Jezhong.this,"接种时间填写有误",Toast.LENGTH_SHORT).show();
                    }else if((year1==year2 && month1>month2)||(year1==year3 && month1>month3)||(year2==year3 && month2>month3)) {
                        Toast.makeText(Jezhong.this,"接种时间填写有误",Toast.LENGTH_SHORT).show();
                    }else if((year1==year2 && month1==month2 && day1>day2)||(year1==year3 && month1==month3 && day1>day3)||(year2==year3 && month2==month3 && day2>day3) ) {
                        Toast.makeText(Jezhong.this,"接种时间填写有误",Toast.LENGTH_SHORT).show();
                    }else {
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
                                                    String postUrl = "http://175.23.169.100:9030/case-vaccination/set";
                                                    JSONObject jsonObject = new JSONObject();
                                                    jsonObject.put("transactor_id",current_transId);
                                                    if (finalTimes != -1){
                                                        jsonObject.put("times",finalTimes);
                                                    }
                                                    if (Switch1.isChecked()){
                                                        jsonObject.put("firsttime", finalDate);
                                                        jsonObject.put("type1",jiezhong1Position);
                                                    }
                                                    if (Switch2.isChecked()){
                                                        jsonObject.put("secondtime", finalDate1);
                                                        jsonObject.put("type2",jiezhong2Position);
                                                    }
                                                    if (Switch3.isChecked()){
                                                        jsonObject.put("thirdtime", finalDate2);
                                                        jsonObject.put("type3",jiezhong3Position);
                                                    }



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
                    }
                }else {
                    final int finalTimes1 = times;
                    final String finalDate3 = date1;
                    final String finalDate4 = date2;
                    final String finalDate5 = date3;

                    int year1=10000;
                    int month1=10000;
                    int day1=10000;
                    int year2=10001;
                    int month2=10001;
                    int day2=10001;
                    int year3=10002;
                    int month3=10002;
                    int day3=10002;
                    if (!date1.equals("")){
                        if (date1.contains("-")) {
                            String [] parts1 = date1.split("-");
                            year1 = Integer.parseInt(parts1[0]);
                            month1 = Integer.parseInt(parts1[1]);
                            day1 = Integer.parseInt(parts1[2]);
                        }
                    }
                    if (!date2.equals("")){
                        if (date2.contains("-")) {
                            String [] parts2 = date2.split("-");
                            year2 = Integer.parseInt(parts2[0]);
                            month2 = Integer.parseInt(parts2[1]);
                            day2 = Integer.parseInt(parts2[2]);
                        }
                    }
                    if (!date3.equals("")){
                        if (date3.contains("-")) {
                            String [] parts3 = date3.split("-");
                            year3 = Integer.parseInt(parts3[0]);
                            month3 = Integer.parseInt(parts3[1]);
                            day3 = Integer.parseInt(parts3[2]);
                        }
                    }
                    if(year1>year2 || year2>year3 || year1>year3){
                        Toast.makeText(Jezhong.this,"接种时间填写有误",Toast.LENGTH_SHORT).show();
                    }else if((year1==year2 && month1>month2)||(year1==year3 && month1>month3)||(year2==year3 && month2>month3)) {
                        Toast.makeText(Jezhong.this,"接种时间填写有误",Toast.LENGTH_SHORT).show();
                    }else if((year1==year2 && month1==month2 && day1>day2)||(year1==year3 && month1==month3 && day1>day3)||(year2==year3 && month2==month3 && day2>day3) ) {
                        Toast.makeText(Jezhong.this,"接种时间填写有误",Toast.LENGTH_SHORT).show();
                    }else {new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String postUrl = "http://175.23.169.100:9030/case-vaccination/set";
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("transactor_id",current_transId);
                                if (finalTimes1 != -1){
                                    jsonObject.put("times",finalTimes1);
                                }
                                if (Switch1.isChecked()){
                                    jsonObject.put("firsttime", finalDate3);
                                    jsonObject.put("type1",jiezhong1Position);
                                }
                                if (Switch2.isChecked()){
                                    jsonObject.put("secondtime", finalDate4);
                                    jsonObject.put("type2",jiezhong2Position);
                                }
                                if (Switch3.isChecked()){
                                    jsonObject.put("thirdtime", finalDate5);
                                    jsonObject.put("type3",jiezhong3Position);
                                }

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
                    }

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
        jiezhong_spinner1.setSelection(3,true);
        jiezhong_spinner2.setAdapter(jiezhong2);
        jiezhong_spinner2.setSelection(3,true);
        jiezhong_spinner3.setAdapter(jiezhong3);
        jiezhong_spinner3.setSelection(3,true);

//        selectJiezhong1.setText(getJiezhongtime1);
//        jiezhong_spinner1.setSelection(getJiezhong1);
//        selectJiezhong2.setText(getJiezhongtime2);
//        jiezhong_spinner2.setSelection(getJiezhong2);
//        selectJiezhong3.setText(getJiezhongtime3);
//        jiezhong_spinner3.setSelection(getJiezhong3);

        jiezhong_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isFirstSpinner1 == true){
//                    view.setVisibility(View.INVISIBLE);
//                }
//                isFirstSpinner1 = false;
                jiezhong1Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jiezhong_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isFirstSpinner2 == true){
//                    view.setVisibility(View.INVISIBLE);
//                }
//                isFirstSpinner2 = false;
                jiezhong2Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jiezhong_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isFirstSpinner3 == true){
//                    view.setVisibility(View.INVISIBLE);
//                }
//                isFirstSpinner3 = false;
                jiezhong3Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectJiezhong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jezhong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong1.setText(year + "-" + (month+1) + "-" + dayOfMonth);

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
                        String date1 = sb1.toString();
//                if (date1.equals("")) {
//                    date1 = null;
//                }

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
                        String date2 = sb2.toString();
//                if (date2.equals("")) {
//                    date2 = null;
//                }

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
                        String date3 = sb3.toString();
//                if (date3.equals("")) {
//                    date3 = null;
//                }
                        final int finalTimes1 = times;
                        final String finalDate3 = date1;
                        final String finalDate4 = date2;
                        final String finalDate5 = date3;

                        int year1=10000;
                        int month1=10000;
                        int day1=10000;
                        int year2=10001;
                        int month2=10001;
                        int day2=10001;
                        int year3=10002;
                        int month3=10002;
                        int day3=10002;
                        if (!date1.equals("")){
                            if (date1.contains("-")) {
                                String [] parts1 = date1.split("-");
                                year1 = Integer.parseInt(parts1[0]);
                                month1 = Integer.parseInt(parts1[1]);
                                day1 = Integer.parseInt(parts1[2]);
                            }
                        }
                        if (!date2.equals("")){
                            if (date2.contains("-")) {
                                String [] parts2 = date2.split("-");
                                year2 = Integer.parseInt(parts2[0]);
                                month2 = Integer.parseInt(parts2[1]);
                                day2 = Integer.parseInt(parts2[2]);
                            }
                        }
                        if (!date3.equals("")){
                            if (date3.contains("-")) {
                                String [] parts3 = date3.split("-");
                                year3 = Integer.parseInt(parts3[0]);
                                month3 = Integer.parseInt(parts3[1]);
                                day3 = Integer.parseInt(parts3[2]);
                            }
                        }
                        if(year1>year2){
                            Toast.makeText(Jezhong.this,"第一针接种时间不能第二针接种时间之后",Toast.LENGTH_SHORT).show();
                            selectJiezhong1.setTextColor(Color.rgb(255,0,0));
                        }else if((year1==year2 && month1>month2)) {
                            Toast.makeText(Jezhong.this,"第一针接种时间不能第二针接种时间之后",Toast.LENGTH_SHORT).show();
                            selectJiezhong1.setTextColor(Color.rgb(255,0,0));
                        }else if((year1==year2 && month1==month2 && day1>day2) ){
                            Toast.makeText(Jezhong.this,"第一针接种时间不能第二针接种时间之后",Toast.LENGTH_SHORT).show();
                            selectJiezhong1.setTextColor(Color.rgb(255,0,0));
                        }else{
                            selectJiezhong1.setTextColor(-1979711488);
                        }
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        selectJiezhong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jezhong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong2.setText(year + "-" + (month+1) + "-" + dayOfMonth);


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
                        String date1 = sb1.toString();
//                if (date1.equals("")) {
//                    date1 = null;
//                }

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
                        String date2 = sb2.toString();
//                if (date2.equals("")) {
//                    date2 = null;
//                }

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
                        String date3 = sb3.toString();
//                if (date3.equals("")) {
//                    date3 = null;
//                }
                        final int finalTimes1 = times;
                        final String finalDate3 = date1;
                        final String finalDate4 = date2;
                        final String finalDate5 = date3;

                        int year1=10000;
                        int month1=10000;
                        int day1=10000;
                        int year2=10001;
                        int month2=10001;
                        int day2=10001;
                        int year3=10002;
                        int month3=10002;
                        int day3=10002;
                        if (!date1.equals("")){
                            if (date1.contains("-")) {
                                String [] parts1 = date1.split("-");
                                year1 = Integer.parseInt(parts1[0]);
                                month1 = Integer.parseInt(parts1[1]);
                                day1 = Integer.parseInt(parts1[2]);
                            }
                        }
                        if (!date2.equals("")){
                            if (date2.contains("-")) {
                                String [] parts2 = date2.split("-");
                                year2 = Integer.parseInt(parts2[0]);
                                month2 = Integer.parseInt(parts2[1]);
                                day2 = Integer.parseInt(parts2[2]);
                            }
                        }
                        if (!date3.equals("")){
                            if (date3.contains("-")) {
                                String [] parts3 = date3.split("-");
                                year3 = Integer.parseInt(parts3[0]);
                                month3 = Integer.parseInt(parts3[1]);
                                day3 = Integer.parseInt(parts3[2]);
                            }
                        }
                        if(year1>year2 || year2>year3){
                            Toast.makeText(Jezhong.this,"第二针接种时间不能在第一针接种时间之前或第三针接种时间之后",Toast.LENGTH_SHORT).show();
                            selectJiezhong2.setTextColor(Color.rgb(255,0,0));
                        }else if((year1==year2 && month1>month2)||(year2==year3 && month2>month3)) {
                            Toast.makeText(Jezhong.this,"第二针接种时间不能在第一针接种时间之前或第三针接种时间之后",Toast.LENGTH_SHORT).show();
                            selectJiezhong2.setTextColor(Color.rgb(255,0,0));
                        }else if((year1==year2 && month1==month2 && day1>day2)||((year2==year3 && month2==month3 && day2>day3) ) ){
                            Toast.makeText(Jezhong.this,"第二针接种时间不能在第一针接种时间之前或第三针接种时间之后",Toast.LENGTH_SHORT).show();
                            selectJiezhong2.setTextColor(Color.rgb(255,0,0));
                        }else{
                            selectJiezhong2.setTextColor(-1979711488);
                        }

                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        selectJiezhong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jezhong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong3.setText(year + "-" + (month+1) + "-" + dayOfMonth);

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
                        String date1 = sb1.toString();
//                if (date1.equals("")) {
//                    date1 = null;
//                }

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
                        String date2 = sb2.toString();
//                if (date2.equals("")) {
//                    date2 = null;
//                }

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
                        String date3 = sb3.toString();
//                if (date3.equals("")) {
//                    date3 = null;
//                }
                        final int finalTimes1 = times;
                        final String finalDate3 = date1;
                        final String finalDate4 = date2;
                        final String finalDate5 = date3;

                        int year1=10000;
                        int month1=10000;
                        int day1=10000;
                        int year2=10001;
                        int month2=10001;
                        int day2=10001;
                        int year3=10002;
                        int month3=10002;
                        int day3=10002;
                        if (!date1.equals("")){
                            if (date1.contains("-")) {
                                String [] parts1 = date1.split("-");
                                year1 = Integer.parseInt(parts1[0]);
                                month1 = Integer.parseInt(parts1[1]);
                                day1 = Integer.parseInt(parts1[2]);
                            }
                        }
                        if (!date2.equals("")){
                            if (date2.contains("-")) {
                                String [] parts2 = date2.split("-");
                                year2 = Integer.parseInt(parts2[0]);
                                month2 = Integer.parseInt(parts2[1]);
                                day2 = Integer.parseInt(parts2[2]);
                            }
                        }
                        if (!date3.equals("")){
                            if (date3.contains("-")) {
                                String [] parts3 = date3.split("-");
                                year3 = Integer.parseInt(parts3[0]);
                                month3 = Integer.parseInt(parts3[1]);
                                day3 = Integer.parseInt(parts3[2]);
                            }
                        }
                        if(year2>year3){
                            Toast.makeText(Jezhong.this,"第三针接种时间不能在第二针接种时间之前",Toast.LENGTH_SHORT).show();
                            selectJiezhong3.setTextColor(Color.rgb(255,0,0));
                        }else if(((year2==year3 && month2>month3))) {
                            Toast.makeText(Jezhong.this,"第三针接种时间不能在第二针接种时间之前",Toast.LENGTH_SHORT).show();
                            selectJiezhong3.setTextColor(Color.rgb(255,0,0));
                        }else if((((year2==year3 && month2==month3 && day2>day3) ) )){
                            Toast.makeText(Jezhong.this,"第三针接种时间不能在第二针接种时间之前",Toast.LENGTH_SHORT).show();
                            selectJiezhong3.setTextColor(Color.rgb(255,0,0));
                        }else{
                            selectJiezhong3.setTextColor(-1979711488);
                        }
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

//    public void dataCommit(){
//        if (!radioButton11.isChecked() && !radioButton21.isChecked() &&!radioButton31.isChecked()){
//            times = 0;
//        }else if (radioButton11.isChecked() && !radioButton21.isChecked() &&!radioButton31.isChecked()){
//            times = 1;
//        }else if (!radioButton11.isChecked() && radioButton21.isChecked() &&!radioButton31.isChecked()){
//            times = 1;
//        }else if (!radioButton11.isChecked() && !radioButton21.isChecked() &&radioButton31.isChecked()){
//            times = 1;
//        }else if (radioButton11.isChecked() && radioButton21.isChecked() &&!radioButton31.isChecked()){
//            times = 2;
//        }else if (radioButton11.isChecked() && !radioButton21.isChecked() &&radioButton31.isChecked()){
//            times = 2;
//        }else if (!radioButton11.isChecked() && radioButton21.isChecked() &&radioButton31.isChecked()){
//            times = 2;
//        }else {
//            times = 3;
//        }
//        SharedPreferences preferences = getSharedPreferences(current_transId+"baogao", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();//获取编辑器
////        editor.putString("jiezhong_time1",selectJiezhong1.getText().toString());
////        editor.putString("jiezhong_time2",selectJiezhong2.getText().toString());
////        editor.putString("jiezhong_time3",selectJiezhong3.getText().toString());
////        editor.putInt("jiezhong_danwei1",jiezhong1Position);
////        editor.putInt("jiezhong_danwei2",jiezhong2Position);
////        editor.putInt("jiezhong_danwei3",jiezhong3Position);
//       // Toast.makeText(Jezhong.this,""+jiezhong1Position+"=="+jiezhong2Position+"=="+jiezhong3Position,Toast.LENGTH_SHORT).show();
//        editor.putInt("yimiaoTimes",times);
//        editor.commit();
//
//        preferences = getSharedPreferences(current_transId+"jiezhong",Activity.MODE_PRIVATE);
//        editor = preferences.edit();
//
//        editor.putBoolean("radiobutton11",radioButton11.isChecked());
//        editor.putBoolean("radiobutton12",radioButton12.isChecked());
//        editor.putBoolean("radiobutton21",radioButton21.isChecked());
//        editor.putBoolean("radiobutton22",radioButton22.isChecked());
//        editor.putBoolean("radiobutton31",radioButton31.isChecked());
//        editor.putBoolean("radiobutton32",radioButton32.isChecked());
//        editor.commit();
//
//
//    }
    public void dataCommit(){
        if (!Switch1.isChecked() && !Switch2.isChecked() &&!Switch3.isChecked()){
            times = 0;
        }else if (Switch1.isChecked() && !Switch2.isChecked() &&!Switch3.isChecked()){
            times = 1;
        }else if (!Switch1.isChecked() && Switch2.isChecked() &&!Switch3.isChecked()){
            times = 1;
        }else if (!Switch1.isChecked() && !Switch2.isChecked() &&Switch3.isChecked()){
            times = 1;
        }else if (Switch1.isChecked() && Switch2.isChecked() &&!Switch3.isChecked()){
            times = 2;
        }else if (Switch1.isChecked() && !Switch2.isChecked() &&Switch3.isChecked()){
            times = 2;
        }else if (!Switch1.isChecked() && Switch2.isChecked() &&Switch3.isChecked()){
            times = 2;
        }else {
            times = 3;
        }
        SharedPreferences preferences = getSharedPreferences(current_transId+"baogao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();//获取编辑器
//        editor.putString("jiezhong_time1",selectJiezhong1.getText().toString());
//        editor.putString("jiezhong_time2",selectJiezhong2.getText().toString());
//        editor.putString("jiezhong_time3",selectJiezhong3.getText().toString());
//        editor.putInt("jiezhong_danwei1",jiezhong1Position);
//        editor.putInt("jiezhong_danwei2",jiezhong2Position);
//        editor.putInt("jiezhong_danwei3",jiezhong3Position);
        // Toast.makeText(Jezhong.this,""+jiezhong1Position+"=="+jiezhong2Position+"=="+jiezhong3Position,Toast.LENGTH_SHORT).show();
        editor.putInt("yimiaoTimes",times);
        editor.commit();

        preferences = getSharedPreferences(current_transId+"jiezhong",Activity.MODE_PRIVATE);
        editor = preferences.edit();

//        editor.putBoolean("radiobutton11",radioButton11.isChecked());
//        editor.putBoolean("radiobutton12",radioButton12.isChecked());
//        editor.putBoolean("radiobutton21",radioButton21.isChecked());
//        editor.putBoolean("radiobutton22",radioButton22.isChecked());
//        editor.putBoolean("radiobutton31",radioButton31.isChecked());
//        editor.putBoolean("radiobutton32",radioButton32.isChecked());
        editor.putBoolean("switch1",Switch1.isChecked());
        editor.putBoolean("switch2",Switch2.isChecked());
        editor.putBoolean("switch3",Switch3.isChecked());
        editor.commit();


    }

}
