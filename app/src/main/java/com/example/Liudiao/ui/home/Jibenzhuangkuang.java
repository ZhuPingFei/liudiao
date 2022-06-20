package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.Liudiao.ui.notifications.RequestDaibanthread;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jibenzhuangkuang extends AppCompatActivity {



    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private int current_transId;
    private String current_banliName;
    private String idCard;
    private boolean isMe;
    private int age;
    private int idcard_sex;
    private String bornDate;

    private String str1;
    private String str2;
    private String str3;

    boolean isSpinnerFirst1 = true ;
    boolean isSpinnerFirst2 = true ;
    boolean isSpinnerFirst3 = true ;


    private ArrayList<String> old = new ArrayList<>();
    private ArrayList<String> zhengjianleixing = new ArrayList<String>();
    //private ArrayList<String> job = new ArrayList<String>();
    private String[] job = {"","幼托儿童","散居儿童","学生（大中小学）","教师","保育员及保姆","餐饮食品业","商业服务","医务人员","工人","民工",
            "农民","渔（船）民","干部职员","离退人员","家务及待业","其他","不详"};

    private Spinner spinner_old;
    private int selectOld;
    private Spinner spinner_zhengjianleixing;
    private int selectZhengjian;
    private Spinner spinner_job;
    private String str_job;
    private int selectJob;
    private TextView selecePlace;
    private ImageView r_back;
    private TextView okey;
    private EditText name;
    private EditText workUnit;
    private String unit_baogao;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private EditText zhengjianedit;
    private EditText phone;
    private TextView borndate;
    private TextView homePlace;
    private EditText homePlacedetail;

    private ArrayAdapter<String> arr_adapter_old;
    private ArrayAdapter<String> arr_adapter_zhegnjianleixing;
    private ArrayAdapter<String> arr_adapter_job;

    private TimePicker tp = null;
    private DatePicker dp = null;
    private Calendar calendar = null;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

    private boolean isZhengjianRight;

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区

    private Handler handler2 = new Handler();
    private Runnable runnable;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();

            String banliren_name = bd.getString("name");

            if (!banliren_name.equals("no")){
                String card_id = bd.getString("card_id");
                int gender = bd.getInt("gender");
                int age = bd.getInt("age");
                int type = bd.getInt("type");
                String tel = bd.getString("tel");
                int job = bd.getInt("job");
                String address = bd.getString("address");
                String address_detail = bd.getString("address_detail");
                String date = bd.getString("birth_date");
                String unit = bd.getString("job_address");
                name = (EditText) findViewById(R.id.edittext_name);
                radioButton1 = (RadioButton)findViewById(R.id.sex_radioButton1);
                radioButton2 = (RadioButton)findViewById(R.id.sex_radioButton2);
                workUnit = (EditText) findViewById(R.id.edittext_workunit);
                radioGroup = (RadioGroup) findViewById(R.id.sex_radiogroup);
                selecePlace = (TextView) findViewById(R.id.select_place);
                spinner_old = (Spinner) findViewById(R.id.spinner_old);
                spinner_zhengjianleixing = (Spinner) findViewById(R.id.spinner_zhegnjianleixing);
                spinner_job = (Spinner) findViewById(R.id.spinner_job);
                zhengjianedit = (EditText) findViewById(R.id.edittext_zhengjian);
                phone = (EditText) findViewById(R.id.edittext_phone);
                borndate = (TextView)findViewById(R.id.select_borndate);
                homePlace = (TextView)findViewById(R.id.select_place);
                homePlacedetail = (EditText) findViewById(R.id.edittext_homeplacedetail);

                name.setText(banliren_name);
                if (gender == 1){
                    radioButton1.setChecked(true);
                }else {
                    radioButton2.setChecked(true);
                }
                zhengjianedit.setText(card_id);
                spinner_old.setSelection(age);
                spinner_zhengjianleixing.setSelection(type);
                phone.setText(tel);
                spinner_job.setSelection(job);
                homePlace.setText(address);
                homePlacedetail.setText(address_detail);
                borndate.setText(date);

                //4.9
                if (unit.equals("null")||unit.equals("")){
                    workUnit.setText("");
                }else {
                    workUnit.setText(unit);
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jibenzhuangkuang);

        radioGroup = (RadioGroup) findViewById(R.id.sex_radiogroup);
        radioButton1 = (RadioButton)findViewById(R.id.sex_radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.sex_radioButton2);
        name = (EditText) findViewById(R.id.edittext_name);

        name.setEnabled(false);//不让改名

        workUnit = (EditText) findViewById(R.id.edittext_workunit);
        radioGroup = (RadioGroup) findViewById(R.id.sex_radiogroup);
        selecePlace = (TextView) findViewById(R.id.select_place);
        spinner_old = (Spinner) findViewById(R.id.spinner_old);
        spinner_zhengjianleixing = (Spinner) findViewById(R.id.spinner_zhegnjianleixing);
        spinner_job = (Spinner) findViewById(R.id.spinner_job);
        zhengjianedit = (EditText) findViewById(R.id.edittext_zhengjian);

        zhengjianedit.setEnabled(false);


        phone = (EditText) findViewById(R.id.edittext_phone);
        phone.setInputType(InputType.TYPE_CLASS_PHONE);

        borndate = (TextView)findViewById(R.id.select_borndate);
        homePlace = (TextView)findViewById(R.id.select_place);
        homePlacedetail = (EditText) findViewById(R.id.edittext_homeplacedetail);

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);
        current_banliName = preferences.getString("current_banliName","");
        idCard = preferences.getString("idCard","");

        Log.d("JIbne", "current_banliId "+current_transId);
        isMe = preferences.getBoolean("isMe",false);

        StringBuffer stringBuffer = new StringBuffer("");
        int age1 = 0;
        if (idCard.length()==18){
            idcard_sex = idCard.toCharArray()[16];
            idcard_sex = idcard_sex - 48;
            stringBuffer = new StringBuffer();
            stringBuffer.append(idCard.substring(6,10));
            age1 = Integer.parseInt(stringBuffer.toString());
            stringBuffer.append("-");
            stringBuffer.append(idCard.substring(10,12));
            stringBuffer.append("-");
            stringBuffer.append(idCard.substring(12,14));

            if (idcard_sex%2 == 0){
                radioButton2.setChecked(true);
            }else {
                radioButton1.setChecked(true);
            }
        }
        age = 2022 - age1;

        phone.addTextChangedListener(new TextWatcher() {
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
                        if (!isMobilPhone(s.toString())){
                            Toast.makeText(Jibenzhuangkuang.this,"请填写正确的手机号！",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                handler2.postDelayed(runnable, 1500);
            }
        });
        


//        if (!current_banliName.equals("本人")){
            String strName = current_banliName;
            name.setText(strName);
//            name.setFocusable(false);
            zhengjianedit.setText(idCard);
//            zhengjianedit.setFocusable(false);
            borndate.setText(stringBuffer.toString());
//        }else {
//            name.setFocusable(true);
//            zhengjianedit.setFocusable(true);
//        }



        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        okey = (TextView) findViewById(R.id.okey);


        String url = "http://175.23.169.100:9030/personal/getPersonal";
        RequestJibenThread rdt = new RequestJibenThread(url,current_transId,handler);
        rdt.start();


        for (int i = 1;i<120;i++){
            old.add(Integer.toString(i));
        }
        zhengjianleixing.add("居民身份证");
        zhengjianleixing.add("港澳台居民来往通行证");
        zhengjianleixing.add("护照");

        arr_adapter_old = new ArrayAdapter<String>(Jibenzhuangkuang.this, R.layout.simple_spinner_item, old);
        arr_adapter_zhegnjianleixing = new ArrayAdapter<String>(Jibenzhuangkuang.this,R.layout.simple_spinner_item,zhengjianleixing);
        arr_adapter_job = new ArrayAdapter<String>(Jibenzhuangkuang.this,R.layout.simple_spinner_item,job);
        //设置样式
        arr_adapter_old.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr_adapter_zhegnjianleixing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr_adapter_job.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner_old.setAdapter(arr_adapter_old);
        spinner_zhengjianleixing.setAdapter(arr_adapter_zhegnjianleixing);
        spinner_job.setAdapter(arr_adapter_job);

        if (idCard.length()==18){
            spinner_old.setSelection(age-1);
        }


        //初始化日期选择器
        dp = (DatePicker) findViewById(R.id.date_picker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        spinner_old.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isSpinnerFirst1){
//                    view.setVisibility(View.INVISIBLE);
//                }
//                isSpinnerFirst1 = false;
                selectOld = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_zhengjianleixing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectZhengjian = position;
                if (selectZhengjian != 0){
                    zhengjianedit.setFocusable(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (isSpinnerFirst3){
//                    view.setVisibility(View.INVISIBLE);
//                }
//                isSpinnerFirst3 = false;
                selectJob = position;
                str_job = (String) spinner_job.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        borndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showDateDialog();
                DatePickerDialog datePickerDialog = new DatePickerDialog(Jibenzhuangkuang.this, android.app.AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        borndate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();


            }
        });


        initJsonData();
        selecePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });
//        final int gender;
//        if (radioButton1.isChecked() == true){
//            gender = 1;
//        }else {
//            gender = 0;
//        }
        zhengjianedit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (selectZhengjian == 0){
                    isZhengjianRight = zhengjianedit.getText().toString().length() == 18 && personIdValidation(zhengjianedit.getText().toString());
                }else if (selectZhengjian == 1){
                    isZhengjianRight = zhengjianedit.getText().toString().length()==9;
                }else {
                    isZhengjianRight = zhengjianedit.getText().toString().length() == 11;
                }
                if (!b && !isZhengjianRight){
                    Toast.makeText(Jibenzhuangkuang.this, "输入的身份证号有误", Toast.LENGTH_SHORT).show();
                    zhengjianedit.setTextColor(Color.rgb(255,0,0));
                }else{
                    zhengjianedit.setTextColor(-1979711488);
                }
            }
        });


        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workUnit.getText().toString().equals("") || workUnit.getText().toString().equals("null")){
                    unit_baogao = "";
                }else {
                    unit_baogao = workUnit.getText().toString();
                }

                String unit = workUnit.getText().toString();
                if (unit.equals("")){
                    unit = null;
                }

                final int gender;
                if (radioButton1.isChecked()) {
                    gender = 1;
                } else {
                    gender = 0;
                }
                if (selectZhengjian == 0){
                    isZhengjianRight = zhengjianedit.getText().toString().length() == 18 && personIdValidation(zhengjianedit.getText().toString());
                }else if (selectZhengjian == 1){
                    isZhengjianRight = zhengjianedit.getText().toString().length()==9;
                }else {
                    isZhengjianRight = zhengjianedit.getText().toString().length() == 11;
                }



                final StringBuffer stringBuffer = new StringBuffer(borndate.getText().toString());
                if (borndate.getText().toString().length()==8){
                    stringBuffer.insert(5,"0");
                    stringBuffer.insert(8,"0");
                }else if (borndate.getText().toString().length()==9){
                    String[] split = borndate.getText().toString().split("-");
                    if (split[1].length() == 2){
                        stringBuffer.insert(8,"0");
                    }else {
                        stringBuffer.insert(5,"0");
                    }
                }
                if (name.getText().toString().length() != 0 && zhengjianedit.getText().toString().length() != 0 && phone.getText().toString().length() != 0
                        && borndate.getText().toString().length() != 0 && homePlace.getText().toString().length() != 0 && homePlacedetail.getText().toString().length() != 0) {
                    if (isZhengjianRight) {
                        if (isMobilPhone(phone.getText().toString())) {
                            dataCommit();
                            if (!isMe){
                                final String finalUnit = unit;
                                builder = new AlertDialog.Builder(Jibenzhuangkuang.this).setTitle("重要提醒")
                                        .setMessage("当前为代办模式，是否确认提交？")
                                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //ToDo: 你想做的事情
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            String postUrl = "http://175.23.169.100:9030/personal/updatePersonal";
                                                            JSONObject jsonObject = new JSONObject();
                                                            jsonObject.put("transactor_id", current_transId);
                                                            jsonObject.put("name", name.getText().toString());
                                                            jsonObject.put("sex", gender);
                                                            jsonObject.put("id_type",selectZhengjian);
                                                            jsonObject.put("age", 2021-Integer.parseInt(zhengjianedit.getText().toString().substring(6,10)));
                                                            jsonObject.put("id_number", zhengjianedit.getText().toString());
                                                            jsonObject.put("mobile", phone.getText().toString());
                                                            jsonObject.put("job", selectJob);
                                                            jsonObject.put("birth_date", stringBuffer.toString());
                                                            jsonObject.put("address", homePlace.getText().toString());
                                                            jsonObject.put("address_detail", homePlacedetail.getText().toString());
                                                            jsonObject.put("job_address", finalUnit);
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
                                                                editor.putBoolean(current_transId+"hasJibenzhuangkuang",true);
                                                                editor.commit();
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(Jibenzhuangkuang.this, "提交成功", Toast.LENGTH_SHORT).show();
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
                            }else {
                                final String finalUnit1 = unit;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String postUrl = "http://175.23.169.100:9030/personal/updatePersonal";
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put("transactor_id", current_transId);
                                            jsonObject.put("name", name.getText().toString());
                                            jsonObject.put("sex", gender);
                                            jsonObject.put("id_type",selectZhengjian);
                                            jsonObject.put("age", 2021-Integer.parseInt(zhengjianedit.getText().toString().substring(6,10)));
                                            jsonObject.put("id_number", zhengjianedit.getText().toString());
                                            jsonObject.put("mobile", phone.getText().toString());
                                            jsonObject.put("job", selectJob);
                                            jsonObject.put("birth_date", stringBuffer.toString());
                                            jsonObject.put("address", homePlace.getText().toString());
                                            jsonObject.put("address_detail", homePlacedetail.getText().toString());
                                            jsonObject.put("job_address", finalUnit1);
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
                                                editor.putBoolean(current_transId+"hasJibenzhuangkuang",true);
                                                editor.commit();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Jibenzhuangkuang.this, "提交成功", Toast.LENGTH_SHORT).show();
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
                        } else {
                            Toast.makeText(Jibenzhuangkuang.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Jibenzhuangkuang.this, "请输入正确的证件号码！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Jibenzhuangkuang.this, "请填写完整信息后再提交！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //判断身份证
    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    private void showPickerView() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selecePlace.setText(options1Items.get(options1).getPickerViewText()+" "
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


//    public void showDateDialog(){
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_data,null);
//        TextView dataCancel = (TextView) view.findViewById(R.id.date_cancel);
//        TextView dataConfirm = (TextView) view.findViewById(R.id.date_confirm);
//    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences(current_transId+"baogao", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("name",name.getText().toString());

        if (radioButton1.isChecked() == true){
            editor.putString("gender","男");
        }else {
            editor.putString("gender","女");
        }
        editor.putInt("age",2021-Integer.parseInt(zhengjianedit.getText().toString().substring(6,10)));
        editor.putString("job",str_job);
        editor.putString("idCard",zhengjianedit.getText().toString());
        editor.putString("phone",phone.getText().toString());
        //editor.putString("jibenzhuangkuang_borndate",borndate.getText().toString());
        editor.putString("homeplace",homePlace.getText().toString());
        editor.putString("homeplaceDetail",homePlacedetail.getText().toString());
        editor.putString("job_address",unit_baogao);
        editor.commit();

        preferences = getSharedPreferences("daiban",Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(current_transId+"address",homePlace.getText().toString());
        editor.putString(current_transId+"address_detail",homePlacedetail.getText().toString());
        editor.commit();

    }

    public static boolean isMobilPhone(String phone) {
        if (phone.isEmpty()) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18([0-4]|[6-9]))|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 判断年龄
     * @param idNumber
     * @return
     */
    public  Integer CalcAgeByIdNumber(String idNumber){
        int age;//会员年龄
        String fyear,year,fyue,yue;
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        if(idNumber.length()==18) {//用户身份证为18位时
            fyear = format.format(date).substring(0,4);// 当前年份
            fyue = format.format(date).substring(5, 7);// 月份
            year = idNumber.substring(6).substring(0, 4);// 得到年份
            yue = idNumber.substring(10).substring(0, 2);// 得到月份
            if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
            } else {// 当前用户还没过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }
        }else {
            fyear = format.format(date).substring(0,4);;// 当前年份
            fyue = format.format(date).substring(5, 7);// 月份
            year = "19" + idNumber.substring(6, 8);
            yue = idNumber.substring(8, 10);// 月份
            if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
            } else {// 当前用户还没过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }
        }
        return age;

    }

}
