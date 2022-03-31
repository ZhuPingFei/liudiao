package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import java.util.List;

public class Jiankang extends AppCompatActivity {

    private SharedPreferences preferences;
    private AlertDialog.Builder builder;
    SharedPreferences.Editor editor;
    private int current_transId;
    private boolean isMe;

    private ImageView r_back;
    private TextView okey;

    private RelativeLayout visible1;
    private RelativeLayout visible2;
    private RelativeLayout visible3;
    private RadioGroup visible4;
    private RelativeLayout visible5;

    private EditText qitabushi;
    private EditText jiuyi1;
    private EditText jiuyi2;
    private EditText yunzhou;
    private EditText jibingshi;

    private RadioGroup radioGroup1;
    private RadioButton radioButton11;
    private RadioButton radioButton12;
    private RadioGroup radioGroup2;
    private RadioButton radioButton21;
    private RadioButton radioButton22;
    private RadioGroup radioGroup3;
    private RadioButton radioButton31;
    private RadioButton radioButton32;
    private RadioGroup radioGroup4;
    private RadioButton radioButton41;
    private RadioButton radioButton42;
    private RadioButton radioButton51;
    private RadioButton radioButton52;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private CheckBox checkBox9;
    private CheckBox checkBox10;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();

            int code = bd.getInt("code");

            if (code == 0){
                int discomfort = bd.getInt("discomfort");
                String symptom = bd.getString("symptom");
                String other_symptom = bd.getString("other_symptom");

                int doctor = bd.getInt("doctor");
                String doctoraddress = bd.getString("doctoraddress");
                String advice = bd.getString("advice");

                int maternity = bd.getInt("maternity");
                int ges_week = bd.getInt("ges_week");

                Boolean smoke = bd.getBoolean("smoke");
                int smoke_fre = bd.getInt("smoke_fre");

                String dis_his = bd.getString("dis_his");

                qitabushi = (EditText) findViewById(R.id.qitabushi_edit);
                jiuyi1 = (EditText) findViewById(R.id.jiuyi_edit1);
                jiuyi2 = (EditText) findViewById(R.id.jiuyi_edit2);
                yunzhou = (EditText) findViewById(R.id.yunzhou_edit);
                jibingshi = (EditText) findViewById(R.id.jibingshi_edit);

                visible1 = (RelativeLayout) findViewById(R.id.visible1);
                visible2 = (RelativeLayout) findViewById(R.id.visible2);
                visible3 = (RelativeLayout) findViewById(R.id.visible3);
                visible5 = (RelativeLayout) findViewById(R.id.visible5);
                visible4 = (RadioGroup) findViewById(R.id.visible4);

                radioGroup1 = (RadioGroup) findViewById(R.id.jiankang_radiogroup1);
                radioGroup2 = (RadioGroup) findViewById(R.id.jiankang_radiogroup2);
                radioGroup3 = (RadioGroup) findViewById(R.id.jiankang_radiogroup3);
                radioGroup4 = (RadioGroup) findViewById(R.id.jiankang_radiogroup4);

                radioButton11 = (RadioButton) findViewById(R.id.jiankang_radioButton11);
                radioButton12 = (RadioButton) findViewById(R.id.jiankang_radioButton12);
                radioButton21 = (RadioButton) findViewById(R.id.jiankang_radioButton21);
                radioButton22 = (RadioButton) findViewById(R.id.jiankang_radioButton22);
                radioButton31 = (RadioButton) findViewById(R.id.jiankang_radioButton31);
                radioButton32 = (RadioButton) findViewById(R.id.jiankang_radioButton32);
                radioButton41 = (RadioButton) findViewById(R.id.jiankang_radioButton41);
                radioButton42 = (RadioButton) findViewById(R.id.jiankang_radioButton42);
                radioButton51 = (RadioButton) findViewById(R.id.jingchangchou);
                radioButton52 = (RadioButton) findViewById(R.id.ouerchou);

                checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
                checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
                checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
                checkBox4 = (CheckBox) findViewById(R.id.checkbox4);
                checkBox5 = (CheckBox) findViewById(R.id.checkbox5);
                checkBox6 = (CheckBox) findViewById(R.id.checkbox6);
                checkBox7 = (CheckBox) findViewById(R.id.checkbox7);
                checkBox8 = (CheckBox) findViewById(R.id.checkbox8);
                checkBox9 = (CheckBox) findViewById(R.id.checkbox9);
                checkBox10 = (CheckBox) findViewById(R.id.checkbox10);

                if (discomfort == 1){
                    radioButton11.setChecked(true);
                }else {
                    radioButton12.setChecked(true);
                }
                if (!symptom.equals("")){
                    if (symptom.length()==2){

                    }

                    else if (symptom.length()==3){
                        String sym = symptom.substring(1,symptom.length()-1);
                        if (Integer.parseInt(sym) == 0){
                            checkBox1.setChecked(true);
                        }else if(Integer.parseInt(sym) == 1){
                            checkBox2.setChecked(true);
                        }else if(Integer.parseInt(sym) == 2){
                            checkBox3.setChecked(true);
                        }else if(Integer.parseInt(sym) == 3){
                            checkBox4.setChecked(true);
                        }else if(Integer.parseInt(sym) == 4){
                            checkBox5.setChecked(true);
                        }else if(Integer.parseInt(sym) == 5){
                            checkBox6.setChecked(true);
                        }else if(Integer.parseInt(sym) == 6){
                            checkBox7.setChecked(true);
                        }else if(Integer.parseInt(sym) == 7){
                            checkBox8.setChecked(true);
                        }else if(Integer.parseInt(sym) == 8){
                            checkBox9.setChecked(true);
                        }else {
                            checkBox10.setChecked(true);
                        }
                    }else {
                        String sym = symptom.substring(1,symptom.length()-1);
                        String[] split = sym.split(",");
                        for (int i = 0;i<split.length;i++){
                            if (Integer.parseInt(split[i]) == 0){
                                checkBox1.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 1){
                                checkBox2.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 2){
                                checkBox3.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 3){
                                checkBox4.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 4){
                                checkBox5.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 5){
                                checkBox6.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 6){
                                checkBox7.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 7){
                                checkBox8.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 8){
                                checkBox9.setChecked(true);
                            }else {
                                checkBox10.setChecked(true);
                            }
                        }
                    }
                }
                qitabushi.setText(other_symptom);

                if (doctor == 1){
                    radioButton21.setChecked(true);
                }else {
                    radioButton22.setChecked(true);
                }
                jiuyi1.setText(doctoraddress);
                jiuyi2.setText(advice);

                if (maternity == 1){
                    radioButton31.setChecked(true);
                }else {
                    radioButton32.setChecked(true);
                }

                yunzhou.setText(""+ges_week);

                if (smoke == true){
                    radioButton41.setChecked(true);
                }else {
                    radioButton42.setChecked(true);
                }
                if (smoke_fre == 1){
                    radioButton51.setChecked(true);
                }else {
                    radioButton52.setChecked(true);
                }
                jibingshi.setText(dis_his);

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jiankang);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);


        String url = "http://175.23.169.100:9000/case-health-condition/get";
        RequestJiankangThread rdt = new RequestJiankangThread(url,current_transId,handler);
        rdt.start();

