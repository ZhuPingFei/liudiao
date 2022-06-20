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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

    private StringBuffer zhengzhuang;
    private String tujing = "";
    private String status_baogao;
    private String first_baogao;
    private String last_baogao;

    private ImageView r_back;
    private TextView okay;

    private RadioButton newButton1;
    private RadioButton newButton2;
    private RadioButton newButton3;
    private RadioButton newButton4;
    private RadioButton newButton5;
    private RadioButton newButton6;
    private RadioButton newButton7;
    private RadioButton newButton8;

    private RadioButton newButton81;
    private RadioButton newButton82;
    private RadioButton newButton83;
    private RadioButton newButton84;
    private RadioButton newButton85;
    private RadioButton newButton86;
    private RadioButton newButton87;
    private RadioButton newButton88;
    private RadioButton newButton89;
    private RadioButton newButton810;
    private RadioButton newButton811;
    private RadioButton newButton812;
    private RadioButton newButton813;

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
    private EditText otherStatus;
    private EditText tiwen;
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
    private TextView ruyuanDate;
    private TextView lastYinxingDate;
    private TextView firstYangxingDate;

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

    private Handler handler2 = new Handler();
    private Runnable runnable;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();

            int code = b.getInt("code");

            if (code == 0) {
                String way = b.getString("way");
                String other_way = b.getString("other_way");
                String symptom = b.getString("symptom");
                String other_symptom = b.getString("other_symptom");
                String has_con = b.getString("has_con");
                String con = b.getString("con");
                String other_con = b.getString("other_con");
                String has_ct = b.getString("has_ct");
                String ct_date1 = b.getString("ct_date");
                String out_date = b.getString("out_date");
                String ruyuan_date = b.getString("admission_date");
                //4.9
                String other_status = b.getString("other_status");
                String last_negative_date = b.getString("last_negative_date");
                String first_positive_date = b.getString("first_positive_date");
                String temperature = b.getString("temperature");
                String status = b.getString("status");

                newButton1 = (RadioButton) findViewById(R.id.radioButton1);
                newButton2 = (RadioButton) findViewById(R.id.radioButton2);
                newButton3 = (RadioButton) findViewById(R.id.radioButton3);
                newButton4 = (RadioButton) findViewById(R.id.radioButton4);
                newButton5 = (RadioButton) findViewById(R.id.radioButton5);
                newButton6 = (RadioButton) findViewById(R.id.radioButton6);
                newButton7 = (RadioButton) findViewById(R.id.radioButton7);
                newButton8 = (RadioButton) findViewById(R.id.radioButton8);
                //4.9
                newButton81 = (RadioButton) findViewById(R.id.radioButton81);
                newButton82 = (RadioButton) findViewById(R.id.radioButton82);
                newButton83 = (RadioButton) findViewById(R.id.radioButton83);
                newButton84 = (RadioButton) findViewById(R.id.radioButton84);
                newButton85 = (RadioButton) findViewById(R.id.radioButton85);
                newButton86 = (RadioButton) findViewById(R.id.radioButton86);
                newButton87 = (RadioButton) findViewById(R.id.radioButton87);
                newButton88 = (RadioButton) findViewById(R.id.radioButton88);
                newButton89 = (RadioButton) findViewById(R.id.radioButton89);
                newButton810 = (RadioButton) findViewById(R.id.radioButton810);
                newButton811 = (RadioButton) findViewById(R.id.radioButton811);
                newButton812 = (RadioButton) findViewById(R.id.radioButton812);
                newButton813 = (RadioButton) findViewById(R.id.radioButton813);

                checkBox21 = (CheckBox) findViewById(R.id.checkbox21);
                checkBox22 = (CheckBox) findViewById(R.id.checkbox22);
                checkBox23 = (CheckBox) findViewById(R.id.checkbox23);
                checkBox24 = (CheckBox) findViewById(R.id.checkbox24);
                checkBox25 = (CheckBox) findViewById(R.id.checkbox25);
                checkBox26 = (CheckBox) findViewById(R.id.checkbox26);
                checkBox27 = (CheckBox) findViewById(R.id.checkbox27);
                checkBox28 = (CheckBox) findViewById(R.id.checkbox28);
                checkBox29 = (CheckBox) findViewById(R.id.checkbox29);
                checkBox210 = (CheckBox) findViewById(R.id.checkbox210);
                checkBox211 = (CheckBox) findViewById(R.id.checkbox211);
                checkBox212 = (CheckBox) findViewById(R.id.checkbox212);
                checkBox213 = (CheckBox) findViewById(R.id.checkbox213);
                checkBox214 = (CheckBox) findViewById(R.id.checkbox214);
                checkBox215 = (CheckBox) findViewById(R.id.checkbox215);
                checkBox216 = (CheckBox) findViewById(R.id.checkbox216);
                checkBox217 = (CheckBox) findViewById(R.id.checkbox217);
                checkBox218 = (CheckBox) findViewById(R.id.checkbox218);
                checkBox219 = (CheckBox) findViewById(R.id.checkbox219);
                checkBox220 = (CheckBox) findViewById(R.id.checkbox220);
                checkBox221 = (CheckBox) findViewById(R.id.checkbox221);
                checkBox222 = (CheckBox) findViewById(R.id.checkbox222);
                checkBox223 = (CheckBox) findViewById(R.id.checkbox223);
                checkBox31 = (CheckBox) findViewById(R.id.checkbox31);
                checkBox32 = (CheckBox) findViewById(R.id.checkbox32);
                checkBox33 = (CheckBox) findViewById(R.id.checkbox33);
                checkBox34 = (CheckBox) findViewById(R.id.checkbox34);
                checkBox35 = (CheckBox) findViewById(R.id.checkbox35);
                checkBox36 = (CheckBox) findViewById(R.id.checkbox36);
                checkBox37 = (CheckBox) findViewById(R.id.checkbox37);
                checkBox38 = (CheckBox) findViewById(R.id.checkbox38);
                checkBox39 = (CheckBox) findViewById(R.id.checkbox39);

                otherTujing = (EditText) findViewById(R.id.bingli_other_edit);
                otherZhengzhuang = (EditText) findViewById(R.id.qitabushi_edit);


                otherBingfazheng = (EditText) findViewById(R.id.bingli_bingfazheng_other_edit);
                radioGroup1 = (RadioGroup) findViewById(R.id.bingli_radiogroup1);
                radioButton1 = (RadioButton) findViewById(R.id.bingli_radioButton11);
                radioButton2 = (RadioButton) findViewById(R.id.bingli_radioButton12);
                bingfazheng = (RelativeLayout) findViewById(R.id.bingli_bingfazheng);
                radioGroup2 = (RadioGroup) findViewById(R.id.bingli_ct_radiogroup);
                ct_radioButton1 = (RadioButton) findViewById(R.id.bingli_ct_radioButton1);
                ct_radioButton2 = (RadioButton) findViewById(R.id.bingli_ct_radioButton2);
                ct_radioButton3 = (RadioButton) findViewById(R.id.bingli_ct_radioButton3);
                ct_date = (RelativeLayout) findViewById(R.id.ct_date);
                ct_date_select = (TextView) findViewById(R.id.select_ct_date);
                chuyuanDate = (TextView) findViewById(R.id.select_chuyuandate);
                ruyuanDate = (TextView) findViewById(R.id.select_ruyuandate);

                //4.9
                lastYinxingDate = (TextView) findViewById(R.id.select_yinxing);
                firstYangxingDate = (TextView) findViewById(R.id.select_yangxing);
                tiwen = (EditText) findViewById(R.id.tiwen_edit);
                otherStatus = (EditText) findViewById(R.id.bingli_otherxianzhuang_edit);

                if (!way.equals("") && !way.equals("null")){
                    if (Integer.parseInt(way) == 0){
                        newButton1.setChecked(true);
                    }else if (Integer.parseInt(way) == 1){
                        newButton2.setChecked(true);
                    }else if (Integer.parseInt(way) == 2){
                        newButton3.setChecked(true);
                    }else if (Integer.parseInt(way) == 3){
                        newButton4.setChecked(true);
                    }else if (Integer.parseInt(way) == 4){
                        newButton5.setChecked(true);
                    }else if (Integer.parseInt(way) == 5){
                        newButton6.setChecked(true);
                    }else if (Integer.parseInt(way) == 6){
                        newButton7.setChecked(true);
                    }else if (Integer.parseInt(way) == 7){
                        newButton8.setChecked(true);
                    }
                }else {

                }
                if (!other_way.equals("") && !other_way.equals("null")){
                    otherTujing.setText(other_way);
                }
                if (symptom.length() != 0 && !symptom.equals("null")) {
                    String sym = symptom;
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
                if (!other_symptom.equals("") && !other_symptom.equals("null")){
                    otherZhengzhuang.setText(other_symptom);
                }
                if (!temperature.equals("") && !temperature.equals("null")){
                    tiwen.setText(temperature);
                }

                if (!has_con.equals("") && !has_con.equals("null")){
                    if (Integer.parseInt(has_con) == 1){
                        radioButton1.setChecked(true);
                    }else if (Integer.parseInt(has_con) == 0){
                        radioButton2.setChecked(true);
                    }
                }
                if (!con.equals("") && !con.equals("null")) {
                    String s = con;
                    if (s.length() == 1) {
                        if (Integer.parseInt(s) == 1) {
                            checkBox31.setChecked(true);
                        } else if (Integer.parseInt(s) == 2) {
                            checkBox32.setChecked(true);
                        } else if (Integer.parseInt(s) == 3) {
                            checkBox33.setChecked(true);
                        } else if (Integer.parseInt(s) == 4) {
                            checkBox34.setChecked(true);
                        } else if (Integer.parseInt(s) == 5) {
                            checkBox35.setChecked(true);
                        } else if (Integer.parseInt(s) == 6) {
                            checkBox36.setChecked(true);
                        } else if (Integer.parseInt(s) == 7) {
                            checkBox37.setChecked(true);
                        } else if (Integer.parseInt(s) == 8) {
                            checkBox38.setChecked(true);
                        } else if (Integer.parseInt(s) == 9) {
                            checkBox39.setChecked(true);
                        }
                    } else {
                        String[] split = s.split(",");
                        for (int i = 0; i < split.length; i++) {
                            if (Integer.parseInt(split[i]) == 1) {
                                checkBox31.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 2) {
                                checkBox32.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 3) {
                                checkBox33.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 4) {
                                checkBox34.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 5) {
                                checkBox35.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 6) {
                                checkBox36.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 7) {
                                checkBox37.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 8) {
                                checkBox38.setChecked(true);
                            } else if (Integer.parseInt(split[i]) == 9) {
                                checkBox39.setChecked(true);
                            }
                        }
                    }
                }
                if (!other_con.equals("") &&!other_con.equals("null")){
                    otherBingfazheng.setText(other_con);
                }

                if (!has_ct.equals("") && !has_ct.equals("null")){
                    if (Integer.parseInt(has_ct) == 1){
                        ct_radioButton1.setChecked(true);
                    }else if (Integer.parseInt(has_ct) == 0){
                        ct_radioButton2.setChecked(true);
                    }else {
                        ct_radioButton3.setChecked(true);
                    }
                }
                if (!ct_date1.equals("") && !ct_date1.equals("null")){
                    ct_date_select.setText(ct_date1);
                }

                if (!ruyuan_date.equals("") &&!ruyuan_date.equals("null")){
                    ruyuanDate.setText(ruyuan_date);
                }
                if (!out_date.equals("") && !out_date.equals("null")){
                    chuyuanDate.setText(out_date);
                }

                if (!last_negative_date.equals("") && !last_negative_date.equals("null")) {
                    lastYinxingDate.setText(last_negative_date);
                }
                if (!first_positive_date.equals("") && !first_positive_date.equals("null")) {
                    firstYangxingDate.setText(first_positive_date);
                }
                if (!status.equals("") &&!status.equals("null")){
                    if (Integer.parseInt(status) == 1){
                        newButton81.setChecked(true);
                    } else if (Integer.parseInt(status) == 2) {
                        newButton82.setChecked(true);
                    }else if (Integer.parseInt(status) == 3) {
                        newButton83.setChecked(true);
                    }else if (Integer.parseInt(status) == 4) {
                        newButton84.setChecked(true);
                    }else if (Integer.parseInt(status) == 5) {
                        newButton85.setChecked(true);
                    }else if (Integer.parseInt(status) == 6) {
                        newButton86.setChecked(true);
                    }else if (Integer.parseInt(status) == 7) {
                        newButton87.setChecked(true);
                    }else if (Integer.parseInt(status) == 8) {
                        newButton88.setChecked(true);
                    }else if (Integer.parseInt(status) == 9) {
                        newButton89.setChecked(true);
                    }else if (Integer.parseInt(status) == 10) {
                        newButton810.setChecked(true);
                    }else if (Integer.parseInt(status) == 11) {
                        newButton811.setChecked(true);
                    }else if (Integer.parseInt(status) == 12) {
                        newButton812.setChecked(true);
                    }else if (Integer.parseInt(status) == 13) {
                        newButton813.setChecked(true);
                    }
                }

                if (!other_status.equals("null") && !other_status.equals("")) {
                    otherStatus.setText(other_status);
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_bingli);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId", 0);
        isMe = preferences.getBoolean("isMe", false);


        newButton1 = (RadioButton) findViewById(R.id.radioButton1);
        newButton2 = (RadioButton) findViewById(R.id.radioButton2);
        newButton3 = (RadioButton) findViewById(R.id.radioButton3);
        newButton4 = (RadioButton) findViewById(R.id.radioButton4);
        newButton5 = (RadioButton) findViewById(R.id.radioButton5);
        newButton6 = (RadioButton) findViewById(R.id.radioButton6);
        newButton7 = (RadioButton) findViewById(R.id.radioButton7);
        newButton8 = (RadioButton) findViewById(R.id.radioButton8);

        //4.9
        newButton81 = (RadioButton) findViewById(R.id.radioButton81);
        newButton82 = (RadioButton) findViewById(R.id.radioButton82);
        newButton83 = (RadioButton) findViewById(R.id.radioButton83);
        newButton84 = (RadioButton) findViewById(R.id.radioButton84);
        newButton85 = (RadioButton) findViewById(R.id.radioButton85);
        newButton86 = (RadioButton) findViewById(R.id.radioButton86);
        newButton87 = (RadioButton) findViewById(R.id.radioButton87);
        newButton88 = (RadioButton) findViewById(R.id.radioButton88);
        newButton89 = (RadioButton) findViewById(R.id.radioButton89);
        newButton810 = (RadioButton) findViewById(R.id.radioButton810);
        newButton811 = (RadioButton) findViewById(R.id.radioButton811);
        newButton812 = (RadioButton) findViewById(R.id.radioButton812);
        newButton813 = (RadioButton) findViewById(R.id.radioButton813);

        checkBox21 = (CheckBox) findViewById(R.id.checkbox21);
        checkBox22 = (CheckBox) findViewById(R.id.checkbox22);
        checkBox23 = (CheckBox) findViewById(R.id.checkbox23);
        checkBox24 = (CheckBox) findViewById(R.id.checkbox24);
        checkBox25 = (CheckBox) findViewById(R.id.checkbox25);
        checkBox26 = (CheckBox) findViewById(R.id.checkbox26);
        checkBox27 = (CheckBox) findViewById(R.id.checkbox27);
        checkBox28 = (CheckBox) findViewById(R.id.checkbox28);
        checkBox29 = (CheckBox) findViewById(R.id.checkbox29);
        checkBox210 = (CheckBox) findViewById(R.id.checkbox210);
        checkBox211 = (CheckBox) findViewById(R.id.checkbox211);
        checkBox212 = (CheckBox) findViewById(R.id.checkbox212);
        checkBox213 = (CheckBox) findViewById(R.id.checkbox213);
        checkBox214 = (CheckBox) findViewById(R.id.checkbox214);
        checkBox215 = (CheckBox) findViewById(R.id.checkbox215);
        checkBox216 = (CheckBox) findViewById(R.id.checkbox216);
        checkBox217 = (CheckBox) findViewById(R.id.checkbox217);
        checkBox218 = (CheckBox) findViewById(R.id.checkbox218);
        checkBox219 = (CheckBox) findViewById(R.id.checkbox219);
        checkBox220 = (CheckBox) findViewById(R.id.checkbox220);
        checkBox221 = (CheckBox) findViewById(R.id.checkbox221);
        checkBox222 = (CheckBox) findViewById(R.id.checkbox222);
        checkBox223 = (CheckBox) findViewById(R.id.checkbox223);
        checkBox31 = (CheckBox) findViewById(R.id.checkbox31);
        checkBox32 = (CheckBox) findViewById(R.id.checkbox32);
        checkBox33 = (CheckBox) findViewById(R.id.checkbox33);
        checkBox34 = (CheckBox) findViewById(R.id.checkbox34);
        checkBox35 = (CheckBox) findViewById(R.id.checkbox35);
        checkBox36 = (CheckBox) findViewById(R.id.checkbox36);
        checkBox37 = (CheckBox) findViewById(R.id.checkbox37);
        checkBox38 = (CheckBox) findViewById(R.id.checkbox38);
        checkBox39 = (CheckBox) findViewById(R.id.checkbox39);

        otherTujing = (EditText) findViewById(R.id.bingli_other_edit);
        otherZhengzhuang = (EditText) findViewById(R.id.qitabushi_edit);

        otherBingfazheng = (EditText) findViewById(R.id.bingli_bingfazheng_other_edit);
        radioGroup1 = (RadioGroup) findViewById(R.id.bingli_radiogroup1);
        radioButton1 = (RadioButton) findViewById(R.id.bingli_radioButton11);
        radioButton2 = (RadioButton) findViewById(R.id.bingli_radioButton12);
        bingfazheng = (RelativeLayout) findViewById(R.id.bingli_bingfazheng);
        radioGroup2 = (RadioGroup) findViewById(R.id.bingli_ct_radiogroup);
        ct_radioButton1 = (RadioButton) findViewById(R.id.bingli_ct_radioButton1);
        ct_radioButton2 = (RadioButton) findViewById(R.id.bingli_ct_radioButton2);
        ct_radioButton3 = (RadioButton) findViewById(R.id.bingli_ct_radioButton3);
        ct_date = (RelativeLayout) findViewById(R.id.ct_date);
        ct_date_select = (TextView) findViewById(R.id.select_ct_date);
        chuyuanDate = (TextView) findViewById(R.id.select_chuyuandate);
        ruyuanDate = (TextView) findViewById(R.id.select_ruyuandate);
        //4.9
        lastYinxingDate = (TextView) findViewById(R.id.select_yinxing);
        firstYangxingDate = (TextView) findViewById(R.id.select_yangxing);
        otherStatus = (EditText) findViewById(R.id.bingli_otherxianzhuang_edit);
        tiwen = (EditText) findViewById(R.id.tiwen_edit);

        String url = "http://175.23.169.100:9040/case-discovery-treatment/get";
        RequestBingliThread rdt = new RequestBingliThread(url, current_transId, handler);
        rdt.start();

        tiwen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (runnable != null) {
                    handler2.removeCallbacks(runnable);
                }
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!isTiwen(s.toString())) {
                            Toast.makeText(Bingli.this, "请填写合理的体温！", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                handler2.postDelayed(runnable, 1000);
            }
        });


        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("无")) {
                    bingfazheng.setVisibility(View.GONE);
                } else {
                    bingfazheng.setVisibility(View.VISIBLE);
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String s = radioButton.getText().toString();
                if (s.equals("未检查")) {
                    ct_date.setVisibility(View.GONE);
                } else {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ct_date_select.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        chuyuanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chuyuanDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        ruyuanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ruyuanDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        firstYangxingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        firstYangxingDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        lastYinxingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Bingli.this, android.app.AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        lastYinxingDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
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
                int way = -1;
                if (newButton1.isChecked()) {
                    way = 0;
                } else if (newButton2.isChecked()) {
                    way = 1;
                } else if (newButton3.isChecked()) {
                    way = 2;
                } else if (newButton4.isChecked()) {
                    way = 3;
                } else if (newButton5.isChecked()) {
                    way = 4;
                } else if (newButton6.isChecked()) {
                    way = 5;
                } else if (newButton7.isChecked()) {
                    way = 6;
                } else if (newButton8.isChecked()) {
                    way = 7;
                }

                String other_way = otherTujing.getText().toString();
                if (other_way.equals("")) {
                    other_way = null;
                }

                final List<Integer> list1 = new ArrayList<>();
                if (checkBox21.isChecked()) {
                    list1.add(1);
                }
                if (checkBox22.isChecked()) {
                    list1.add(2);
                }
                if (checkBox23.isChecked()) {
                    list1.add(3);
                }
                if (checkBox24.isChecked()) {
                    list1.add(4);
                }
                if (checkBox25.isChecked()) {
                    list1.add(5);
                }
                if (checkBox26.isChecked()) {
                    list1.add(6);
                }
                if (checkBox27.isChecked()) {
                    list1.add(7);
                }
                if (checkBox28.isChecked()) {
                    list1.add(8);
                }
                if (checkBox29.isChecked()) {
                    list1.add(9);
                }
                if (checkBox210.isChecked()) {
                    list1.add(10);
                }
                if (checkBox211.isChecked()) {
                    list1.add(11);
                }
                if (checkBox212.isChecked()) {
                    list1.add(12);
                }
                if (checkBox213.isChecked()) {
                    list1.add(13);
                }
                if (checkBox214.isChecked()) {
                    list1.add(14);
                }
                if (checkBox215.isChecked()) {
                    list1.add(15);
                }
                if (checkBox216.isChecked()) {
                    list1.add(16);
                }
                if (checkBox217.isChecked()) {
                    list1.add(17);
                }
                if (checkBox218.isChecked()) {
                    list1.add(18);
                }
                if (checkBox219.isChecked()) {
                    list1.add(19);
                }
                if (checkBox220.isChecked()) {
                    list1.add(20);
                }
                if (checkBox221.isChecked()) {
                    list1.add(21);
                }
                if (checkBox222.isChecked()) {
                    list1.add(22);
                }
                if (checkBox223.isChecked()) {
                    list1.add(23);
                }

                String s = list1.toString().replace(" ", "");
                if (s.length() == 2) {
                    s = null;
                } else {
                    s = s.substring(1, s.length() - 1);
                }

                String other_symptom = otherZhengzhuang.getText().toString();
                if (other_symptom.equals("")) {
                    other_symptom = null;
                }

                String temperature = tiwen.getText().toString();
                if (temperature.equals("")) {
                    temperature = null;
                }

                int has_con = -1;
                if (radioButton1.isChecked()) {
                    has_con = 1;
                } else if (radioButton2.isChecked()) {
                    has_con = 0;
                }

                final List<Integer> list2 = new ArrayList<>();
                if (checkBox31.isChecked()) {
                    list2.add(1);
                }
                if (checkBox32.isChecked()) {
                    list2.add(2);
                }
                if (checkBox33.isChecked()) {
                    list2.add(3);
                }
                if (checkBox34.isChecked()) {
                    list2.add(4);
                }
                if (checkBox35.isChecked()) {
                    list2.add(5);
                }
                if (checkBox36.isChecked()) {
                    list2.add(6);
                }
                if (checkBox37.isChecked()) {
                    list2.add(7);
                }
                if (checkBox38.isChecked()) {
                    list2.add(8);
                }
                if (checkBox39.isChecked()) {
                    list2.add(9);
                }
                String s2 = list2.toString().replace(" ", "");
                if (s2.length() == 2) {
                    s2 = null;
                } else {
                    s2 = s2.substring(1, s2.length() - 1);
                }
                String other_con = otherBingfazheng.getText().toString();
                if (other_con.equals("")) {
                    other_con = null;
                }

                int has_ct = -1;
                if (ct_radioButton1.isChecked()) {
                    has_ct = 1;
                } else if (ct_radioButton2.isChecked()) {
                    has_ct = 0;
                } else if (ct_radioButton3.isChecked()) {
                    has_ct = 2;
                }
                final StringBuffer stringBuffer = new StringBuffer(ct_date_select.getText().toString());
                if (ct_date_select.getText().toString().length() == 8) {
                    stringBuffer.insert(5, "0");
                    stringBuffer.insert(8, "0");
                } else if (ct_date_select.getText().toString().length() == 9) {
                    String[] split = ct_date_select.getText().toString().split("-");
                    if (split[1].length() == 2) {
                        stringBuffer.insert(8, "0");
                    } else {
                        stringBuffer.insert(5, "0");
                    }
                }
                String ct_date = stringBuffer.toString();
                if (ct_date.equals("")) {
                    ct_date = null;
                }

                final StringBuffer stringBuffer3 = new StringBuffer(ruyuanDate.getText().toString());
                if (ruyuanDate.getText().toString().length() == 8) {
                    stringBuffer3.insert(5, "0");
                    stringBuffer3.insert(8, "0");
                } else if (ruyuanDate.getText().toString().length() == 9) {
                    String[] split = ruyuanDate.getText().toString().split("-");
                    if (split[1].length() == 2) {
                        stringBuffer3.insert(8, "0");
                    } else {
                        stringBuffer3.insert(5, "0");
                    }
                }
                String admission_date = stringBuffer3.toString();
                if (admission_date.equals("")) {
                    admission_date = null;
                }

                final StringBuffer stringBuffer2 = new StringBuffer(chuyuanDate.getText().toString());
                if (chuyuanDate.getText().toString().length() == 8) {
                    stringBuffer2.insert(5, "0");
                    stringBuffer2.insert(8, "0");
                } else if (chuyuanDate.getText().toString().length() == 9) {
                    String[] split = chuyuanDate.getText().toString().split("-");
                    if (split[1].length() == 2) {
                        stringBuffer2.insert(8, "0");
                    } else {
                        stringBuffer2.insert(5, "0");
                    }
                }
                String out_date = stringBuffer2.toString();
                if (out_date.equals("")) {
                    out_date = null;
                }

                final StringBuffer stringBuffer4 = new StringBuffer(lastYinxingDate.getText().toString());
                if (lastYinxingDate.getText().toString().length() == 8) {
                    stringBuffer4.insert(5, "0");
                    stringBuffer4.insert(8, "0");
                } else if (lastYinxingDate.getText().toString().length() == 9) {
                    String[] split = lastYinxingDate.getText().toString().split("-");
                    if (split[1].length() == 2) {
                        stringBuffer4.insert(8, "0");
                    } else {
                        stringBuffer4.insert(5, "0");
                    }
                }
                String last_date = stringBuffer4.toString();
                if (last_date.equals("")) {
                    last_date = null;
                }

                final StringBuffer stringBuffer5 = new StringBuffer(firstYangxingDate.getText().toString());
                if (firstYangxingDate.getText().toString().length() == 8) {
                    stringBuffer5.insert(5, "0");
                    stringBuffer5.insert(8, "0");
                } else if (firstYangxingDate.getText().toString().length() == 9) {
                    String[] split = firstYangxingDate.getText().toString().split("-");
                    if (split[1].length() == 2) {
                        stringBuffer5.insert(8, "0");
                    } else {
                        stringBuffer5.insert(5, "0");
                    }
                }
                String first_date = stringBuffer5.toString();
                if (first_date.equals("")) {
                    first_date = null;
                }


                //4.9
                int way2 = -1;
                if (newButton81.isChecked()) {
                    way2 = 1;
                } else if (newButton82.isChecked()) {
                    way2 = 2;
                } else if (newButton83.isChecked()) {
                    way2 = 3;
                } else if (newButton84.isChecked()) {
                    way2 = 4;
                } else if (newButton85.isChecked()) {
                    way2 = 5;
                } else if (newButton86.isChecked()) {
                    way2 = 6;
                } else if (newButton87.isChecked()) {
                    way2 = 7;
                } else if (newButton88.isChecked()) {
                    way2 = 8;
                } else if (newButton89.isChecked()) {
                    way2 = 9;
                } else if (newButton810.isChecked()) {
                    way2 = 10;
                } else if (newButton811.isChecked()) {
                    way2 = 11;
                } else if (newButton812.isChecked()) {
                    way2 = 12;
                } else if (newButton813.isChecked()) {
                    way2 = 13;
                }
                String other_status = otherStatus.getText().toString();
                if (other_status.equals("")) {
                    other_status = null;
                }

                dataCommit();
                final int finalWay = way;
                final int finalHas_con = has_con;
                final int finalHas_ct = has_ct;

                if (!isMe) {
                    final int finalWay1 = way2;
                    final String finalOther_way = other_way;
                    final String finalS = s;
                    final String finalOther_symptom = other_symptom;
                    final String finalTemperature = temperature;
                    final String finalS1 = s2;
                    final String finalOther_con = other_con;
                    final String finalCt_date = ct_date;
                    final String finalAdmission_date = admission_date;
                    final String finalOut_date = out_date;
                    final String finalLast_date = last_date;
                    final String finalFirst_date = first_date;
                    final String finalOther_status = other_status;
                    builder = new AlertDialog.Builder(Bingli.this).setTitle("重要提醒")
                            .setMessage("当前为代办模式，是否确认提交？")
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    if (isTiwen(tiwen.getText().toString())) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String postUrl = "http://175.23.169.100:9040/case-discovery-treatment/set";
                                                    JSONObject jsonObject = new JSONObject();
                                                    jsonObject.put("transactor_id", current_transId);
                                                    if (finalWay != -1) {
                                                        jsonObject.put("way", finalWay);
                                                    }
                                                    jsonObject.put("other_way", finalOther_way);

                                                    jsonObject.put("symptom", finalS);
                                                    jsonObject.put("other_symptom", finalOther_symptom);
                                                    jsonObject.put("temperature", finalTemperature);

                                                    if (finalHas_con == 1) {
                                                        jsonObject.put("has_con", finalHas_con);
                                                        jsonObject.put("con", finalS1);
                                                        jsonObject.put("other_con", finalOther_con);
                                                    } else if (finalHas_con == 0) {
                                                        jsonObject.put("has_con", finalHas_con);
                                                    }

                                                    if (finalHas_ct == 1 || finalHas_ct == 0) {
                                                        jsonObject.put("has_ct", finalHas_ct);
                                                        jsonObject.put("ct_date", finalCt_date);
                                                    } else if (finalHas_ct == 2) {
                                                        jsonObject.put("has_ct", finalHas_ct);
                                                    }

                                                    jsonObject.put("admission_date", finalAdmission_date);
                                                    jsonObject.put("out_date", finalOut_date);

                                                    jsonObject.put("first_positive_date", finalFirst_date);
                                                    jsonObject.put("last_negative_date", finalLast_date);

                                                    if (finalWay1 != -1) {
                                                        jsonObject.put("status", finalWay1);
                                                    }
                                                    jsonObject.put("other_status", finalOther_status);
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
                                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                                                    StringBuffer sb = new StringBuffer();
                                                    String str;
                                                    while ((str = reader.readLine()) != null) {
                                                        sb.append(str);
                                                    }
                                                    JSONObject jsonObj1 = new JSONObject(sb.toString());
                                                    int isUpadteSeccess = jsonObj1.getInt("code");
                                                    if (isUpadteSeccess == 0) {
                                                        editor.putBoolean(current_transId + "hasBingli", true);
                                                        editor.commit();
                                                        Looper.prepare();
                                                        Toast.makeText(Bingli.this, "提交成功", Toast.LENGTH_SHORT).show();
                                                        Looper.loop();
                                                    }
                                                } catch (MalformedURLException e) {
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
                                    } else {
                                        Toast.makeText(Bingli.this, "请填写合理的体温！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                } else {
                    final int finalWay2 = way2;
                    if (isTiwen(tiwen.getText().toString())) {
                        final String finalOther_way1 = other_way;
                        final String finalS2 = s;
                        final String finalOther_symptom1 = other_symptom;
                        final String finalTemperature1 = temperature;
                        final String finalS3 = s2;
                        final String finalOther_con1 = other_con;
                        final String finalCt_date1 = ct_date;
                        final String finalAdmission_date1 = admission_date;
                        final String finalOut_date1 = out_date;
                        final String finalLast_date1 = last_date;
                        final String finalFirst_date1 = first_date;
                        final int finalWay3 = way2;
                        final int finalWay4 = way2;
                        final String finalOther_status1 = other_status;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String postUrl = "http://175.23.169.100:9040/case-discovery-treatment/set";
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("transactor_id", current_transId);
                                    if (finalWay != -1) {
                                        jsonObject.put("way", finalWay);
                                    }
                                    jsonObject.put("other_way", finalOther_way1);

                                    jsonObject.put("symptom", finalS2);
                                    jsonObject.put("other_symptom", finalOther_symptom1);
                                    jsonObject.put("temperature", finalTemperature1);

                                    if (finalHas_con == 1) {
                                        jsonObject.put("has_con", finalHas_con);
                                        jsonObject.put("con", finalS3);
                                        jsonObject.put("other_con", finalOther_con1);
                                    } else if (finalHas_con == 0) {
                                        jsonObject.put("has_con", finalHas_con);
                                    }

                                    if (finalHas_ct == 1 || finalHas_ct == 0) {
                                        jsonObject.put("has_ct", finalHas_ct);
                                        jsonObject.put("ct_date", finalCt_date1);
                                    } else if (finalHas_ct == 2) {
                                        jsonObject.put("has_ct", finalHas_ct);
                                    }

                                    jsonObject.put("admission_date", finalAdmission_date1);
                                    jsonObject.put("out_date", finalOut_date1);

                                    jsonObject.put("first_positive_date", finalFirst_date1);
                                    jsonObject.put("last_negative_date", finalLast_date1);

                                    if (finalWay4 != -1) {
                                        jsonObject.put("status", finalWay4);
                                    }
                                    jsonObject.put("other_status", finalOther_status1);

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
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                                    StringBuffer sb = new StringBuffer();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        sb.append(str);
                                    }
                                    JSONObject jsonObj1 = new JSONObject(sb.toString());
                                    int isUpadteSeccess = jsonObj1.getInt("code");
                                    if (isUpadteSeccess == 0) {
                                        editor.putBoolean(current_transId + "hasBingli", true);
                                        editor.commit();
                                        Looper.prepare();
                                        Toast.makeText(Bingli.this, "提交成功", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        onBackPressed();
                    } else {
                        Toast.makeText(Bingli.this, "请填写合理的体温！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    //检查体温合理性
    public boolean isTiwen(String tiwen) {
        int integer_tiwen;
        if (tiwen.length() == 1) {
            return false;
        } else {
            if (tiwen.length() == 2) {
                if (tiwen.indexOf(".") != -1) {
                    return false;
                } else {
                    integer_tiwen = Integer.parseInt(tiwen);
                    if (integer_tiwen <= 42 && integer_tiwen >= 35) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else if (tiwen.length() == 3) {
                return false;
            } else {
                if (tiwen.indexOf(".") != -1) {
                    if ('.' == tiwen.toCharArray()[0] || '.' == tiwen.toCharArray()[1] || '.' == tiwen.toCharArray()[3]) {
                        return false;
                    } else {
                        integer_tiwen = Integer.parseInt(tiwen.substring(0, 2));
                        if (integer_tiwen <= 42 && integer_tiwen >= 35) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
    }

    public void dataCommit() {
        SharedPreferences preferences = getSharedPreferences(current_transId + "baogao", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        zhengzhuang = new StringBuffer("");
        if (newButton1.isChecked()) {
            tujing = "集中隔离";
        } else if (newButton2.isChecked()) {
            tujing = "居家隔离";
        } else if (newButton3.isChecked()) {
            tujing = "封控区筛查";
        } else if (newButton4.isChecked()) {
            tujing = "管控区筛查";
        } else if (newButton5.isChecked()) {
            tujing = "社区筛查";
        } else if (newButton6.isChecked()) {
            tujing = "主动就诊";
        } else if (newButton7.isChecked()) {
            tujing = "主动检测";
        } else if (newButton8.isChecked()) {
            tujing = "重点人员筛查";
        } else {
            tujing = "其他途径";
        }
        if (checkBox21.isChecked()) {
            zhengzhuang.append("发热,");
        }
        if (checkBox22.isChecked()) {
            zhengzhuang.append("寒战,");
        }
        if (checkBox23.isChecked()) {
            zhengzhuang.append("干咳,");
        }
        if (checkBox24.isChecked()) {
            zhengzhuang.append("咳痰,");
        }
        if (checkBox25.isChecked()) {
            zhengzhuang.append("鼻塞,");
        }
        if (checkBox26.isChecked()) {
            zhengzhuang.append("流涕,");
        }
        if (checkBox27.isChecked()) {
            zhengzhuang.append("咽痛,");
        }
        if (checkBox28.isChecked()) {
            zhengzhuang.append("头痛,");
        }
        if (checkBox29.isChecked()) {
            zhengzhuang.append("乏力,");
        }
        if (checkBox210.isChecked()) {
            zhengzhuang.append("嗅觉减退,");
        }
        if (checkBox211.isChecked()) {
            zhengzhuang.append("味觉减退,");
        }
        if (checkBox212.isChecked()) {
            zhengzhuang.append("头晕,");
        }
        if (checkBox213.isChecked()) {
            zhengzhuang.append("肌肉酸痛,");
        }
        if (checkBox214.isChecked()) {
            zhengzhuang.append("关节酸痛,");
        }
        if (checkBox215.isChecked()) {
            zhengzhuang.append("气促,");
        }
        if (checkBox216.isChecked()) {
            zhengzhuang.append("呼吸困难,");
        }
        if (checkBox217.isChecked()) {
            zhengzhuang.append("胸闷,");
        }
        if (checkBox218.isChecked()) {
            zhengzhuang.append("胸痛,");
        }
        if (checkBox219.isChecked()) {
            zhengzhuang.append("结膜充血,");
        }
        if (checkBox220.isChecked()) {
            zhengzhuang.append("恶心,");
        }
        if (checkBox221.isChecked()) {
            zhengzhuang.append("呕吐,");
        }
        if (checkBox222.isChecked()) {
            zhengzhuang.append("腹泻,");
        }
        if (checkBox223.isChecked()) {
            zhengzhuang.append("腹痛,");
        }
        String str = zhengzhuang.toString();
        if (str.length() != 0) {
            editor.putString("zhengzhuang", str.substring(0, str.length() - 1));
        }

        editor.putString("tujing", tujing);
        //4.9
        if (newButton81.isChecked()) {
            status_baogao = "居家隔离（贴封条）";
        } else if (newButton82.isChecked()) {
            status_baogao = "集中隔离（政府指定的隔离地点）";
        } else if (newButton83.isChecked()) {
            status_baogao = "封控区居住（现住地是否在封控区域内）";
        } else if (newButton84.isChecked()) {
            status_baogao = "因新冠在医院治疗（方舱、各级定点收治医疗机构）";
        } else if (newButton85.isChecked()) {
            status_baogao = "在管控区居住（现住地是否在管控区域内）";
        } else if (newButton86.isChecked()) {
            status_baogao = "在家自行隔离（未贴封条）";
        } else if (newButton87.isChecked()) {
            status_baogao = "在工作单位自行隔离";
        } else if (newButton88.isChecked()) {
            status_baogao = "在车辆、仓库、车库、停车场等临时场所自行隔离";
        } else if (newButton89.isChecked()) {
            status_baogao = "因其他疾病在医院治疗";
        } else if (newButton810.isChecked()) {
            status_baogao = "发热门诊";
        } else if (newButton811.isChecked()) {
            status_baogao = "因疫情防控、出差等原因滞留在酒店、单位、宿舍等地居住（非集中隔离地点）";
        } else if (newButton812.isChecked()) {
            status_baogao = "正在转运中";
        } else if (newButton813.isChecked()) {
            status_baogao = "密接人员近四日核酸检测阳性";
        } else {
            status_baogao = "";
        }
        if (!lastYinxingDate.getText().toString().equals("选择日期") && lastYinxingDate.getText().toString().length() != 0
                && !lastYinxingDate.getText().toString().equals("null")) {
            String[] split = lastYinxingDate.getText().toString().split("-");
            last_baogao = Integer.parseInt(split[1]) + " 月 " + Integer.parseInt(split[2]) + " 日 ";
        } else {
            last_baogao = "";
        }

        if (!firstYangxingDate.getText().toString().equals("选择日期") && firstYangxingDate.getText().toString().length() != 0
                 && !firstYangxingDate.getText().toString().equals("null")) {
            String[] split = firstYangxingDate.getText().toString().split("-");
            first_baogao = Integer.parseInt(split[1]) + " 月 " + Integer.parseInt(split[2]) + " 日 ";
        } else {
            first_baogao = "";
        }
        editor.putString("other_symptom",otherZhengzhuang.getText().toString());
        editor.putString("status", status_baogao);
        editor.putString("other_status", otherStatus.getText().toString());
        editor.putString("tiwen", tiwen.getText().toString());
        editor.putString("first_date", first_baogao);
        editor.putString("last_date", last_baogao);
        editor.commit();

    }
}
