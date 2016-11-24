package com.sjitportal.home.portal;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dept_events extends ActionBarActivity implements NavigationDrawerCallbacks,RecyclerViewAdapternew.OnItemClickListener{


    CoordinatorLayout coordinatorLayout;
    private Toolbar mToolbar;
    ProgressDialog mProgressDialog,mProgressDialogload;
    PowerManager.WakeLock mWakeLock;
    String rn,u;
    Dept_Uploads[] c,d;
    int Images;
    Download downloaded;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    ListView eventdisp;
    SwipeRefreshLayout swipeRefreshLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    RecyclerViewAdapternew myadapter;
    RecyclerView myrecycler;
    int deptevent=0;



    @Override
    protected void onStart() {
        super.onStart();

        Event event=new Event();
        event.execute();

        TextView usrname = (TextView) findViewById(R.id.SlideUsername);
        TextView rollno = (TextView) findViewById(R.id.SlideRollno);

        usrname.setText(u);
        rollno.setText(rn);

        Loadpic lp=new Loadpic();
        lp.execute();


    }


    public int getImage(String name){

        if(name.endsWith(".pdf"))
            return 1;
        if (name.endsWith(".doc")||name.endsWith(".docx"))
            return 2;
        if(name.endsWith(".jpg") || name.endsWith(".png")||name.endsWith(".gif"))
            return 3;
        if(name.endsWith(".txt"))
            return 4;
        if(name.endsWith(".xls"))
            return 5;
        if (name.endsWith(".rar")||name.endsWith(".zip"))
            return 6;

        return 0;
    }


    public class Event extends AsyncTask<String,Integer,String>
    {
        Query_execute q;
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int i=0;

            if(c[i].getName()==null)
                myadapter.add(myadapter.getItemCount(),"No Events Found", R.drawable.doc);

            while(c[i].getName()!=null) {


            myadapter.add(myadapter.getItemCount(),
                        c[i].getDesc(), getImage(c[i].getName()));


                if(c[i+1]!=null)
                    i++;
                else
                    break;
            }

        }

        @Override
        protected String doInBackground(String... params) {

            if(isCancelled())
                return null;


            c=q.onsjitcommonevents();
            int i=0;

            while(c[i].getName()!=null) {


             /*   myadapter.add(myadapter.getItemCount(),
                        c[i].getDesc(), getImage(c[i].getName()));
*/
                i++;
            }


            int j=i;
            i=0;


            if(getIntent().getExtras().get("DeptEvent")!=null) {
                deptevent = (Integer) getIntent().getExtras().get("DeptEvent");
              //  Toast.makeText(getApplicationContext(),String.valueOf(deptevent),Toast.LENGTH_SHORT).show();

            }


            d=q.ondeptuploads("select * from deptuploads where type='event'");

            while(d[i].getName()!=null) {

                c[j++]=d[i];
                i++;
            }




            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogload.setMessage("Loading...");
            mProgressDialogload.setIndeterminate(true);
            mProgressDialogload.setProgressStyle(ProgressDialog.STYLE_SPINNER);


            try {
                q = new Query_execute(Find.dept(rn),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(Dept_events.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
                this.cancel(true);
                return ;
            }

            mProgressDialogload.show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_events);
        myrecycler= (RecyclerView) findViewById(R.id.eventdisp);
        rn=getIntent().getExtras().getString("rollno");
        u=getIntent().getExtras().getString("Usr");


      /*  mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar_new);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        image = (ImageView) findViewById(R.id.images1);
        image.setImageResource(R.drawable.clg_2);
        Animation anima= AnimationUtils.loadAnimation(this, R.anim.zoomin);
        image.startAnimation(anima);*/


        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinator2);

        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar_new1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        //setPalette();

        myrecycler= (RecyclerView) findViewById(R.id.eventdisp);
        myadapter=new RecyclerViewAdapternew(this);
        myadapter.setOnItemClickListener(this);

        myrecycler.setAdapter(myadapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));





        mProgressDialog = new ProgressDialog(this);
        mProgressDialogload=new ProgressDialog(this);
        mProgressDialog.setMessage("Fetching File.....");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);







        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);


    }



    @Override
    public void onItemClick(RecyclerViewAdapternew.ItemHolder item, int position) {



        downloaded = new Download();
        downloaded.setType("events");

        downloaded.setName(c[position].getDesc()+"»"+c[position].getName());
        downloaded.setPath(c[position].getPath());


        c[position].setName(c[position].getName());
        DownloadSetup ds=new DownloadSetup(getApplicationContext());

        ds.execute(rn, c[position].getName(), downloaded.getPath());

      //  Toast.makeText(getApplicationContext(),downloaded.getPath()+downloaded.getName(),Toast.LENGTH_SHORT).show();
    }


    private void setPalette() {

        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.myPrimaryDarkColor);
                int primary = getResources().getColor(R.color.myPrimaryColor);
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
            }
        });

    }


    private class DownloadSetup extends AsyncTask<String, Integer, String> {

        private Context context;


        public DownloadSetup(Context context) {
            this.context = context;

        }
        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            Log.i("setup", "started");
            HttpsURLConnection connection = null;
            try {
                sUrl[1]=sUrl[1].replace("&","%26").replace(" ","%20");
                URL url = new URL(ServerPath.path+"AndroidNotes?rollno="+sUrl[0]+"&filename="+sUrl[1]+"&path="+sUrl[2]);

                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                connection.getResponseCode();


                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    System.out.println("Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                }
                System.out.println(connection.getResponseCode());
                input = connection.getInputStream();
                char c;
                String s = new String();
                while ((c = (char) input.read()) != (char) -1)
                    s += c;


            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(input!=null)
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (connection != null)
                    connection.disconnect();
            }

            return sUrl[1];
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }




        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

        }

        @Override
        protected void onPostExecute(String result) {

           // Toast.makeText(getApplicationContext(),"over",Toast.LENGTH_SHORT).show();

            DownloadFile downloder = new DownloadFile(getApplicationContext());
            downloder.execute(ServerPath.path+"Android/"+rn+"/"+result, result);
        }




    }




    private class DownloadFile extends AsyncTask<String, Integer, String> {

        private Context context;


        public DownloadFile(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... sUrl) {


            InputStream input = null;
            OutputStream output = null;
            HttpsURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Portal/");
                if(!f.exists())
                    f.mkdir();

                sUrl[1]=sUrl[1].replace("%26","&").replace("%20"," ");
                output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Portal/"+sUrl[1]);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
                //For deleting
                url=new URL(ServerPath.path+"AndroidDelDir?rollno="+rn);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                connection.getResponseCode();


                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                    System.out.println("Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                }
                System.out.println(connection.getResponseCode());
                input = connection.getInputStream();
                char c;
                String s = new String();
                while ((c = (char) input.read()) != (char) -1)
                    s += c;


            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }


            return null;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download

        }




        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();


            coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinator2);
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download1 error: " + result, Toast.LENGTH_LONG).show();
            else
            { Localdb db=new Localdb(getApplicationContext(),Find.dept(rn),null,1);
                db.adddownload(downloaded);

                Snackbar snackbar=Snackbar.make(coordinatorLayout,"File Downloaded",Snackbar.LENGTH_SHORT).setAction("Downloads", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Dept_events.this, Download_view.class);
                        intent.putExtra("rollno",rn);
                        intent.putExtra("usr",u);
                        startActivity(intent);

                    }
                });
                snackbar.show();

                //Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();

            }
        }




    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {


        Intent intent=new Intent();

        switch (position)
        {
            case 0:
                return;

            case 1:
                //mNavigationDrawerFragment.closeDrawer();
                intent = new Intent(this, Profile.class);
                Toast.makeText(this, "Profile ", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //mNavigationDrawerFragment.closeDrawer();
                Toast.makeText(this, "Attendance ", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Attendance_query.class);
                break;
            case 3:
                // mNavigationDrawerFragment.closeDrawer();
                intent = new Intent(this, Mark_query.class);
                Toast.makeText(this, "Marks", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                //mNavigationDrawerFragment.closeDrawer();
                Toast.makeText(this, "Announcements", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Deptupload_query.class);
                break;
            case 5:
                mNavigationDrawerFragment.closeDrawer();
                Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();

                return;
            case 6:
                //mNavigationDrawerFragment.closeDrawer();
                intent = new Intent(this, Note_query.class);
                Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show();
                break;
            case 8:


                Toast.makeText(this,"Fee Payment",Toast.LENGTH_SHORT).show();

            case 7:
                //mNavigationDrawerFragment.closeDrawer();
                intent = new Intent(this, Download_view.class);
                Toast.makeText(this, "Downloads", Toast.LENGTH_SHORT).show();
                break;


        }


        intent.putExtra("rollno", rn);
        intent.putExtra("Usr", u);

        final Intent intent1=intent;
        mNavigationDrawerFragment.closeDrawer(intent1);





       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(getApplicationContext(),"250",Toast.LENGTH_SHORT).show();
                // startActivity(intent1);
                  //finish();
            }
        },200);*/

        //if(mNavigationDrawerFragment.isDrawerOpen())
        //  Toast.makeText(getApplicationContext(),"eeee",Toast.LENGTH_SHORT).show();

        // startActivity(intent);
        //  finish();

    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }




    private class Loadpic extends AsyncTask<String, Void, String> {

        Bitmap bitmap;
        int a;
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(a==1) {
                CircleImageView dp = (CircleImageView) findViewById(R.id.slide_dp);
                dp.setImageBitmap(bitmap);
            }
            mProgressDialogload.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            Localdb db = new Localdb(getApplicationContext(), Find.dept(rn), null, 1);
            byte[] byteArrayImage = new byte[16 * 1024];
            byteArrayImage = db.outdpicture();

            if (byteArrayImage != null)

            {
                BitmapFactory.Options opt;

                opt = new BitmapFactory.Options();
                opt.inTempStorage = new byte[16 * 1024];
                opt.inSampleSize = 2;
                bitmap = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length, opt);
                a=1;

            }

            return  null;
        }

    }








}
