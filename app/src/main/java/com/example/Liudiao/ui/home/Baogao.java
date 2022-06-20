package com.example.Liudiao.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.example.Liudiao.R;
import com.example.Liudiao.ui.home.adpter.FamilyAdapter2;
import com.example.Liudiao.ui.home.adpter.MijieAdapter2;
import com.example.Liudiao.ui.home.adpter.RequestFamilyThread;
import com.example.Liudiao.ui.huodong.HuodongAdapter2;
import com.example.Liudiao.ui.xingcheng.Adapter.XingchengAdapter2;
import com.example.Liudiao.ui.xingcheng.RequestXingchengHisThread;
import com.example.Liudiao.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Liudiao.ui.notifications.Daiban.TAG;

public class Baogao extends Activity {

    private static String demoPath ;
    private static String newPath ;;

    StringBuffer mijie = new StringBuffer();
    StringBuffer mijie_family = new StringBuffer();
    StringBuffer xingcheng = new StringBuffer();
    StringBuffer huodong = new StringBuffer();

    private List<Map<String, String>> mList = new ArrayList<>();
    private ListView listView;
    private XingchengAdapter2 xingchengAdapter;

    private List<Map<String, String>> mList2 = new ArrayList<>();
    private ListView listView2;
    private MijieAdapter2 mijieAdapter;

    private List<Map<String, String>> mList3 = new ArrayList<>();
    private ListView listView3;
    private FamilyAdapter2 familyAdapter;

    private List<Map<String, String>> mList4 = new ArrayList<>();
    private ListView listView4;
    private HuodongAdapter2 huodongAdapter;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private String current_name;

    private String name;
    private String gender;
    private int age;
    private String idCard;
    private String phone;
    private String address;
    private String job;
    private int yimiaoTimes;

    private String zhengzhuang;
    private String other_zhengzhuang;
    private String faxianfangshi;
    private String zhengzhuang_date;

    //4.9
    private String first_date;
    private String last_date;
    private String status;
    private String other_status;
    private String tiwen;
    private String job_address;


    private ImageView r_back;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text_xingcheng;
    private TextView text_mijiejiaren;
    private TextView text_mijie;
    private TextView text_huodong;

    private String xingcheng_history;
    private String mijie_family_history;
    private String mijie_history;
    private String huodong_history;

    private TextView open;

    private Calendar calendar = null;
    private int year;
    private int month;
    private int day;
    private String time;
    private int hour;
    private int min;

    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse1(msg.obj.toString());

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse(msg.obj.toString());

        }
    };

    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse2(msg.obj.toString());

        }
    };

    private Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse3(msg.obj.toString());

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_baogao);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
//月
        month = calendar.get(Calendar.MONTH)+1;
