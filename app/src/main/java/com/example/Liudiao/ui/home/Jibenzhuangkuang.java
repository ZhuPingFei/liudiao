package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.ArraJsonBean;
import com.example.Liudiao.ui.GetJsonDataUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class Jibenzhuangkuang extends AppCompatActivity {

    private String str1;
    private String str2;
    private String str3;

    boolean isSpinnerFirst1 = true ;
    boolean isSpinnerFirst2 = true ;
    boolean isSpinnerFirst3 = true ;


    private ArrayList<String> old = new ArrayList<>();
    private ArrayList<String> zhengjianleixing = new ArrayList<String>();
    //private ArrayList<String> job = new ArrayList<String>();
    private String[] job = {"教师","工人","记者","厨师","医务人员","司机","军人","律师","会计","出纳",
            "作家","程序员","模特","警察","公职人员","保安","其他"};

    private Spinner spinner_old;
    private int selectOld;
    private Spinner spinner_zhengjianleixing;
    private int selectZhengjian;
    private Spinner spinner_job;
    private int selectJob;
    private TextView seleteDate;
    private TextView selecePlace;
    private ImageView r_back;
    private TextView okey;
    private EditText name;
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

    private ArrayList<ArraJsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>(); //区



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_jibenzhuangkuang);

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

        for (int i = 1;i<100;i++){
            old.add(Integer.toString(i));
        }
        zhengjianleixing.add("居民身份证");
        zhengjianleixing.add("护照");
        zhengjianleixing.add("港澳台居民来往通行证");

        SharedPreferences preferences = getSharedPreferences("user_jibenzhuangkuang", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        String getName =preferences.getString("jibenzhuangkuang_name","");
        boolean getSex_man = preferences.getBoolean("jibenzhuangkuang_sexMan", Boolean.parseBoolean(""));
        boolean getSex_woman = preferences.getBoolean("jibenzhuangkuang_sexWoman", Boolean.parseBoolean(""));
        int getOld = preferences.getInt("jibenzhuangkuang_old", 0);
        int getZhengjian = preferences.getInt("jibenzhuangkuang_zhengjian",0);
        int getJob = preferences.getInt("jibenzhuangkuang_job",0);
        String zhengjian = preferences.getString("jibenzhuangkuang_zhengjianhaoma","");
        String getPhone = preferences.getString("jibenzhuangkuang_phone","");
        String getBorndate = preferences.getString("jibenzhuangkuang_borndate","");
        String getHomeplace = preferences.getString("jibenzhuangkuang_homeplace","");
        String getHomeplacedetail = preferences.getString("jibenzhuangkuang_homeplaceDetail","");


        radioButton1 = (RadioButton)findViewById(R.id.sex_radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.sex_radioButton2);
        name = (EditText) findViewById(R.id.edittext_name);
        radioGroup = (RadioGroup) findViewById(R.id.sex_radiogroup);
        seleteDate = (TextView) findViewById(R.id.select_borndate);
        selecePlace = (TextView) findViewById(R.id.select_place);
        spinner_old = (Spinner) findViewById(R.id.spinner_old);
        spinner_zhengjianleixing = (Spinner) findViewById(R.id.spinner_zhegnjianleixing);
        spinner_job = (Spinner) findViewById(R.id.spinner_job);
        zhengjianedit = (EditText) findViewById(R.id.edittext_zhengjian);
        phone = (EditText) findViewById(R.id.edittext_phone);
        borndate = (TextView)findViewById(R.id.select_borndate);
        homePlace = (TextView)findViewById(R.id.select_place);
        homePlacedetail = (EditText) findViewById(R.id.edittext_homeplacedetail);

        name.setText(getName);


        if (getSex_man == false){
            radioButton2.setChecked(true);
        }else {
            radioButton1.setChecked(true);
        }
        zhengjianedit.setText(zhengjian);
        phone.setText(getPhone);
        borndate.setText(getBorndate);
        homePlace.setText(getHomeplace);
        homePlacedetail.setText(getHomeplacedetail);




        //str1 =(String)spinner_old.getSelectedItem();
        // str2 =(String)spinner_zhengjianleixing.getSelectedItem();
        //str3 =(String)spinner_job.getSelectedItem();
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

        //初始化日期选择器
        dp = (DatePicker) findViewById(R.id.date_picker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        spinner_old.setSelection(getOld);
        spinner_zhengjianleixing.setSelection(getZhengjian);
        spinner_job.setSelection(getJob);

        spinner_old.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectJob = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        seleteDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDateDialog();
                new DatePickerDialog(Jibenzhuangkuang.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        seleteDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
            }
        });


        initJsonData();
        selecePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });



    }

    private void showPickerView() {    // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                selecePlace.setText(options1Items.get(options1).getPickerViewText() + "  "
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


//    public void showDateDialog(){
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_data,null);
//        TextView dataCancel = (TextView) view.findViewById(R.id.date_cancel);
//        TextView dataConfirm = (TextView) view.findViewById(R.id.date_confirm);
//    }

    public void dataCommit(){
        SharedPreferences preferences = getSharedPreferences("user_jibenzhuangkuang", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("jibenzhuangkuang_name",name.getText().toString());

        if (radioButton1.isChecked() == true){
            editor.putBoolean("jibenzhuangkuang_sexMan",true);
            editor.putBoolean("jibenzhuangkuang_sexWoMan",false);
        }else {
            editor.putBoolean("jibenzhuangkuang_sexWoman",true);
            editor.putBoolean("jibenzhuangkuang_sexMan",false);
        }
        editor.putInt("jibenzhuangkuang_old",selectOld);
        editor.putInt("jibenzhuangkuang_zhengjian",selectZhengjian);
        editor.putInt("jibenzhuangkuang_job",selectJob);
        editor.putString("jibenzhuangkuang_zhengjianhaoma",zhengjianedit.getText().toString());
        editor.putString("jibenzhuangkuang_phone",phone.getText().toString());
        editor.putString("jibenzhuangkuang_borndate",borndate.getText().toString());
        editor.putString("jibenzhuangkuang_homeplace",homePlace.getText().toString());
        editor.putString("jibenzhuangkuang_homeplaceDetail",homePlacedetail.getText().toString());
        editor.commit();

    }
}
