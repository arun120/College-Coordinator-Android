package com.sjitportal.home.portal;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;


import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Download_view extends ActionBarActivity implements NavigationDrawerCallbacks{

    public interface searchClick
    {
        public boolean search(String s);

    }

    private Toolbar mToolbar;
    Fragment fragmentnote;
    FragmentPagerAdapter adapterViewPager;
    String rn,u;
    ListView noteslist;
    Bundle args;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    public static searchClick searchclick=null;


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
        setContentView(R.layout.activity_download_view);

        rn=getIntent().getExtras().getString("rollno");
        u=getIntent().getExtras().getString("Usr");



        mToolbar = (Toolbar) findViewById(R.id.toolbar_slide);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // ActionBar bar = getActionBar();
        // bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //for (int i=1; i <= 3; i++) {
        //  ActionBar.Tab tab = bar.newTab();
        //   tab.setText("Tab " + i);
        //     tab.setTabListener((ActionBar.TabListener) this); bar.addTab(tab);
        //   }

        ViewPager mViewPager;
        mViewPager=(ViewPager) findViewById(R.id.pager);

        args=new Bundle();
        args.putString("rollno",getIntent().getExtras().getString("rollno"));
        args.putString("Usr",getIntent().getExtras().getString("Usr"));



        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),args,Download_view.this);
        mViewPager.setAdapter(adapterViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //fragmentnote = new Notes_fragment();
        //fragmentnote.setArguments(getIntent().getExtras());

        // getFragmentManager().beginTransaction()
        //   .add(R.id.container, fragmentnote).commit();




        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_download_view, menu);
        MenuItem search=menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_LONG).show();
               return searchclick.search(newText);

            }
        });
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



    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final int NUM_ITEMS = 3;
        Bundle args;
        private Context context;

        public MyPagerAdapter(FragmentManager fragmentManager,Bundle a,Context context) {

            super(fragmentManager);
            this.context=context;
            args=a;
        }

        // Returns total number of pages
        @Override public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override   public android.support.v4.app.Fragment getItem(int position) {

            android.support.v4.app.Fragment fragment;

            switch (position) {


                case 0:                 // Fragment # 0 - This will show FirstFragment

                    fragment=new Notes_fragment();
                    break;
                case 1:
                    fragment=new Event_fragment();
                    break;
                case 2: // Fragment # 1 - This will show SecondFragment
                    fragment=new Circular_fragment();
                    break;
                default:
                    return null;
            }
            fragment.setArguments(args);

            return fragment;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {


                case 0:
                    return "Notes";

                case 1:
                    return "Events";

                case 2:
                    return "Circulars";

                default:
                    return null;
            }
        }

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