//        SharedPreferences preferences = getSharedPreferences("user_jiankang", Activity.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

//        boolean getRadio11 = preferences.getBoolean("jiankang_radio11",Boolean.parseBoolean(""));
//        boolean getRadio21 = preferences.getBoolean("jiankang_radio21",Boolean.parseBoolean(""));
//        boolean getRadio31 = preferences.getBoolean("jiankang_radio31",Boolean.parseBoolean(""));
//        boolean getRadio41 = preferences.getBoolean("jiankang_radio41",Boolean.parseBoolean(""));
//        boolean getRadio51 = preferences.getBoolean("jiankang_radio51",Boolean.parseBoolean(""));
//        boolean getRadio12 = preferences.getBoolean("jiankang_radio12", Boolean.parseBoolean(""));
//        boolean getRadio22 = preferences.getBoolean("jiankang_radio22",Boolean.parseBoolean(""));
//        boolean getRadio32 = preferences.getBoolean("jiankang_radio32",Boolean.parseBoolean(""));
//        boolean getRadio42 = preferences.getBoolean("jiankang_radio42",Boolean.parseBoolean(""));
//        boolean getRadio52 = preferences.getBoolean("jiankang_radio52",Boolean.parseBoolean(""));
//
//        boolean getCheckbox1 = preferences.getBoolean("jiankang_checkbox1",Boolean.parseBoolean(""));
//        boolean getCheckbox2 = preferences.getBoolean("jiankang_checkbox2",Boolean.parseBoolean(""));
//        boolean getCheckbox3 = preferences.getBoolean("jiankang_checkbox3",Boolean.parseBoolean(""));
//        boolean getCheckbox4 = preferences.getBoolean("jiankang_checkbox4",Boolean.parseBoolean(""));
//        boolean getCheckbox5 = preferences.getBoolean("jiankang_checkbox5",Boolean.parseBoolean(""));
//        boolean getCheckbox6 = preferences.getBoolean("jiankang_checkbox6",Boolean.parseBoolean(""));
//        boolean getCheckbox7 = preferences.getBoolean("jiankang_checkbox7",Boolean.parseBoolean(""));
//        boolean getCheckbox8 = preferences.getBoolean("jiankang_checkbox8",Boolean.parseBoolean(""));
//        boolean getCheckbox9 = preferences.getBoolean("jiankang_checkbox9",Boolean.parseBoolean(""));
//        boolean getCheckbox10 = preferences.getBoolean("jiankang_checkbox10",Boolean.parseBoolean(""));
//
//        String getQitabushi = preferences.getString("jiankang_qitabushi","");
//        String getJiuyi1 = preferences.getString("jiankang_jiuyi1","");
//        String getJiuyi2 = preferences.getString("jiankang_jiuyi2","");
//        String getYunzhou = preferences.getString("jiankang_yunzhou","");
//        String getJibingshi = preferences.getString("jiankang_jibingshi","");

        qitabushi = (EditText) findViewById(R.id.qitabushi_edit);
        jiuyi1 = (EditText) findViewById(R.id.jiuyi_edit1);
        jiuyi2 = (EditText) findViewById(R.id.jiuyi_edit2);
        yunzhou = (EditText) findViewById(R.id.yunzhou_edit);
        jibingshi = (EditText) findViewById(R.id.jibingshi_edit);

        visible1 = (RelativeLayout) findViewById(R.id.visible1);
        visible2 = (RelativeLayout) findViewById(R.id.visible2);
        visible3 = (RelativeLayout) findViewById(R.id.visible3);
        visible5 = (RelativeLayout) findViewById(R.id.visible5);
        visible4 = (RadioGroup) findViewById(R.id.visible4);

        radioGroup1 = (RadioGroup) findViewById(R.id.jiankang_radiogroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.jiankang_radiogroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.jiankang_radiogroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.jiankang_radiogroup4);

        radioButton11 = (RadioButton) findViewById(R.id.jiankang_radioButton11);
        radioButton12 = (RadioButton) findViewById(R.id.jiankang_radioButton12);
        radioButton21 = (RadioButton) findViewById(R.id.jiankang_radioButton21);
        radioButton22 = (RadioButton) findViewById(R.id.jiankang_radioButton22);
        radioButton31 = (RadioButton) findViewById(R.id.jiankang_radioButton31);
        radioButton32 = (RadioButton) findViewById(R.id.jiankang_radioButton32);
        radioButton41 = (RadioButton) findViewById(R.id.jiankang_radioButton41);
        radioButton42 = (RadioButton) findViewById(R.id.jiankang_radioButton42);
        radioButton51 = (RadioButton) findViewById(R.id.jingchangchou);
        radioButton52 = (RadioButton) findViewById(R.id.ouerchou);

        checkBox1 = (CheckBox) findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkbox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkbox8);
        checkBox9 = (CheckBox) findViewById(R.id.checkbox9);
        checkBox10 = (CheckBox) findViewById(R.id.checkbox10);

