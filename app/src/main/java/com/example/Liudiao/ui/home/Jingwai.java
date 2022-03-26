package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;

import java.util.ArrayList;

public class Jingwai extends AppCompatActivity {

    private ImageView r_back;
    private TextView okey;

    private EditText huzhao;
    private EditText kouan;
    private EditText trasfferDetail;
    private TextView rujingDate;

    private Spinner nation;
    private int nationPosition;
    private Spinner trasffer;
    private int trasfferPosition;
    private ArrayList<String> trasfferLeixing = new ArrayList<String>();
    private String[] nationArray = {"中国",
        "安哥拉               ",
        "阿富汗                ",
        "阿尔巴尼亚           ",
        "阿尔及利亚           ",
        "安道尔共和国         ",
        "安圭拉岛            ",
        "安提瓜和巴布达      ",
        "澳大利亚              ",
        "奥地利                ",
        "阿塞拜疆             ",
        "巴哈马              ",
        "巴林                 ",
        "孟加拉国             ",
        "巴巴多斯            ",
        "白俄罗斯             ",
        "比利时                ",
        "伯利兹               ",
        "贝宁                 ",
        "百慕大群岛          ",
        "玻利维亚             ",
        "博茨瓦纳             ",
        "巴西                  ",
        "文莱                 ",
        "保加利亚             ",
        "布基纳法索           ",
        "缅甸                  ",
        "布隆迪               ",
        "喀麦隆               ",
        "加拿大                 ",
        "开曼群岛            ",
        "中非共和国           ",
        "乍得                 ",
        "智利                  ",
        "哥伦比亚              ",
        "刚果                 ",
        "库克群岛             ",
        "哥斯达黎加           ",
        "古巴                  ",
        "塞浦路斯             ",
        "捷克                 ",
        "丹麦                  ",
        "吉布提               ",
        "多米尼加共和国      ",
        "厄瓜多尔             ",
        "埃及                  ",
        "萨尔瓦多             ",
        "爱沙尼亚             ",
        "埃塞俄比亚           ",
        "斐济                 ",
        "芬兰                 ",
        "法国                  ",
        "法属圭亚那           ",
        "加蓬                 ",
        "冈比亚               ",
        "格鲁吉亚             ",
        "德国                  ",
        "加纳                 ",
        "直布罗陀             ",
        "希腊                  ",
        "关岛                ",
        "危地马拉             ",
        "几内亚               ",
        "圭亚那               ",
        "海地                 ",
        "洪都拉斯             ",
        "香港                 ",
        "匈牙利                ",
        "冰岛                 ",
        "印度                  ",
        "印度尼西亚            ",
        "伊朗                  ",
        "伊拉克               ",
        "爱尔兰               ",
        "以色列               ",
        "意大利                ",
        "科特迪瓦             ",
        "日本                  ",
        "约旦                 ",
        "柬埔寨               ",
        "肯尼亚               ",
        "韩国                  ",
        "科威特               ",
        "老挝                 ",
        "拉脱维亚             ",
        "黎巴嫩               ",
        "莱索托               ",
        "利比里亚             ",
        "利比亚               ",
        "列支敦士登           ",
        "立陶宛               ",
        "卢森堡               ",
        "澳门                 ",
        "马达加斯加           ",
        "马拉维               ",
        "马来西亚              ",
        "马尔代夫             ",
        "马里                 ",
        "马提尼克             ",
        "毛里求斯             ",
        "墨西哥                ",
        "摩尔多瓦             ",
        "摩纳哥               ",
        "蒙古                 ",
        "蒙特塞拉特岛        ",
        "摩洛哥               ",
        "莫桑比克             ",
        "纳米比亚             ",
        "尼泊尔               ",
        "荷属安的列斯         ",
        "荷兰                  ",
        "新西兰                ",
        "尼加拉瓜             ",
        "尼日尔               ",
        "尼日利亚             ",
        "挪威                  ",
        "阿曼                 ",
        "巴基斯坦              ",
        "巴拿马               ",
        "巴布亚新几内亚       ",
        "巴拉圭               ",
        "秘鲁                  ",
        "菲律宾                ",
        "波兰                  ",
        "法属玻利尼西亚       ",
        "葡萄牙               ",
        "波多黎各            ",
        "卡塔尔               ",
        "留尼旺               ",
        "罗马尼亚              ",
        "俄罗斯                 ",
        "西萨摩亚             ",
        "圣多美和普林西比     ",
        "沙特阿拉伯           ",
        "塞内加尔             ",
        "塞舌尔               ",
        "塞拉利昂             ",
        "新加坡                ",
        "斯洛伐克             ",
        "斯洛文尼亚           ",
        "所罗门群岛           ",
        "索马里               ",
        "南非                  ",
        "西班牙                ",
        "斯里兰卡              ",
        "圣卢西亚            ",
        "圣文森特            ",
        "苏丹                 ",
        "苏里南               ",
        "斯威士兰             ",
        "瑞典                  ",
        "瑞士                  ",
        "叙利亚               ",
        "台湾省               ",
        "塔吉克斯坦           ",
        "坦桑尼亚             ",
        "泰国                  ",
        "多哥                 ",
        "汤加                 ",
        "突尼斯               ",
        "土耳其                ",
        "土库曼斯坦           ",
        "乌干达               ",
        "乌克兰               ",
        "阿拉伯联合酋长国     ",
        "英国                  ",
        "美国                   ",
        "乌拉圭               ",
        "委内瑞拉              ",
        "越南                  ",
        "也门                 ",
        "南斯拉夫             ",
        "津巴布韦             ",
        "扎伊尔               ",
        "赞比亚               ",
        "牙买加              ",
        "毛里塔尼亚           ",
        "佛得角              ",
        "赤道几内亚           ",
        "几内亚比绍           ",
        "卢旺达              ",
        "科摩罗              ",
        "阿鲁巴岛            ",
        "法罗群岛            ",
        "格陵兰岛            ",
        "基里巴斯            ",
        "瓦努阿图            ",
        "不丹                "};

