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
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.example.Liudiao.Main;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.adpter.RequestBingliThread;

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

public class Bingli extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private boolean isMe;

    private ImageView r_back;
    private TextView okay;

    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;

    private EditText otherTujing;

    private CheckBox checkBox21;
    private CheckBox checkBox22;
    private CheckBox checkBox23;
    private CheckBox checkBox24;
    private CheckBox checkBox25;
    private CheckBox checkBox26;
    private CheckBox checkBox27;
    private CheckBox checkBox28;
    private CheckBox checkBox29;
    private CheckBox checkBox210;
    private CheckBox checkBox211;
    private CheckBox checkBox212;
    private CheckBox checkBox213;
    private CheckBox checkBox214;
    private CheckBox checkBox215;
    private CheckBox checkBox216;
    private CheckBox checkBox217;
    private CheckBox checkBox218;
    private CheckBox checkBox219;
    private CheckBox checkBox220;
    private CheckBox checkBox221;
    private CheckBox checkBox222;
    private CheckBox checkBox223;

    private EditText otherZhengzhuang;
    private EditText otherBingfazheng;

    private RadioGroup radioGroup1;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RelativeLayout bingfazheng;

    private RadioGroup radioGroup2;
    private RadioButton ct_radioButton1;
    private RadioButton ct_radioButton2;
    private RadioButton ct_radioButton3;
    private RelativeLayout ct_date;
    private TextView ct_date_select;
    private TextView chuyuanDate;

    private CheckBox checkBox31;
    private CheckBox checkBox32;
    private CheckBox checkBox33;
    private CheckBox checkBox34;
    private CheckBox checkBox35;
    private CheckBox checkBox36;
    private CheckBox checkBox37;
    private CheckBox checkBox38;
    private CheckBox checkBox39;

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
            Bundle b = msg.getData();

            int code = b.getInt("code");

            if (code == 0) {
                int way = b.getInt("way");
                String other_way = b.getString("other_way");
                String symptom = b.getString("symptom");
                String other_symptom = b.getString("other_symptom");
                int has_con = b.getInt("has_con");
                String con =  b.getString("con");
                String other_con = b.getString("other_con");
                int has_ct =  b.getInt("has_ct");
                String ct_date1 = b.getString("ct_date");
                String out_date = b.getString("out_date");

                checkBox11 = (CheckBox)findViewById(R.id.checkbox11);
                checkBox12 = (CheckBox)findViewById(R.id.checkbox12);
                checkBox13 = (CheckBox)findViewById(R.id.checkbox13);
                checkBox14 = (CheckBox)findViewById(R.id.checkbox14);
                checkBox15 = (CheckBox)findViewById(R.id.checkbox15);
                checkBox21 = (CheckBox)findViewById(R.id.checkbox21);
                checkBox22 = (CheckBox)findViewById(R.id.checkbox22);
                checkBox23 = (CheckBox)findViewById(R.id.checkbox23);
                checkBox24 = (CheckBox)findViewById(R.id.checkbox24);
                checkBox25 = (CheckBox)findViewById(R.id.checkbox25);
                checkBox26 = (CheckBox)findViewById(R.id.checkbox26);
                checkBox27 = (CheckBox)findViewById(R.id.checkbox27);
                checkBox28 = (CheckBox)findViewById(R.id.checkbox28);
                checkBox29 = (CheckBox)findViewById(R.id.checkbox29);
                checkBox210 = (CheckBox)findViewById(R.id.checkbox210);
                checkBox211 = (CheckBox)findViewById(R.id.checkbox211);
                checkBox212 = (CheckBox)findViewById(R.id.checkbox212);
                checkBox213 = (CheckBox)findViewById(R.id.checkbox213);
                checkBox214 = (CheckBox)findViewById(R.id.checkbox214);
                checkBox215 = (CheckBox)findViewById(R.id.checkbox215);
                checkBox216 = (CheckBox)findViewById(R.id.checkbox216);
                checkBox217 = (CheckBox)findViewById(R.id.checkbox217);
                checkBox218 = (CheckBox)findViewById(R.id.checkbox218);
                checkBox219 = (CheckBox)findViewById(R.id.checkbox219);
                checkBox220 = (CheckBox)findViewById(R.id.checkbox220);
                checkBox221 = (CheckBox)findViewById(R.id.checkbox221);
                checkBox222 = (CheckBox)findViewById(R.id.checkbox222);
                checkBox223 = (CheckBox)findViewById(R.id.checkbox223);
                checkBox31 = (CheckBox)findViewById(R.id.checkbox31);
                checkBox32 = (CheckBox)findViewById(R.id.checkbox32);
                checkBox33 = (CheckBox)findViewById(R.id.checkbox33);
                checkBox34 = (CheckBox)findViewById(R.id.checkbox34);
                checkBox35 = (CheckBox)findViewById(R.id.checkbox35);
                checkBox36 = (CheckBox)findViewById(R.id.checkbox36);
                checkBox37 = (CheckBox)findViewById(R.id.checkbox37);
                checkBox38 = (CheckBox)findViewById(R.id.checkbox38);
                checkBox39 = (CheckBox)findViewById(R.id.checkbox39);

                otherTujing = (EditText)findViewById(R.id.bingli_other_edit);
                otherZhengzhuang = (EditText)findViewById(R.id.qitabushi_edit);
                otherBingfazheng = (EditText)findViewById(R.id.bingli_bingfazheng_other_edit);
                radioGroup1 = (RadioGroup)findViewById(R.id.bingli_radiogroup1);
                radioButton1 = (RadioButton)findViewById(R.id.bingli_radioButton11);
                radioButton2 = (RadioButton)findViewById(R.id.bingli_radioButton12);
                bingfazheng = (RelativeLayout)findViewById(R.id.bingli_bingfazheng);
                radioGroup2 = (RadioGroup)findViewById(R.id.bingli_ct_radiogroup);
                ct_radioButton1 = (RadioButton)findViewById(R.id.bingli_ct_radioButton1);
                ct_radioButton2 = (RadioButton)findViewById(R.id.bingli_ct_radioButton2);
                ct_radioButton3 = (RadioButton)findViewById(R.id.bingli_ct_radioButton3);
                ct_date = (RelativeLayout) findViewById(R.id.ct_date);
                ct_date_select = (TextView)findViewById(R.id.select_ct_date);
                chuyuanDate = (TextView)findViewById(R.id.select_chuyuandate);

                if (way == 1){
                    checkBox11.setChecked(true);
                }else if (way == 2){
                    checkBox12.setChecked(true);
                }else if (way == 3){
                    checkBox13.setChecked(true);
                }else if (way == 4){
                    checkBox14.setChecked(true);
                }else {
                    checkBox15.setChecked(true);
                }
                otherTujing.setText(other_way);
                if (symptom.length()!=0) {
                    String sym = symptom.substring(1, symptom.length() - 1);
                    if (sym.length() == 1) {
                        if (Integer.parseInt(sym) == 1) {
                            checkBox21.setChecked(true);
                        } else if (Integer.parseInt(sym) == 2) {
                            checkBox22.setChecked(true);
                        } else if (Integer.parseInt(sym) == 3) {
                            checkBox23.setChecked(true);
                        } else if (Integer.parseInt(sym) == 4) {
                            checkBox24.setChecked(true);
                        } else if (Integer.parseInt(sym) == 5) {
                            checkBox25.setChecked(true);
                        } else if (Integer.parseInt(sym) == 6) {
                            checkBox26.setChecked(true);
                        } else if (Integer.parseInt(sym) == 7) {
                            checkBox27.setChecked(true);
                        } else if (Integer.parseInt(sym) == 8) {
                            checkBox28.setChecked(true);
                        } else if (Integer.parseInt(sym) == 9) {
                            checkBox29.setChecked(true);
                        } else if (Integer.parseInt(sym) == 10) {
                            checkBox210.setChecked(true);
                        } else if (Integer.parseInt(sym) == 11) {
                            checkBox211.setChecked(true);
                        } else if (Integer.parseInt(sym) == 12) {
                            checkBox212.setChecked(true);
                        } else if (Integer.parseInt(sym) == 13) {
                            checkBox213.setChecked(true);
                        } else if (Integer.parseInt(sym) == 14) {
                            checkBox214.setChecked(true);
                        } else if (Integer.parseInt(sym) == 15) {
                            checkBox215.setChecked(true);
                        } else if (Integer.parseInt(sym) == 16) {
                            checkBox216.setChecked(true);
                        } else if (Integer.parseInt(sym) == 17) {
                            checkBox217.setChecked(true);
                        } else if (Integer.parseInt(sym) == 18) {
                            checkBox218.setChecked(true);
                        } else if (Integer.parseInt(sym) == 19) {
                            checkBox219.setChecked(true);
                        } else if (Integer.parseInt(sym) == 20) {
                            checkBox220.setChecked(true);
                        } else if (Integer.parseInt(sym) == 21) {
                            checkBox221.setChecked(true);
                        } else if (Integer.parseInt(sym) == 22) {
                            checkBox222.setChecked(true);
                        } else {
                            checkBox223.setChecked(true);
                        }
                    } else {
                        String[] split = sym.split(",");
                        for (int i = 0; i < split.length; i++) {
                            if (Integer.parseInt(split[i]) == 1) {
                                checkBox21.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 2) {
                                checkBox22.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 3) {
                                checkBox23.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 4) {
                                checkBox24.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 5) {
                                checkBox25.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 6) {
                                checkBox26.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 7) {
                                checkBox27.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 8) {
                                checkBox28.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 9) {
                                checkBox29.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 10) {
                                checkBox210.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 11) {
                                checkBox211.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 12) {
                                checkBox212.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 13) {
                                checkBox213.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 14) {
                                checkBox214.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 15) {
                                checkBox215.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 16) {
                                checkBox216.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 17) {
                                checkBox217.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 18) {
                                checkBox218.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 19) {
                                checkBox219.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 20) {
                                checkBox220.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 21) {
                                checkBox221.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 22) {
                                checkBox222.setChecked(true);
                            } else {
                                checkBox223.setChecked(true);
                            }
                        }
                    }
                }
                otherZhengzhuang.setText(other_symptom);
                if (has_con == 0){
                    radioButton1.setChecked(true);
                }else {
                    radioButton2.setChecked(true);
                }

                if (con.length()!=0){
                    String s = con.substring(1, con.length() - 1);
                    if (s.length() == 1){
                        if (Integer.parseInt(s)== 1){
                            checkBox31.setChecked(true);
                        }else if (Integer.parseInt(s) == 2){
                            checkBox32.setChecked(true);
                        }else if (Integer.parseInt(s) == 3){
                            checkBox33.setChecked(true);
                        }else if (Integer.parseInt(s) == 4){
                            checkBox34.setChecked(true);
                        }else if (Integer.parseInt(s) == 5){
                            checkBox35.setChecked(true);
                        }else if (Integer.parseInt(s) == 6){
                            checkBox36.setChecked(true);
                        }else if (Integer.parseInt(s) == 7){
                            checkBox37.setChecked(true);
                        }else if (Integer.parseInt(s) == 8){
                            checkBox38.setChecked(true);
                        }else if (Integer.parseInt(s) == 9){
                            checkBox39.setChecked(true);
                        }
                    }else {
                        String[] split = s.split(",");
                        for (int i = 0;i<split.length;i++){

                            if (Integer.parseInt(split[i])== 1){
                                checkBox31.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 2){
                                checkBox32.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 3){
                                checkBox33.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 4){
                                checkBox34.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 5){
                                checkBox35.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 6){
                                checkBox36.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 7){
                                checkBox37.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 8){
                                checkBox38.setChecked(true);
                            }else if (Integer.parseInt(split[i]) == 9){
                                checkBox39.setChecked(true);
                            }
                        }
                    }
                }
                otherBingfazheng.setText(other_con);
                if (has_ct == 0){
                    ct_radioButton1.setChecked(true);
                }else if(has_ct ==1){
                    ct_radioButton2.setChecked(true);
                }else {
                    ct_radioButton3.setChecked(true);
                }

                ct_date_select.setText(ct_date1);
                chuyuanDate.setText(out_date);

            }

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_bingli);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);


        checkBox11 = (CheckBox)findViewById(R.id.checkbox11);
        checkBox12 = (CheckBox)findViewById(R.id.checkbox12);
        checkBox13 = (CheckBox)findViewById(R.id.checkbox13);
        checkBox14 = (CheckBox)findViewById(R.id.checkbox14);
        checkBox15 = (CheckBox)findViewById(R.id.checkbox15);
        checkBox21 = (CheckBox)findViewById(R.id.checkbox21);
        checkBox22 = (CheckBox)findViewById(R.id.checkbox22);
        checkBox23 = (CheckBox)findViewById(R.id.checkbox23);
        checkBox24 = (CheckBox)findViewById(R.id.checkbox24);
        checkBox25 = (CheckBox)findViewById(R.id.checkbox25);
        checkBox26 = (CheckBox)findViewById(R.id.checkbox26);
        checkBox27 = (CheckBox)findViewById(R.id.checkbox27);
        checkBox28 = (CheckBox)findViewById(R.id.checkbox28);
        checkBox29 = (CheckBox)findViewById(R.id.checkbox29);
        checkBox210 = (CheckBox)findViewById(R.id.checkbox210);
        checkBox211 = (CheckBox)findViewById(R.id.checkbox211);
        checkBox212 = (CheckBox)findViewById(R.id.checkbox212);
        checkBox213 = (CheckBox)findViewById(R.id.checkbox213);
        checkBox214 = (CheckBox)findViewById(R.id.checkbox214);
        checkBox215 = (CheckBox)findViewById(R.id.checkbox215);
        checkBox216 = (CheckBox)findViewById(R.id.checkbox216);
        checkBox217 = (CheckBox)findViewById(R.id.checkbox217);
        checkBox218 = (CheckBox)findViewById(R.id.checkbox218);
        checkBox219 = (CheckBox)findViewById(R.id.checkbox219);
        checkBox220 = (CheckBox)findViewById(R.id.checkbox220);
        checkBox221 = (CheckBox)findViewById(R.id.checkbox221);
        checkBox222 = (CheckBox)findViewById(R.id.checkbox222);
        checkBox223 = (CheckBox)findViewById(R.id.checkbox223);
        checkBox31 = (CheckBox)findViewById(R.id.checkbox31);
        checkBox32 = (CheckBox)findViewById(R.id.checkbox32);
        checkBox33 = (CheckBox)findViewById(R.id.checkbox33);
        checkBox34 = (CheckBox)findViewById(R.id.checkbox34);
        checkBox35 = (CheckBox)findViewById(R.id.checkbox35);
        checkBox36 = (CheckBox)findViewById(R.id.checkbox36);
        checkBox37 = (CheckBox)findViewById(R.id.checkbox37);
        checkBox38 = (CheckBox)findViewById(R.id.checkbox38);
        checkBox39 = (CheckBox)findViewById(R.id.checkbox39);

        otherTujing = (EditText)findViewById(R.id.bingli_other_edit);
        otherZhengzhuang = (EditText)findViewById(R.id.qitabushi_edit);
        otherBingfazheng = (EditText)findViewById(R.id.bingli_bingfazheng_other_edit);
        radioGroup1 = (RadioGroup)findViewById(R.id.bingli_radiogroup1);
        radioButton1 = (RadioButton)findViewById(R.id.bingli_radioButton11);
        radioButton2 = (RadioButton)findViewById(R.id.bingli_radioButton12);
        bingfazheng = (RelativeLayout)findViewById(R.id.bingli_bingfazheng);
        radioGroup2 = (RadioGroup)findViewById(R.id.bingli_ct_radiogroup);
        ct_radioButton1 = (RadioButton)findViewById(R.id.bingli_ct_radioButton1);
        ct_radioButton2 = (RadioButton)findViewById(R.id.bingli_ct_radioButton2);
        ct_radioButton3 = (RadioButton)findViewById(R.id.bingli_ct_radioButton3);
        ct_date = (RelativeLayout) findViewById(R.id.ct_date);
        ct_date_select = (TextView)findViewById(R.id.select_ct_date);
        chuyuanDate = (TextView)findViewById(R.id.select_chuyuandate);

        String url = "http://175.23.169.100:9000/case-discovery-treatment/get";
        RequestBingliThread rdt = new RequestBingliThread(url,current_transId,handler);
        rdt.start();


        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("无")){
                    bingfazheng.setVisibility(View.GONE);
                }else {
                    bingfazheng.setVisibility(View.VISIBLE);
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("未检查")){
                    ct_date.setVisibility(View.GONE);
                }else {
                    ct_date.setVisibility(View.VISIBLE);
                }
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

        ct_date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ct_date_select.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
                //showDateDialog();

            }
        });

        chuyuanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chuyuanDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();

                //showDateDialog();
            }
        });











        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        okay = (TextView) findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int way = 0;
                if (checkBox11.isChecked()){
                    way = 1;
                }else if (checkBox12.isChecked()){
                    way = 2;
                }else if (checkBox13.isChecked()){
                    way = 3;
                }else if (checkBox14.isChecked()){
                    way = 4;
                }else {
                    way = 5;
                }
                final List<Integer> list1 = new ArrayList<>();
                if (checkBox21.isChecked()){
                    list1.add(1);
                }if (checkBox22.isChecked()){
                    list1.add(2);
                }
                if (checkBox23.isChecked()){
                    list1.add(3);
                }if (checkBox24.isChecked()){
                    list1.add(4);
                }if (checkBox25.isChecked()){
                    list1.add(5);
                }if (checkBox26.isChecked()){
                    list1.add(6);
                }if (checkBox27.isChecked()){
                    list1.add(7);
                }if (checkBox28.isChecked()){
                    list1.add(8);
                }if (checkBox29.isChecked()){
                    list1.add(9);
                }if (checkBox210.isChecked()){
                    list1.add(10);
                }if (checkBox211.isChecked()){
                    list1.add(11);
                }if (checkBox212.isChecked()){
                    list1.add(12);
                }
                if (checkBox213.isChecked()){
                    list1.add(13);
                }if (checkBox214.isChecked()){
                    list1.add(14);
                }if (checkBox215.isChecked()){
                    list1.add(15);
                }if (checkBox216.isChecked()){
                    list1.add(16);
                }if (checkBox217.isChecked()){
                    list1.add(17);
                }if (checkBox218.isChecked()){
                    list1.add(18);
                }if (checkBox219.isChecked()){
                    list1.add(19);
                }if (checkBox220.isChecked()){
                    list1.add(20);
                }if (checkBox221.isChecked()){
                    list1.add(21);
                }if (checkBox222.isChecked()){
                    list1.add(22);
                }
                if (checkBox223.isChecked()){
                    list1.add(23);
                }

                int has_con = 0;
                if (radioButton1.isChecked()){
                    has_con = 1;
                }else {
                    has_con = 0;
                }



                final String s = list1.toString().replace(" ","");
                s.replace("[","");
                s.replace("]","");


                final List<Integer> list2 = new ArrayList<>();
                if (checkBox31.isChecked()){
                    list2.add(1);
                }if (checkBox32.isChecked()){
                    list2.add(2);
                }
                if (checkBox33.isChecked()){
                    list2.add(3);
                }if (checkBox34.isChecked()){
                    list2.add(4);
                }if (checkBox35.isChecked()){
                    list2.add(5);
                }if (checkBox36.isChecked()){
                    list2.add(6);
                }if (checkBox37.isChecked()){
                    list2.add(7);
                }if (checkBox38.isChecked()){
                    list2.add(8);
                }if (checkBox39.isChecked()){
                    list2.add(9);
                }
                final String s2 = list2.toString().replace(" ","");
                s2.replace("[","");
                s2.replace("]","");

                int has_ct = 0;
                if (ct_radioButton1.isChecked()){
                    has_ct = 0;
                }else if(ct_radioButton2.isChecked()){
                    has_ct =1;
                }else {
                    has_ct = 2;
                }

                final StringBuffer stringBuffer = new StringBuffer(ct_date_select.getText().toString());
                if (ct_date_select.getText().toString().length()==8){
                    stringBuffer.insert(5,"0");
                    stringBuffer.insert(8,"0");
                }else if (ct_date_select.getText().toString().length()==9){
                    String[] split = ct_date_select.getText().toString().split("-");
                    if (split[1].length() == 2){
                        stringBuffer.insert(8,"0");
                    }else {
                        stringBuffer.insert(5,"0");
                    }
                }
                final StringBuffer stringBuffer2 = new StringBuffer(chuyuanDate.getText().toString());
                if (chuyuanDate.getText().toString().length()==8){
                    stringBuffer2.insert(5,"0");
                    stringBuffer2.insert(8,"0");
                }else if (chuyuanDate.getText().toString().length()==9){
                    String[] split = chuyuanDate.getText().toString().split("-");
                    if (split[1].length() == 2){
                        stringBuffer2.insert(8,"0");
                    }else {
                        stringBuffer2.insert(5,"0");
                    }
                }
                //dataCommit();
                final int finalWay = way;
                final int finalHas_con = has_con;
                final int finalHas_ct = has_ct;
                if (!isMe){
                    builder = new AlertDialog.Builder(Bingli.this).setTitle("重要提醒")
                            .setMessage("当前为代办模式，是否确认提交？")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String postUrl = "http://175.23.169.100:9000/case-discovery-treatment/set";
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put("transactor_id",current_transId);
                                                jsonObject.put("way", finalWay);
                                                jsonObject.put("other_way",otherTujing.getText().toString());
                                                jsonObject.put("admission_date","2022-03-25");
                                                jsonObject.put("symptom",s);
                                                jsonObject.put("other_symptom",otherZhengzhuang.getText().toString());
                                                jsonObject.put("has_con", finalHas_con);
                                                jsonObject.put("con",s2);
                                                jsonObject.put("other_con",otherBingfazheng.getText().toString());
                                                jsonObject.put("has_ct", finalHas_ct);
                                                jsonObject.put("ct_date",stringBuffer.toString());
                                                jsonObject.put("out_date",stringBuffer2.toString());

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
                                                    editor.putBoolean(current_transId+"hasBingli",true);
                                                    editor.commit();
                                                    Looper.prepare();
                                                    Toast.makeText(Bingli.this,"提交成功",Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(Bingli.this, Main.class);
                                    startActivity(intent);
                                    finish();

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
                                String postUrl = "http://175.23.169.100:9000/case-discovery-treatment/set";
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("transactor_id",current_transId);
                                jsonObject.put("way", finalWay);
                                jsonObject.put("other_way",otherTujing.getText().toString());
                                jsonObject.put("admission_date","2022-03-25");
                                jsonObject.put("symptom",s);
                                jsonObject.put("other_symptom",otherZhengzhuang.getText().toString());
                                jsonObject.put("has_con", finalHas_con);
                                jsonObject.put("con",s2);
                                jsonObject.put("other_con",otherBingfazheng.getText().toString());
                                jsonObject.put("has_ct", finalHas_ct);
                                jsonObject.put("ct_date",stringBuffer.toString());
                                jsonObject.put("out_date",stringBuffer2.toString());

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
                                    Toast.makeText(Bingli.this,"提交成功",Toast.LENGTH_SHORT).show();
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
        SharedPreferences preferences = getSharedPreferences("user_bingli", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

        editor.putBoolean("bingli_checkbox11",checkBox11.isChecked());
        editor.putBoolean("bingli_checkbox12",checkBox12.isChecked());
        editor.putBoolean("bingli_checkbox13",checkBox13.isChecked());
        editor.putBoolean("bingli_checkbox14",checkBox14.isChecked());
        editor.putBoolean("bingli_checkbox15",checkBox15.isChecked());
        editor.putBoolean("bingli_checkbox21",checkBox21.isChecked());
        editor.putBoolean("bingli_checkbox22",checkBox22.isChecked());
        editor.putBoolean("bingli_checkbox23",checkBox23.isChecked());
        editor.putBoolean("bingli_checkbox24",checkBox24.isChecked());
        editor.putBoolean("bingli_checkbox25",checkBox25.isChecked());
        editor.putBoolean("bingli_checkbox26",checkBox26.isChecked());
        editor.putBoolean("bingli_checkbox27",checkBox27.isChecked());
        editor.putBoolean("bingli_checkbox28",checkBox28.isChecked());
        editor.putBoolean("bingli_checkbox29",checkBox29.isChecked());
        editor.putBoolean("bingli_checkbox210",checkBox210.isChecked());
        editor.putBoolean("bingli_checkbox211",checkBox211.isChecked());
        editor.putBoolean("bingli_checkbox212",checkBox212.isChecked());
        editor.putBoolean("bingli_checkbox213",checkBox213.isChecked());
        editor.putBoolean("bingli_checkbox214",checkBox214.isChecked());
        editor.putBoolean("bingli_checkbox215",checkBox215.isChecked());
        editor.putBoolean("bingli_checkbox216",checkBox216.isChecked());
        editor.putBoolean("bingli_checkbox217",checkBox217.isChecked());
        editor.putBoolean("bingli_checkbox218",checkBox218.isChecked());
        editor.putBoolean("bingli_checkbox219",checkBox219.isChecked());
        editor.putBoolean("bingli_checkbox220",checkBox220.isChecked());
        editor.putBoolean("bingli_checkbox221",checkBox221.isChecked());
        editor.putBoolean("bingli_checkbox222",checkBox222.isChecked());
        editor.putBoolean("bingli_checkbox223",checkBox223.isChecked());
        editor.putBoolean("bingli_checkbox31",checkBox31.isChecked());
        editor.putBoolean("bingli_checkbox32",checkBox32.isChecked());
        editor.putBoolean("bingli_checkbox33",checkBox33.isChecked());
        editor.putBoolean("bingli_checkbox34",checkBox34.isChecked());
        editor.putBoolean("bingli_checkbox35",checkBox35.isChecked());
        editor.putBoolean("bingli_checkbox36",checkBox36.isChecked());
        editor.putBoolean("bingli_checkbox37",checkBox37.isChecked());
        editor.putBoolean("bingli_checkbox38",checkBox38.isChecked());
        editor.putBoolean("bingli_checkbox39",checkBox39.isChecked());

        editor.putString("bingli_otherTujing",otherTujing.getText().toString());
        editor.putString("bingli_otherZhengzhuang",otherZhengzhuang.getText().toString());
        editor.putString("bingli_otherBingfazheng",otherBingfazheng.getText().toString());

        if (radioButton1.isChecked() == true){
            editor.putBoolean("bingli_hasBingfazheng",true);
            editor.putBoolean("bingli_noBingfazheng",false);
        }else {
            editor.putBoolean("bingli_noBingfazheng",true);
            editor.putBoolean("bingli_hasBingfazheng",false);
        }

        if (ct_radioButton1.isChecked() == true){
            editor.putBoolean("bingli_hasCt",true);
            editor.putBoolean("bingli_noCt",false);
            editor.putBoolean("bingli_neverCt",false);
        } else if (ct_radioButton2.isChecked() == true) {
            editor.putBoolean("bingli_hasCt",false);
            editor.putBoolean("bingli_noCt",true);
            editor.putBoolean("bingli_neverCt",false);
        }else {
            editor.putBoolean("bingli_hasCt",false);
            editor.putBoolean("bingli_noCt",false);
            editor.putBoolean("bingli_neverCt",true);
        }

        editor.putString("bingli_ctDate",ct_date_select.getText().toString());
        editor.putString("bingli_chuyuanDate",chuyuanDate.getText().toString());
        editor.commit();

    }
}
