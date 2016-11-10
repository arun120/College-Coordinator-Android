package com.sjitportal.home.portal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Notes_download extends AppCompatActivity implements RecyclerViewAdapternew.OnItemClickListener {


    CoordinatorLayout coordinatorLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView image;
    RecyclerViewAdapternew myadapter;
    RecyclerView myrecycler;
    ImageView card_image;

    ProgressDialog mProgressDialog,mLoadDialog;
    PowerManager.WakeLock mWakeLock;
    ListView down;
    String sql,rn,u;
    Download downloaded;
    Notes[] c;
    Notes [] d ;
    String dates;
    //TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_download);

       /* Toolbar collapse_bar = (Toolbar) findViewById(R.id.collapse_toolbar_new);
        setSupportActionBar(collapse_bar);
        image = (ImageView) findViewById(R.id.images1);
        image.setImageResource(R.drawable.clg_2);
        Animation anima= AnimationUtils.loadAnimation(this, R.anim.zoomin);
        image.startAnimation(anima);*/

        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinator);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
       // setPalette();

        myrecycler= (RecyclerView) findViewById(R.id.myrecyclerview);
        myadapter=new RecyclerViewAdapternew(this);
        myadapter.setOnItemClickListener(this);
        myrecycler.setAdapter(myadapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(this));


        mLoadDialog=new ProgressDialog(this);
        mLoadDialog.setMessage("Fetching data.....");
        mLoadDialog.setIndeterminate(true);
        mLoadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Fetching File.....");
        mProgressDialog.setIndeterminate(true);

        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);

        // execute this when the downloader must be fired
        // final DownloadTask downloadTask = new DownloadTask(YourActivity.this);
        // downloadTask.execute("the url to the file you want to download");

       // down=(ListView) findViewById(R.id.downlist);
         sql=getIntent().getExtras().getString("sql");
        rn=getIntent().getExtras().getString("rollno");
        u=getIntent().getExtras().getString("Usr");

        c=new Notes[100];


        new AsyncTask<Void,Void,Void>(){
            Query_execute qe = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                try {
                    qe = new Query_execute(Find.dept(rn),getApplicationContext());
                } catch (Internet_Exception e) {
                    e.printStackTrace();
                    Intent intent=new Intent(Notes_download.this,Retry.class);
                    intent.putExtra("rollno",rn);
                    intent.putExtra("Usr",u);
                    startActivity(intent);
                    this.cancel(true);
                    return;
                }
                mLoadDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                if(isCancelled())
                    return null;
                c = qe.onnotes("select * from notes group by notes_type");
                int   i = 0,j=0;
                int found=0;
                String[] check;
                Localdb db=new Localdb(getApplicationContext(),Find.dept(rn),null,1);
                check=db.outnotetype("Select * from notetype");
                while (c[i].getNotes_type() != null) {
                    j=0;
                    while(check[j]!=null)
                    {
                        if(c[i].getNotes_type().equals(check[j]))
                            found=1;
                        j++;
                    }

                    if(found==0)
                        db.addnotetype(c[i].getNotes_type());
                    found=0;
                    i++;
                }

                c=qe.onnotes("select * from notes group by acadamic_yr");
                i=0;
                check=db.outacadamic_yr("select * from acadamic_yr");
                while(c[i].getAcadamic_yr()!=null)
                {

                    j=0;
                    while(check[j]!=null)
                    {
                        if(c[i].getAcadamic_yr().equals(check[j]))
                            found=1;
                        j++;
                    }

                    if(found==0)
                        db.addacadamic_yr(c[i].getAcadamic_yr());
                    found=0;
                    i++;

                }

                d =new Notes[100];

                d = qe.onnotes(sql);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                int i;
                i=0;
                List<String> list =new ArrayList<>();
                if(d[i].getPath()==null)
                {
                    myadapter.add(myadapter.getItemCount(),"No Data Found", 0);

                    //list.add("No Data");
                }
                while(d[i].getPath()!=null) {
                    //Setting Card Resources

                    myadapter.add(myadapter.getItemCount(),
                            d[i].getDescp()+"»"+d[i].getFilename(), getImage(d[i].getFilename()));


                    i++;


                }


            mLoadDialog.dismiss();



            }
        }.execute();





        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                @Override
                                                public void onCancel(DialogInterface dialog) {
                                                    //downloadTask.cancel(true);
                                                }
                                            }

        );




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



    @Override
    public void onItemClick(RecyclerViewAdapternew.ItemHolder item, int position) {

       // Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();


        downloaded = new Download();
        downloaded.setType("notes");
        d[position].setFilename(d[position].getFilename().replace(" ","%20"));
        downloaded.setName(d[position].getSem()+"»"+d[position].getSubcode()+"»"+d[position].getDescp()+"»"+d[position].getFilename());
        downloaded.setPath(d[position].getPath());

        DownloadSetup ds=new DownloadSetup(getApplicationContext());
        ds.execute(rn,d[position].getFilename(),downloaded.getPath());
        /*if (position == 0) {
               //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();




        }*/
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
            HttpURLConnection connection = null;
            try {
                URL url = new URL(ServerPath.path+"AndroidNotes?rollno="+sUrl[0]+"&filename="+sUrl[1]+"&path="+sUrl[2]);
                Log.i("url",url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                connection.getResponseCode();


                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    System.out.println("Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                }
                System.out.println(connection.getResponseCode());
                input = connection.getInputStream();
                char c;
                String s = new String();
                while ((c = (char) input.read()) != (char) -1)
                    s += c;

//                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
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

          //  Toast.makeText(getApplicationContext(),"over",Toast.LENGTH_SHORT).show();

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
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
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
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    connection.getResponseCode();


                    // expect HTTP 200 OK, so we don't mistakenly save error report
                    // instead of the file
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
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


            coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinator);
              mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
            { Localdb db=new Localdb(getApplicationContext(),Find.dept(rn),null,1);
                db.adddownload(downloaded);
                Snackbar snackbar=Snackbar.make(coordinatorLayout,"File Downloaded",Snackbar.LENGTH_SHORT).setAction("Downloads", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Notes_download.this, Download_view.class);
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
        getMenuInflater().inflate(R.menu.menu_notes_download, menu);



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
}
