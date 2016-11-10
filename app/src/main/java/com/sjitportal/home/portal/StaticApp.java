package com.sjitportal.home.portal;

import android.app.Application;
import android.content.Context;

/**
 * Created by Home on 07-08-2016.
 */
public class StaticApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
