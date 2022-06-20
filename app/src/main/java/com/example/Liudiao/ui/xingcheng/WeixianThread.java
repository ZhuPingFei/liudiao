package com.example.Liudiao.ui.xingcheng;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeixianThread extends Thread {

    StringBuffer data = new StringBuffer();
    Handler handler;
    String url;
    double lat;
    double lot;

    public WeixianThread(String url, double lat,double lot) {
        this.lat= lat;
        this.lot = lot;
        this.url = url;
    }

    public WeixianThread(String url, double lat,double lot, Handler handler) {
        this.lat= lat;
        this.lot = lot;
        this.url = url;
        this.handler = handler;
    }


    public void run() {

        HttpURLConnection httpConn = null;
        try {

            url = url +Double.toString(lot)+"&latitude="+Double.toString(lat)+"&kilometer=2.000";

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
