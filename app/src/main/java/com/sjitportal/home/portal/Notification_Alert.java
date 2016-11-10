package com.sjitportal.home.portal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.Handler;

public class Notification_Alert extends Service {

    Handler handler=new Handler();
    Runnable runnable =null;
    SharedPreferences sharedPreferences=null;
    SharedPreferences.Editor edit=null;

    public Notification_Alert() {
    }

    String rn,u;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Notification", "Oncreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int sno=0;
        sharedPreferences= getSharedPreferences("Notification", Context.MODE_PRIVATE);
         edit=sharedPreferences.edit();
        Student b[];
        Localdb db=new Localdb(getApplicationContext(),"login",null,1);
        b=db.outstudent("select * from student_personal");
        rn=b[0].getRollno();
        u=b[0].getName();

       // Toast.makeText(getApplicationContext(),"notification"+rn,Toast.LENGTH_SHORT).show();
        Log.i("Notification", "Onstart");
        if(rn==null)
            Log.i("Intent","Error");

        runnable=new Runnable() {
            @Override
            public void run() {
                int min=1000*60;
                DbNotification dbn = new DbNotification();
                Network_State n=new Network_State();
                if(n.isNetworkAvailable(getApplicationContext())==true)
                    dbn.execute(Find.dept(rn));

                Log.i("Notification", "expecting");
                //Toast.makeText(getApplicationContext(),"dbcalled",Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable,1000*60*2);//2hrs

            }
        };

        handler.postDelayed(runnable,15000);




        return Service.START_STICKY;
    }

    private class DbNotification extends AsyncTask<String,Void,Void>
    {
        // public String ret=new String();
        int deptcsno,deptesno;
        int examcir,clgcir,clgevent;
        String description[]=new String[5];

        @Override
        protected Void doInBackground(String... params) {
            Dbdetails db=new Dbdetails(params[0]);
            Dbdetails db1=new Dbdetails("sjitportal");

            Connection conn=null;
            Statement stmt=null;


            try
            {
                Class.forName(db.getDriver());


                conn= DriverManager.getConnection(db.getUrl(), db.getUserName(), db.getPass());
                stmt = conn.createStatement();

                ResultSet rs;

                rs=stmt.executeQuery("select * from deptuploads where type='circular';");
                if(rs.last()) {
                    deptcsno=rs.getInt("sno");
                    description[0]=rs.getString("desc");
                }
                rs.close();
                rs=stmt.executeQuery("select * from deptuploads where type='event';");

                if(rs.last()) {
                    deptesno=rs.getInt("sno");
                    description[1]=rs.getString("desc");
                }
                rs.close();

                if(stmt!=null)
                stmt.close();
                if(conn!=null)
                    conn.close();
                conn= DriverManager.getConnection(db1.getUrl(), db1.getUserName(), db1.getPass());
                stmt=conn.createStatement();
                rs=stmt.executeQuery("select * from exam_circular");
                if(rs.last()) {
                    examcir=rs.getInt("sno");
                    description[2]=rs.getString("descp");
                }
                rs.close();
                rs=stmt.executeQuery("select * from circular where type='circular'");
                if(rs.last()) {
                    clgcir=rs.getInt("sno");
                    description[3]=rs.getString("des");
                }
                rs.close();

                rs=stmt.executeQuery("select * from circular where type='event'");
                if(rs.last()) {
                    clgevent=rs.getInt("sno");
                    description[4]=rs.getString("des");
                }
                rs.close();
            }catch(SQLException se)
            {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                try{

                    if(stmt!=null)
                        stmt.close();
                    if(conn!=null)
                        conn.close();
                }catch(SQLException se)
                {
                    se.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("sno",String.valueOf(deptcsno));

            Intent intent=null;


            if(sharedPreferences.getInt("DeptCircular",0)<deptcsno)
                    {
                        intent=new Intent(Notification_Alert.this,Deptupload_query.class);
                        intent.putExtra("DeptCircular", sharedPreferences.getInt("DeptCircular",0));
                        edit.putInt("DeptCircular",deptcsno);
                        sendNotification(intent,"Circular",description[0],0);

                        Log.i("intent","added");

                    }
            if(sharedPreferences.getInt("DeptEvent",0)<deptesno)
                    {
                        intent=new Intent(Notification_Alert.this,Dept_events.class);
                        intent.putExtra("DeptEvent", sharedPreferences.getInt("DeptEvent",0));
                        sendNotification(intent,"Event",description[1],1);

                        edit.putInt("DeptEvent",deptesno);


                    }
            if(sharedPreferences.getInt("Exam",0)<examcir)
                    {
                        intent=new Intent(Notification_Alert.this,Deptupload_query.class);
                        intent.putExtra("Exam", sharedPreferences.getInt("Exam",0));
                        sendNotification(intent,"ExamCircular",description[2],2);

                        edit.putInt("Exam",examcir);

                    }
            if(sharedPreferences.getInt("ClgCircular",0)<clgcir)
                    {
                        intent=new Intent(Notification_Alert.this,Deptupload_query.class);
                        intent.putExtra("ClgCircular", sharedPreferences.getInt("ClgCircular",0));
                        sendNotification(intent,"Circular",description[3],3);
                        edit.putInt("ClgCircular",clgcir);
                    }
            if(sharedPreferences.getInt("ClgEvent",0)<clgevent)
                    {

                        intent=new Intent(Notification_Alert.this,Dept_events.class);
                        intent.putExtra("ClgEvent", sharedPreferences.getInt("ClgEvent",0));
                        sendNotification(intent,"Event",description[4],4);
                        edit.putInt("ClgEvent",clgevent);
                    }




            edit.commit();
           //Toast.makeText(getApplicationContext(),String.valueOf(sno),Toast.LENGTH_SHORT).show();
          //  Toast.makeText(getApplicationContext(),String.valueOf(sharedPreferences.getInt("DeptCircular",1)),Toast.LENGTH_SHORT).show();



        }

        void sendNotification(Intent intent,String type,String description,int id){
            if(intent!=null){
                intent.putExtra("rollno", rn);
                intent.putExtra("Usr", u);
                PendingIntent pIntent = PendingIntent.getActivity(Notification_Alert.this, (int) System.currentTimeMillis(), intent, 0);

                //Log.i("pintent","created");



                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Uri sounduri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



                    Notification.Builder mbuilder = new Notification.Builder(Notification_Alert.this)
                            .setContentTitle("New "+type+" Added ")
                            .setContentText(description)
                            .setSound(sounduri)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pIntent)

                            .setAutoCancel(true);


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(id,  mbuilder.build());




                }

            }
            else
                return;

                        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
