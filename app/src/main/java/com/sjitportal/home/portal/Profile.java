package com.sjitportal.home.portal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lenovo on 8/25/2015.
 */
public class Profile extends ActionBarActivity implements NavigationDrawerCallbacks {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Bitmap bitmap;
    String user;
    TextView usrname;
    TextView rollno;
    TextView usr;
    TextView roll;
    TextView reg;
    TextView dept;
    TextView course;
    TextView year;
    String rn, u;
    ImageView pp;
    CircleImageView dp, dp1;

    @Override
    protected void onStart() {
        super.onStart();
        reg = (TextView) findViewById(R.id.profile_regid);
        dept = (TextView) findViewById(R.id.profile_dept);
        course = (TextView) findViewById(R.id.profile_course);
        year = (TextView) findViewById(R.id.profile_year);
        Loadpic lp=new Loadpic();
        lp.execute();


        Student[] res ;
        rn = getIntent().getExtras().getString("rollno");
        res = callstudent("select * from student_personal where rollno='"+rn+"'");
        user=res[0].getName();
        reg.setText(res[0].getRegno());
//        roll.setText(res[0].getRollno());
        dept.setText(res[0].getDept() + " " + res[0].getSec());
        course.setText(res[0].getCourse());
        year.setText(res[0].getBatch());



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // setContentView(R.layout.activity_main_topdrawer);

        usrname = (TextView) findViewById(R.id.SlideUsername);
        rollno = (TextView) findViewById(R.id.SlideRollno);
        u = getIntent().getExtras().getString("Usr");
        rn = getIntent().getExtras().getString("rollno");

        usrname.setText(u);
        rollno.setText(rn);





        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        pp = (ImageView) findViewById(R.id.pp);
        bitmap = ((BitmapDrawable) pp.getDrawable()).getBitmap();



        dp = (CircleImageView) findViewById(R.id.dp);

        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getApplication(),"hi",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(user);
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        setPalette(bitmap);



        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);



    }

    private void setPalette(Bitmap bitmap1) {




        Palette.from(bitmap1).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.primary_dark);
                int primary = getResources().getColor(R.color.primary);
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getTitle().equals("Refresh")) {

            callstudent("select * from student_personal where rollno like '"+rn+"'",1);
            onStart();

            Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        menu.add("Refresh");
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                ByteArrayOutputStream boas = new ByteArrayOutputStream();


                Bitmap btmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                btmap.compress(Bitmap.CompressFormat.JPEG, 70, boas); //bm is the bitmap object
                byte[] byteArrayImage = boas.toByteArray();

                Localdb db = new Localdb(getApplicationContext(), Find.dept(rn), null, 1);
                db.adddpicture(byteArrayImage);

                byteArrayImage = db.outdpicture();
                BitmapFactory.Options opt;

                opt = new BitmapFactory.Options();
                opt.inTempStorage = new byte[16 * 1024];
                opt.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length, opt);

                ImageView imageView = (ImageView) findViewById(R.id.pp);
                CircleImageView dp = (CircleImageView) findViewById(R.id.slide_dp);
                CircleImageView dp1 = (CircleImageView) findViewById(R.id.dp);
                dp.setImageBitmap(bitmap);
                dp1.setImageBitmap(bitmap);
                imageView.setImageBitmap(bitmap);
            } catch (OutOfMemoryError a) {
                Toast.makeText(getApplicationContext(), "Image size high", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Student[] callstudent(String sql) {
        return callstudent(sql,0);
    }


    public Student[] callstudent(String sql,int refresh) {
        Student b[] ;
        Localdb db = new Localdb(this, Find.dept(rn), null, 1);
        b = db.outstudent(sql);

        if (b[0].getRollno() == null) {

            Student[] c = new Student[100];
            Query_execute qe = null;
            try {
                qe = new Query_execute(Find.dept(rn),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(Profile.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
                return c;
            }

            c = qe.onstudent(sql);
            Log.i("online","yesh");
            if (c[0].getRollno() != null) {
                for (int i = 0; c[i].getRegno() != null; i++)
                    db.addstudent(c[i]);

                return c;


            } else {
                //              comm.setText("not_found");
                return c;

            }
        }

        else {
//            comm.setText(b[0].getRegno());
            if(refresh==1)
            {
                Log.i("refresh", "success");
                db.delstudent();

                return callstudent(sql,0);
            }
            else
            {

                return b;
            }
        }

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
                return;
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

            if(a==1)
            {
                ImageView imageView = (ImageView) findViewById(R.id.pp);
                CircleImageView dp = (CircleImageView) findViewById(R.id.slide_dp);
                CircleImageView dp1 = (CircleImageView) findViewById(R.id.dp);
                dp.setImageBitmap(bitmap);
                dp1.setImageBitmap(bitmap);
                imageView.setImageBitmap(bitmap);

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