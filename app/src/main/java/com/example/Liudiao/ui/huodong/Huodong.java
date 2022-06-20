package com.example.Liudiao.ui.huodong;

import static com.example.Liudiao.Welcome.isConnect;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.GetJsonDataUtil;
import com.example.Liudiao.ui.home.Jezhong;
import com.example.Liudiao.ui.xingcheng.HuodongMap;
import com.example.Liudiao.ui.xingcheng.Xingcheng;
import com.google.gson.Gson;

import org.json.JSONArray;
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

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

public class Huodong extends AppCompatActivity {
    private int isempty=1;//1空2不空
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private boolean isMe;

    private ImageView r_back;
    private TextView okey;

    private TextView place;
    private EditText detailPlace;

    private TextView start_date;
    private TextView startTime;
    private TextView endTime;
    private TextView end_date;

    private EditText danwei;

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区

    private DatePicker dp = null;
    private Calendar calendar = null;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    private EditText edit;

    List<Yiyouhuodong> huodongyiyou = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingcheng_huodong);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);

//        preferences = getSharedPreferences("user_huodong", Activity.MODE_PRIVATE);
//        editor = preferences.edit();//获取编辑器
//        String getPlace = preferences.getString("huodong_place","");
//        String getDetailplace = preferences.getString("huodong_detailPlace","");
//        String getDate = preferences.getString("huodong_date","");
//        String getStartTime = preferences.getString("huodong_startTime","");
//        String getEndTime = preferences.getString("huodong_endTime","");
//        String getDanwei = preferences.getString("huodong_danwei","");
//        String getEdit = preferences.getString("huodong_edit","");

        place = (TextView)findViewById(R.id.xingcheng_qidian_select_place);
        detailPlace = (EditText) findViewById(R.id.xingcheng_qidianDetail_edit);
        start_date = (TextView)findViewById(R.id.huodong_date);
        end_date = (TextView)findViewById(R.id.end_date);
        startTime = (TextView)findViewById(R.id.huodong_startTime);
        endTime = (TextView)findViewById(R.id.huodong_endTime);
        danwei = (EditText) findViewById(R.id.huodongdanwei_edit);
        edit = (EditText)findViewById(R.id.huodong_Detail_edit);

