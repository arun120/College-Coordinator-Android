package com.sjitportal.home.portal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Lenovo on 8/25/2015.
 */
public class Mark_query extends ActionBarActivity implements NavigationDrawerCallbacks{

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Integer check;
    TextView usrname;
    TextView rollno;
    com.rey.material.widget.Spinner sem,exam;
    String rn,u;
    Button submit;
    String semgiven,examgiven;
    ImageView image;

    @Override
    protected void onStart() {
        super.onStart();

        TextView usrname = (TextView) findViewById(R.id.SlideUsername);
        TextView rollno = (TextView) findViewById(R.id.SlideRollno);

        usrname.setText(u);
        rollno.setText(rn);

        Loadpic lp=new Loadpic();
        lp.execute();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_query);
        // setContentView(R.layout.activity_main_topdrawer);

        usrname=(TextView) findViewById(R.id.SlideUsername);
        rollno=(TextView) findViewById(R.id.SlideRollno);
        u=getIntent().getExtras().getString("Usr");
        rn=getIntent().getExtras().getString("rollno");


        usrname.setText(u);
        rollno.setText(rn);

        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar_new1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Toast.makeText(this,"hi", Toast.LENGTH_SHORT).show();
       submit=(Button) findViewById(R.id.marksubmit);
        exam = (com.rey.material.widget.Spinner) findViewById(R.id.examdrop);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sql = null;
                sql = "select * from marks_table where rollno='" + rn + "' and sem='" + semgiven + "' and " + examgiven + "<>'null';";
                Intent disp = new Intent(Mark_query.this, Mark_display.class);
                disp.putExtra("rollno", rn);
                disp.putExtra("exam", examgiven);
                disp.putExtra("Usr",u);
                disp.putExtra("sem", semgiven);
                disp.putExtra("sql", sql);

                if (examgiven == null) {

                    Toast.makeText(getApplication(), "Details Missing", Toast.LENGTH_SHORT).show();
                } else if (examgiven.equals("Select Exam") || semgiven.equals("Select Sem")) {

                    Toast.makeText(getApplication(), "Details Missing", Toast.LENGTH_SHORT).show();
                } else
                    startActivity(disp);
            }
        });


        examgiven=null;








       semgiven=new String();
        sem= (com.rey.material.widget.Spinner) findViewById(R.id.semdrop);
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
        sem.setAdapter(dataadapter);


        sem.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                semgiven = String.valueOf(sem.getSelectedItem());

                if (position != 0) {
                    List<String> examl = new ArrayList<String>();
                    examl.add("Select Exam");
                    examl.add("IAE1");
                    examl.add("IAE2");
                    examl.add("IAE3");
                    examl.add("IAE4");
                    examl.add("model3");

                    ArrayAdapter<String> dataforexam = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_spn, examl);
                    dataforexam.setDropDownViewResource(R.layout.row_spn_dropdown);
                    exam.setAdapter(dataforexam);


                    check = 1;

                }
            }
        });


        exam.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                examgiven = String.valueOf(ExamMap.get((String)exam.getSelectedItem() ) );
                if (position != 0 && check == 1)
                    check = 2;
            }
        });



       /*
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        */




        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);




    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
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
                Toast.makeText(this, "Profile ", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Profile.class);
                break;
            case 2:
                //mNavigationDrawerFragment.closeDrawer();
                Toast.makeText(this, "Attendance ", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, Attendance_query.class);
                break;
            case 3:
                // mNavigationDrawerFragment.closeDrawer();

                Toast.makeText(this, "Marks", Toast.LENGTH_SHORT).show();
                return;
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