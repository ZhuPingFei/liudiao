package com.example.Liudiao.ui.home;

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

public class RequestWeixianThread extends Thread{
    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestWeixianThread(String url, int trans_id) {
        this.trans_id= trans_id;
        this.url = url;
    }

    public RequestWeixianThread(String url, int trans_id, Handler handler) {
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

                final int job = object1.getInt("job");
                final int job_detail = object1.getInt("job_detail");
                final String other_job = object1.getString("other_job");

                final int maternity = object1.getInt("maternity");
                final int ges_week = object1.getInt("ges_week");

                final int smoke = object1.getInt("smoke");

                final String dis_his = object1.getString("dis_his");
                final String other_dis = object1.getString("other_dis");

                final int radiobutton1 = object1.getInt("radiobutton1");
                final String address = object1.getString("address");

                final int radiobutton2 = object1.getInt("radiobutton2");
                final int nation = object1.getInt("nation");

                final int radiobutton3 = object1.getInt("radiobutton3");
                final int radiobutton4 = object1.getInt("radiobutton4");
                final int radiobutton5 = object1.getInt("radiobutton5");
                final int radiobutton6 = object1.getInt("radiobutton6");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putInt("job",job);
                        b.putInt("job_detail",job_detail);
                        b.putString("other_job",other_job);

                        b.putInt("maternity",maternity);
                        b.putInt("ges_week",ges_week);

                        b.putInt("smoke",smoke);

                        b.putString("dis_his",dis_his);
                        b.putString("other_dis",other_dis);

                        b.putInt("radiobutton1",radiobutton1);
                        b.putString("address",address);

                        b.putInt("radiobutton2",radiobutton2);
                        b.putInt("nation",nation);

                        b.putInt("radiobutton3",radiobutton3);
                        b.putInt("radiobutton4",radiobutton4);
                        b.putInt("radiobutton5",radiobutton5);
                        b.putInt("radiobutton6",radiobutton6);

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
