package com.sjitportal.home.portal;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Home on 14-02-2016.
 */
public class Network_State{

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }
}
