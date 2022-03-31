package com.example.Liudiao.ui.notifications;

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

public class RequestUserThread extends Thread{

    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int user_id;

    public RequestUserThread(String url, int user_id) {
        this.user_id= user_id;
        this.url = url;
    }

    public RequestUserThread(String url, int user_id, Handler handler) {
        this.user_id= user_id;
        this.url = url;
        this.handler = handler;
    }


    public void doGet() {

        HttpURLConnection httpConn = null;
        try {

            url = url + "?user_id=" + user_id;

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
            final int transactor_num = object1.getInt("transactor_num");
            if (code == 0 && transactor_num!=0){
                JSONArray jsonArray = object1.getJSONArray("detial");
                final int trans_id =jsonArray.getJSONObject(0).getInt("transactor_id");
                final String name = jsonArray.getJSONObject(0).getString("name");
                //final String name = jsonArray2.getJSONObject(0).getString("name");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putInt("transactor_num",transactor_num);
                        b.putInt("transactor_id",trans_id);
                        b.putString("name",name);
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
                        b.putInt("transactor_num",transactor_num);
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
