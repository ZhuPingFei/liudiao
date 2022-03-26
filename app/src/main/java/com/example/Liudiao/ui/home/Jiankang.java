package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;

public class Jiankang extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jiankang);

        SharedPreferences preferences = getSharedPreferences("user_jiankang", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

        boolean getRadio11 = preferences.getBoolean("jiankang_radio11",Boolean.parseBoolean(""));
        boolean getRadio21 = preferences.getBoolean("jiankang_radio21",Boolean.parseBoolean(""));
        boolean getRadio31 = preferences.getBoolean("jiankang_radio31",Boolean.parseBoolean(""));
        boolean getRadio41 = preferences.getBoolean("jiankang_radio41",Boolean.parseBoolean(""));
        boolean getRadio51 = preferences.getBoolean("jiankang_radio51",Boolean.parseBoolean(""));
        boolean getRadio12 = preferences.getBoolean("jiankang_radio12", Boolean.parseBoolean(""));
        boolean getRadio22 = preferences.getBoolean("jiankang_radio22",Boolean.parseBoolean(""));
        boolean getRadio32 = preferences.getBoolean("jiankang_radio32",Boolean.parseBoolean(""));
        boolean getRadio42 = preferences.getBoolean("jiankang_radio42",Boolean.parseBoolean(""));
        boolean getRadio52 = preferences.getBoolean("jiankang_radio52",Boolean.parseBoolean(""));

        boolean getCheckbox1 = preferences.getBoolean("jiankang_checkbox1",Boolean.parseBoolean(""));
        boolean getCheckbox2 = preferences.getBoolean("jiankang_checkbox2",Boolean.parseBoolean(""));
        boolean getCheckbox3 = preferences.getBoolean("jiankang_checkbox3",Boolean.parseBoolean(""));
        boolean getCheckbox4 = preferences.getBoolean("jiankang_checkbox4",Boolean.parseBoolean(""));
        boolean getCheckbox5 = preferences.getBoolean("jiankang_checkbox5",Boolean.parseBoolean(""));
        boolean getCheckbox6 = preferences.getBoolean("jiankang_checkbox6",Boolean.parseBoolean(""));
        boolean getCheckbox7 = preferences.getBoolean("jiankang_checkbox7",Boolean.parseBoolean(""));
        boolean getCheckbox8 = preferences.getBoolean("jiankang_checkbox8",Boolean.parseBoolean(""));
        boolean getCheckbox9 = preferences.getBoolean("jiankang_checkbox9",Boolean.parseBoolean(""));
        boolean getCheckbox10 = preferences.getBoolean("jiankang_checkbox10",Boolean.parseBoolean(""));

        String getQitabushi = preferences.getString("jiankang_qitabushi","");
        String getJiuyi1 = preferences.getString("jiankang_jiuyi1","");
        String getJiuyi2 = preferences.getString("jiankang_jiuyi2","");
        String getYunzhou = preferences.getString("jiankang_yunzhou","");
        String getJibingshi = preferences.getString("jiankang_jibingshi","");

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

        checkBox1.setChecked(getCheckbox1);
        checkBox2.setChecked(getCheckbox2);
        checkBox3.setChecked(getCheckbox3);
        checkBox4.setChecked(getCheckbox4);
        checkBox5.setChecked(getCheckbox5);
        checkBox6.setChecked(getCheckbox6);
        checkBox7.setChecked(getCheckbox7);
        checkBox8.setChecked(getCheckbox8);
        checkBox9.setChecked(getCheckbox9);
        checkBox10.setChecked(getCheckbox10);

        qitabushi.setText(getQitabushi);
        jiuyi1.setText(getJiuyi1);
        jiuyi2.setText(getJiuyi2);
        yunzhou.setText(getYunzhou);
        jibingshi.setText(getJibingshi);

        if (getRadio11 == false){
            radioButton12.setChecked(true);
            visible1.setVisibility(View.GONE);
        }else {
            radioButton11.setChecked(true);
            visible1.setVisibility(View.VISIBLE);
        }
        if (getRadio21 == false){
            radioButton22.setChecked(true);
            visible2.setVisibility(View.GONE);
            visible5.setVisibility(View.GONE);
        }else {
            radioButton21.setChecked(true);
            visible2.setVisibility(View.VISIBLE);
            visible5.setVisibility(View.VISIBLE);
        }
        if (getRadio31 == false){
            radioButton32.setChecked(true);
            visible3.setVisibility(View.GONE);
        }else {
            radioButton31.setChecked(true);
            visible3.setVisibility(View.VISIBLE);
        }
        if (getRadio41 == false){
            radioButton42.setChecked(true);
            visible4.setVisibility(View.GONE);
        }else {
            radioButton41.setChecked(true);
            visible4.setVisibility(View.VISIBLE);
        }
        if (getRadio51 == false){
            radioButton52.setChecked(true);
        }else {
            radioButton51.setChecked(true);
        }
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
                dataCommit();
                onBackPressed();
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
