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
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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

import static java.lang.reflect.Array.getInt;

public class Baogaonew extends AppCompatActivity {

    private static String demoPath = "/storage/emulated/0/Documents/liudiaoS.doc";
    private static String newPath = "/storage/emulated/0/Documents/liudiao.doc";
    ;
    private List<Map<String, String>> mList = new ArrayList<>();
    private List<Map<String, String>> mList2 = new ArrayList<>();
    private List<Map<String, String>> mList3 = new ArrayList<>();
    private List<Map<String, String>> mList4 = new ArrayList<>();
    StringBuffer mijie = new StringBuffer();
    StringBuffer mijie_family = new StringBuffer();
    StringBuffer xingcheng = new StringBuffer();
    StringBuffer huodong = new StringBuffer();

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_transId;
    private String current_name;

    private Calendar calendar = null;
    private int year;
    private int month;
    private int day;
    private String time;
    private int hour;
    private int min;


    private ImageView r_back;
    private TextView open;
    private TextView titou;
    private TextView text1;
    private TextView text2;
    private TextView text_yimiao;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text_xingcheng;
    private TextView text_mijiejiaren;
    private TextView text_mijie;
    private TextView text_huodong;

    private String titou_str = "";


    private Handler handler5 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            text1 = (TextView) findViewById(R.id.text1);
            titou = (TextView) findViewById(R.id.titou);
            text2 = (TextView) findViewById(R.id.text2);
            text_yimiao = (TextView) findViewById(R.id.text_yimiao);
            text3 = (TextView) findViewById(R.id.text3);
            text4 = (TextView) findViewById(R.id.text4);
            text5 = (TextView) findViewById(R.id.text5);
            text_xingcheng = (TextView) findViewById(R.id.text_xingcheng);
            text_mijiejiaren = (TextView) findViewById(R.id.text_mijiejiaren);
            text_mijie = (TextView) findViewById(R.id.text_mijie);
            text_huodong = (TextView) findViewById(R.id.text_huodong);

