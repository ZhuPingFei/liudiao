package com.example.Liudiao.ui.xingcheng;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.GetJsonDataUtil;
import com.example.Liudiao.ui.home.Bingli;
import com.example.Liudiao.ui.home.Jibenzhuangkuang;
import com.example.Liudiao.ui.home.Jingwai;
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

public class Xingcheng extends AppCompatActivity {

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private boolean isMe;

    private ImageView r_back;
    private TextView okey;

    private int num ;
    String trasfferStr;

    private TextView startPlace;
    private EditText startDetailplace;
    private TextView endPlace;
    private EditText endDetailplace;

    private TextView startDate;
    private TextView startTime;
    private TextView endDate;
    private TextView endTime;

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

    private ArrayAdapter<String> arr_adapter_trasffer;
    private Spinner trasffer;
    private int trasfferPosition;
    private ArrayList<String> trasfferLeixing = new ArrayList<String>();
    private EditText trasfferDetail;
    private EditText tongxing;

    private EditText edit;
    StringBuffer xingcheng ;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingcheng_xingcheng);

        xingcheng = new StringBuffer();

        trasfferLeixing.add("飞机");
        trasfferLeixing.add("轮船");
        trasfferLeixing.add("火车");
        trasfferLeixing.add("客车");

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        isMe = preferences.getBoolean("isMe",false);


//        preferences = getSharedPreferences("user_xingcheng", Activity.MODE_PRIVATE);
//        editor = preferences.edit();//获取编辑器
//        final String getStartplace = preferences.getString("xingcheng_startPlace","");
//        final String getStartdetailplace = preferences.getString("xingcheng_startDetailplace","");
//        final String getStartdate = preferences.getString("xingcheng_startDate","");
//        final String getStartTime = preferences.getString("xingcheng_startTime","");
//        final int getTrasffer = preferences.getInt("xingcheng_trasffer",0);
//        final String getTrasfferdetail = preferences.getString("xingcheng_trasfferDetail","");
//        final String getTongxing = preferences.getString("xingcheng_tongxing","");
//        final String getEndplace = preferences.getString("xingcheng_endPlace","");
//        final String getEnddetailplace = preferences.getString("xingcheng_endDetailplace","");
//        final String getEnddate = preferences.getString("xingcheng_endDate","");
//        final String getEndTime = preferences.getString("xingcheng_endTime","");
//        String getEdit = preferences.getString("xingcheng_edit","");
//        final String xingchengOld = preferences.getString("xingcheng","");

        //num = preferences.getInt("xingcheng_num",0);

        startPlace = (TextView)findViewById(R.id.xingcheng_qidian_select_place);
        startDetailplace = (EditText)findViewById(R.id.xingcheng_qidianDetail_edit);
        startDate = (TextView)findViewById(R.id.xingcheng_qidianDate);
        startTime = (TextView)findViewById(R.id.xingcheng_qidianTime);
        endDate = (TextView) findViewById(R.id.xingcheng_endDate);
        endTime = (TextView) findViewById(R.id.xingcheng_endTime);
        endPlace = (TextView)findViewById(R.id.xingcheng_end_select_place);
        endDetailplace = (EditText) findViewById(R.id.xingcheng_endDetail_edit);
        trasffer = (Spinner) findViewById(R.id.xingcheng_jiaotong_spinner);
        trasfferDetail = (EditText)findViewById(R.id.trasfferDetail);
        tongxing = (EditText)findViewById(R.id.xingcheng_qidianTongxing_edit);
        edit = (EditText) findViewById(R.id.xingcheng_Detail_edit);

        arr_adapter_trasffer = new ArrayAdapter<String>(Xingcheng.this,R.layout.simple_spinner_item,trasfferLeixing);
        //设置样式
        arr_adapter_trasffer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        trasffer.setAdapter(arr_adapter_trasffer);

