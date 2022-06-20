package com.example.Liudiao.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RequestJiankangThread extends Thread {

    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestJiankangThread(String url, int trans_id) {
        this.trans_id= trans_id;
        this.url = url;
    }

    public RequestJiankangThread(String url, int trans_id, Handler handler) {
        this.trans_id= trans_id;
        this.url = url;
        this.handler = handler;
    }


    public void doGet() {

        HttpURLConnection httpConn = null;
        try {

            url = url + "?transactor_id=" + trans_id;

            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }

            JSONObject object1 = new JSONObject(sb.toString());
            int code = object1.getInt("code");
            if (code == 0){
                final String discomfort = object1.getString("discomfort")+"";
                final String symptom = object1.getString("symptom")+"";
                final String other_symptom = object1.getString("other_symptom")+"";

                final String zhengzhuangDate = object1.getString("symptom_start_date")+"";

                final String doctor = object1.getInt("doctor")+"";
                final String doctoraddress = object1.getString("doctoraddress")+"";
                final String advice = object1.getString("advice")+"";

                final String maternity = object1.getInt("maternity")+"";
                final String ges_week = object1.getInt("ges_week")+"";

                final String smoke = object1.getInt("smoke")+"";
                final String smoke_fre = object1.getInt("smoke_fre")+"";

                final String dis_his = object1.getString("dis_his")+"";

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putString("symptom_start_date",zhengzhuangDate);
                        b.putString("discomfort",discomfort);
                        b.putString("symptom",symptom);
                        b.putString("other_symptom",other_symptom);
                        b.putString("doctor",doctor);
                        b.putString("doctoraddress",doctoraddress);
                        b.putString("advice",advice);
                        b.putString("maternity",maternity);
                        b.putString("ges_week",ges_week);
                        b.putString("smoke",smoke);
                        b.putString("smoke_fre",smoke_fre);
                        b.putString("dis_his",dis_his);
                        Message msg = new Message();
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                });
            }else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",500);
                        Message msg = new Message();
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }
                });
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
