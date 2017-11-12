package com.sjitportal.home.portal;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Fluffy on 14-10-2017.
 */
public class HTTPClient {

    public static String post(String surl,String Header,String body,String params){
        String result="";

       // Log.i("httpclient","Inside POst");
        HttpURLConnection connection = null;


        URL url = null;
//        if(true)
//            return "[]";
        try {
            if(params!=null)
            url = new URL(surl + "?" + params);
             else
                url = new URL(surl);


            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
           // Log.i("httpclient","Connection header set");
            if(Header!=null) {
                String[] headers = Header.split("&");
                for (String pair : headers) {
                    String[] values = pair.split("=");
                    connection.setRequestProperty(values[0], values[1]);
                }

            }
           // Log.i("httpclient","Connection write body");
            if(body!=null){
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write( outputInBytes );
                os.close();
            }

            Log.i("httpclient","Connection Requested");
            connection.connect();
            InputStream input = connection.getInputStream();
            char c;
            while ((c = (char) input.read()) != (char) -1)
                result += c;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
