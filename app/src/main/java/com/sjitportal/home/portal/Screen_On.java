package com.sjitportal.home.portal;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class Screen_On extends Service {
    public Screen_On() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_SCREEN_ON);
        BroadcastReceiver mReceiver=new Notification_Broadcast();
        registerReceiver(mReceiver,intentFilter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
