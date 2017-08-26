package com.sjitportal.home.portal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

/**
 * Created by Home on 18-08-2015.
 */
public class Dbdetails {

static {

    Bitmap bm = BitmapFactory.decodeResource(StaticApp.getContext().getResources(),R.drawable.db);
    String enc="";
    try {
        InputStream is = StaticApp.getContext().getAssets().open("db.jpg");
        InputStreamReader reader=new InputStreamReader(is);
        byte[] b=new byte[4096];
        is.read(b);


        for(byte bb:b)
            enc+=(char)((char)((int)bb%26)+'A');



    } catch (IOException e) {
        e.printStackTrace();
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    bm.compress(Bitmap.CompressFormat.JPEG, 0, baos);
    byte[] b = baos.toByteArray();
    String a= Base64.encodeToString(b, Base64.DEFAULT);
  //  Log.i("Code Decrypted 1", enc.substring(5,15)+"    "+enc.substring(91,101));
   // Log.i("Code Decrypted 2", a.substring(250,260)+"    "+a.substring(310,320));
//    Toast.makeText(StaticApp.getContext(),a.substring(250,260)+"    "+a.substring(310,320),Toast.LENGTH_LONG).show();
    UserName="Android7";
    Pass=enc.substring(5,15)+enc.substring(91,101);
}
    static  public String dept;
    final private String Driver="com.mysql.jdbc.Driver";
    final static private String UserName;
    final static private String Pass;
    private String Url="jdbc:mysql://182.74.154.218:3306/";
       Dbdetails()
         {
              Url=Url+dept;
         }
    Dbdetails(String s)
    {
        Url=Url+s;
    }

    public static String getDept() {
        return dept;
    }

    public String getDriver() {
        return Driver;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPass() {
        return Pass;
    }

    public String getUrl() {
        return Url;
    }
}
