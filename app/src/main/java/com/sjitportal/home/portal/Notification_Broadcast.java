package com.sjitportal.home.portal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class Notification_Broadcast extends BroadcastReceiver {
    public Notification_Broadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Notification", "broadcast");
       // Toast.makeText(context,"broadcast",Toast.LENGTH_LONG).show();
        Student b[];
        Localdb db=new Localdb(context,"login",null,1);
        b=db.outstudent("select * from student_personal");

        if(b[0].getRollno()==null)
            return;

        Intent intent1=new Intent(context,Notification_Alert.class);
        intent1.putExtra("rollno",b[0].getRollno());
        intent1.putExtra("Usr",b[0].getName());
        //context.startService(intent1);



        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Notification_Alert.class);
        i.putExtra("rollno",b[0].getRollno());
        i.putExtra("Usr",b[0].getName());
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        am.cancel(pi);


            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 5*1000,//5 sec
                    2*60*60*1000, pi);//2 hrs




    }



}
