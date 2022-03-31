package com.example.Liudiao.ui.xingcheng;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.xingcheng.Adapter.XingchengAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XingchengHistory extends AppCompatActivity implements View.OnClickListener{

    private ImageView r_back;
    private TextView okey;

    int xingcheng_num;
    private int current_transId;

    private TextView add;
    private TextView delete;

    private int i=-1;
    private LinearLayout my_layout;
    private TextView textView1;
    private ArrayList<TextView> textTexts;

    private ListView listView;
    private XingchengAdapter xingchengAdapter;

    int xingchengTimes;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;//获取编辑器

    private List<Map<String, String>> mList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse(msg.obj.toString());

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingcheng_history);
        listView = (ListView) findViewById(R.id.xingcheng_list);


        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_transId = preferences.getInt("current_banliId",0);

        String url = "http://175.23.169.100:9000/case-travel-trajectory/get";
        RequestXingchengHisThread rdt = new RequestXingchengHisThread(url,current_transId,handler);
        rdt.start();

        textTexts = new ArrayList<>();

        preferences = getSharedPreferences("user_xingcheng", Activity.MODE_PRIVATE);
        editor = preferences.edit();

        int getXingchengTimes = preferences.getInt("xingcheng_times",0);
        xingchengTimes = getXingchengTimes;

        String xinxi = preferences.getString("xingcheng","");
        xingcheng_num = preferences.getInt("xingcheng_num",0);
//        if (xingcheng_num != 0){
//            String[] spilt = xinxi.split("%");
//            for (int j = 0;j<xingcheng_num;j++){
//                addView(spilt[j+1]);
//            }
//        }
        //initView();

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
                //dataCommit();
                int nums = 0;
                StringBuffer str = new StringBuffer();
                for (TextView view : textTexts){
                    nums++;
                    str.append("%"+view.getText().toString());
                }
                editor.putString("xingcheng",str.toString());
                editor.putInt("xingcheng_num",nums);
                editor.commit();
                onBackPressed();
            }
        });
    }

//    private void initView() {
//        add.setOnClickListener(this);
//        delete.setOnClickListener(this);
//    }

//    public void addView(String s) {
//        my_layout = findViewById(R.id.history_list);
//        textView1 = new TextView(this);
//        i++;
//        textView1.setWidth(300);
//        textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        textView1.setPadding(50, 30, 50, 30);
//        textView1.setText(s);
//        textView1.setTextSize(17);
//        textView1.setBackgroundColor(Color.WHITE);
//        textView1.setTop(10);
//        //textView1.setEllipsize(TextUtils.TruncateAt.valueOf("END"));//动隐藏尾部溢出数据，一般用于文字内容过长一行无法全部显示时
//        textView1.setMovementMethod(LinkMovementMethod.getInstance());//设置textview滚动事件的
//        textTexts.add(i,textView1);
//
//        my_layout.addView(textView1);
//    }

//    public void addView1() {
//        String s = getDate();
//        my_layout = findViewById(R.id.history_list);
//        textView1 = new TextView(this);
//        i++;
//        textView1.setWidth(300);
//        textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//
//        textView1.setPadding(50, 30, 50, 30);
//        textView1.setText(s);
//        textView1.setTextSize(17);
//        textView1.setBackgroundColor(Color.WHITE);
//        textView1.setTop(10);
//        //textView1.setEllipsize(TextUtils.TruncateAt.valueOf("END"));//动隐藏尾部溢出数据，一般用于文字内容过长一行无法全部显示时
//        textView1.setMovementMethod(LinkMovementMethod.getInstance());//设置textview滚动事件的
//        textTexts.add(i,textView1);
//
//        my_layout.addView(textView1);
//    }
//    public void deleteView() {
//        TextView textView = textTexts.get(i);
//        my_layout.removeView(textView);
//        textTexts.remove(i);
//        i--;
//        xingcheng_num--;
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.weiluru:
//                if (xingchengTimes == 1){
//                    addView1();
//                    xingchengTimes--;
//                    editor.putInt("xingcheng_times",0);
//                    editor.commit();
//                }else {
//                    Toast.makeText(XingchengHistory.this,"当前没有需要添加的行程",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//            case R.id.delete:
//                if (i>-1){
//                    deleteView();
//                }else {
//                    Toast.makeText(this,"当前没有行程", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

    public String getDate(){
        StringBuffer xingcheng = new StringBuffer();
        String trasffer;
        String getStartplace = preferences.getString("xingcheng_startPlace","");
        String getStartdetailplace = preferences.getString("xingcheng_startDetailplace","");
        String getStartdate = preferences.getString("xingcheng_startDate","");
        String getStartTime = preferences.getString("xingcheng_startTime","");
        int getTrasffer = preferences.getInt("xingcheng_trasffer",0);
        String getTrasfferdetail = preferences.getString("xingcheng_trasfferDetail","");
        String getTongxing = preferences.getString("xingcheng_tongxing","");
        String getEndplace = preferences.getString("xingcheng_endPlace","");
        String getEnddetailplace = preferences.getString("xingcheng_endDetailplace","");
        String getEnddate = preferences.getString("xingcheng_endDate","");
        String getEndTime = preferences.getString("xingcheng_endTime","");

        if (getTrasffer == 0){
            trasffer = "飞机";
        }else if (getTrasffer == 1){
            trasffer = "轮船";
        }else if (getTrasffer == 1){
            trasffer = "火车";
        }else {
            trasffer = "客车";
        }


        xingcheng.append("时间:  "+getStartdate+" "+getStartTime+" - "+getEnddate+" "+getEndTime+"\n");
        xingcheng.append("位置： "+getStartplace+" "+getStartdetailplace+" - "+getEndplace+" "+getEnddetailplace+"\n");
        xingcheng.append("乘坐交通方式： "+trasffer+" "+getTrasfferdetail+"\n");
        xingcheng.append("同行人员： "+getTongxing);

        return xingcheng.toString();
    }

    public void parse(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("trajs").toString();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            xingchengAdapter = new XingchengAdapter(XingchengHistory.this, mList, listView);
            listView.setAdapter(xingchengAdapter);
            xingchengAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