            int code = b.getInt("code");
            int info_num = b.getInt("info_num");
            int code_jiankang = b.getInt("code_jiankang");
            int code_yimiao = b.getInt("code_yimiao");
            int code_bingli = b.getInt("code_bingli");
            int mem_num = b.getInt("mem_num");
            int code_family = b.getInt("code_family");
            int contacts_num = b.getInt("contacts_num");
            int code_mijie = b.getInt("code_mijie");
            int journey_num = b.getInt("journey_num");
            int code_xingcheng = b.getInt("code_xingcheng");
            int acti_num = b.getInt("acti_num");
            int code_huodong = b.getInt("code_huodong");
            String card_id = "";
            String name = "";
            int gender;
            int age;
            int type;
            String tel = "";
            int job;
            String birthDate = "";
            String address = "";
            String address_detail = "";
            String job_address = "";
            String job_str = "";
            String symptom_start_date = "";
            String yimiao_times = "";
            String way;
            String way_baogao = "";
            String other_way;
            String symptom;
            StringBuffer zhengzhuang = new StringBuffer("");
            String zhengzhuang_str = "";
            String sym = "";
            String other_symptom;
            String temperature;
            String last_negative_date;
            String first_positive_date;
            String last_baogao = "";
            String first_baogao = "";
            String status;
            String status_baogao = "";
            String other_status;
            String[] family_name;
            String[] family_mobile;
            String[] family_card_id;
            String[] family_relation;
            String[] family_address;
            String[] family_address_detail;
            String[] family_contact_type;
            String[] mijie_name;
            String[] mijie_mobile;
            String[] mijie_card_id;
            String[] mijie_address;
            String[] mijie_address_detail;
            String[] mijie_contact_type;
            String[] start;
            String[] end;
            String[] start_detail;
            String[] end_detail;
            String[] start_date;
            String[] start_time;
            String[] end_date;
            String[] end_time;
            String[] traffic;
            String[] tra_detail;
            String[] peers;
            String[] acti_place;
            String[] actipla_detail;
            String[] acti_start_date;
            String[] acti_starttime;
            String[] acti_end_date;
            String[] acti_endtime;
            if (code == 0) {
                if (info_num == 1) {
                    card_id = b.getString("card_id");
                    name = b.getString("name");
                    gender = b.getInt("gender");
                    age = b.getInt("age");
                    type = b.getInt("type");
                    tel = b.getString("tel");
                    job = b.getInt("job");
                    address = b.getString("address");
                    address_detail = b.getString("address_detail");
                    job_address = b.getString("job_address");
                    titou_str = address.split(" ")[1];
                    if (job == 0) {
                        job_str = "??????";
                    } else if (job == 1) {
                        job_str = "????????????";
                    } else if (job == 2) {
                        job_str = "????????????";
                    } else if (job == 3) {
                        job_str = "????????????????????????";
                    } else if (job == 4) {
                        job_str = "??????";
                    } else if (job == 5) {
                        job_str = "??????????????????";
                    } else if (job == 6) {
                        job_str = "???????????????";
                    } else if (job == 7) {
                        job_str = "????????????";
                    } else if (job == 8) {
                        job_str = "????????????";
                    } else if (job == 9) {
                        job_str = "??????";
                    } else if (job == 10) {
                        job_str = "??????";
                    } else if (job == 11) {
                        job_str = "??????";
                    } else if (job == 12) {
                        job_str = "???????????????";
                    } else if (job == 13) {
                        job_str = "????????????";
                    } else if (job == 14) {
                        job_str = "????????????";
                    } else if (job == 15) {
                        job_str = "???????????????";
                    } else if (job == 16) {
                        job_str = "??????";
                    } else {
                        job_str = "??????";
                    }
                    String sex = "";
                    if (gender == 1) {
                        sex = "???";
                    } else {
                        sex = "???";
                    }
                    if (job_address.equals("null")) {
                        job_address = "";
                    }
                    text2.setText("    ?????????" + name + "     ?????????" + sex + "      ?????????" + age + " ??????\n" +
                            "???????????????" + card_id + "\n???????????????" + tel + "\n" +
                            "????????????" + address + address_detail + "\n???????????????" + job_address + "      ?????????" + job_str + "???");
                    titou.setText(titou_str + "???????????????????????????");
                }

                if (code_yimiao == 0) {
                    yimiao_times = b.getString("times");
                    if (!yimiao_times.equals("null")) {
                        text_yimiao.setText("??????????????? " + yimiao_times + " ?????????");
                    }

                }
                if (code_jiankang == 0) {
                    symptom_start_date = b.getString("symptom_start_date");
                }

                if (code_bingli == 0) {
                    way = b.getString("way");
                    other_way = b.getString("other_way");
                    symptom = b.getString("symptom");
                    other_symptom = b.getString("other_symptom");
                    temperature = b.getString("temperature");
                    last_negative_date = b.getString("last_negative_date");
                    first_positive_date = b.getString("first_positive_date");
                    status = b.getString("status");
                    other_status = b.getString("other_status");

                    if (symptom.length() != 0 && !symptom.equals("null")) {
                        if (symptom.length() == 1) {
                            if (Integer.parseInt(symptom) == 1) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 2) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 3) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 4) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 5) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 6) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 7) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 8) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 9) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 10) {
                                sym = "????????????";
                            } else if (Integer.parseInt(symptom) == 11) {
                                sym = "????????????";
                            } else if (Integer.parseInt(symptom) == 12) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 13) {
                                sym = "????????????";
                            } else if (Integer.parseInt(symptom) == 14) {
                                sym = "????????????";
                            } else if (Integer.parseInt(symptom) == 15) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 16) {
                                sym = "????????????";
                            } else if (Integer.parseInt(symptom) == 17) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 18) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 19) {
                                sym = "????????????";
                            } else if (Integer.parseInt(symptom) == 20) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 21) {
                                sym = "??????";
                            } else if (Integer.parseInt(symptom) == 22) {
                                sym = "??????";
                            } else {
                                sym = "??????";
                            }
                        } else {
                            String[] split = symptom.split(",");
                            for (int i = 0; i < split.length; i++) {
                                if (Integer.parseInt(split[i]) == 1) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 2) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 3) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 4) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 5) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 6) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 7) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 8) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 9) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 10) {
                                    zhengzhuang.append("????????????,");
                                }
                                if (Integer.parseInt(split[i]) == 11) {
                                    zhengzhuang.append("????????????,");
                                }
                                if (Integer.parseInt(split[i]) == 12) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 13) {
                                    zhengzhuang.append("????????????,");
                                }
                                if (Integer.parseInt(split[i]) == 14) {
                                    zhengzhuang.append("????????????,");
                                }
                                if (Integer.parseInt(split[i]) == 15) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 16) {
                                    zhengzhuang.append("????????????,");
                                }
                                if (Integer.parseInt(split[i]) == 17) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 18) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 19) {
                                    zhengzhuang.append("????????????,");
                                }
                                if (Integer.parseInt(split[i]) == 20) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 21) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 22) {
                                    zhengzhuang.append("??????,");
                                }
                                if (Integer.parseInt(split[i]) == 23) {
                                    zhengzhuang.append("??????,");
                                }
                            }
                            if (zhengzhuang.toString().length() != 0) {
                                zhengzhuang_str = zhengzhuang.toString().substring(0, zhengzhuang.toString().length() - 1);
                            }
                            if (zhengzhuang_str.equals("null")) {
                                zhengzhuang_str = "";
                            }

                        }
                    }
                    if (!way.equals("") && !way.equals("null")) {
                        if (Integer.parseInt(way) == 0) {
                            way_baogao = "????????????";
                        } else if (Integer.parseInt(way) == 1) {
                            way_baogao = "????????????";
                        } else if (Integer.parseInt(way) == 2) {
                            way_baogao = "???????????????";
                        } else if (Integer.parseInt(way) == 3) {
                            way_baogao = "???????????????";
                        } else if (Integer.parseInt(way) == 4) {
                            way_baogao = "????????????";
                        } else if (Integer.parseInt(way) == 5) {
                            way_baogao = "????????????";
                        } else if (Integer.parseInt(way) == 6) {
                            way_baogao = "????????????";
                        } else if (Integer.parseInt(way) == 7) {
                            way_baogao = "??????????????????";
                        } else {
                            if (other_way.equals("null")) {
                                way_baogao = "????????????";
                            } else {
                                way_baogao = other_way;
                            }

                        }
                    }
                    if (!status.equals("") && !status.equals("null")) {
                        if (Integer.parseInt(status) == 1) {
                            status_baogao = "???????????????????????????";
                        } else if (Integer.parseInt(status) == 2) {
                            status_baogao = "?????????????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 3) {
                            status_baogao = "??????????????????????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 4) {
                            status_baogao = "?????????????????????????????????????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 5) {
                            status_baogao = "?????????????????????????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 6) {
                            status_baogao = "????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 7) {
                            status_baogao = "???????????????????????????";
                        } else if (Integer.parseInt(status) == 8) {
                            status_baogao = "??????????????????????????????????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 9) {
                            status_baogao = "??????????????????????????????";
                        } else if (Integer.parseInt(status) == 10) {
                            status_baogao = "????????????";
                        } else if (Integer.parseInt(status) == 11) {
                            status_baogao = "?????????????????????????????????????????????????????????????????????????????????????????????????????????";
                        } else if (Integer.parseInt(status) == 12) {
                            status_baogao = "???????????????";
                        } else if (Integer.parseInt(status) == 13) {
                            status_baogao = "???????????????????????????????????????";
                        } else {
                            if (other_status.equals("null")) {
                                status_baogao = "??????";
                            } else {
                                status_baogao = other_status;
                            }
                        }
                    }


                    if (last_negative_date.length() != 0 && !last_negative_date.equals("null")) {
                        String[] split = last_negative_date.split("-");
                        last_baogao = Integer.parseInt(split[1]) + " ??? " + Integer.parseInt(split[2]) + " ??? ";
                    } else {
                        last_baogao = "";
                    }

                    if (first_positive_date.length() != 0 && !first_positive_date.equals("null")) {
                        String[] split = first_positive_date.split("-");
                        first_baogao = Integer.parseInt(split[1]) + " ??? " + Integer.parseInt(split[2]) + " ??? ";
                    } else {
                        first_baogao = "";
                    }


                    if (first_baogao.length() != 0) {
                        text1.setText("        ??????????????? " + current_name + " ??????????????????????????????????????????????????? " + first_baogao + "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                    } else {
                        text1.setText("        ??????????????? " + current_name + " ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                    }

                    if (symptom_start_date.length() != 0 && !symptom_start_date.equals("null")) {
                        String[] split = symptom_start_date.split("-");
                        String s = Integer.parseInt(split[1]) + " ??? " + Integer.parseInt(split[2]) + " ??? ";
                        if (other_symptom.equals("null")) {
                            other_symptom = "";
                        }
                        text3.setText("        " + s + " ???????????????????????? " + sym + " " + zhengzhuang_str + " " + other_symptom + "?????????????????? " + temperature + " ??????\n  ????????????????????? " + way_baogao + " ???\n" +
                                "???1??????????????????2??????????????????3?????????????????????4?????????????????????5??????????????????6??????????????????7??????????????????8???????????????????????????");
                    } else {
                        if (other_symptom.equals("null")) {
                            other_symptom = "";
                        }
                        text3.setText("        ???????????????" + sym + " " + zhengzhuang_str + " " + other_symptom + "?????????????????? " + temperature + " ??????\n  ????????????????????? " + way_baogao + " ???\n" +
                                "???1??????????????????2??????????????????3?????????????????????4?????????????????????5??????????????????6??????????????????7??????????????????8???????????????????????????");
                    }

                    text4.setText("        ???????????????????????????????????????" + last_baogao + ";\n        ???????????????????????????????????????" + first_baogao + "???");
                    text5.setText("        ???????????????????????? " + status_baogao + " ??????");

                }

            }
        }
    };


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
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        String year1 = String.valueOf(year);
        String month1 = String.valueOf(month);
        String day1 = String.valueOf(day);
        if (day1.length() == 1) {
            day1 = "0" + day1;
        }
        if (month1.length() == 1) {
            month1 = "0" + month1;
        }
        time = year1 + month1 + day1 + hour + min;

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        current_transId = preferences.getInt("current_banliId", 0);
        current_name = preferences.getString("current_banliName", "");

        String strName = "";
        if (!current_name.equals("??????")) {
            strName = current_name;
        }
        demoPath = "/storage/emulated/0/Documents/" + time + strName + "S.doc";
        newPath = "/storage/emulated/0/Documents/" + time + strName + ".doc";

        String url5 = "http://175.23.169.100:9030/get-all";
        RequestAllThread rdt5 = new RequestAllThread(url5, current_transId, handler5);
        rdt5.start();

        String url = "http://175.23.169.100:9030/case-travel-trajectory/get";
        RequestXingchengHisThread rdt = new RequestXingchengHisThread(url, current_transId, handler1);
        rdt.start();

        String url1 = "http://175.23.169.100:9030/contacts/getContacts";
        RequestFamilyThread rdt1 = new RequestFamilyThread(url1, current_transId, handler);
        rdt1.start();

        String url2 = "http://175.23.169.100:9030/family/getFamily";
        RequestFamilyThread rdt2 = new RequestFamilyThread(url2, current_transId, handler2);
        rdt2.start();

        String url3 = "http://175.23.169.100:9030/case-aggregated-activity/get";
        RequestXingchengHisThread rdt3 = new RequestXingchengHisThread(url3, current_transId, handler3);
        rdt3.start();

        open = (TextView) findViewById(R.id.open);
        r_back = (ImageView) findViewById(R.id.back);
        text1 = (TextView) findViewById(R.id.text1);
        titou = (TextView) findViewById(R.id.titou);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text_xingcheng = (TextView) findViewById(R.id.text_xingcheng);
        text_mijiejiaren = (TextView) findViewById(R.id.text_mijiejiaren);
        text_mijie = (TextView) findViewById(R.id.text_mijie);
        text_huodong = (TextView) findViewById(R.id.text_huodong);

        //firstScan();
        text_xingcheng.setText(xingcheng.toString());
        text_huodong.setText(huodong.toString());
        text_mijiejiaren.setText(mijie_family);
        text_mijie.setText(mijie);

        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final String finalStrName = strName;
        open.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                try {
                    InputStream inputStream = getAssets().open("test1.doc");
                    FileUtils.writeFile(new File(demoPath), inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                doScan();

                File file = new File("/storage/emulated/0/Documents/" + time + finalStrName + ".doc");

                Bundle bundle = new Bundle();
                bundle.putString(WpsModel.OPEN_MODE, WpsModel.OpenMode.NORMAL); // ????????????
                bundle.putBoolean(WpsModel.ENTER_REVISE_MODE, true); // ???????????????????????????
                bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // ?????????????????????????????????
                bundle.putBoolean(WpsModel.SEND_SAVE_BROAD, true); // ?????????????????????????????????
                bundle.putBoolean(WpsModel.HOMEKEY_DOWN, true); // ??????home?????????????????????
                bundle.putBoolean(WpsModel.BACKKEY_DOWN, true); // ??????back?????????????????????
                bundle.putBoolean(WpsModel.SAVE_PATH, true); // ???????????????????????????
                bundle.putString(WpsModel.THIRD_PACKAGE, WpsModel.PackageName.NORMAL); // ???????????????????????????????????????????????????????????????

                openFile(Baogaonew.this, file);
            }
        });
    }

    private void firstScan() {
        for (int i = 0; i < mList2.size(); i++) {
            Map<String, String> map = mList2.get(i);
            String str = "";
            String name = map.get("name");
            String mobile = map.get("mobile");
            String idCard = map.get("card_id") + "";

            String contact_type = map.get("contact_type");
            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length() != 0 && !contact_type.equals("null")) {
                String sym = contact_type;
                if (sym.length() == 1) {
                    if (Integer.parseInt(sym) == 1) {
                        stringBuffer.append("??????");
                    } else if (Integer.parseInt(sym) == 2) {
                        stringBuffer.append("??????");
                    } else if (Integer.parseInt(sym) == 3) {
                        stringBuffer.append("??????");
                    } else {
                        stringBuffer.append("??????");
                    }
                } else {
                    String[] split = sym.split(",");
                    for (int j = 0; j < split.length; j++) {
                        if (Integer.parseInt(split[j]) == 1) {
                            stringBuffer.append("?????? ");
                        } else if (Integer.parseInt(split[j]) == 2) {
                            stringBuffer.append("?????? ");
                        } else if (Integer.parseInt(split[j]) == 3) {
                            stringBuffer.append("?????? ");
                        } else {
                            stringBuffer.append("??????");
                        }
                    }
                }
            }
            String address = map.get("address") + "";
            String address_detail = map.get("address_detail") + "";
            String dizhi;
            if (address.equals("null") || address.equals("")) {
                if (address_detail.equals("null") || address_detail.equals("")) {
                    dizhi = "";
                } else {
                    dizhi = address_detail;
                }
            } else {
                if (address_detail.equals("null") || address_detail.equals("")) {
                    dizhi = address;
                } else {
                    dizhi = address + address_detail;
                }
            }

            if (!idCard.equals("null") && idCard.length() != 0) {
                if (!dizhi.equals("")) {
                    mijie.append("????????????????????????????????? " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                            + ",??????????????? " + map.get("card_id").toString() + "????????? " + dizhi + "???\n");
                } else {
                    mijie.append("????????????????????????????????? " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                            + ",??????????????? " + map.get("card_id").toString() + "???\n");
                }
            } else {
                mijie.append("????????????????????????????????? " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString() + "???\n");
            }
        }

        for (int i = 0; i < mList3.size(); i++) {
            Map<String, String> map = mList3.get(i);
            String str = "";
            String name = map.get("name");
            String contact_type = map.get("contact_type");
            int relationText = Integer.parseInt(map.get("relation"));
            String mobile = map.get("mobile") + "";
            String idCard = map.get("card_id") + "";

            String relation;
            if (relationText == 0) {
                relation = "??????";
            } else if (relationText == 1) {
                relation = "??????";
            } else if (relationText == 2) {
                relation = "??????";
            } else if (relationText == 3) {
                relation = "??????";
            } else if (relationText == 4) {
                relation = "??????";
            } else if (relationText == 5) {
                relation = "??????";
            } else if (relationText == 6) {
                relation = "??????";
            } else if (relationText == 7) {
                relation = "??????";
            } else if (relationText == 8) {
                relation = "??????????????????";
            } else if (relationText == 9) {
                relation = "?????????????????????";
            } else {
                relation = "??????????????????";
            }

            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length() != 0 && !contact_type.equals("null")) {
                String sym = contact_type;
                if (sym.length() == 1) {
                    if (Integer.parseInt(sym) == 1) {
                        stringBuffer.append("??????");
                    } else if (Integer.parseInt(sym) == 2) {
                        stringBuffer.append("??????");
                    } else if (Integer.parseInt(sym) == 3) {
                        stringBuffer.append("??????");
                    } else {
                        stringBuffer.append("??????");
                    }
                } else {
                    String[] split = sym.split(",");
                    for (int j = 0; j < split.length; j++) {
                        if (Integer.parseInt(split[j]) == 1) {
                            stringBuffer.append("?????? ");
                        } else if (Integer.parseInt(split[j]) == 2) {
                            stringBuffer.append("?????? ");
                        } else if (Integer.parseInt(split[j]) == 3) {
                            stringBuffer.append("?????? ");
                        } else {
                            stringBuffer.append("??????");
                        }
                    }
                }
            }
            String address = map.get("address") + "";
            String address_detail = map.get("address_detail") + "";
            String dizhi;
            if (address.equals("null") || address.equals("")) {
                if (address_detail.equals("null") || address_detail.equals("")) {
                    dizhi = "";
                } else {
                    dizhi = address_detail;
                }
            } else {
                if (address_detail.equals("null") || address_detail.equals("")) {
                    dizhi = address;
                } else {
                    dizhi = address + address_detail;
                }
            }
            if (!idCard.equals("null") && idCard.length() != 0) {
                if (!dizhi.equals("")) {
                    mijie_family.append("??????????????????" + relation + " " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                            + ",??????????????? " + map.get("card_id").toString() + "????????? " + dizhi + "???\n");

                } else {
                    mijie_family.append("??????????????????" + relation + " " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                            + ",??????????????? " + map.get("card_id").toString() + "???\n");
                }
            } else {
                mijie_family.append("??????????????????" + relation + " " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString() + "???\n");
            }


        }

        for (int i = 0; i < mList.size(); i++) {
            Map<String, String> map = mList.get(i);
            StringBuffer strTime = new StringBuffer();
            strTime.append(map.get("start_date"));
            strTime.append(" " + map.get("start_time") + " - ");
            strTime.append(map.get("end_date"));
            strTime.append(" " + map.get("end_time") + " - ");

            StringBuffer strPlace = new StringBuffer();
            strPlace.append(map.get("start"));
            strPlace.append(" " + map.get("start_detail") + " - ");
            strPlace.append(map.get("end"));
            strPlace.append(" " + map.get("end_detail"));

            String tra = null;
            if (map.get("traffic").toString().equals("1")) {
                tra = "??????";
            } else if (map.get("traffic").toString().equals("2")) {
                tra = "??????";
            } else if (map.get("traffic").toString().equals("3")) {
                tra = "??????";
            } else if (map.get("traffic").toString().equals("4")) {
                tra = "??????";
            } else if (map.get("traffic").toString().equals("5")) {
                tra = "?????????";
            } else if (map.get("traffic").toString().equals("6")) {
                tra = "?????????";
            } else if (map.get("traffic").toString().equals("7")) {
                tra = "?????????";
            } else if (map.get("traffic").toString().equals("8")) {
                tra = "??????????????????";
            } else if (map.get("traffic").toString().equals("9")) {
                tra = "??????";
            } else if (map.get("traffic").toString().equals("10")) {
                tra = "??????";
            }
            xingcheng.append("??????????????? " + strTime.toString() + "," + "??????" + tra + " " + map.get("tra_detail").toString() + ","
                    + "???" + map.get("start").toString() + map.get("start_detail").toString()
                    + "???" + map.get("end").toString() + map.get("end_detail").toString() + "???");
            if ((map.get("peers") + "").length() != 0 && !(map.get("peers") + "").equals("???")) {
                xingcheng.append("????????? " + map.get("peers").toString() + "?????????\n");
            } else {
                xingcheng.append("\n");
            }
        }

        for (int i = 0; i < mList4.size(); i++) {
            Map<String, String> map = mList4.get(i);
            huodong.append("??????????????? " + map.get("acti_start_date") + " " + map.get("acti_starttime") + " - "
                    + map.get("acti_end_date") + " " + map.get("acti_endtime") + "?????? " + map.get("acti_place")
                    + map.get("actipla_detail") + " ???????????????????????????");
            if ((map.get("acti_detail") + "").length() != 0 && !(map.get("acti_detail") + "").equals("null")) {
                huodong.append("???????????????" + map.get("acti_detail") + "???\n");
            } else {
                huodong.append("\n");
            }
        }
    }

    public static void openFile(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uriForFile;
        if (Build.VERSION.SDK_INT > 23) {
            //Android 7.0??????
            uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);//???????????????????????????
        } else {
            uriForFile = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//???????????????????????????????????????Task???????????????????????????Activity???Task;
        // ??????????????????Task?????????Activity??????????????????????????????Activity?????????Task?????????????????????Task?????????Activity???
        intent.setDataAndType(uriForFile, "application/msword");
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void doScan() {
        //??????????????????
        File demoFile = new File(demoPath);
        //?????????????????????
        File newFile = new File(newPath);
        Map<String, String> map = new HashMap<String, String>();
        map.put("$TITOU$", titou.getText().toString());
        map.put("$TEXT1$", text1.getText().toString());
        map.put("$TEXT2$", text2.getText().toString());
        map.put("$TEXTYIMIAO$", text_yimiao.getText().toString());
        map.put("$TEXT3$", text3.getText().toString());
        map.put("$XINGCHENG$", xingcheng.toString());
        map.put("$HUODONG$", huodong.toString());
        map.put("$MIJIEFAMILY$", mijie_family.toString());
        map.put("$MIJIE$", mijie.toString());
        map.put("$TEXT4$", text4.getText().toString());
        map.put("$STATUS$", text5.getText().toString());
        map.put("$TIWEI$", titou_str + "??????????????????????????????");
        map.put("$DATE$", year + "???" + month + "???" + day + "???");
        writeDoc(demoFile, newFile, map);
        //??????
        //doOpenWord();
    }

    /**
     * demoFile ????????????
     * newFile ????????????
     * map ??????????????????
     */
    public void writeDoc(File demoFile, File newFile, Map<String, String> map) {
        try {
            FileInputStream in = new FileInputStream(demoPath);
            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // ??????word????????????
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // ??????????????????
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // ???????????????
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse1(String var) {
        try {
            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("trajs").toString();
            mList = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            for (int i = 0; i < mList.size(); i++) {
                Map<String, String> map = mList.get(i);
                StringBuffer strTime = new StringBuffer();
                strTime.append(map.get("start_date"));
                strTime.append(" " + map.get("start_time") + " - ");
                strTime.append(map.get("end_date"));
                strTime.append(" " + map.get("end_time"));

                StringBuffer strPlace = new StringBuffer();
                strPlace.append(map.get("start"));
                strPlace.append(" " + map.get("start_detail") + " - ");
                strPlace.append(map.get("end"));
                strPlace.append(" " + map.get("end_detail"));

                String tra = null;
                if (map.get("traffic").toString().equals("1")) {
                    tra = "??????";
                } else if (map.get("traffic").toString().equals("2")) {
                    tra = "??????";
                } else if (map.get("traffic").toString().equals("3")) {
                    tra = "??????";
                } else if (map.get("traffic").toString().equals("4")) {
                    tra = "??????";
                } else if (map.get("traffic").toString().equals("5")) {
                    tra = "?????????";
                } else if (map.get("traffic").toString().equals("6")) {
                    tra = "?????????";
                } else if (map.get("traffic").toString().equals("7")) {
                    tra = "?????????";
                } else if (map.get("traffic").toString().equals("8")) {
                    tra = "??????????????????";
                } else if (map.get("traffic").toString().equals("9")) {
                    tra = "??????";
                } else if (map.get("traffic").toString().equals("10")) {
                    tra = "??????";
                }
                xingcheng.append("        ??????????????? " + strTime.toString() + "," + "??????" + tra + " " + map.get("tra_detail").toString() + ","
                        + "???" + map.get("start").toString() + map.get("start_detail").toString()
                        + "???" + map.get("end").toString() + map.get("end_detail").toString() + "???");
                if ((map.get("peers") + "").length() != 0 && !(map.get("peers") + "").equals("???")) {
                    xingcheng.append("????????? " + map.get("peers").toString() + "?????????\n");
                } else {
                    xingcheng.append("\n");
                }
            }
            text_xingcheng.setText(xingcheng.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(String var) {
        try {
            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            mList2 = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            for (int i = 0; i < mList2.size(); i++) {
                Map<String, String> map = mList2.get(i);
                String str = "";
                String name = map.get("name");
                String mobile = map.get("mobile");
                String idCard = map.get("card_id") + "";

                String contact_type = map.get("contact_type");
                StringBuffer stringBuffer = new StringBuffer("");
                if (contact_type.length() != 0 && !contact_type.equals("null")) {
                    String sym = contact_type;
                    if (sym.length() == 1) {
                        if (Integer.parseInt(sym) == 1) {
                            stringBuffer.append("??????");
                        } else if (Integer.parseInt(sym) == 2) {
                            stringBuffer.append("??????");
                        } else if (Integer.parseInt(sym) == 3) {
                            stringBuffer.append("??????");
                        } else {
                            stringBuffer.append("??????");
                        }
                    } else {
                        String[] split = sym.split(",");
                        for (int j = 0; j < split.length; j++) {
                            if (Integer.parseInt(split[j]) == 1) {
                                stringBuffer.append("?????? ");
                            } else if (Integer.parseInt(split[j]) == 2) {
                                stringBuffer.append("?????? ");
                            } else if (Integer.parseInt(split[j]) == 3) {
                                stringBuffer.append("?????? ");
                            } else {
                                stringBuffer.append("??????");
                            }
                        }
                    }
                }
                String address = map.get("address") + "";
                String address_detail = map.get("address_detail") + "";
                String dizhi;
                if (address.equals("null") || address.equals("")) {
                    if (address_detail.equals("null") || address_detail.equals("")) {
                        dizhi = "";
                    } else {
                        dizhi = address_detail;
                    }
                } else {
                    if (address_detail.equals("null") || address_detail.equals("")) {
                        dizhi = address;
                    } else {
                        dizhi = address + address_detail;
                    }
                }

                if (!idCard.equals("null") && idCard.length() != 0) {
                    if (!dizhi.equals("")) {
                        mijie.append("        ????????????????????????????????? " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                                + ",??????????????? " + map.get("card_id").toString() + "????????? " + dizhi + "???\n");
                    } else {
                        mijie.append("        ????????????????????????????????? " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                                + ",??????????????? " + map.get("card_id").toString() + "???\n");
                    }
                } else {
                    mijie.append("        ????????????????????????????????? " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString() + "???\n");
                }
            }
            text_mijie.setText(mijie.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse2(String var) {
        try {
            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("detial").toString();
            mList3 = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            for (int i = 0; i < mList3.size(); i++) {
                Map<String, String> map = mList3.get(i);
                String str = "";
                String name = map.get("name");
                String contact_type = map.get("contact_type");
                int relationText = Integer.parseInt(map.get("relation"));
                String mobile = map.get("mobile") + "";
                String idCard = map.get("card_id") + "";

                String relation;
                if (relationText == 0) {
                    relation = "??????";
                } else if (relationText == 1) {
                    relation = "??????";
                } else if (relationText == 2) {
                    relation = "??????";
                } else if (relationText == 3) {
                    relation = "??????";
                } else if (relationText == 4) {
                    relation = "??????";
                } else if (relationText == 5) {
                    relation = "??????";
                } else if (relationText == 6) {
                    relation = "??????";
                } else if (relationText == 7) {
                    relation = "??????";
                } else if (relationText == 8) {
                    relation = "??????????????????";
                } else if (relationText == 9) {
                    relation = "?????????????????????";
                } else {
                    relation = "??????????????????";
                }

                StringBuffer stringBuffer = new StringBuffer("");
                if (contact_type.length() != 0 && !contact_type.equals("null")) {
                    String sym = contact_type;
                    if (sym.length() == 1) {
                        if (Integer.parseInt(sym) == 1) {
                            stringBuffer.append("??????");
                        } else if (Integer.parseInt(sym) == 2) {
                            stringBuffer.append("??????");
                        } else if (Integer.parseInt(sym) == 3) {
                            stringBuffer.append("??????");
                        } else {
                            stringBuffer.append("??????");
                        }
                    } else {
                        String[] split = sym.split(",");
                        for (int j = 0; j < split.length; j++) {
                            if (Integer.parseInt(split[j]) == 1) {
                                stringBuffer.append("?????? ");
                            } else if (Integer.parseInt(split[j]) == 2) {
                                stringBuffer.append("?????? ");
                            } else if (Integer.parseInt(split[j]) == 3) {
                                stringBuffer.append("?????? ");
                            } else {
                                stringBuffer.append("??????");
                            }
                        }
                    }
                }
                String address = map.get("address") + "";
                String address_detail = map.get("address_detail") + "";
                String dizhi;
                if (address.equals("null") || address.equals("")) {
                    if (address_detail.equals("null") || address_detail.equals("")) {
                        dizhi = "";
                    } else {
                        dizhi = address_detail;
                    }
                } else {
                    if (address_detail.equals("null") || address_detail.equals("")) {
                        dizhi = address;
                    } else {
                        dizhi = address + address_detail;
                    }
                }
                if (!idCard.equals("null") && idCard.length() != 0) {
                    if (!dizhi.equals("")) {
                        mijie_family.append("        ??????????????????" + relation + " " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                                + ",??????????????? " + map.get("card_id").toString() + "????????? " + dizhi + "???\n");

                    } else {
                        mijie_family.append("        ??????????????????" + relation + " " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString()
                                + ",??????????????? " + map.get("card_id").toString() + "???\n");
                    }
                } else {
                    mijie_family.append("        ??????????????????" + relation + " " + map.get("name").toString() + " " + stringBuffer.toString() + "????????????????????? " + map.get("mobile").toString() + "???\n");
                }


            }
            text_mijiejiaren.setText(mijie_family.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse3(String var) {
        try {
            JsonObject jsonObject1 = new JsonParser().parse(var).getAsJsonObject();
            String jGroup1 = jsonObject1.get("activities").toString();
            mList4 = new Gson().fromJson(jGroup1, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            for (int i = 0; i < mList4.size(); i++) {
                Map<String, String> map = mList4.get(i);
                huodong.append("        ??????????????? " + map.get("acti_start_date") + " " + map.get("acti_starttime") + " - "
                        + map.get("acti_end_date") + " " + map.get("acti_endtime") + "?????? " + map.get("acti_place")
                        + map.get("actipla_detail") + " ???????????????????????????");
                if ((map.get("acti_detail") + "").length() != 0 && !(map.get("acti_detail") + "").equals("null")) {
                    huodong.append("???????????????" + map.get("acti_detail") + "???\n");
                } else {
                    huodong.append("\n");
                }
            }
            text_huodong.setText(huodong.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
