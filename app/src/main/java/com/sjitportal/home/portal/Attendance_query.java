package com.sjitportal.home.portal;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class Attendance_query extends ActionBarActivity implements NavigationDrawerCallbacks {


    private Toolbar mToolbar;
    TextView text;
    com.rey.material.widget.Spinner semdrop;
    Button submit;
    ListView dispdate;
    String semgiven, rn, u;
    ProgressDialog mProgressDialog;


    @Override
    protected void onStart() {
        super.onStart();
        mProgressDialog=new ProgressDialog(this);
        semdrop.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                semgiven = String.valueOf(semdrop.getSelectedItem());



                new AsyncTask<Void,Void,Void>(){

                    Attendance[] c;
                    Query_execute q;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        mProgressDialog.setMessage("Loading...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.setCancelable(true);

                        try {
                            q = new Query_execute(Find.dept(rn), getApplicationContext());
                        } catch (Internet_Exception e) {

                            e.printStackTrace();
                            Intent intent = new Intent(Attendance_query.this, Retry.class);
                            intent.putExtra("rollno", rn);
                            intent.putExtra("Usr", u);
                            startActivity(intent);
                            this.cancel(true);
                            return;

                        }

                        mProgressDialog.show();

                    }



                    @Override
                    protected Void doInBackground(Void... params) {
                        if(isCancelled())
                            return null;

                        c = q.onattendance("select * from overallattendence where rollno='" + rn + "' and sem='" + semgiven + "';");

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        int i = 0;
                        List<String> date = new ArrayList<String>();
                        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

                        mProgressDialog.dismiss();
                        if (c[i].getSem() == null) {

                            Snackbar snackbar = Snackbar.make(drawerLayout, "No Leave Taken", Snackbar.LENGTH_LONG);
                            text.setVisibility(View.INVISIBLE);
                            // date.add("2016-07-12");
                            date_adapter adapter = new date_adapter(getApplicationContext(), date);
                            dispdate.setAdapter(adapter);
                            snackbar.show();
                        } else {
                            text.setVisibility(View.VISIBLE);
                            while (c[i].getRollno() != null) {
                                date.add(c[i].getDate());

                                i++;
                            }


                            date_adapter adapter1 = new date_adapter(getApplicationContext(), date);

                            dispdate.setAdapter(adapter1);
                        }
                    }
                }.execute();
               //dateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //dispdate.setAdapter(dateadapter);

            }
        });


       /* submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Attendance[] c ;
                Query_execute q = null;
                try {
                    q = new Query_execute(Find.dept(rn),getApplicationContext());
                } catch (Internet_Exception e) {

                    e.printStackTrace();
                    Intent intent=new Intent(Attendance_query.this,Retry.class);
                    intent.putExtra("rollno",rn);
                    intent.putExtra("Usr",u);
                    startActivity(intent);
                    return;
                }
                c = q.onattendance("select * from overallattendence where rollno='" + rn + "' and sem='" + semgiven + "';");
                int i = 0;
                List<String> date = new ArrayList<String>();


                if (c[i].getSem() == null) {

                    date.add("No Leave Taken For This Semister");
                }
                while (c[i].getRollno()!= null) {
                    date.add(c[i].getDate());

                    i++;
                }


                ArrayAdapter<String> dateadapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_spn_dropdown, date);
                //dateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dispdate.setAdapter(dateadapter);
                text.setVisibility(View.VISIBLE);

            }
        });*/




        Loadpic lp = new Loadpic();
        lp.execute();

    }





    private NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_query);

        text= (TextView) findViewById(R.id.newtext);
        semdrop= (com.rey.material.widget.Spinner) findViewById(R.id.atten_sem_dropdown);
        dispdate=(ListView) findViewById(R.id.dispdate);
       // submit=(Button) findViewById(R.id.attendance_submit);
         rn=getIntent().getExtras().getString("rollno");
         u=getIntent().getExtras().getString("Usr");



        TextView usrname = (TextView) findViewById(R.id.SlideUsername);
        TextView rollno = (TextView) findViewById(R.id.SlideRollno);

        usrname.setText(u);
        rollno.setText(rn);

        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar_new1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        semgiven=new String();
        List<String> item=new ArrayList<String>();
        item.add("Select Sem");
        item.add("01");
        item.add("02");
        item.add("03");
        item.add("04");
        item.add("05");
        item.add("06");
        item.add("07");
        item.add("08");
        ArrayAdapter<String> dataadapter=new ArrayAdapter<String>(this,R.layout.row_spn,item);
        dataadapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        semdrop.setAdapter(dataadapter);






      /*  mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/



        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);



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

                intent = new Intent(this, Profile.class);
                //mNavigationDrawerFragment.closeDrawer();
                Toast.makeText(this, "Profile ", Toast.LENGTH_SHORT).show();
                break;
                //return;
            case 2:
                //mNavigationDrawerFragment.closeDrawer();
                Toast.makeText(this, "Attendance ", Toast.LENGTH_SHORT).show();
               return;

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
                intent = new Intent(this, Dept_events.class);
                break;
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
