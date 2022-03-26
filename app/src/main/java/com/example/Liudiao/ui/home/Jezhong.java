package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;

import java.util.ArrayList;

public class Jezhong extends AppCompatActivity {

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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jiezhong);

        SharedPreferences preferences = getSharedPreferences("user_jiezhong", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        String getJiezhongtime1 = preferences.getString("jiezhong_time1","");
        int getJiezhong1 = preferences.getInt("jiezhong_danwei1",0);
        String getJiezhongtime2 = preferences.getString("jiezhong_time2","");
        int getJiezhong2 = preferences.getInt("jiezhong_danwei2",0);
        String getJiezhongtime3 = preferences.getString("jiezhong_time3","");
        int getJiezhong3 = preferences.getInt("jiezhong_danwei3",0);

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

        jiezhong_spinner1 = (Spinner) findViewById(R.id.jiezhong1_spinner);
        jiezhong_spinner2 = (Spinner) findViewById(R.id.jiezhong2_spinner);
        jiezhong_spinner3 = (Spinner) findViewById(R.id.jiezhong3_spinner);

        selectJiezhong1 = (TextView) findViewById(R.id.select_jiezhongDate1);
        selectJiezhong2 = (TextView) findViewById(R.id.select_jiezhongDate2);
        selectJiezhong3 = (TextView) findViewById(R.id.select_jiezhongDate3);



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

        selectJiezhong1.setText(getJiezhongtime1);
        jiezhong_spinner1.setSelection(getJiezhong1);
        selectJiezhong2.setText(getJiezhongtime2);
        jiezhong_spinner2.setSelection(getJiezhong2);
        selectJiezhong3.setText(getJiezhongtime3);
        jiezhong_spinner3.setSelection(getJiezhong3);

        jiezhong_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jiezhong1Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jiezhong_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jiezhong2Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        jiezhong_spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jiezhong3Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectJiezhong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Jezhong.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong1.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
            }
        });
        selectJiezhong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Jezhong.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong2.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
            }
        });
        selectJiezhong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Jezhong.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectJiezhong3.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
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
