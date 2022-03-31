package com.example.Liudiao.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestJibenThread extends Thread {

    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestJibenThread(String url, int trans_id) {
        this.trans_id= trans_id;
        this.url = url;
    }

    public RequestJibenThread(String url, int trans_id, Handler handler) {
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
            int info_num = object1.getInt("info_num");
            if (info_num != 0){
                JSONArray info = object1.getJSONArray("info");
                final String card_id = info.getJSONObject(0).getString("card_id");
                final String name = info.getJSONObject(0).getString("name");
                final int gender = info.getJSONObject(0).getInt("gender");
                final int age = info.getJSONObject(0).getInt("age");
                final int type = info.getJSONObject(0).getInt("type");
                final String tel = info.getJSONObject(0).getString("tel");
                final int job = info.getJSONObject(0).getInt("job");
                final String birthDate = info.getJSONObject(0).getString("birth_date");
                final String address = info.getJSONObject(0).getString("address");
                final String address_detail = info.getJSONObject(0).getString("address_detail");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putString("card_id",card_id);
                        b.putString("name",name);
                        b.putInt("gender",gender);
                        b.putInt("age",age);
                        b.putInt("type",type);
                        b.putString("tel",tel);
                        b.putInt("job",job);
                        b.putString("address",address);
                        b.putString("address_detail",address_detail);
                        b.putString("birth_date",birthDate);
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
                        b.putString("name","no");
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