//日
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        String year1 = String.valueOf(year);
        String month1 = String.valueOf(month);
        String day1 = String.valueOf(day);

        if (day1.length() == 1){
            day1 = "0"+day1;
        }
        if (month1.length() == 1){
            month1 = "0"+month1;
        }
        time = year1+month1+day1+hour+min;

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        current_transId = preferences.getInt("current_banliId",0);
        current_name = preferences.getString("current_banliName","");
        Log.d(TAG, "current_banliId:   "+current_transId);

        String strName = "";
        if (!current_name.equals("本人")){
            strName= current_name.substring(0,current_name.length()-"（本人）".length());
        }
        demoPath =  "/storage/emulated/0/Documents/"+time+strName+"S.doc";
        newPath = "/storage/emulated/0/Documents/"+time+strName+".doc";

        listView = (ListView) findViewById(R.id.xingcheng_list);
        listView2 = (ListView) findViewById(R.id.mijie_list);
        listView3 = (ListView) findViewById(R.id.family_list);

        open = (TextView) findViewById(R.id.open);





        String url = "http://175.23.169.100:9030/case-travel-trajectory/get";
        RequestXingchengHisThread rdt = new RequestXingchengHisThread(url,current_transId,handler1);
        rdt.start();

        String url1 = "http://175.23.169.100:9030/contacts/getContacts";
        RequestFamilyThread rdt1 = new RequestFamilyThread(url1,current_transId,handler);
        rdt1.start();

        String url2 = "http://175.23.169.100:9030/family/getFamily";
        RequestFamilyThread rdt2 = new RequestFamilyThread(url2,current_transId,handler2);
        rdt2.start();

        String url3 = "http://175.23.169.100:9030/case-aggregated-activity/get";
        RequestXingchengHisThread rdt3 = new RequestXingchengHisThread(url3,current_transId,handler3);
        rdt3.start();

        preferences = getSharedPreferences(current_transId+"baogao", Activity.MODE_PRIVATE);
        editor = preferences.edit();



        r_back = (ImageView) findViewById(R.id.back);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        text3 = (TextView)findViewById(R.id.text3);
        text4 = (TextView)findViewById(R.id.text4);
        text5 = (TextView)findViewById(R.id.text5);
        text_xingcheng = (TextView) findViewById(R.id.text_xingcheng);
        text_mijiejiaren = (TextView) findViewById(R.id.text_mijiejiaren);
        text_mijie = (TextView) findViewById(R.id.text_mijie);
        text_huodong = (TextView) findViewById(R.id.text_huodong);

        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name = preferences.getString("name","");
        gender = preferences.getString("gender","");
        //age = preferences.getInt("age",0);
        job = preferences.getString("job","");
        idCard = preferences.getString("idCard","");
        phone = preferences.getString("phone","");
        address = preferences.getString("homeplace","")+preferences.getString("homeplaceDetail","");
        yimiaoTimes = preferences.getInt("yimiaoTimes",0);
        faxianfangshi = preferences.getString("tujing","");
        zhengzhuang = preferences.getString("zhengzhuang","");
        other_zhengzhuang = preferences.getString("other_symptom","");

        //4.9
        status = preferences.getString("status","");
        other_status = preferences.getString("other_status","");
        tiwen = preferences.getString("tiwen","");
        first_date = preferences.getString("first_date","");
        last_date = preferences.getString("last_date","");
        job_address = preferences.getString("job_address","");
        zhengzhuang_date = preferences.getString("zhengzhuang_date","");

        xingcheng_history = preferences.getString("xingcheng","");
        huodong_history = preferences.getString("huodong","");
        mijie_family_history = preferences.getString("mijieFamily","");
        mijie_history = preferences.getString("mijie","");
        int age1 = 0;
        if (idCard.length()==18){
            age1 = Integer.parseInt(idCard.substring(6,10));
            age = 2021 - age1;
        }
        if (first_date.length()!=0){
            text1.setText("    我专班接到 "+name+" 核酸检测报告初筛呈阳性，采样时间为 "+first_date+"。接到信息后，专班领导高度重视，立即指派专业人员开展流行病学调查等工作，现将情况报告如下：");
        }else {
            text1.setText("    我专班接到 "+name+" 核酸检测报告初筛呈阳性。接到信息后，专班领导高度重视，立即指派专业人员开展流行病学调查等工作，现将情况报告如下：");
        }

        text2.setText("    姓名："+name+"     性别："+gender+"      年龄："+age+" 岁，\n" +
                "身份证号："+idCard+"\n联系电话："+phone+"\n" +
                "现住址："+address+"\n工作单位："+job_address+"      职业："+job+
                "\n已接种疫苗 "+yimiaoTimes+" 剂次。");

        if (other_zhengzhuang.equals("null") || other_zhengzhuang.equals("无")){
            other_zhengzhuang = "";
        }

        if (zhengzhuang_date.length()!=0){
            text3.setText("    "+zhengzhuang_date+" 以来，该病例出现 "+ zhengzhuang +" "+other_zhengzhuang+" 等症状，体温 "+tiwen+" ℃。\n  发现方式为：（ "+ faxianfangshi+" ）\n"+
                    "（1、集中隔离；2、居家隔离；3、封控区筛查；4、管控区筛查；5、社区筛查；6、主动就诊；7、主动检测；8、重点人员筛查。）");
        }else {
            text3.setText("    该病例出现 "+ zhengzhuang +" "+other_zhengzhuang+" 等症状，体温 "+tiwen+" ℃。\n  发现方式为：（ "+ faxianfangshi+" ）\n"+
                    "（1、集中隔离；2、居家隔离；3、封控区筛查；4、管控区筛查；5、社区筛查；6、主动就诊；7、主动检测；8、重点人员筛查。）");
        }


        text4.setText("    末次核酸检测阴性采样时间："+last_date+";\n    首次核酸检测阳性采样时间："+first_date+"。");

        text5.setText("    目前，此人现状（ "+ status +" ）。");

        text_xingcheng.setText(xingcheng_history);
        text_mijiejiaren.setText(mijie_family_history);
        text_mijie.setText(mijie_history);
        text_huodong.setText(huodong_history);

        final String finalStrName = strName;
        open.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                try {
                    InputStream inputStream = getAssets().open("test.doc");
                    FileUtils.writeFile(new File(demoPath), inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                doScan();

                File file = new File("/storage/emulated/0/Documents/"+time+ finalStrName +".doc");

                Bundle bundle = new Bundle();
                bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // 打开模式
                bundle.putBoolean(WpsModel.ENTER_REVISE_MODE, true); // 以修订模式打开文档
                bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 文件关闭时是否发送广播
                bundle.putBoolean(WpsModel.SEND_SAVE_BROAD, true); // 文件保存时是否发送广播
                bundle.putBoolean(WpsModel.HOMEKEY_DOWN, true); // 单机home键是否发送广播
                bundle.putBoolean(WpsModel.BACKKEY_DOWN, true); // 单机back键是否发送广播
                bundle.putBoolean(WpsModel.SAVE_PATH, true); // 文件这次保存的路径
                bundle.putString(WpsModel.THIRD_PACKAGE, WpsModel.PackageName.NORMAL); // 第三方应用的包名，用于对改应用合法性的验证

                openFile(Baogao.this,file);
            }
        });
    }

    public static void openFile(Context context, File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uriForFile;
        if (Build.VERSION.SDK_INT > 23){
            //Android 7.0之后
            uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);//给目标文件临时授权
        }else {
            uriForFile = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//系统会检查当前所有已创建的Task中是否有该要启动的Activity的Task;
        // 若有，则在该Task上创建Activity；若没有则新建具有该Activity属性的Task，并在该新建的Task上创建Activity。
        intent.setDataAndType(uriForFile,"application/msword");
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doScan(){
        for (int i = 0;i<mList2.size();i++){
            Map<String, String> map = mList2.get(i);
            String str = "";
            String name = map.get("name");
            String mobile = map.get("mobile");
            String idCard = map.get("card_id")+"";

            String contact_type = map.get("contact_type");
            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length()!=0 && !contact_type.equals("null")) {
                String sym = contact_type;
                if (sym.length() == 1) {
                    if (Integer.parseInt(sym) == 1) {
                        stringBuffer.append("同吃");
                    } else if (Integer.parseInt(sym) == 2) {
                        stringBuffer.append("同行");
                    } else if (Integer.parseInt(sym) == 3) {
                        stringBuffer.append("同住");
                    } else {
                        stringBuffer.append("同事");
                    }
                } else {
                    String[] split = sym.split(",");
                    for (int j = 0; j < split.length; j++) {
                        if (Integer.parseInt(split[j]) == 1) {
                            stringBuffer.append("同吃 ");
                        } else if (Integer.parseInt(split[j]) == 2) {
                            stringBuffer.append("同行 ");
                        } else if (Integer.parseInt(split[j]) == 3) {
                            stringBuffer.append("同住 ");
                        } else {
                            stringBuffer.append("同事");
                        }
                    }
                }
            }
            String address =map.get("address")+"";
            String address_detail =map.get("address_detail")+"";
            String dizhi;
            if (address.equals("null") || address.equals("")){
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = "";
                }else {
                    dizhi = address_detail;
                }
            }else {
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = address;
                }else {
                    dizhi = address+address_detail;
                }
            }

            if (!idCard.equals("null")&&idCard.length()!=0){
                if (!dizhi.equals("")){
                    mijie.append("该病例曾与其非家人密接 "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +",身份证号是 "+map.get("card_id").toString()+"，住在 "+dizhi+"。\n");
                }else {
                    mijie.append("该病例曾与其非家人密接 "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +",身份证号是 "+map.get("card_id").toString()+"。\n");
                }
            }else {
                mijie.append("该病例曾与其非家人密接 "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()+"。\n");
            }
        }

        for (int i = 0;i<mList3.size();i++){
            Map<String, String> map = mList3.get(i);
            String str = "";
            String name = map.get("name");
            String contact_type = map.get("contact_type");
            int relationText = Integer.parseInt(map.get("relation"));
            String mobile = map.get("mobile")+"";
            String idCard = map.get("card_id")+"";

            String relation;
            if (relationText ==0){
                relation="未知";
            }else if (relationText == 1){
                relation="父亲";
            }else if (relationText == 2){
                relation="母亲";
            }else if (relationText == 3){
                relation="儿子";
            }else if (relationText == 4){
                relation="女儿";
            }else if (relationText == 5){
                relation="夫妻";
            }else if (relationText == 6){
                relation="兄弟";
            }else if (relationText == 7){
                relation="姐妹";
            }else if (relationText == 8){
                relation="（外）祖父母";
            }else if (relationText == 9){
                relation="（外）孙子孙女";
            }else {
                relation="（其他关系）";
            }

            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length()!=0 && !contact_type.equals("null")) {
                String sym = contact_type;
                if (sym.length() == 1) {
                    if (Integer.parseInt(sym) == 1) {
                        stringBuffer.append("同吃");
                    } else if (Integer.parseInt(sym) == 2) {
                        stringBuffer.append("同行");
                    } else if (Integer.parseInt(sym) == 3) {
                        stringBuffer.append("同住");
                    } else {
                        stringBuffer.append("同事");
                    }
                } else {
                    String[] split = sym.split(",");
                    for (int j = 0; j < split.length; j++) {
                        if (Integer.parseInt(split[j]) == 1) {
                            stringBuffer.append("同吃 ");
                        } else if (Integer.parseInt(split[j]) == 2) {
                            stringBuffer.append("同行 ");
                        } else if (Integer.parseInt(split[j]) == 3) {
                            stringBuffer.append("同住 ");
                        } else {
                            stringBuffer.append("同事");
                        }
                    }
                }
            }
            String address = map.get("address")+"";
            String address_detail =map.get("address_detail")+"";
            String dizhi;
            if (address.equals("null") || address.equals("")){
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = "";
                }else {
                    dizhi = address_detail;
                }
            }else {
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = address;
                }else {
                    dizhi = address+address_detail;
                }
            }
            if (!idCard.equals("null")&&idCard.length()!=0){
                if (!dizhi.equals("")){
                    mijie_family.append("该病例曾与其"+relation+" "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +",身份证号是 "+map.get("card_id").toString()+"，住在 "+dizhi+"。\n");

                }else {
                    mijie_family.append("该病例曾与其"+relation+" "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()
                            +",身份证号是 "+map.get("card_id").toString()+"。\n");
                }
            }else {
                mijie_family.append("该病例曾与其"+relation+" "+map.get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+map.get("mobile").toString()+"。\n");
            }


        }

        for (int i = 0;i<mList.size();i++){
            Map<String, String> map = mList.get(i);
            StringBuffer strTime = new StringBuffer();
            strTime.append(map.get("start_date"));
            strTime.append(" "+map.get("start_time")+" - ");
            strTime.append(map.get("end_date"));
            strTime.append(" "+map.get("end_time")+" - ");

            StringBuffer strPlace = new StringBuffer();
            strPlace.append(map.get("start"));
            strPlace.append(" "+map.get("start_detail")+" - ");
            strPlace.append(map.get("end"));
            strPlace.append(" "+map.get("end_detail"));

            String tra = null;
            if (map.get("traffic").toString().equals("1")){
                tra ="飞机";
            }else if (map.get("traffic").toString().equals("2")){
                tra = "火车";
            }else if (map.get("traffic").toString().equals("3")){
                tra = "客车";
            }else if (map.get("traffic").toString().equals("4")){
                tra = "轮船";
            }else if (map.get("traffic").toString().equals("5")){
                tra = "出租车";
            }else if (map.get("traffic").toString().equals("6")){
                tra = "私家车";
            }else if (map.get("traffic").toString().equals("7")){
                tra = "公交车";
            }else if (map.get("traffic").toString().equals("8")){
                tra = "市内轨道交通";
            }else if (map.get("traffic").toString().equals("9")){
                tra = "骑行";
            }else if (map.get("traffic").toString().equals("10")){
                tra = "步行";
            }
            xingcheng.append("该病例曾于 "+strTime.toString()+","+"乘坐"+tra+" "+map.get("tra_detail").toString()+","
                    +"从"+map.get("start").toString()+map.get("start_detail").toString()
                    +"到"+map.get("end").toString()+map.get("end_detail").toString()+"。");
            if((map.get("peers")+"").length()!=0 && !(map.get("peers")+"").equals("无")){
                xingcheng.append("路上与 "+map.get("peers").toString()+"同行。\n");
            }else {
                xingcheng.append("\n");
            }
        }

        for (int i = 0;i<mList4.size();i++){
            Map<String, String> map = mList4.get(i);
            huodong.append("该病例曾于 "+map.get("acti_start_date")+" "+map.get("acti_starttime")+" - "
                    +map.get("acti_end_date")+" "+map.get("acti_endtime")+"，在 "+map.get("acti_place")
                    +map.get("actipla_detail")+" 参加了聚集性活动。");
            if ((map.get("acti_detail")+"").length()!=0 && !(map.get("acti_detail")+"").equals("null")){
                huodong.append("情况描述："+map.get("acti_detail")+"。\n");
            }else{
                huodong.append("\n");
            }
        }

        //获取模板文件
        File demoFile=new File(demoPath);
        //创建生成的文件
        File newFile=new File(newPath);
        Map<String, String> map = new HashMap<String, String>();
        map.put("$NAME$", name);
        map.put("$SEX$", gender);
        map.put("$AGE$", String.valueOf(age));
        map.put("$IDCARD$", idCard);
        map.put("$PHONE$", phone);
        map.put("$ADDRESS$", address);
        map.put("$JOB$", job);
        map.put("$TIME$", String.valueOf(yimiaoTimes));
        map.put("$ZHENGZHUANG$", zhengzhuang);
        map.put("$TUJING$", faxianfangshi);
        map.put("$XINGCHENG$", xingcheng.toString());
        map.put("$HUODONG$",huodong.toString());
        map.put("$MIJIEFAMILY$",mijie_family.toString());
        map.put("$MIJIE$", mijie.toString());
        map.put("$DATE$",year+"年"+month+"月"+day+"日");

        //4.9
        map.put("$LASTDATE$",last_date);
        map.put("$FIRSTDATE$",first_date);
        map.put("$STATUS$",status);
        //map.put("$OTHERSTATUS$",other_status);
        map.put("$TIWEN$",tiwen);
        map.put("$JOBADDRESS$",job_address);
        if (first_date.length()!=0){
            map.put("$CAIYANGTIME$","采样时间为 "+first_date+"。");
        }else {
            map.put("$CAIYANGTIME$","");
        }
        if (zhengzhuang_date.length()!=0){
            map.put("$STATUSDATE$",zhengzhuang_date+" 以来，");
        }else {
            map.put("$STATUSDATE$","");
        }

        writeDoc(demoFile,newFile,map);
        //查看
        //doOpenWord();
    }

    /**
     * demoFile 模板文件
     * newFile 生成文件
     * map 要填充的数据
     * */
    public void writeDoc(File demoFile ,File newFile ,Map<String, String> map)
    {
        try
        {
            FileInputStream in = new FileInputStream(demoPath);
            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // 替换文本内容
            for(Map.Entry<String, String> entry : map.entrySet())
            {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void parse1(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("trajs").toString();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            xingchengAdapter = new XingchengAdapter2(Baogao.this, mList, listView);
            listView.setAdapter(xingchengAdapter);
            xingchengAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            //mList.clear();
            mList2 = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            mijieAdapter = new MijieAdapter2(Baogao.this, mList2, listView2);
            listView2.setAdapter(mijieAdapter);
            mijieAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse2(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            //mList.clear();
            mList3 = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            familyAdapter = new FamilyAdapter2(Baogao.this, mList3, listView3);
            listView3.setAdapter(familyAdapter);
            setListViewHeightBasedOnChildren(listView3);
            familyAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void parse3(String var) {
        try {

            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("activities").toString();
            //mList.clear();
            mList4 = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            huodongAdapter = new HuodongAdapter2(Baogao.this, mList4, listView4);
            listView4.setAdapter(huodongAdapter);
            setListViewHeightBasedOnChildren(listView4);
            huodongAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
