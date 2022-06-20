package com.example.Liudiao.ui.home.adpter;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestMijieThread extends Thread {
    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    int trans_id;

    public RequestMijieThread(String url, int trans_id) {
        this. trans_id= trans_id;
        this.url = url;
    }

    public RequestMijieThread(String url, int trans_id, Handler handler) {
        this. trans_id= trans_id;
        this.url = url;
        this.handler = handler;
    }


    public void run() {

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
            data = sb;

            Message message1 = new Message();
            message1.obj = data;
            handler.sendMessage(message1);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }


    }

    public StringBuffer databack() {
        return data;
    }
}
