package com.sjitportal.home.portal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

//TODO :Pending
//Images - Year,Slide BG,logo
//Server Create user

//Notification-over
//Database Security-assign Only
// Copy UI-over
//File-Give type option-over
//Loading page -IDK-use progress bar-Basic over
//File Mapping-over
//Comment unlock for delete-over
//Increase 10 to 100 for arrays-over
//Internet Connectivity-over

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    TextView usrname;
    TextView rollno;
    String u;
    String rn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_topdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        usrname = (TextView) findViewById(R.id.SlideUsername);
        rollno = (TextView) findViewById(R.id.SlideRollno);
        u = getIntent().getExtras().getString("Usr");
        rn = getIntent().getExtras().getString("rollno");

        usrname.setText(u);
        rollno.setText(rn);


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

        Fragment object_fragment = null;

        switch (position) {
            case 0:

                TextView usrname;
                TextView rollno;
                TextView usr;
                TextView roll;
                TextView reg;
                TextView dept;
                TextView course;
                TextView year;



               // usr = (TextView) findViewById(R.id.profile_username);
                //roll = (TextView) findViewById(R.id.profile_rollno);
               // reg = (TextView) findViewById(R.id.profile_regid);
                //dept = (TextView) findViewById(R.id.profile_dept);
                //course = (TextView) findViewById(R.id.profile_course);
               // year = (TextView) findViewById(R.id.profile_year);


                Student[] res = new Student[100];
                rn=getIntent().getExtras().getString("rollno");
                res = callstudent("select * from student_personal");

              //  usr.setText(res[0].getName());
               // reg.setText(res[0].getRegno());
               //  roll.setText(res[0].getRollno());
                //dept.setText(res[0].getDept()+" "+res[0].getSec());
                //course.setText(res[0].getCourse());
                //year.setText(res[0].getBatch());


                //object_fragment=new Profile_Class();










                /*Intent home = new Intent(MainActivity.this, Profile.class);
                //String rollno=null;
                home.putExtra("rollno", rn);
                //home.putExtra("Usr","");
                startActivity(home);
                finish();*/

                Toast.makeText(this, "Profile ", Toast.LENGTH_SHORT).show();
                break;
            case 1:

               Intent home = new Intent(MainActivity.this, Profile.class);
                //String rollno=null;
                home.putExtra("rollno", rn);
                home.putExtra("Usr",u);
                startActivity(home);
                finish();
                Toast.makeText(this, "Attendance ", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Marks", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Announcements", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Downloads", Toast.LENGTH_SHORT).show();
                break;
        }
        //FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.container, object_fragment).commit();

    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }



    public Student[] callstudent(String sql) {
        Student b[] ;
        Localdb db = new Localdb(this, Find.dept(rn), null, 1);
        b = db.outstudent(sql);

        if (b[0].getRegno() == null) {

            Student[] c = new Student[100];
            Query_execute qe = null;
            try {
                qe = new Query_execute(Find.dept(rn),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(MainActivity.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
                return c;
            }

            c = qe.onstudent(sql);
            if (c[0].getRegno() != null) {
                for (int i = 0; c[i].getRegno() != null; i++)
                    db.addstudent(c[i]);

                return callstudent(sql);


            } else {
                //              comm.setText("not_found");
                return c;

            }
        } else {
//            comm.setText(b[0].getRegno());
            return b;
        }

    }


}
/*
package com.poliveira.apps.materialtests;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_topdrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



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

        Fragment object_fragment=null;


        switch (position)
        {
            case 0:
                object_fragment=new Profile_Class();
                Toast.makeText(this, "Profile ", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Attendance " , Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Marks", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Announcements" , Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Downloads", Toast.LENGTH_SHORT).show();
                break;
        }

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.container, object_fragment)
        .commit();
    }




    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}

 */