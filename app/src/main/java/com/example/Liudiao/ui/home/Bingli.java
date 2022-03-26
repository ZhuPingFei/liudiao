package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;

public class Bingli extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_bingli);


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
                dataCommit();
                onBackPressed();
            }
        });

        SharedPreferences preferences = getSharedPreferences("user_bingli", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

        boolean getCheckbox11 = preferences.getBoolean("bingli_checkbox11",Boolean.parseBoolean(""));
        boolean getCheckbox12 = preferences.getBoolean("bingli_checkbox12",Boolean.parseBoolean(""));
        boolean getCheckbox13 = preferences.getBoolean("bingli_checkbox13",Boolean.parseBoolean(""));
        boolean getCheckbox14 = preferences.getBoolean("bingli_checkbox14",Boolean.parseBoolean(""));
        boolean getCheckbox15 = preferences.getBoolean("bingli_checkbox15",Boolean.parseBoolean(""));
        boolean getCheckbox21 = preferences.getBoolean("bingli_checkbox21",Boolean.parseBoolean(""));
        boolean getCheckbox22 = preferences.getBoolean("bingli_checkbox22",Boolean.parseBoolean(""));
        boolean getCheckbox23 = preferences.getBoolean("bingli_checkbox23",Boolean.parseBoolean(""));
        boolean getCheckbox24 = preferences.getBoolean("bingli_checkbox24",Boolean.parseBoolean(""));
        boolean getCheckbox25 = preferences.getBoolean("bingli_checkbox25",Boolean.parseBoolean(""));
        boolean getCheckbox26 = preferences.getBoolean("bingli_checkbox26",Boolean.parseBoolean(""));
        boolean getCheckbox27 = preferences.getBoolean("bingli_checkbox27",Boolean.parseBoolean(""));
        boolean getCheckbox28 = preferences.getBoolean("bingli_checkbox28",Boolean.parseBoolean(""));
        boolean getCheckbox29 = preferences.getBoolean("bingli_checkbox29",Boolean.parseBoolean(""));
        boolean getCheckbox210 = preferences.getBoolean("bingli_checkbox210",Boolean.parseBoolean(""));
        boolean getCheckbox211 = preferences.getBoolean("bingli_checkbox211",Boolean.parseBoolean(""));
        boolean getCheckbox212 = preferences.getBoolean("bingli_checkbox212",Boolean.parseBoolean(""));
        boolean getCheckbox213 = preferences.getBoolean("bingli_checkbox213",Boolean.parseBoolean(""));
        boolean getCheckbox214 = preferences.getBoolean("bingli_checkbox214",Boolean.parseBoolean(""));
        boolean getCheckbox215 = preferences.getBoolean("bingli_checkbox215",Boolean.parseBoolean(""));
        boolean getCheckbox216 = preferences.getBoolean("bingli_checkbox216",Boolean.parseBoolean(""));
        boolean getCheckbox217 = preferences.getBoolean("bingli_checkbox217",Boolean.parseBoolean(""));
        boolean getCheckbox218 = preferences.getBoolean("bingli_checkbox218",Boolean.parseBoolean(""));
        boolean getCheckbox219 = preferences.getBoolean("bingli_checkbox219",Boolean.parseBoolean(""));
        boolean getCheckbox220 = preferences.getBoolean("bingli_checkbox220",Boolean.parseBoolean(""));
        boolean getCheckbox221 = preferences.getBoolean("bingli_checkbox221",Boolean.parseBoolean(""));
        boolean getCheckbox222 = preferences.getBoolean("bingli_checkbox222",Boolean.parseBoolean(""));
        boolean getCheckbox223 = preferences.getBoolean("bingli_checkbox223",Boolean.parseBoolean(""));
        boolean getCheckbox31 = preferences.getBoolean("bingli_checkbox31",Boolean.parseBoolean(""));
        boolean getCheckbox32 = preferences.getBoolean("bingli_checkbox32",Boolean.parseBoolean(""));
        boolean getCheckbox33 = preferences.getBoolean("bingli_checkbox33",Boolean.parseBoolean(""));
        boolean getCheckbox34 = preferences.getBoolean("bingli_checkbox34",Boolean.parseBoolean(""));
        boolean getCheckbox35 = preferences.getBoolean("bingli_checkbox35",Boolean.parseBoolean(""));
        boolean getCheckbox36 = preferences.getBoolean("bingli_checkbox36",Boolean.parseBoolean(""));
        boolean getCheckbox37 = preferences.getBoolean("bingli_checkbox37",Boolean.parseBoolean(""));
        boolean getCheckbox38 = preferences.getBoolean("bingli_checkbox38",Boolean.parseBoolean(""));
        boolean getCheckbox39 = preferences.getBoolean("bingli_checkbox39",Boolean.parseBoolean(""));

        String getOthertujing = preferences.getString("bingli_otherTujing","");
        String getOtherzhengzhuang = preferences.getString("bingli_otherZhengzhuang","");
        String getOtherbingfazheng = preferences.getString("bingli_otherBingfazheng","");
        String getCtdate = preferences.getString("bingli_ctDate","");
        String getChuyuanDate = preferences.getString("bingli_chuyuanDate","");

        Boolean getHasbingfazheng = preferences.getBoolean("bingli_hasBingfazheng", Boolean.parseBoolean(""));
        Boolean getNobingfazheng = preferences.getBoolean("bingli_noBingfazheng", Boolean.parseBoolean(""));

        Boolean getHasct = preferences.getBoolean("bingli_hasCt",Boolean.parseBoolean(""));
        Boolean getNoct = preferences.getBoolean("bingli_noCt",Boolean.parseBoolean(""));
        Boolean getNeverct = preferences.getBoolean("bingli_neverCt",Boolean.parseBoolean(""));



        checkBox11.setChecked(getCheckbox11);
        checkBox12.setChecked(getCheckbox12);
        checkBox13.setChecked(getCheckbox13);
        checkBox14.setChecked(getCheckbox14);
        checkBox15.setChecked(getCheckbox15);
        checkBox21.setChecked(getCheckbox21);
        checkBox22.setChecked(getCheckbox22);
        checkBox23.setChecked(getCheckbox23);
        checkBox24.setChecked(getCheckbox24);
        checkBox25.setChecked(getCheckbox25);
        checkBox26.setChecked(getCheckbox26);
        checkBox27.setChecked(getCheckbox27);
        checkBox28.setChecked(getCheckbox28);
        checkBox29.setChecked(getCheckbox29);
        checkBox210.setChecked(getCheckbox210);
        checkBox211.setChecked(getCheckbox211);
        checkBox212.setChecked(getCheckbox212);
        checkBox213.setChecked(getCheckbox213);
        checkBox214.setChecked(getCheckbox214);
        checkBox215.setChecked(getCheckbox215);
        checkBox216.setChecked(getCheckbox216);
        checkBox217.setChecked(getCheckbox217);
        checkBox218.setChecked(getCheckbox218);
        checkBox219.setChecked(getCheckbox219);
        checkBox220.setChecked(getCheckbox220);
        checkBox221.setChecked(getCheckbox221);
        checkBox222.setChecked(getCheckbox222);
        checkBox223.setChecked(getCheckbox223);
        checkBox31.setChecked(getCheckbox31);
        checkBox32.setChecked(getCheckbox32);
        checkBox33.setChecked(getCheckbox33);
        checkBox34.setChecked(getCheckbox34);
        checkBox35.setChecked(getCheckbox35);
        checkBox36.setChecked(getCheckbox36);
        checkBox37.setChecked(getCheckbox37);
        checkBox38.setChecked(getCheckbox38);
        checkBox39.setChecked(getCheckbox39);

        otherTujing.setText(getOthertujing);
        otherZhengzhuang.setText(getOtherzhengzhuang);
        otherBingfazheng.setText(getOtherbingfazheng);
        ct_date_select.setText(getCtdate);
        chuyuanDate.setText(getChuyuanDate);

        if (getHasbingfazheng == false){
            radioButton2.setChecked(true);
            bingfazheng.setVisibility(View.GONE);
        }else {
            radioButton1.setChecked(true);
            bingfazheng.setVisibility(View.VISIBLE);
        }

        if (getNeverct == false){
            ct_date.setVisibility(View.VISIBLE);
            if (getHasct == false){
                ct_radioButton2.setChecked(true);
            }else {
                ct_radioButton1.setChecked(true);
            }
        }else {
            ct_radioButton3.setChecked(true);
            ct_date.setVisibility(View.GONE);
        }

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
                //showDateDialog();
                new DatePickerDialog(Bingli.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ct_date_select.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
            }
        });

        chuyuanDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDateDialog();
                new DatePickerDialog(Bingli.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chuyuanDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
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
