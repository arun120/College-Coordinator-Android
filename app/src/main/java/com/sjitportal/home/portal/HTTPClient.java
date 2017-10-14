package com.sjitportal.home.portal;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Fluffy on 14-10-2017.
 */
public class HTTPClient {

    public static String get(String surl,String params){
        String result="";

        HttpURLConnection connection = null;


        URL url = null;
        try {
            url = new URL(surl + "?" + params);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