//        checkBox1.setChecked(getCheckbox1);
//        checkBox2.setChecked(getCheckbox2);
//        checkBox3.setChecked(getCheckbox3);
//        checkBox4.setChecked(getCheckbox4);
//        checkBox5.setChecked(getCheckbox5);
//        checkBox6.setChecked(getCheckbox6);
//        checkBox7.setChecked(getCheckbox7);
//        checkBox8.setChecked(getCheckbox8);
//        checkBox9.setChecked(getCheckbox9);
//        checkBox10.setChecked(getCheckbox10);
//
//        qitabushi.setText(getQitabushi);
//        jiuyi1.setText(getJiuyi1);
//        jiuyi2.setText(getJiuyi2);
//        yunzhou.setText(getYunzhou);
//        jibingshi.setText(getJibingshi);

//        if (getRadio11 == false){
//            radioButton12.setChecked(true);
//            visible1.setVisibility(View.GONE);
//        }else {
//            radioButton11.setChecked(true);
//            visible1.setVisibility(View.VISIBLE);
//        }
//        if (getRadio21 == false){
//            radioButton22.setChecked(true);
//            visible2.setVisibility(View.GONE);
//            visible5.setVisibility(View.GONE);
//        }else {
//            radioButton21.setChecked(true);
//            visible2.setVisibility(View.VISIBLE);
//            visible5.setVisibility(View.VISIBLE);
//        }
//        if (getRadio31 == false){
//            radioButton32.setChecked(true);
//            visible3.setVisibility(View.GONE);
//        }else {
//            radioButton31.setChecked(true);
//            visible3.setVisibility(View.VISIBLE);
//        }
//        if (getRadio41 == false){
//            radioButton42.setChecked(true);
//            visible4.setVisibility(View.GONE);
//        }else {
//            radioButton41.setChecked(true);
//            visible4.setVisibility(View.VISIBLE);
//        }
//        if (getRadio51 == false){
//            radioButton52.setChecked(true);
//        }else {
//            radioButton51.setChecked(true);
//        }
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    visible1.setVisibility(View.GONE);
                }else{
                    visible1.setVisibility(View.VISIBLE);
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    visible2.setVisibility(View.GONE);
                    visible5.setVisibility(View.GONE);
                }else{
                    visible5.setVisibility(View.VISIBLE);
                    visible2.setVisibility(View.VISIBLE);
                }
            }
        });
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    visible3.setVisibility(View.GONE);
                }else{
                    visible3.setVisibility(View.VISIBLE);
                }
            }
        });
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    visible4.setVisibility(View.GONE);
                }else{
                    visible4.setVisibility(View.VISIBLE);
                }
            }
        });
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
                final int discomfort;
                if (radioButton11.isChecked()){
                    discomfort = 1;
                }else {
                    discomfort = 0;
                }
                final List<Integer> list = new ArrayList<>();
                if (checkBox1.isChecked()){
                    list.add(0);
                }if (checkBox2.isChecked()){
                    list.add(1);
                }
                if (checkBox3.isChecked()){
                    list.add(2);
                }if (checkBox4.isChecked()){
                    list.add(3);
                }if (checkBox5.isChecked()){
                    list.add(4);
                }if (checkBox6.isChecked()){
                    list.add(5);
                }if (checkBox7.isChecked()){
                    list.add(6);
                }if (checkBox8.isChecked()){
                    list.add(7);
                }if (checkBox9.isChecked()){
                    list.add(8);
                }if (checkBox10.isChecked()){
                    list.add(9);
                }
                Log.d("List", "  "+list.toString()+"   ");
                final String s = list.toString().replace(" ","");
                final int doctor;
                if (radioButton21.isChecked()){
                    doctor = 1;
                }else {
                    doctor = 0;
                }

                final int mannity;
                if (radioButton31.isChecked()){
                    mannity = 1;
                }else {
                    mannity = 0;
                }

                final int smoke;
                if (radioButton41.isChecked()){
                    smoke = 1;
                }else {
                    smoke = 0;
                }
                final int smoke_fre;
                if (radioButton51.isChecked()){
                    smoke_fre = 1;
                }else {
                    smoke_fre = 0;
                }
                int ges_week = 0;
                if (yunzhou.getText().toString().length() == 0){
                    ges_week = 0;
                }else {
                    ges_week = Integer.parseInt(yunzhou.getText().toString());
                }
                //dataCommit();
                final int finalGes_week = ges_week;
                if (!isMe){
                    builder = new AlertDialog.Builder(Jiankang.this).setTitle("重要提醒")
                            .setMessage("当前为代办模式，是否确认提交？")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String postUrl = "http://175.23.169.100:9000/case-health-condition/set";
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("transactor_id",current_transId);
                                                jsonObject.put("discomfort",discomfort);
                                                jsonObject.put("symptom",s);
                                                jsonObject.put("other_symptom",qitabushi.getText().toString());
                                                jsonObject.put("doctor",doctor);
                                                jsonObject.put("doctoraddress",jiuyi1.getText().toString());
                                                jsonObject.put("advice",jiuyi2.getText().toString());
                                                jsonObject.put("maternity",mannity);
                                                jsonObject.put("ges_week", finalGes_week);
                                                jsonObject.put("smoke",smoke);
                                                jsonObject.put("smoke_fre",smoke_fre);
                                                jsonObject.put("dis_his",jibingshi.getText().toString());
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
                                                    editor.putBoolean(current_transId+"hasJiankang",true);
                                                    editor.commit();
                                                    Looper.prepare();
                                                    Toast.makeText(Jiankang.this,"提交成功",Toast.LENGTH_SHORT).show();
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
                                    dialogInterface.dismiss();
                                    onBackPressed();
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
                                String postUrl = "http://175.23.169.100:9000/case-health-condition/set";
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("transactor_id",current_transId);
                                jsonObject.put("discomfort",discomfort);
                                jsonObject.put("symptom",s);
                                jsonObject.put("other_symptom",qitabushi.getText().toString());
                                jsonObject.put("doctor",doctor);
                                jsonObject.put("doctoraddress",jiuyi1.getText().toString());
                                jsonObject.put("advice",jiuyi2.getText().toString());
                                jsonObject.put("maternity",mannity);
                                jsonObject.put("ges_week", finalGes_week);
                                jsonObject.put("smoke",smoke);
                                jsonObject.put("smoke_fre",smoke_fre);
                                jsonObject.put("dis_his",jibingshi.getText().toString());
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
                                    Toast.makeText(Jiankang.this,"提交成功",Toast.LENGTH_SHORT).show();
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

            }
        });


    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences("user_jiankang", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (radioButton11.isChecked() == true){
            editor.putBoolean("jiankang_radio11",true);
            editor.putBoolean("jiankang_radio12",false);
        }else {
            editor.putBoolean("jiankang_radio12",true);
            editor.putBoolean("jiankang_radio11",false);
        }
        if (radioButton21.isChecked() == true){
            editor.putBoolean("jiankang_radio21",true);
            editor.putBoolean("jiankang_radio22",false);
        }else {
            editor.putBoolean("jiankang_radio22",true);
            editor.putBoolean("jiankang_radio21",false);
        }
        if (radioButton31.isChecked() == true){
            editor.putBoolean("jiankang_radio31",true);
            editor.putBoolean("jiankang_radio32",false);
        }else {
            editor.putBoolean("jiankang_radio32",true);
            editor.putBoolean("jiankang_radio31",false);
        }
        if (radioButton41.isChecked() == true){
            editor.putBoolean("jiankang_radio41",true);
            editor.putBoolean("jiankang_radio42",false);
        }else {
            editor.putBoolean("jiankang_radio42",true);
            editor.putBoolean("jiankang_radio41",false);
        }
        if (radioButton51.isChecked() == true){
            editor.putBoolean("jiankang_radio51",true);
            editor.putBoolean("jiankang_radio52",false);
        }else {
            editor.putBoolean("jiankang_radio52",true);
            editor.putBoolean("jiankang_radio51",false);
        }

        editor.putBoolean("jiankang_checkbox1",checkBox1.isChecked());
        editor.putBoolean("jiankang_checkbox2",checkBox2.isChecked());
        editor.putBoolean("jiankang_checkbox3",checkBox3.isChecked());
        editor.putBoolean("jiankang_checkbox4",checkBox4.isChecked());
        editor.putBoolean("jiankang_checkbox5",checkBox5.isChecked());
        editor.putBoolean("jiankang_checkbox6",checkBox6.isChecked());
        editor.putBoolean("jiankang_checkbox7",checkBox7.isChecked());
        editor.putBoolean("jiankang_checkbox8",checkBox8.isChecked());
        editor.putBoolean("jiankang_checkbox9",checkBox9.isChecked());
        editor.putBoolean("jiankang_checkbox10",checkBox10.isChecked());

        editor.putString("jiankang_qitabushi",qitabushi.getText().toString());
        editor.putString("jiankang_jiuyi1",jiuyi1.getText().toString());
        editor.putString("jiankang_jiuyi2",jiuyi2.getText().toString());
        editor.putString("jiankang_yunzhou",yunzhou.getText().toString());
        editor.putString("jiankang_jibingshi",jibingshi.getText().toString());

        editor.commit();
    }
}
