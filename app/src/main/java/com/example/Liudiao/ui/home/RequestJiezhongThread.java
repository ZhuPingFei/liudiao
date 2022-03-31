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

public class RequestJiezhongThread extends Thread {

    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestJiezhongThread(String url, int trans_id) {
        this.trans_id= trans_id;
        this.url = url;
    }

    public RequestJiezhongThread(String url, int trans_id, Handler handler) {
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

                final String time1 = object1.getString("firsttime");
                final String time2 = object1.getString("secondtime");
                final String time3 = object1.getString("thirdtime");

                final int type1 = object1.getInt("type1");
                final int type2 = object1.getInt("type2");
                final int type3 = object1.getInt("type3");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putInt("type1",type1);
                        b.putInt("type2",type2);
                        b.putInt("type3",type3);
                        b.putString("firsttime",time1);
                        b.putString("secondtime",time2);
                        b.putString("thirdtime",time3);

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