//        startPlace.setText(getStartplace);
//        startDetailplace.setText(getStartdetailplace);
//        startDate.setText(getStartdate);
//        startTime.setText(getStartTime);
//        endPlace.setText(getEndplace);
//        endDetailplace.setText(getEnddetailplace);
//        endDate.setText(getEnddate);
//        endTime.setText(getEndTime);
//        trasffer.setSelection(getTrasffer);
//        trasfferDetail.setText(getTrasfferdetail);
//        tongxing.setText(getTongxing);
//        edit.setText(getEdit);

        trasffer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trasfferPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initJsonData();
        startPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView1();
            }
        });
        endPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView2();
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
                if (!startPlace.getText().toString().equals("") && !startDetailplace.getText().toString().equals("")
                        && !startDate.getText().toString().equals("") && !startTime.getText().toString().equals("")
                        && !trasfferDetail.getText().toString().equals("") && !tongxing.getText().toString().equals("")
                        && !endPlace.getText().toString().equals("") && !endDetailplace.getText().toString().equals("")
                        && !endDate.getText().toString().equals("") && !endTime.getText().toString().equals("")) {

                    final StringBuffer stringBuffer1 = new StringBuffer(startDate.getText().toString());
                    if (startDate.getText().toString().length()==8){
                        stringBuffer1.insert(5,"0");
                        stringBuffer1.insert(8,"0");
                    }else if (startDate.getText().toString().length()==9){
                        String[] split = startDate.getText().toString().split("-");
                        if (split[1].length() == 2){
                            stringBuffer1.insert(8,"0");
                        }else {
                            stringBuffer1.insert(5,"0");
                        }
                    }
                    final StringBuffer stringBuffer2 = new StringBuffer(endDate.getText().toString());
                    if (endDate.getText().toString().length()==8){
                        stringBuffer2.insert(5,"0");
                        stringBuffer2.insert(8,"0");
                    }else if (endDate.getText().toString().length()==9){
                        String[] split = endDate.getText().toString().split("-");
                        if (split[1].length() == 2){
                            stringBuffer2.insert(8,"0");
                        }else {
                            stringBuffer2.insert(5,"0");
                        }
                    }
                    if (!isMe){
                        builder = new AlertDialog.Builder(Xingcheng.this).setTitle("重要提醒")
                                .setMessage("当前为代办模式，是否确认提交？")
                                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //ToDo: 你想做的事情
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String postUrl = "http://175.23.169.100:9000/case-travel-trajectory/add";
                                                    JSONObject jsonObject = new JSONObject();
                                                    jsonObject.put("transactor_id",current_transId);
                                                    jsonObject.put("start",startPlace.getText().toString());
                                                    jsonObject.put("start_detail",startDetailplace.getText().toString());
                                                    jsonObject.put("start_date",stringBuffer1.toString());
                                                    jsonObject.put("start_time",startTime.getText().toString());
                                                    jsonObject.put("traffic",trasfferPosition);
                                                    jsonObject.put("tra_detail",trasfferDetail.getText().toString());
                                                    jsonObject.put("peers",tongxing.getText().toString());
                                                    jsonObject.put("end",endPlace.getText().toString());
                                                    jsonObject.put("end_detail",endDetailplace.getText().toString());
                                                    jsonObject.put("end_date",stringBuffer2.toString());
                                                    jsonObject.put("end_time",endTime.getText().toString());
                                                    jsonObject.put("journey_det",edit.getText().toString());

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
                                                        editor.putBoolean(current_transId+"hasXingcheng",true);
                                                        editor.commit();
                                                        Looper.prepare();
                                                        Toast.makeText(Xingcheng.this,"提交成功!可在历史行程查看已提交行程信息",Toast.LENGTH_SHORT).show();
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
                                    String postUrl = "http://175.23.169.100:9000/case-travel-trajectory/add";
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("transactor_id",current_transId);
                                    jsonObject.put("start",startPlace.getText().toString());
                                    jsonObject.put("start_detail",startDetailplace.getText().toString());
                                    jsonObject.put("start_date",stringBuffer1.toString());
                                    jsonObject.put("start_time",startTime.getText().toString());
                                    jsonObject.put("traffic",trasfferPosition);
                                    jsonObject.put("tra_detail",trasfferDetail.getText().toString());
                                    jsonObject.put("peers",tongxing.getText().toString());
                                    jsonObject.put("end",endPlace.getText().toString());
                                    jsonObject.put("end_detail",endDetailplace.getText().toString());
                                    jsonObject.put("end_date",stringBuffer2.toString());
                                    jsonObject.put("end_time",endTime.getText().toString());
                                    jsonObject.put("journey_det",edit.getText().toString());

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
                                        Toast.makeText(Xingcheng.this,"提交成功!可在历史行程查看已提交行程信息",Toast.LENGTH_SHORT).show();
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


                }else {
                    Toast.makeText(Xingcheng.this,"请录入完整信息再提交！",Toast.LENGTH_SHORT).show();
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

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDateDialog();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Xingcheng.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Xingcheng.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, min, true).show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(Xingcheng.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();

                //showDateDialog();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Xingcheng.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, min, true).show();
            }
        });
    }

    private void showPickerView1() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                startPlace.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));

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
    private void showPickerView2() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                endPlace.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));

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

    public void dataCommit(String s){
        SharedPreferences preferences = getSharedPreferences("user_xingcheng", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

        editor.putString("xingcheng_startPlace",startPlace.getText().toString());
        editor.putString("xingcheng_startDetailplace",startDetailplace.getText().toString());
        editor.putString("xingcheng_startDate",startDate.getText().toString());
        editor.putString("xingcheng_startTime",startTime.getText().toString());
        editor.putString("xingcheng_endPlace",endPlace.getText().toString());
        editor.putString("xingcheng_endDetailplace",endDetailplace.getText().toString());
        editor.putString("xingcheng_endDate",endDate.getText().toString());
        editor.putString("xingcheng_endTime",endTime.getText().toString());
        editor.putInt("xingcheng_trasffer",trasfferPosition);
        editor.putString("xingcheng_trasfferDetail",trasfferDetail.getText().toString());
        editor.putString("xingcheng_tongxing",tongxing.getText().toString());
        editor.putString("xingcheng_edit",edit.getText().toString());
        editor.putInt("xingcheng_times",1);//添加形成用来计数的
        //editor.putInt("xingcheng_num",num+1);
       // editor.putString("xingcheng",s);

        editor.commit();
    }
}
