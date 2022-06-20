package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextDirectionHeuristic;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
    private LinearLayout zhengzhuangDate;
    private TextView select_zhengzhuangDate;
    private String first_baogao;

    private EditText qitabushi;
    private EditText jiuyi1;
    private EditText jiuyi2;
    private EditText yunzhou;

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

    private CheckBox checkBox50;
    private CheckBox checkBox51;
    private CheckBox checkBox52;
    private CheckBox checkBox53;
    private CheckBox checkBox54;
    private CheckBox checkBox55;
    private CheckBox checkBox56;
    private CheckBox checkBox57;
    private CheckBox checkBox58;
    private CheckBox checkBox59;
    private CheckBox checkBox510;
    private CheckBox checkBox511;
    private CheckBox checkBox512;
    private CheckBox checkBox513;

    private DatePicker dp = null;
    private Calendar calendar = null;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();

            int code = bd.getInt("code");

            if (code == 0){
                String discomfort = bd.getString("discomfort");
                String symptom = bd.getString("symptom");
                String other_symptom = bd.getString("other_symptom");

                String doctor = bd.getString("doctor");
                String doctoraddress = bd.getString("doctoraddress");
                String advice = bd.getString("advice");

                String maternity = bd.getString("maternity");
                String ges_week = bd.getString("ges_week");

                String smoke = bd.getString("smoke");
                String smoke_fre = bd.getString("smoke_fre");

                String dis_his = bd.getString("dis_his");
                String zhengzhuang_date = bd.getString("symptom_start_date");

                qitabushi = (EditText) findViewById(R.id.qitabushi_edit);
                jiuyi1 = (EditText) findViewById(R.id.jiuyi_edit1);
                jiuyi2 = (EditText) findViewById(R.id.jiuyi_edit2);
                yunzhou = (EditText) findViewById(R.id.yunzhou_edit);

                visible1 = (RelativeLayout) findViewById(R.id.visible1);
                visible2 = (RelativeLayout) findViewById(R.id.visible2);
                visible3 = (RelativeLayout) findViewById(R.id.visible3);
                visible5 = (RelativeLayout) findViewById(R.id.visible5);
                visible4 = (RadioGroup) findViewById(R.id.visible4);

                select_zhengzhuangDate = (TextView) findViewById(R.id.select_zhengzhuangdate);

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

                checkBox50 = (CheckBox) findViewById(R.id.checkbox50);
                checkBox51 = (CheckBox) findViewById(R.id.checkbox51);
                checkBox52 = (CheckBox) findViewById(R.id.checkbox52);
                checkBox53 = (CheckBox) findViewById(R.id.checkbox53);
                checkBox54 = (CheckBox) findViewById(R.id.checkbox54);
                checkBox55 = (CheckBox) findViewById(R.id.checkbox55);
                checkBox56 = (CheckBox) findViewById(R.id.checkbox56);
                checkBox57 = (CheckBox) findViewById(R.id.checkbox57);
                checkBox58 = (CheckBox) findViewById(R.id.checkbox58);
                checkBox59 = (CheckBox) findViewById(R.id.checkbox59);
                checkBox510 = (CheckBox) findViewById(R.id.checkbox510);
                checkBox511 = (CheckBox) findViewById(R.id.checkbox511);
                checkBox512 = (CheckBox) findViewById(R.id.checkbox512);
                checkBox513 = (CheckBox) findViewById(R.id.checkbox513);

                if (!discomfort.equals("null") && !discomfort.equals("")){
                    if (Integer.parseInt(discomfort) == 1){
                        radioButton11.setChecked(true);
                    }else if (Integer.parseInt(discomfort) == 0){
                        radioButton12.setChecked(true);
                    }
                }

                if (!symptom.equals("") && !symptom.equals("null")){
                    if (symptom.length()==1){
                        String sym = symptom;
                        if (Integer.parseInt(sym) == 1){
                            checkBox1.setChecked(true);
                        }else if(Integer.parseInt(sym) == 2){
                            checkBox2.setChecked(true);
                        }else if(Integer.parseInt(sym) == 3){
                            checkBox3.setChecked(true);
                        }else if(Integer.parseInt(sym) == 4){
                            checkBox4.setChecked(true);
                        }else if(Integer.parseInt(sym) == 5){
                            checkBox5.setChecked(true);
                        }else if(Integer.parseInt(sym) == 6){
                            checkBox6.setChecked(true);
                        }else if(Integer.parseInt(sym) == 7){
                            checkBox7.setChecked(true);
                        }else if(Integer.parseInt(sym) == 8){
                            checkBox8.setChecked(true);
                        }else if(Integer.parseInt(sym) == 9){
                            checkBox9.setChecked(true);
                        }else {
                            checkBox10.setChecked(true);
                        }
                    }else {
                        String sym = symptom;
                        String[] split = sym.split(",");
                        for (int i = 0;i<split.length;i++){
                            if (Integer.parseInt(split[i]) == 1){
                                checkBox1.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 2){
                                checkBox2.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 3){
                                checkBox3.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 4){
                                checkBox4.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 5){
                                checkBox5.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 6){
                                checkBox6.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 7){
                                checkBox7.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 8){
                                checkBox8.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 9){
                                checkBox9.setChecked(true);
                            }else {
                                checkBox10.setChecked(true);
                            }
                        }
                    }
                }
                if (!other_symptom.equals("")&&!other_symptom.equals("null")){
                    qitabushi.setText(other_symptom);
                }else {
                    qitabushi.setText("");
                }

                if (!zhengzhuang_date.equals("") && !zhengzhuang_date.equals("null")){
                    select_zhengzhuangDate.setText(zhengzhuang_date);
                }else {
                    select_zhengzhuangDate.setText("");
                }

                if (!doctor.equals("null") && !doctor.equals("")){
                    if (Integer.parseInt(doctor)== 1){
                        radioButton21.setChecked(true);
                    }else if (Integer.parseInt(doctor)== 0){
                        radioButton22.setChecked(true);
                    }
                }
                if (!doctoraddress.equals("")&&!doctoraddress.equals("null")){
                    jiuyi1.setText(doctoraddress);
                }else {
                    jiuyi1.setText("");
                }
                if (!advice.equals("") && !advice.equals("null")){
                    jiuyi2.setText(advice);
                }else {
                    jiuyi2.setText("");
                }

                if (!maternity.equals("null") && !maternity.equals("")){
                    if (Integer.parseInt(maternity) == 1){
                        radioButton31.setChecked(true);
                    }else if (Integer.parseInt(maternity) == 0){
                        radioButton32.setChecked(true);
                    }
                }
                if (!ges_week.equals("")&&!ges_week.equals("null")){
                    if (!ges_week.equals("0")){
                        yunzhou.setText(ges_week);
                    }
                }else {
                    yunzhou.setText("");
                }

                if (!smoke.equals("")&&!smoke.equals("null")){
                    if (Integer.parseInt(smoke) == 1){
                        radioButton41.setChecked(true);
                    }else if (Integer.parseInt(smoke) == 0){
                        radioButton42.setChecked(true);
                    }
                }

                if (!smoke_fre.equals("")&&!smoke_fre.equals("null")){
                    if (Integer.parseInt(smoke_fre) == 1){
                        radioButton51.setChecked(true);
                    }else if (Integer.parseInt(smoke_fre) == 0){
                        radioButton52.setChecked(true);
                    }
                }

                if (!dis_his.equals("") && !dis_his.equals("null")){
                    if (dis_his.length()==1){
                        String sym = dis_his;
                        if (Integer.parseInt(sym) == 0){
                            checkBox50.setChecked(true);
                        }else if(Integer.parseInt(sym) == 1){
                            checkBox51.setChecked(true);
                        }else if(Integer.parseInt(sym) == 2){
                            checkBox52.setChecked(true);
                        }else if(Integer.parseInt(sym) == 3){
                            checkBox53.setChecked(true);
                        }else if(Integer.parseInt(sym) == 4){
                            checkBox54.setChecked(true);
                        }else if(Integer.parseInt(sym) == 5){
                            checkBox55.setChecked(true);
                        }else if(Integer.parseInt(sym) == 6){
                            checkBox56.setChecked(true);
                        }else if(Integer.parseInt(sym) == 7){
                            checkBox57.setChecked(true);
                        }else if(Integer.parseInt(sym) == 8){
                            checkBox58.setChecked(true);
                        }else if (Integer.parseInt(sym) == 9){
                            checkBox59.setChecked(true);
                        }else if(Integer.parseInt(sym) == 10){
                            checkBox510.setChecked(true);
                        }else if (Integer.parseInt(sym) == 11){
                            checkBox511.setChecked(true);
                        }else if (Integer.parseInt(sym) == 12){
                            checkBox512.setChecked(true);
                        } else  {
                            checkBox513.setChecked(true);
                        }
                    }else {
                        String sym = dis_his;
                        String[] split = sym.split(",");
                        for (int i = 0;i<split.length;i++){
                            if (Integer.parseInt(split[i]) == 0){
                                checkBox50.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 1){
                                checkBox51.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 2){
                                checkBox52.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 3){
                                checkBox53.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 4){
                                checkBox54.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 5){
                                checkBox55.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 6){
                                checkBox56.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 7){
                                checkBox57.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 8){
                                checkBox58.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 9){
                                checkBox59.setChecked(true);
                            }else if(Integer.parseInt(split[i]) == 10){
                                checkBox510.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 11){
                                checkBox511.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 12){
                                checkBox512.setChecked(true);
                            } else {
                                checkBox513.setChecked(true);
                            }
                        }
                    }
                }

            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jiankang);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);


        String url = "http://175.23.169.100:9030/case-health-condition/get";
        RequestJiankangThread rdt = new RequestJiankangThread(url,current_transId,handler);
        rdt.start();

        qitabushi = (EditText) findViewById(R.id.qitabushi_edit);
        jiuyi1 = (EditText) findViewById(R.id.jiuyi_edit1);
        jiuyi2 = (EditText) findViewById(R.id.jiuyi_edit2);
        yunzhou = (EditText) findViewById(R.id.yunzhou_edit);

        visible1 = (RelativeLayout) findViewById(R.id.visible1);
        visible2 = (RelativeLayout) findViewById(R.id.visible2);
        visible3 = (RelativeLayout) findViewById(R.id.visible3);
        visible5 = (RelativeLayout) findViewById(R.id.visible5);
        zhengzhuangDate = (LinearLayout) findViewById(R.id.jiankang_zhengzhuangdate);
        select_zhengzhuangDate = (TextView) findViewById(R.id.select_zhengzhuangdate);
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

        checkBox50 = (CheckBox) findViewById(R.id.checkbox50);
        checkBox51 = (CheckBox) findViewById(R.id.checkbox51);
        checkBox52 = (CheckBox) findViewById(R.id.checkbox52);
        checkBox53 = (CheckBox) findViewById(R.id.checkbox53);
        checkBox54 = (CheckBox) findViewById(R.id.checkbox54);
        checkBox55 = (CheckBox) findViewById(R.id.checkbox55);
        checkBox56 = (CheckBox) findViewById(R.id.checkbox56);
        checkBox57 = (CheckBox) findViewById(R.id.checkbox57);
        checkBox58 = (CheckBox) findViewById(R.id.checkbox58);
        checkBox59 = (CheckBox) findViewById(R.id.checkbox59);
        checkBox510 = (CheckBox) findViewById(R.id.checkbox510);
        checkBox511 = (CheckBox) findViewById(R.id.checkbox511);
        checkBox512 = (CheckBox) findViewById(R.id.checkbox512);
        checkBox513 = (CheckBox) findViewById(R.id.checkbox513);

        //初始化日期选择器
        dp = (DatePicker) findViewById(R.id.date_picker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        select_zhengzhuangDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jiankang.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        select_zhengzhuangDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("否")){
                    visible1.setVisibility(View.GONE);
                    zhengzhuangDate.setVisibility(View.GONE);
                }else{
                    visible1.setVisibility(View.VISIBLE);
                    zhengzhuangDate.setVisibility(View.VISIBLE);
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
                int discomfort = -1;
                if (radioButton11.isChecked()){
                    discomfort = 1;
                }else if (radioButton12.isChecked()){
                    discomfort = 0;
                }

                final List<Integer> list = new ArrayList<>();
                if (checkBox1.isChecked()){
                    list.add(1);
                }if (checkBox2.isChecked()){
                    list.add(2);
                }
                if (checkBox3.isChecked()){
                    list.add(3);
                }if (checkBox4.isChecked()){
                    list.add(4);
                }if (checkBox5.isChecked()){
                    list.add(5);
                }if (checkBox6.isChecked()){
                    list.add(6);
                }if (checkBox7.isChecked()){
                    list.add(7);
                }if (checkBox8.isChecked()){
                    list.add(8);
                }if (checkBox9.isChecked()){
                    list.add(9);
                }if (checkBox10.isChecked()){
                    list.add(10);
                }
                String s = list.toString().replace(" ","");
                if (s.length() == 2){
                    s = null;
                }else {
                    s = s.substring(1,s.length()-1);
                }

                String other_symptom = qitabushi.getText().toString();
                if (other_symptom.equals("")){
                    other_symptom = null;
                }

                final StringBuffer stringBuffer = new StringBuffer(select_zhengzhuangDate.getText().toString());
                if (select_zhengzhuangDate.getText().toString().length()==8){
                    stringBuffer.insert(5,"0");
                    stringBuffer.insert(8,"0");
                }else if (select_zhengzhuangDate.getText().toString().length()==9){
                    String[] split = select_zhengzhuangDate.getText().toString().split("-");
                    if (split[1].length() == 2){
                        stringBuffer.insert(8,"0");
                    }else {
                        stringBuffer.insert(5,"0");
                    }
                }
                String s1 = stringBuffer.toString();
                if (s1.equals("")){
                    s1 = null;
                }

                int doctor = -1;
                if (radioButton21.isChecked()){
                    doctor = 1;
                }else if (radioButton22.isChecked()){
                    doctor = 0;
                }

                String doctorAddress = jiuyi1.getText().toString();
                if (doctorAddress.equals("")){
                    doctorAddress = null;
                }

                String doctorAdvice = jiuyi2.getText().toString();
                if (doctorAdvice.equals("")){
                    doctorAdvice = null;
                }



                int mannity = -1;
                if (radioButton31.isChecked()){
                    mannity = 1;
                }else if (radioButton32.isChecked()){
                    mannity = 0;
                }

                int ges_week = -1;
                if (!yunzhou.getText().toString().equals("")){
                    ges_week = Integer.parseInt(yunzhou.getText().toString());
                }

                int smoke = -1;
                if (radioButton41.isChecked()){
                    smoke = 1;
                }else if (radioButton42.isChecked()){
                    smoke = 0;
                }

                int smoke_fre = -1;
                if (radioButton51.isChecked()){
                    smoke_fre = 1;
                }else if (radioButton52.isChecked()){
                    smoke_fre = 0;
                }

                final List<Integer> list2 = new ArrayList<>();
                if (checkBox50.isChecked()){
                    list2.add(0);
                }if (checkBox51.isChecked()){
                    list2.add(1);
                }
                if (checkBox52.isChecked()){
                    list2.add(2);
                }if (checkBox53.isChecked()){
                    list2.add(3);
                }if (checkBox54.isChecked()){
                    list2.add(4);
                }if (checkBox55.isChecked()){
                    list2.add(5);
                }if (checkBox56.isChecked()){
                    list2.add(6);
                }if (checkBox57.isChecked()){
                    list2.add(7);
                }if (checkBox58.isChecked()){
                    list2.add(8);
                }if (checkBox59.isChecked()){
                    list2.add(9);
                }if (checkBox510.isChecked()){
                    list2.add(10);
                }if (checkBox511.isChecked()){
                    list2.add(11);
                }if (checkBox512.isChecked()){
                    list2.add(12);
                }if (checkBox513.isChecked()){
                    list2.add(13);
                }
                String s2 = list2.toString().replace(" ","");
                if (s2.length() == 2){
                    s2 = null;
                }else {
                    s2 = s2.substring(1,s2.length()-1);
                }


//
                dataCommit();
                    if (!isMe){
                        final String finalS = s;
                        final String finalS2 = s1;
                        final int finalDiscomfort3 = discomfort;
                        final String finalOther_symptom = other_symptom;
                        final int finalDoctor3 = doctor;
                        final String finalDoctorAdvice3 = doctorAdvice;
                        final String finalDoctorAddress3 = doctorAddress;
                        final int finalMannity3 = mannity;
                        final String finalS6 = s2;
                        final int finalSmoke_fre3 = smoke_fre;
                        final int finalSmoke3 = smoke;
                        final int finalGes_week3 = ges_week;
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
                                                    String postUrl = "http://175.23.169.100:9030/case-health-condition/set";
                                                    JSONObject jsonObject = new JSONObject();
                                                    jsonObject.put("transactor_id",current_transId);
                                                    if (finalDiscomfort3 == 1) {
                                                        jsonObject.put("discomfort", finalDiscomfort3);
                                                        jsonObject.put("symptom", finalS);
                                                        jsonObject.put("symptom_start_date", finalS2);
                                                        jsonObject.put("other_symptom", finalOther_symptom);
                                                    }else if (finalDiscomfort3 == 0){
                                                        jsonObject.put("discomfort", finalDiscomfort3);
                                                    }
                                                    if (finalDoctor3 == 1){
                                                        jsonObject.put("doctor", finalDoctor3);
                                                        jsonObject.put("doctoraddress", finalDoctorAddress3);
                                                        jsonObject.put("advice", finalDoctorAdvice3);
                                                    }else if (finalDoctor3 == 0){
                                                        jsonObject.put("doctor", finalDoctor3);
                                                    }
                                                    if (finalMannity3 == 1){
                                                        jsonObject.put("maternity", finalMannity3);
                                                        if (finalGes_week3 != -1){
                                                            jsonObject.put("ges_week", finalGes_week3);
                                                        }
                                                    }else if (finalMannity3 == 0){
                                                        jsonObject.put("maternity", finalMannity3);
                                                    }


                                                    if (finalSmoke3 ==1){
                                                        jsonObject.put("smoke", finalSmoke3);
                                                        if (finalSmoke_fre3 == 1){
                                                            jsonObject.put("smoke_fre", finalSmoke_fre3);
                                                        }
                                                    }else if (finalSmoke3 == 0){
                                                        jsonObject.put("smoke", finalSmoke3);
                                                    }

                                                    jsonObject.put("dis_his", finalS6);
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
                        final String finalS1 = s;
                        final int finalDiscomfort2 = discomfort;
                        final String finalOther_symptom1 = other_symptom;
                        final int finalDoctor2 = doctor;
                        final String finalDoctorAdvice2 = doctorAdvice;
                        final String finalDoctorAddress2 = doctorAddress;
                        final int finalMannity2 = mannity;
                        final String finalS5 = s2;
                        final int finalSmoke_fre2 = smoke_fre;
                        final int finalSmoke2 = smoke;
                        final int finalGes_week2 = ges_week;
                        final String finalS3 = s1;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String postUrl = "http://175.23.169.100:9030/case-health-condition/set";
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("transactor_id",current_transId);
                                    if (finalDiscomfort2 == 1) {
                                        jsonObject.put("discomfort", finalDiscomfort2);
                                        jsonObject.put("symptom", finalS1);
                                        jsonObject.put("symptom_start_date", finalS3);
                                        jsonObject.put("other_symptom", finalOther_symptom1);
                                    }else if (finalDiscomfort2 == 0){
                                        jsonObject.put("discomfort", finalDiscomfort2);
                                    }

                                    if (finalDoctor2 == 1){
                                        jsonObject.put("doctor", finalDoctor2);
                                        jsonObject.put("doctoraddress", finalDoctorAddress2);
                                        jsonObject.put("advice", finalDoctorAdvice2);
                                    }else if (finalDoctor2 == 0){
                                        jsonObject.put("doctor", finalDoctor2);
                                    }

                                    if (finalMannity2 == 1){
                                        jsonObject.put("maternity", finalMannity2);
                                        if (finalGes_week2 != -1){
                                            jsonObject.put("ges_week", finalGes_week2);
                                        }
                                    }else if (finalMannity2 == 0){
                                        jsonObject.put("maternity", finalMannity2);
                                    }

                                    if (finalSmoke2 ==1){
                                        jsonObject.put("smoke", finalSmoke2);
                                        if (finalSmoke_fre2 == 1){
                                            jsonObject.put("smoke_fre", finalSmoke_fre2);
                                        }
                                    }else {
                                        jsonObject.put("smoke", finalSmoke2);
                                    }

                                    jsonObject.put("dis_his", finalS5);
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
                        onBackPressed();
                    }

            }
        });


    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences(current_transId+"baogao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!select_zhengzhuangDate.getText().toString().equals("选择日期") && select_zhengzhuangDate.getText().toString().length()!=0){
            String[] split = select_zhengzhuangDate.getText().toString().split("-");
            first_baogao = Integer.parseInt(split[1])+" 月 "+Integer.parseInt(split[2])+" 日 ";
        }else {
            first_baogao = "";
        }

        editor.putString("zhengzhuang_date",first_baogao);
        editor.commit();



    }
}
