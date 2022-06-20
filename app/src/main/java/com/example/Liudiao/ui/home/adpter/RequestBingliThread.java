package com.example.Liudiao.ui.home.adpter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestBingliThread extends Thread {
    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestBingliThread(String url, int trans_id) {
        this.trans_id= trans_id;
        this.url = url;
    }

    public RequestBingliThread(String url, int trans_id, Handler handler) {
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

                final String way = object1.getString("way")+"";
                final String other_way = object1.getString("other_way")+"";
                final String symptom = object1.getString("symptom")+"";
                final String ruyuan_date = object1.getString("admission_date")+"";
                final String other_symptom = object1.getString("other_symptom")+"";
                final String has_con = object1.getString("has_con")+"";
                final String con = object1.getString("con")+"";
                final String other_con = object1.getString("other_con")+"";
                final String has_ct = object1.getString("has_ct")+"";
                final String ct_date = object1.getString("ct_date")+"";
                final String out_date = object1.getString("out_date")+"";
                final String other_status = object1.getString("other_status")+"";
                final String last_negative_date = object1.getString("last_negative_date")+"";
                final String first_positive_date = object1.getString("first_positive_date")+"";
                final String temperature = object1.getString("temperature")+"";
                final String status = object1.getString("status")+"";


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putString("way",way);
                        b.putString("other_way",other_way);

                        b.putString("symptom",symptom);
                        b.putString("other_symptom",other_symptom);
                        b.putString("temperature",temperature);

                        b.putString("has_con",has_con);
                        b.putString("con",con);
                        b.putString("other_con",other_con);

                        b.putString("has_ct",has_ct);
                        b.putString("ct_date",ct_date);

                        b.putString("admission_date",ruyuan_date);
                        b.putString("out_date",out_date);

                        b.putString("last_negative_date",last_negative_date);
                        b.putString("first_positive_date",first_positive_date);

                        b.putString("status",status);
                        b.putString("other_status",other_status);

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