    private ArrayAdapter<String> arr_adapter_nation;
    private ArrayAdapter<String> arr_adapter_trasffer;

    private TextView selectRujingdate;

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
        setContentView(R.layout.home_jingwai);

        SharedPreferences preferences = getSharedPreferences("user_jingwai", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器
        int getNation = preferences.getInt("jingwai_nation",0);
        int getTrasffer = preferences.getInt("jingwai_trasffer",0);
        String getHuzhao  = preferences.getString("jingwai_huzhao","");
        String getKouan = preferences.getString("jingwai_kouan","");
        String getTrasfferdetail = preferences.getString("jingwai_trasfferDetail","");
        String getRujingdate = preferences.getString("jingwai_rujingDate","");

        trasfferLeixing.add("飞机");
        trasfferLeixing.add("轮船");
        trasfferLeixing.add("火车");
        trasfferLeixing.add("客车");

        nation = (Spinner) findViewById(R.id.jingwai_nation_spinner);
        trasffer = (Spinner) findViewById(R.id.jingwai_trasffer_spinner);

        huzhao = (EditText) findViewById(R.id.jingwai_huzhao_edit);
        kouan = (EditText) findViewById(R.id.jingwai_kouan_edit);
        trasfferDetail = (EditText) findViewById(R.id.jingwai_trasffer_edit);
        rujingDate = (TextView) findViewById(R.id.select_rujingDate);

        selectRujingdate = (TextView) findViewById(R.id.select_rujingDate);

        //初始化日期选择器
        dp = (DatePicker) findViewById(R.id.date_picker);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        arr_adapter_nation = new ArrayAdapter<String>(Jingwai.this, R.layout.simple_spinner_item,nationArray);
        arr_adapter_trasffer = new ArrayAdapter<String>(Jingwai.this,R.layout.simple_spinner_item,trasfferLeixing);
        //设置样式
        arr_adapter_nation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr_adapter_trasffer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        nation.setAdapter(arr_adapter_nation);
        trasffer.setAdapter(arr_adapter_trasffer);

        nation.setSelection(getNation);
        trasffer.setSelection(getTrasffer);

        huzhao.setText(getHuzhao);
        kouan.setText(getKouan);
        trasfferDetail.setText(getTrasfferdetail);
        rujingDate.setText(getRujingdate);

        nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        trasffer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trasfferPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectRujingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDateDialog();
                new DatePickerDialog(Jingwai.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectRujingdate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    }
                },year, calendar.get(Calendar.MONTH), day).show();
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
        SharedPreferences preferences = getSharedPreferences("user_jingwai", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();//获取编辑器

        editor.putInt("jingwai_nation",nationPosition);
        editor.putInt("jingwai_trasffer",trasfferPosition);
        editor.putString("jingwai_huzhao",huzhao.getText().toString());
        editor.putString("jingwai_kouan",kouan.getText().toString());
        editor.putString("jingwai_trasfferDetail",trasfferDetail.getText().toString());
        editor.putString("jingwai_rujingDate",rujingDate.getText().toString());
        editor.commit();
    }
}