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

                final String job = object1.getString("job")+"";
                final String job_detail = object1.getString("job_detail")+"";
                final String other_job = object1.getString("other_job")+"";

                final String radiobutton1 = object1.getString("radiobutton1")+"";
                final String address = object1.getString("address")+"";

                final String radiobutton2 = object1.getString("radiobutton2")+"";
                final String nation = object1.getString("nation")+"";

                final String radiobutton3 = object1.getString("radiobutton3")+"";
                final String radiobutton4 = object1.getString("radiobutton4")+"";
                final String radiobutton5 = object1.getString("radiobutton5")+"";
                final String radiobutton6 = object1.getString("radiobutton6")+"";

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle b = new Bundle();
                        b.putInt("code",0);
                        b.putString("job",job);
                        b.putString("job_detail",job_detail);
                        b.putString("other_job",other_job);

                        b.putString("radiobutton1",radiobutton1);
                        b.putString("address",address);

                        b.putString("radiobutton2",radiobutton2);
                        b.putString("nation",nation);

                        b.putString("radiobutton3",radiobutton3);
                        b.putString("radiobutton4",radiobutton4);
                        b.putString("radiobutton5",radiobutton5);
                        b.putString("radiobutton6",radiobutton6);

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