//        place.setText(getPlace);
//        detailPlace.setText(getDetailplace);
//        date.setText(getDate);
//        startTime.setText(getStartTime);
//        endTime.setText(getEndTime);
//        danwei.setText(getDanwei);
//        edit.setText(getEdit);



        initJsonData();
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
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



        //getdata
        huodongyiyou = getdata();




        int cansub = 1;//1能交2不能交3日期本身前后有问题
        if(huodongyiyou.size()>0){
            isempty=2;
        }






        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDateDialog();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Huodong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        start_date.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                        final StringBuffer stringBuffer2 = new StringBuffer(start_date.getText().toString());
                        if (start_date.getText().toString().length() == 8) {
                            stringBuffer2.insert(5, "0");
                            stringBuffer2.insert(8, "0");
                        } else if (start_date.getText().toString().length() == 9) {
                            String[] split = start_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer2.insert(8, "0");
                            } else {
                                stringBuffer2.insert(5, "0");
                            }
                        }
                        final StringBuffer stringBuffer3 = new StringBuffer(end_date.getText().toString());
                        if (end_date.getText().toString().length() == 8) {
                            stringBuffer3.insert(5, "0");
                            stringBuffer3.insert(8, "0");
                        } else if (end_date.getText().toString().length() == 9) {
                            String[] split = end_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer3.insert(8, "0");
                            } else {
                                stringBuffer3.insert(5, "0");
                            }
                        }

                        String acti_detail = edit.getText().toString();
                        if (acti_detail.equals("")) {
                            acti_detail = null;
                        }

                        final StringBuffer stringBuffer = new StringBuffer(startTime.getText().toString());
                        if (startTime.getText().toString().length() == 3) {
                            stringBuffer.insert(0, "0");
                            stringBuffer.insert(3, "0");
                        } else if (startTime.getText().toString().length() == 4) {
                            String[] split = startTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer.insert(3, "0");
                            } else {
                                stringBuffer.insert(0, "0");
                            }
                        }

                        final StringBuffer stringBuffer1 = new StringBuffer(endTime.getText().toString());
                        if (endTime.getText().toString().length() == 3) {
                            stringBuffer1.insert(0, "0");
                            stringBuffer1.insert(3, "0");
                        } else if (endTime.getText().toString().length() == 4) {
                            String[] split = endTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer1.insert(3, "0");
                            } else {
                                stringBuffer1.insert(0, "0");
                            }
                        }
                        if (place.getText().toString().length()!=0 && detailPlace.getText().toString().length()!=0 &&stringBuffer2.toString().length()!=0 && stringBuffer3.toString().length()!=0
                                && startTime.getText().toString().length()!=0 && endTime.getText().toString().length()!=0) {



                            //闭环逻辑，判断是否能交
                            //先把写入的时间化成double。
                            double substarty = Double.valueOf(stringBuffer2.toString().split("-")[0]);
                            double substartmon = Double.valueOf(stringBuffer2.toString().split("-")[1]);
                            double substartd = Double.valueOf(stringBuffer2.toString().split("-")[2]);
                            double substarth = Double.valueOf(stringBuffer.toString().split(":")[0]);
                            double substartmin = Double.valueOf(stringBuffer.toString().split(":")[1]);
                            double subendy = Double.valueOf(stringBuffer3.toString().split("-")[0]);
                            double subendmon = Double.valueOf(stringBuffer3.toString().split("-")[1]);
                            double subendd = Double.valueOf(stringBuffer3.toString().split("-")[2]);
                            double subendh = Double.valueOf(stringBuffer1.toString().split(":")[0]);
                            double subendmin = Double.valueOf(stringBuffer1.toString().split(":")[1]);


                            final double substart = (((substarty * 100 + substartmon) * 100 + substartd) * 100 + substarth) * 100 + substartmin;
                            final double subend = (((subendy * 100 + subendmon) * 100 + subendd) * 100 + subendh) * 100 + subendmin;
                            int cansub = 1;//1能交2不能交3日期本身前后有问题
                            if(huodongyiyou.size()>0){
                                isempty=2;
                            }


                            if(isempty != 1){
                                for (int i = 0; i < huodongyiyou.size(); i++) {
                                    if(huodongyiyou.get(i).addok(substart,subend)==false){
                                        cansub = 2;
                                    }
                                }
                            }
                            if(substart>subend){
                                cansub = 3;
                            }


                            if (cansub == 1){
                                start_date.setTextColor(-1979711488);
                                startTime.setTextColor(-1979711488);
                                end_date.setTextColor(-1979711488);
                                endTime.setTextColor(-1979711488);
                            }else if (cansub == 2){
                                Toast.makeText(Huodong.this, "提交活动起止时间与已有活动起止时间存在冲突，请修改时间", Toast.LENGTH_SHORT).show();
                                start_date.setTextColor(Color.rgb(255, 0, 0));
                                startTime.setTextColor(Color.rgb(255, 0, 0));
                            }else if (cansub == 3){
                                Toast.makeText(Huodong.this, "起始时间在结束时间之后，请修改时间", Toast.LENGTH_SHORT).show();
                                start_date.setTextColor(Color.rgb(255, 0, 0));
                                startTime.setTextColor(Color.rgb(255, 0, 0));
                            }
                        }

                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDateDialog();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Huodong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        end_date.setText(year + "-" + (month+1) + "-" + dayOfMonth);

                        final StringBuffer stringBuffer2 = new StringBuffer(start_date.getText().toString());
                        if (start_date.getText().toString().length() == 8) {
                            stringBuffer2.insert(5, "0");
                            stringBuffer2.insert(8, "0");
                        } else if (start_date.getText().toString().length() == 9) {
                            String[] split = start_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer2.insert(8, "0");
                            } else {
                                stringBuffer2.insert(5, "0");
                            }
                        }
                        final StringBuffer stringBuffer3 = new StringBuffer(end_date.getText().toString());
                        if (end_date.getText().toString().length() == 8) {
                            stringBuffer3.insert(5, "0");
                            stringBuffer3.insert(8, "0");
                        } else if (end_date.getText().toString().length() == 9) {
                            String[] split = end_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer3.insert(8, "0");
                            } else {
                                stringBuffer3.insert(5, "0");
                            }
                        }

                        String acti_detail = edit.getText().toString();
                        if (acti_detail.equals("")) {
                            acti_detail = null;
                        }

                        final StringBuffer stringBuffer = new StringBuffer(startTime.getText().toString());
                        if (startTime.getText().toString().length() == 3) {
                            stringBuffer.insert(0, "0");
                            stringBuffer.insert(3, "0");
                        } else if (startTime.getText().toString().length() == 4) {
                            String[] split = startTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer.insert(3, "0");
                            } else {
                                stringBuffer.insert(0, "0");
                            }
                        }

                        final StringBuffer stringBuffer1 = new StringBuffer(endTime.getText().toString());
                        if (endTime.getText().toString().length() == 3) {
                            stringBuffer1.insert(0, "0");
                            stringBuffer1.insert(3, "0");
                        } else if (endTime.getText().toString().length() == 4) {
                            String[] split = endTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer1.insert(3, "0");
                            } else {
                                stringBuffer1.insert(0, "0");
                            }
                        }
                        if (place.getText().toString().length()!=0 && detailPlace.getText().toString().length()!=0 &&stringBuffer2.toString().length()!=0 && stringBuffer3.toString().length()!=0
                                && startTime.getText().toString().length()!=0 && endTime.getText().toString().length()!=0) {



                            //闭环逻辑，判断是否能交
                            //先把写入的时间化成double。
                            double substarty = Double.valueOf(stringBuffer2.toString().split("-")[0]);
                            double substartmon = Double.valueOf(stringBuffer2.toString().split("-")[1]);
                            double substartd = Double.valueOf(stringBuffer2.toString().split("-")[2]);
                            double substarth = Double.valueOf(stringBuffer.toString().split(":")[0]);
                            double substartmin = Double.valueOf(stringBuffer.toString().split(":")[1]);
                            double subendy = Double.valueOf(stringBuffer3.toString().split("-")[0]);
                            double subendmon = Double.valueOf(stringBuffer3.toString().split("-")[1]);
                            double subendd = Double.valueOf(stringBuffer3.toString().split("-")[2]);
                            double subendh = Double.valueOf(stringBuffer1.toString().split(":")[0]);
                            double subendmin = Double.valueOf(stringBuffer1.toString().split(":")[1]);


                            final double substart = (((substarty * 100 + substartmon) * 100 + substartd) * 100 + substarth) * 100 + substartmin;
                            final double subend = (((subendy * 100 + subendmon) * 100 + subendd) * 100 + subendh) * 100 + subendmin;
                            int cansub = 1;//1能交2不能交3日期本身前后有问题
                            if(huodongyiyou.size()>0){
                                isempty=2;
                            }


                            if(isempty != 1){
                                for (int i = 0; i < huodongyiyou.size(); i++) {
                                    if(huodongyiyou.get(i).addok(substart,subend)==false){
                                        cansub = 2;
                                    }
                                }
                            }
                            if(substart>subend){
                                cansub = 3;
                            }


                            if (cansub == 1){
                                start_date.setTextColor(-1979711488);
                                startTime.setTextColor(-1979711488);
                                end_date.setTextColor(-1979711488);
                                endTime.setTextColor(-1979711488);
                            }else if (cansub == 2){
                                Toast.makeText(Huodong.this, "提交活动起止时间与已有活动起止时间存在冲突，请修改时间", Toast.LENGTH_SHORT).show();
                                end_date.setTextColor(Color.rgb(255, 0, 0));
                                endTime.setTextColor(Color.rgb(255, 0, 0));
                            }else if (cansub == 3){
                                Toast.makeText(Huodong.this, "起始时间在结束时间之后，请修改时间", Toast.LENGTH_SHORT).show();
                                end_date.setTextColor(Color.rgb(255, 0, 0));
                                endTime.setTextColor(Color.rgb(255, 0, 0));
                            }
                        }


                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Huodong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(hourOfDay + ":" + minute);
                        final StringBuffer stringBuffer2 = new StringBuffer(start_date.getText().toString());
                        if (start_date.getText().toString().length() == 8) {
                            stringBuffer2.insert(5, "0");
                            stringBuffer2.insert(8, "0");
                        } else if (start_date.getText().toString().length() == 9) {
                            String[] split = start_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer2.insert(8, "0");
                            } else {
                                stringBuffer2.insert(5, "0");
                            }
                        }
                        final StringBuffer stringBuffer3 = new StringBuffer(end_date.getText().toString());
                        if (end_date.getText().toString().length() == 8) {
                            stringBuffer3.insert(5, "0");
                            stringBuffer3.insert(8, "0");
                        } else if (end_date.getText().toString().length() == 9) {
                            String[] split = end_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer3.insert(8, "0");
                            } else {
                                stringBuffer3.insert(5, "0");
                            }
                        }

                        String acti_detail = edit.getText().toString();
                        if (acti_detail.equals("")) {
                            acti_detail = null;
                        }

                        final StringBuffer stringBuffer = new StringBuffer(startTime.getText().toString());
                        if (startTime.getText().toString().length() == 3) {
                            stringBuffer.insert(0, "0");
                            stringBuffer.insert(3, "0");
                        } else if (startTime.getText().toString().length() == 4) {
                            String[] split = startTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer.insert(3, "0");
                            } else {
                                stringBuffer.insert(0, "0");
                            }
                        }

                        final StringBuffer stringBuffer1 = new StringBuffer(endTime.getText().toString());
                        if (endTime.getText().toString().length() == 3) {
                            stringBuffer1.insert(0, "0");
                            stringBuffer1.insert(3, "0");
                        } else if (endTime.getText().toString().length() == 4) {
                            String[] split = endTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer1.insert(3, "0");
                            } else {
                                stringBuffer1.insert(0, "0");
                            }
                        }
                        if (place.getText().toString().length()!=0 && detailPlace.getText().toString().length()!=0 &&stringBuffer2.toString().length()!=0 && stringBuffer3.toString().length()!=0
                                && startTime.getText().toString().length()!=0 && endTime.getText().toString().length()!=0) {



                            //闭环逻辑，判断是否能交
                            //先把写入的时间化成double。
                            double substarty = Double.valueOf(stringBuffer2.toString().split("-")[0]);
                            double substartmon = Double.valueOf(stringBuffer2.toString().split("-")[1]);
                            double substartd = Double.valueOf(stringBuffer2.toString().split("-")[2]);
                            double substarth = Double.valueOf(stringBuffer.toString().split(":")[0]);
                            double substartmin = Double.valueOf(stringBuffer.toString().split(":")[1]);
                            double subendy = Double.valueOf(stringBuffer3.toString().split("-")[0]);
                            double subendmon = Double.valueOf(stringBuffer3.toString().split("-")[1]);
                            double subendd = Double.valueOf(stringBuffer3.toString().split("-")[2]);
                            double subendh = Double.valueOf(stringBuffer1.toString().split(":")[0]);
                            double subendmin = Double.valueOf(stringBuffer1.toString().split(":")[1]);


                            final double substart = (((substarty * 100 + substartmon) * 100 + substartd) * 100 + substarth) * 100 + substartmin;
                            final double subend = (((subendy * 100 + subendmon) * 100 + subendd) * 100 + subendh) * 100 + subendmin;
                            int cansub = 1;//1能交2不能交3日期本身前后有问题
                            if(huodongyiyou.size()>0){
                                isempty=2;
                            }


                            if(isempty != 1){
                                for (int i = 0; i < huodongyiyou.size(); i++) {
                                    if(huodongyiyou.get(i).addok(substart,subend)==false){
                                        cansub = 2;
                                    }
                                }
                            }
                            if(substart>subend){
                                cansub = 3;
                            }


                            if (cansub == 1){
                                start_date.setTextColor(-1979711488);
                                startTime.setTextColor(-1979711488);
                                end_date.setTextColor(-1979711488);
                                endTime.setTextColor(-1979711488);
                            }else if (cansub == 2){
                                Toast.makeText(Huodong.this, "提交活动起止时间与已有活动起止时间存在冲突，请修改时间", Toast.LENGTH_SHORT).show();
                                start_date.setTextColor(Color.rgb(255, 0, 0));
                                startTime.setTextColor(Color.rgb(255, 0, 0));
                            }else if (cansub == 3){
                                Toast.makeText(Huodong.this, "起始时间在结束时间之后，请修改时间", Toast.LENGTH_SHORT).show();
                                start_date.setTextColor(Color.rgb(255, 0, 0));
                                startTime.setTextColor(Color.rgb(255, 0, 0));
                            }
                        }

                    }
                }, hour, min, true).show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Huodong.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(hourOfDay + ":" + minute);
                        final StringBuffer stringBuffer2 = new StringBuffer(start_date.getText().toString());
                        if (start_date.getText().toString().length() == 8) {
                            stringBuffer2.insert(5, "0");
                            stringBuffer2.insert(8, "0");
                        } else if (start_date.getText().toString().length() == 9) {
                            String[] split = start_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer2.insert(8, "0");
                            } else {
                                stringBuffer2.insert(5, "0");
                            }
                        }
                        final StringBuffer stringBuffer3 = new StringBuffer(end_date.getText().toString());
                        if (end_date.getText().toString().length() == 8) {
                            stringBuffer3.insert(5, "0");
                            stringBuffer3.insert(8, "0");
                        } else if (end_date.getText().toString().length() == 9) {
                            String[] split = end_date.getText().toString().split("-");
                            if (split[1].length() == 2) {
                                stringBuffer3.insert(8, "0");
                            } else {
                                stringBuffer3.insert(5, "0");
                            }
                        }

                        String acti_detail = edit.getText().toString();
                        if (acti_detail.equals("")) {
                            acti_detail = null;
                        }

                        final StringBuffer stringBuffer = new StringBuffer(startTime.getText().toString());
                        if (startTime.getText().toString().length() == 3) {
                            stringBuffer.insert(0, "0");
                            stringBuffer.insert(3, "0");
                        } else if (startTime.getText().toString().length() == 4) {
                            String[] split = startTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer.insert(3, "0");
                            } else {
                                stringBuffer.insert(0, "0");
                            }
                        }

                        final StringBuffer stringBuffer1 = new StringBuffer(endTime.getText().toString());
                        if (endTime.getText().toString().length() == 3) {
                            stringBuffer1.insert(0, "0");
                            stringBuffer1.insert(3, "0");
                        } else if (endTime.getText().toString().length() == 4) {
                            String[] split = endTime.getText().toString().split(":");
                            if (split[1].length() == 1) {
                                stringBuffer1.insert(3, "0");
                            } else {
                                stringBuffer1.insert(0, "0");
                            }
                        }
                        if (place.getText().toString().length()!=0 && detailPlace.getText().toString().length()!=0 &&stringBuffer2.toString().length()!=0 && stringBuffer3.toString().length()!=0
                                && startTime.getText().toString().length()!=0 && endTime.getText().toString().length()!=0) {



                            //闭环逻辑，判断是否能交
                            //先把写入的时间化成double。
                            double substarty = Double.valueOf(stringBuffer2.toString().split("-")[0]);
                            double substartmon = Double.valueOf(stringBuffer2.toString().split("-")[1]);
                            double substartd = Double.valueOf(stringBuffer2.toString().split("-")[2]);
                            double substarth = Double.valueOf(stringBuffer.toString().split(":")[0]);
                            double substartmin = Double.valueOf(stringBuffer.toString().split(":")[1]);
                            double subendy = Double.valueOf(stringBuffer3.toString().split("-")[0]);
                            double subendmon = Double.valueOf(stringBuffer3.toString().split("-")[1]);
                            double subendd = Double.valueOf(stringBuffer3.toString().split("-")[2]);
                            double subendh = Double.valueOf(stringBuffer1.toString().split(":")[0]);
                            double subendmin = Double.valueOf(stringBuffer1.toString().split(":")[1]);


                            final double substart = (((substarty * 100 + substartmon) * 100 + substartd) * 100 + substarth) * 100 + substartmin;
                            final double subend = (((subendy * 100 + subendmon) * 100 + subendd) * 100 + subendh) * 100 + subendmin;
                            int cansub = 1;//1能交2不能交3日期本身前后有问题
                            if(huodongyiyou.size()>0){
                                isempty=2;
                            }


                            if(isempty != 1){
                                for (int i = 0; i < huodongyiyou.size(); i++) {
                                    if(huodongyiyou.get(i).addok(substart,subend)==false){
                                        cansub = 2;
                                    }
                                }
                            }
                            if(substart>subend){
                                cansub = 3;
                            }


                            if (cansub == 1){
                                start_date.setTextColor(-1979711488);
                                startTime.setTextColor(-1979711488);
                                end_date.setTextColor(-1979711488);
                                endTime.setTextColor(-1979711488);
                            }else if (cansub == 2){
                                Toast.makeText(Huodong.this, "提交活动起止时间与已有活动起止时间存在冲突，请修改时间", Toast.LENGTH_SHORT).show();
                                end_date.setTextColor(Color.rgb(255, 0, 0));
                                endTime.setTextColor(Color.rgb(255, 0, 0));
                            }else if (cansub == 3){
                                Toast.makeText(Huodong.this, "起始时间在结束时间之后，请修改时间", Toast.LENGTH_SHORT).show();
                                end_date.setTextColor(Color.rgb(255, 0, 0));
                                endTime.setTextColor(Color.rgb(255, 0, 0));
                            }




                        }


                    }
                }, hour, min, true).show();
            }
        });


        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        okey = (TextView) findViewById(R.id.okay);








        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuffer stringBuffer2 = new StringBuffer(start_date.getText().toString());
                if (start_date.getText().toString().length()==8){
                    stringBuffer2.insert(5,"0");
                    stringBuffer2.insert(8,"0");
                }else if (start_date.getText().toString().length()==9){
                    String[] split = start_date.getText().toString().split("-");
                    if (split[1].length() == 2){
                        stringBuffer2.insert(8,"0");
                    }else {
                        stringBuffer2.insert(5,"0");
                    }
                }
                final StringBuffer stringBuffer3 = new StringBuffer(end_date.getText().toString());
                if (end_date.getText().toString().length()==8){
                    stringBuffer3.insert(5,"0");
                    stringBuffer3.insert(8,"0");
                }else if (end_date.getText().toString().length()==9){
                    String[] split = end_date.getText().toString().split("-");
                    if (split[1].length() == 2){
                        stringBuffer3.insert(8,"0");
                    }else {
                        stringBuffer3.insert(5,"0");
                    }
                }

                String acti_detail = edit.getText().toString();
                if (acti_detail.equals("")){
                    acti_detail = null;
                }

                final StringBuffer stringBuffer = new StringBuffer(startTime.getText().toString());
                if (startTime.getText().toString().length()==3){
                    stringBuffer.insert(0,"0");
                    stringBuffer.insert(3,"0");
                }else if (startTime.getText().toString().length()==4){
                    String[] split = startTime.getText().toString().split(":");
                    if (split[1].length() == 1){
                        stringBuffer.insert(3,"0");
                    }else {
                        stringBuffer.insert(0,"0");
                    }
                }

                final StringBuffer stringBuffer1 = new StringBuffer(endTime.getText().toString());
                if (endTime.getText().toString().length()==3){
                    stringBuffer1.insert(0,"0");
                    stringBuffer1.insert(3,"0");
                }else if (endTime.getText().toString().length()==4){
                    String[] split = endTime.getText().toString().split(":");
                    if (split[1].length() == 1){
                        stringBuffer1.insert(3,"0");
                    }else {
                        stringBuffer1.insert(0,"0");
                    }
                }

                if (place.getText().toString().length()!=0 && detailPlace.getText().toString().length()!=0 &&stringBuffer2.toString().length()!=0 && stringBuffer3.toString().length()!=0
                && startTime.getText().toString().length()!=0 && endTime.getText().toString().length()!=0) {






                    //闭环逻辑，判断是否能交
                    //先把写入的时间化成double。
                    double substarty = Double.valueOf(stringBuffer2.toString().split("-")[0]);
                    double substartmon = Double.valueOf(stringBuffer2.toString().split("-")[1]);
                    double substartd = Double.valueOf(stringBuffer2.toString().split("-")[2]);
                    double substarth = Double.valueOf(stringBuffer.toString().split(":")[0]);
                    double substartmin = Double.valueOf(stringBuffer.toString().split(":")[1]);
                    double subendy = Double.valueOf(stringBuffer3.toString().split("-")[0]);
                    double subendmon = Double.valueOf(stringBuffer3.toString().split("-")[1]);
                    double subendd = Double.valueOf(stringBuffer3.toString().split("-")[2]);
                    double subendh = Double.valueOf(stringBuffer1.toString().split(":")[0]);
                    double subendmin = Double.valueOf(stringBuffer1.toString().split(":")[1]);



                    final double substart = (((substarty*100+substartmon)*100+substartd)*100+substarth)*100+substartmin;
                    final double subend = (((subendy*100+subendmon)*100+subendd)*100+subendh)*100+subendmin;

                    int cansub = 1;//1能交2不能交3日期本身前后有问题
                    if(huodongyiyou.size()>0){
                        isempty=2;
                    }


                    if(isempty != 1){
                        for (int i = 0; i < huodongyiyou.size(); i++) {
                            if(huodongyiyou.get(i).addok(substart,subend)==false){
                                cansub = 2;
                            }
                        }
                    }
                    if(substart>subend){
                        cansub = 3;
                    }




                    if (cansub == 2) {
                        Toast.makeText(Huodong.this, "提交活动起止时间与已有活动起止时间存在冲突，请修改时间", Toast.LENGTH_LONG).show();
                    } else if(cansub == 3){
                        Toast.makeText(Huodong.this, "起始时间在结束时间之后，请修改时间", Toast.LENGTH_LONG).show();
                    }else {
                            if (!isMe) {
                                final String finalActi_detail1 = acti_detail;
                                builder = new AlertDialog.Builder(Huodong.this).setTitle("重要提醒")
                                        .setMessage("当前为代办模式，是否确认提交？")
                                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //ToDo: 你想做的事情
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            String postUrl = "http://175.23.169.100:9030/case-aggregated-activity/add";
                                                            JSONObject jsonObject = new JSONObject();
                                                            jsonObject.put("transactor_id", current_transId);
                                                            jsonObject.put("acti_end_date", stringBuffer3.toString());
                                                            jsonObject.put("acti_place", place.getText().toString());
                                                            jsonObject.put("actipla_detail", detailPlace.getText().toString());
                                                            jsonObject.put("acti_start_date", stringBuffer2.toString());
                                                            jsonObject.put("acti_starttime", stringBuffer.toString());
                                                            jsonObject.put("acti_endtime", stringBuffer1.toString());
                                                            jsonObject.put("acti_unit", danwei.getText().toString());
                                                            jsonObject.put("acti_detail", finalActi_detail1);

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
                                                                editor.putBoolean(current_transId + "hasHuodong", true);
                                                                editor.commit();
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(Huodong.this, "提交成功", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
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
                                                onBackPressed();
                                            }
                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.create().show();

                            } else {
                                final String finalActi_detail2 = acti_detail;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String postUrl = "http://175.23.169.100:9030/case-aggregated-activity/add";
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("transactor_id", current_transId);
                                            jsonObject.put("acti_place", place.getText().toString());
                                            jsonObject.put("acti_end_date", stringBuffer3.toString());
                                            jsonObject.put("actipla_detail", detailPlace.getText().toString());
                                            jsonObject.put("acti_start_date", stringBuffer2.toString());
                                            jsonObject.put("acti_starttime", stringBuffer.toString());
                                            jsonObject.put("acti_endtime", stringBuffer1.toString());
                                            jsonObject.put("acti_unit", danwei.getText().toString());
                                            jsonObject.put("acti_detail", finalActi_detail2);

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
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Huodong.this, "提交成功", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
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
                            }
                        }
                    }else {
                        Toast.makeText(Huodong.this,"请录入完整信息再提交！",Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

    private void showPickerView() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                place.setText(options1Items.get(options1).getPickerViewText()+" "
                        + options2Items.get(options1).get(options2)+" "
                        + options3Items.get(options1).get(options2).get(options3)+" ");

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据 （省市区三级联动）
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<ArraJsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<ArraJsonBean> parseData(String result) {//Gson 解析
        ArrayList<ArraJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ArraJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), ArraJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences("user_huodong", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

//        editor.putString("huodong_place",place.getText().toString());
//        editor.putString("huodong_detailPlace",detailPlace.getText().toString());
//        editor.putString("huodong_date",date.getText().toString());
//        editor.putString("huodong_startTime",startTime.getText().toString());
//        editor.putString("huodong_endTime",endTime.getText().toString());
//        editor.putString("huodong_danwei",danwei.getText().toString());
//        editor.putString("huodong_edit",edit.getText().toString());
        editor.commit();
    }
    private List<Yiyouhuodong> getdata(){
        //检查联网
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
        //从接口拿数据

        new Thread() {
            @Override
            public void run() {
        try {
            String url = "http://175.23.169.100:9028/case-aggregated-activity/get?caseId=" + current_transId;
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str1;
            while ((str1 = reader1.readLine()) != null) {
                sb.append(str1);
            }
            JSONObject object1 = new JSONObject(sb.toString());
            int code = object1.getInt("code");
            int acti_num = 0;
            if (code == 0) {
                JSONArray json1 = object1.getJSONArray("activities");

                acti_num = json1.length();
                if (acti_num != 0) {
                    isempty = 2;
                    for (int i = 0; i < acti_num; i++) {
                        String sd = json1.getJSONObject(i).getString("acti_start_date");
                        String st = json1.getJSONObject(i).getString("acti_start_time");
                        String ed = json1.getJSONObject(i).getString("acti_end_date");
                        String et = json1.getJSONObject(i).getString("acti_end_time");

                        double yearsd = Double.valueOf(sd.split("-")[0]);
                        double monthsd = Double.valueOf(sd.split("-")[1]);
                        double daysd = Double.valueOf(sd.split("-")[2]);
                        double hourst = Double.valueOf(st.split(":")[0]);
                        double minst = Double.valueOf(st.split(":")[1]);

                        double yeared = Double.valueOf(ed.split("-")[0]);
                        double monthed = Double.valueOf(ed.split("-")[1]);
                        double dayed = Double.valueOf(ed.split("-")[2]);
                        double houret = Double.valueOf(et.split(":")[0]);
                        double minet = Double.valueOf(et.split(":")[1]);

                        double starttime;
                        double endtime;
                        starttime = (((yearsd*100+monthsd)*100+daysd)*100+hourst)*100+minst;
                        endtime = (((yeared*100+monthed)*100+dayed)*100+houret)*100+minet;
                        Yiyouhuodong a = new Yiyouhuodong(starttime,endtime);
                        huodongyiyou.add(a);
                    }
                }else {
                    isempty=1;
                }
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
        return huodongyiyou;
    }
}

//这个class用来在获取数据以后把拿到的已有活动变成一个类，这个类两个double存放起止时间。遍历这个存放这个class的表，依次调用这个对象本身的addok来判断新的能不能加
class Yiyouhuodong{
    private double hdstart;
    private double hdend;
    Yiyouhuodong(double a,double b){
        hdstart = a;
        hdend = b;
    }
    public boolean addok(double addstart,double addend){
        if(addstart<hdend && addstart>hdstart){
            return false;
        }
        if(addend<hdend && addend>hdstart){
            return false;
        }
        if(addstart<hdstart && addend>hdend){
            return false;
        }
        return true;
    }
}