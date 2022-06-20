package com.example.Liudiao.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.JsonObject;

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

public class RequestAllThread extends Thread {
    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestAllThread(String url, int trans_id) {
        this.trans_id = trans_id;
        this.url = url;
    }

    public RequestAllThread(String url, int trans_id, Handler handler) {
        this.trans_id = trans_id;
        this.url = url;
        this.handler = handler;
    }

    public void doGet() {
        HttpURLConnection httpConn = null;
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("transactor_id", trans_id);
            URL httpUrl = new URL(url);
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
            int code = jsonObj1.getInt("code");
            if (code == 0) {
                //基本状况
                JSONObject jsonJiben = jsonObj1.getJSONObject("personal");
                final int info_num = jsonJiben.getInt("info_num");
                String card_id = "";
                String name = "";
                int gender = 0;
                int age = 0;
                int type = 0;
                String tel = "";
                int job = 0;
                String birthDate = "";
                String address = "";
                String address_detail = "";
                String job_address = "";
                if (info_num == 1) {
                    JSONArray info = jsonJiben.getJSONArray("info");
                    card_id = info.getJSONObject(0).getString("id_number");
                    name = info.getJSONObject(0).getString("name");
                    gender = info.getJSONObject(0).getInt("sex");
                    age = info.getJSONObject(0).getInt("age");
                    type = info.getJSONObject(0).getInt("type");
                    tel = info.getJSONObject(0).getString("mobile");
                    job = info.getJSONObject(0).getInt("job");
                    birthDate = info.getJSONObject(0).getString("birth_date");
                    address = info.getJSONObject(0).getString("address");
                    address_detail = info.getJSONObject(0).getString("address_detail");
                    job_address = info.getJSONObject(0).getString("job_address") + "";
                }


                //健康状况，只要症状起始日期
                final JSONObject jsonJiankang = jsonObj1.getJSONObject("health");
                final int code_jiankang = jsonJiankang.getInt("code");
                String symptom_start_date = "";
                if (code_jiankang == 0) {
                    symptom_start_date = jsonJiankang.getString("symptom_start_date") + "";
                }

                //接种疫苗，只要接种次数
                final JSONObject jsonYimiao = jsonObj1.getJSONObject("vaccination");
                final int code_yimiao = jsonYimiao.getInt("code");
                String yimiao_times = "";
                if (code_yimiao == 0) {
                    yimiao_times = jsonYimiao.getInt("times") + "";
                }

                //病例发现
                final JSONObject jsonBingli = jsonObj1.getJSONObject("discovery");
                final int code_bingli = jsonBingli.getInt("code");
                String way = "";
                String other_way = "";
                String symptom = "";
                String other_symptom = "";
                String temperature = "";
                String last_negative_date = "";
                String first_positive_date = "";
                String status = "";
                String other_status = "";
                if (code_bingli == 0) {
                    way = jsonBingli.getString("way") + "";
                    other_way = jsonBingli.getString("other_way") + "";
                    symptom = jsonBingli.getString("symptom") + "";
                    other_symptom = jsonBingli.getString("other_symptom") + "";
                    temperature = jsonBingli.getString("temperature") + "";
                    last_negative_date = jsonBingli.getString("last_negative_date") + "";
                    first_positive_date = jsonBingli.getString("first_positive_date") + "";
                    status = jsonBingli.getString("status") + "";
                    other_status = jsonBingli.getString("other_status") + "";
                }

                //密接家庭成员
                final JSONObject jsonFamily = jsonObj1.getJSONObject("family");
                final int mem_num = jsonFamily.getInt("mem_num");
                final int code_family = jsonFamily.getInt("code");
                String[] family_name = new String[0];
                String[] family_mobile = new String[0];
                String[] family_card_id = new String[0];
                String[] family_relation = new String[0];
                String[] family_address = new String[0];
                String[] family_address_detail = new String[0];
                String[] family_contact_type = new String[0];
                if (mem_num != 0 && code_family == 0) {
                    JSONArray detial = jsonFamily.getJSONArray("detial");
                    family_name = new String[mem_num];
                    family_mobile = new String[mem_num];
                    family_card_id = new String[mem_num];
                    family_relation = new String[mem_num];
                    family_address = new String[mem_num];
                    family_address_detail = new String[mem_num];
                    family_contact_type = new String[mem_num];
                    for (int i = 0; i < mem_num; i++) {
                        family_name[i] = detial.getJSONObject(i).getString("name") + "";
                        family_mobile[i] = detial.getJSONObject(i).getString("mobile") + "";
                        family_card_id[i] = detial.getJSONObject(i).getString("card_id") + "";
                        family_relation[i] = detial.getJSONObject(i).getString("relation") + "";
                        family_address[i] = detial.getJSONObject(i).getString("address") + "";
                        family_address_detail[i] = detial.getJSONObject(i).getString("address_detail") + "";
                        family_contact_type[i] = detial.getJSONObject(i).getString("contact_type") + "";
                    }
                }

                //密接其他成员
                final JSONObject jsonMijie = jsonObj1.getJSONObject("contacts");
                final int contacts_num = jsonMijie.getInt("contacts_num");
                final int code_mijie = jsonMijie.getInt("code");
                String[] mijie_name = new String[0];
                String[] mijie_mobile = new String[0];
                String[] mijie_card_id = new String[0];
                String[] mijie_address = new String[0];
                String[] mijie_address_detail = new String[0];
                String[] mijie_contact_type = new String[0];
                if (contacts_num != 0 && code_mijie == 0) {
                    JSONArray detial = jsonMijie.getJSONArray("detial");
                    mijie_name = new String[contacts_num];
                    mijie_mobile = new String[contacts_num];
                    mijie_card_id = new String[contacts_num];
                    mijie_address = new String[contacts_num];
                    mijie_address_detail = new String[contacts_num];
                    mijie_contact_type = new String[contacts_num];
                    for (int i = 0; i < contacts_num; i++) {
                        mijie_name[i] = detial.getJSONObject(i).getString("name") + "";
                        mijie_mobile[i] = detial.getJSONObject(i).getString("mobile") + "";
                        mijie_card_id[i] = detial.getJSONObject(i).getString("card_id") + "";
                        mijie_address[i] = detial.getJSONObject(i).getString("address") + "";
                        mijie_address_detail[i] = detial.getJSONObject(i).getString("address_detail") + "";
                        mijie_contact_type[i] = detial.getJSONObject(i).getString("contact_type") + "";
                    }
                }

                //外地旅行史
                final JSONObject jsonXingcheng = jsonObj1.getJSONObject("trajectory");
                final int journey_num = jsonXingcheng.getInt("journey_num");
                final int code_xingcheng = jsonXingcheng.getInt("code");
                String[] start = new String[0];
                String[] end = new String[0];
                String[] start_detail = new String[0];
                String[] end_detail = new String[0];
                String[] start_date = new String[0];
                String[] start_time = new String[0];
                String[] end_date = new String[0];
                String[] end_time = new String[0];
                String[] traffic = new String[0];
                String[] tra_detail = new String[0];
                String[] peers = new String[0];
                if (journey_num != 0 && code_xingcheng == 0) {
                    JSONArray detial = jsonXingcheng.getJSONArray("trajs");
                    start = new String[journey_num];
                    end = new String[journey_num];
                    start_detail = new String[journey_num];
                    end_detail = new String[journey_num];
                    start_date = new String[journey_num];
                    start_time = new String[journey_num];
                    end_date = new String[journey_num];
                    end_time = new String[journey_num];
                    traffic = new String[journey_num];
                    tra_detail = new String[journey_num];
                    peers = new String[journey_num];
                    for (int i = 0; i < journey_num; i++) {
                        start[i] = detial.getJSONObject(i).getString("start") + "";
                        end[i] = detial.getJSONObject(i).getString("end") + "";
                        start_detail[i] = detial.getJSONObject(i).getString("start_detail") + "";
                        end_detail[i] = detial.getJSONObject(i).getString("end_detail") + "";
                        start_date[i] = detial.getJSONObject(i).getString("start_date") + "";
                        start_time[i] = detial.getJSONObject(i).getString("start_time") + "";
                        end_date[i] = detial.getJSONObject(i).getString("end_date") + "";
                        end_time[i] = detial.getJSONObject(i).getString("end_time") + "";
                        traffic[i] = detial.getJSONObject(i).getString("traffic") + "";
                        tra_detail[i] = detial.getJSONObject(i).getString("tra_detail") + "";
                        peers[i] = detial.getJSONObject(i).getString("peers") + "";
                    }
                }

                //活动

                final JSONObject jsonHuodong = jsonObj1.getJSONObject("activity");
                final int acti_num = jsonHuodong.getInt("acti_num");
                final int code_huodong = jsonHuodong.getInt("code");
                String[] acti_place = new String[0];
                String[] actipla_detail = new String[0];
                String[] acti_start_date = new String[0];
                String[] acti_starttime = new String[0];
                String[] acti_end_date = new String[0];
                String[] acti_endtime = new String[0];
                if (acti_num != 0 && code_huodong == 0) {
                    JSONArray detial = jsonHuodong.getJSONArray("activities");
                    acti_place = new String[acti_num];
                    actipla_detail = new String[acti_num];
                    acti_start_date = new String[acti_num];
                    acti_starttime = new String[acti_num];
                    acti_end_date = new String[acti_num];
                    acti_endtime = new String[acti_num];
                    for (int i = 0; i < acti_num; i++) {
                        acti_place[i] = detial.getJSONObject(i).getString("acti_place") + "";
                        actipla_detail[i] = detial.getJSONObject(i).getString("actipla_detail") + "";
                        acti_start_date[i] = detial.getJSONObject(i).getString("acti_start_date") + "";
                        acti_starttime[i] = detial.getJSONObject(i).getString("acti_starttime") + "";
                        acti_end_date[i] = detial.getJSONObject(i).getString("acti_end_date") + "";
                        acti_endtime[i] = detial.getJSONObject(i).getString("acti_endtime") + "";
                    }
                }

                final String finalCard_id = card_id;
                final String finalName = name;
                final int finalGender = gender;
                final int finalAge = age;
                final int finalType = type;
                final String finalTel = tel;
                final int finalJob = job;
                final String finalBirthDate = birthDate;
                final String finalAddress = address;
                final String finalAddress_detail = address_detail;
                final String finalJob_address = job_address;
                final String finalSymptom_start_date = symptom_start_date;
                final String finalYimiao_times = yimiao_times;
                final String finalWay = way;
                final String finalOther_way = other_way;
                final String finalSymptom = symptom;
                final String finalOther_symptom = other_symptom;
                final String finalTemperature = temperature;
                final String finalLast_negative_date = last_negative_date;
                final String finalFirst_positive_date = first_positive_date;
                final String finalStatus = status;
                final String finalOther_status = other_status;
                final String[] finalFamily_name = family_name;
                final String[] finalFamily_mobile = family_mobile;
                final String[] finalFamily_card_id = family_card_id;
                final String[] finalFamily_relation = family_relation;
                final String[] finalFamily_address = family_address;
                final String[] finalFamily_address_detail = family_address_detail;
                final String[] finalFamily_contact_type = family_contact_type;
                final String[] finalMijie_name = mijie_name;
                final String[] finalMijie_mobile = mijie_mobile;
                final String[] finalMijie_card_id = mijie_card_id;
                final String[] finalMijie_address = mijie_address;
                final String[] finalMijie_address_detail = mijie_address_detail;
                final String[] finalMijie_contact_type = mijie_contact_type;
                final String[] finalStart = start;
                final String[] finalEnd = end;
                final String[] finalStart_detail = start_detail;
                final String[] finalEnd_detail = end_detail;
                final String[] finalStart_date = start_date;
                final String[] finalStart_time = start_time;
                final String[] finalEnd_date = end_date;
                final String[] finalEnd_time = end_time;
                final String[] finalTraffic = traffic;
                final String[] finalTra_detail = tra_detail;
                final String[] finalPeers = peers;
                final String[] finalActi_place = acti_place;
                final String[] finalActipla_detail = actipla_detail;
                final String[] finalActi_start_date = acti_start_date;
                final String[] finalActi_starttime = acti_starttime;
                final String[] finalActi_end_date = acti_end_date;
                final String[] finalActi_endtime = acti_endtime;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putInt("info_num",info_num);
                        b.putInt("code_jiankang",code_jiankang);
                        b.putInt("code_yimiao",code_yimiao);
                        b.putInt("code_bingli",code_bingli);
                        b.putInt("mem_num",mem_num);
                        b.putInt("code_family",code_family);
                        b.putInt("contacts_num",contacts_num);
                        b.putInt("code_mijie",code_mijie);
                        b.putInt("journey_num",journey_num);
                        b.putInt("code_xingcheng",code_xingcheng);
                        b.putInt("acti_num",acti_num);
                        b.putInt("code_huodong",code_huodong);
                        if (info_num == 1) {
                            b.putString("card_id", finalCard_id);
                            b.putString("name", finalName);
                            b.putInt("gender", finalGender);
                            b.putInt("age", finalAge);
                            b.putInt("type", finalType);
                            b.putString("tel", finalTel);
                            b.putInt("job", finalJob);
                            b.putString("birthDate", finalBirthDate);
                            b.putString("address", finalAddress);
                            b.putString("address_detail", finalAddress_detail);
                            b.putString("job_address", finalJob_address);
                        }
                        if (code_jiankang == 0) {
                            b.putString("symptom_start_date", finalSymptom_start_date);
                        }
                        if (code_yimiao == 0) {
                            b.putString("times", finalYimiao_times);
                        }
                        if (code_bingli == 0) {
                            b.putString("way", finalWay);
                            b.putString("other_way", finalOther_way);
                            b.putString("symptom", finalSymptom);
                            b.putString("other_symptom", finalOther_symptom);
                            b.putString("temperature", finalTemperature);
                            b.putString("last_negative_date", finalLast_negative_date);
                            b.putString("first_positive_date", finalFirst_positive_date);
                            b.putString("status", finalStatus);
                            b.putString("other_status", finalOther_status);
                        }
                        if (mem_num != 0 && code_family == 0) {
                            b.putStringArray("family_name", finalFamily_name);
                            b.putStringArray("family_mobile", finalFamily_mobile);
                            b.putStringArray("family_card_id", finalFamily_card_id);
                            b.putStringArray("family_relation", finalFamily_relation);
                            b.putStringArray("family_address", finalFamily_address);
                            b.putStringArray("family_address_detail", finalFamily_address_detail);
                            b.putStringArray("family_contact_type", finalFamily_contact_type);
                        }

                        if (contacts_num != 0 && code_mijie == 0) {
                            b.putStringArray("mijie_name", finalMijie_name);
                            b.putStringArray("mijie_mobile", finalMijie_mobile);
                            b.putStringArray("mijie_card_id", finalMijie_card_id);
                            b.putStringArray("mijie_address", finalMijie_address);
                            b.putStringArray("mijie_address_detail", finalMijie_address_detail);
                            b.putStringArray("mijie_contact_type", finalMijie_contact_type);
                        }

                        if (journey_num != 0 && code_xingcheng == 0) {
                            b.putStringArray("start", finalStart);
                            b.putStringArray("end", finalEnd);
                            b.putStringArray("start_detail", finalStart_detail);
                            b.putStringArray("end_detail", finalEnd_detail);
                            b.putStringArray("start_date", finalStart_date);
                            b.putStringArray("start_time", finalStart_time);
                            b.putStringArray("end_date", finalEnd_date);
                            b.putStringArray("end_time", finalEnd_time);
                            b.putStringArray("traffic", finalTraffic);
                            b.putStringArray("tra_detail", finalTra_detail);
                            b.putStringArray("peers", finalPeers);
                        }

                        if (acti_num != 0 && code_huodong == 0) {
                            b.putStringArray("acti_place", finalActi_place);
                            b.putStringArray("actipla_detail", finalActipla_detail);
                            b.putStringArray("acti_start_date", finalActi_start_date);
                            b.putStringArray("acti_starttime", finalActi_starttime);
                            b.putStringArray("acti_end_date", finalActi_end_date);
                            b.putStringArray("acti_endtime", finalActi_endtime);
                        }

                        Message msg = new Message();
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code", 500);
                        Message msg = new Message();
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                });
            }
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                JSONException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }

    }

    public void run() {
        doGet();
    }
}
