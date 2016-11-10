package com.sjitportal.home.portal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class Note_query extends ActionBarActivity implements NavigationDrawerCallbacks{

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    TextView usrname;
    TextView rollno;
    com.rey.material.widget.Spinner sem,year,notes,sub;
    String rn,u;
    Button submit;
    String semgiven,acadamic_yrgiven,notesgiven,subgiven;
    ImageView image;

    @Override
    protected void onStart() {
         super.onStart();



        Loadpic lp=new Loadpic();
        lp.execute();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_query);
        // setContentView(R.layout.activity_main_topdrawer);

        usrname=(TextView) findViewById(R.id.SlideUsername);
        rollno=(TextView) findViewById(R.id.SlideRollno);
        u=getIntent().getExtras().getString("Usr");
        rn=getIntent().getExtras().getString("rollno");

        usrname.setText(u);
        rollno.setText(rn);
        // Toast.makeText(this,"hi", Toast.LENGTH_SHORT).show();

        mToolbar = (Toolbar) findViewById(R.id.collapse_toolbar_new1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);






        year= (com.rey.material.widget.Spinner) findViewById(R.id.yeardrop);
         notes= (com.rey.material.widget.Spinner) findViewById(R.id.notesdrop);
         sub= (com.rey.material.widget.Spinner) findViewById(R.id.subdrop);
         notes= (com.rey.material.widget.Spinner) findViewById(R.id.notesdrop);
         submit=(Button) findViewById(R.id.note_submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql=null;
                sql="select * from notes where acadamic_yr='"+acadamic_yrgiven+"'and notes_type='"+ notesgiven + "'and subcode='"+subgiven+"'and sem='"+semgiven+"'";
               // Toast.makeText(getApplicationContext(),sql, Toast.LENGTH_SHORT).show();
                Intent next=new Intent(Note_query.this,Notes_download.class);
                next.putExtra("sql",sql);
                next.putExtra("rollno",rn);
                next.putExtra("Usr", u);


                if (notesgiven == null) {

                    Toast.makeText(getApplication(), "Details Missing", Toast.LENGTH_SHORT).show();
                } else if (notes.getSelectedItemPosition()==0) {

                    Toast.makeText(getApplication(), "Details Missing", Toast.LENGTH_SHORT).show();
                } else

                startActivity(next);



            }
        });








        notes.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {

                if (position != 0) {
                    notesgiven = String.valueOf(notes.getSelectedItem());
                }
            }
        });



        sub.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {

                if (position != 0) {


                    subgiven = String.valueOf(sub.getSelectedItem());
                    Subject_sem[] d ;
                    //  Query_execute q = new Query_execute(Find.dept(rn));
                    // d = q.onsubject_sem("select * from subject_sem_table where subname='"+subgiven+"'");
                    d = callsubject_sem("select * from subject_sem_table where subname='" + subgiven + "'");
                    subgiven = d[0].getSubcode();

                    List<String> typel = new ArrayList<String>();
                  /*  Notes[] c = new Notes[100];
                    Query_execute qe = new Query_execute(Find.dept(rn));
                    c = qe.onnotes("select * from notes group by notes_type");
                    int i = 0;
                    typel.add("Select Notes Type");
                    if (c[i].getNotes_type() == null) {
                        typel.add("Not Found");
                    }
                    while (c[i].getNotes_type() != null) {
                        typel.add(c[i].getNotes_type());
                        i++;
                    }*/
                    String[] c ;
                    Localdb db = new Localdb(getApplicationContext(), Find.dept(rn), null, 1);
                    c = db.outnotetype("Select * from notetype");
                    int i = 0;
                    typel.add("Select Notes Type");
                    if (c[i] == null) {
                        typel.add("Not Found");
                    }
                    while (c[i] != null) {
                        typel.add(c[i]);
                        i++;
                    }

                    ArrayAdapter<String> adaptersub = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_spn, typel);
                    adaptersub.setDropDownViewResource(R.layout.row_spn_dropdown);
                    notes.setAdapter(adaptersub);
                    notes.setSelection(0);
                }
            }
        });

        year.setOnItemSelectedListener(new com.rey.material.widget.Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(com.rey.material.widget.Spinner parent, View view, int position, long id) {
                acadamic_yrgiven=String.valueOf(year.getSelectedItem());

                if (position != 0) {
                    Subject_sem[] c ;

                  //  Toast.makeText(getApplicationContext(), Find.dept(rn), Toast.LENGTH_SHORT).show();

                    //  Query_execute qe=new Query_execute(Find.dept(rn));
                    // c=qe.onsubject_sem("select * from subject_sem_table where regulation='"+Find.regulation(rn)+"' and sem='"+semgiven+"'");

                    c = callsubject_sem("select * from subject_sem_table where subtype='theory' and regulation='" + Find.regulation(rn) + "' and sem='" + semgiven + "'");
                    // c=callsubject_sem("select * from subject_sem_table where regulation='"+"2008"+"' and sem='"+"05"+"'");

                    List<String> subl = new ArrayList<String>();
                    int i = 0;
                    subl.add("Select Subject");
                    if (c[i].getSubname() == null) {
                        subl.add("Not Found");
                    }


                    while (c[i].getSubname() != null) {
                        subl.add(c[i].getSubname());
                        i++;
                    }

                    ArrayAdapter<String> adaptersem = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_spn, subl);
                    adaptersem.setDropDownViewResource(R.layout.row_spn_dropdown);
                    sub.setAdapter(adaptersem);
                    sub.setSelection(0);


                }

            }
        });



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
                semgiven=String.valueOf(sem.getSelectedItem());
                if(position!=0) {


                    List<String> foryear=new ArrayList<String>();
                    foryear.add("Select Academic Year");

                    /*Notes[] c =new Notes[100];
                    Query_execute qe=new Query_execute(Find.dept(rn));
                    c=qe.onnotes("select * from notes group by acadamic_yr");
                    int i=0;
                    if(c[0].getAcadamic_yr()==null)
                        foryear.add("Not Found");
                    while(c[i].getAcadamic_yr()!=null)
                    {
                        foryear.add(c[i].getAcadamic_yr());
                        i++;
                    }*/


                    String[] c;
                    Localdb db=new Localdb(getApplicationContext(),Find.dept(rn),null,1);
                    c=db.outacadamic_yr("Select * from acadamic_yr");
                    int i = 0;
                    if (c[i]== null) {
                        foryear.add("Not Found");
                    }
                    while (c[i]!= null) {
                        foryear.add(c[i]);
                        i++;
                    }

                    ArrayAdapter<String> adapteryear=new ArrayAdapter<String>(getApplicationContext(),R.layout.row_spn,foryear);
                    adapteryear.setDropDownViewResource(R.layout.row_spn_dropdown);
                    year.setAdapter(adapteryear);
                    year.setSelection(0);

                }

            }
        });












     /*   mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/



        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);




    }


/*
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

    }*/



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
                // Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show();
                return;
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





    public Notes[] callnotes(String sql)
    {
        Notes b[];
        Localdb db=new Localdb(this,Find.dept(rn),null,1);
        b=db.outnotes(sql);

        if(b[0].getSem()== null)
        {

            Notes[] c =new Notes[100];
            Query_execute qe= null;
            try {
                qe = new Query_execute(Find.dept(rn),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(Note_query.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
                return c;
            }
            c=qe.onnotes(sql);
            if(c[0].getSem()!=null)
            {
                for(int i=0;c[i].getSem()!=null;i++)
                    db.addnotes(c[i]);

                return callnotes(sql);


            }
            else
            {
              //  comm.setText("not_found");
                return c;

            }
        }
        else {
            //comm.setText(b[0].getFilename());
            return b;
        }

    }



    public Subject_sem[] callsubject_sem(String sql)
    {
        Subject_sem b[];
        Localdb db=new Localdb(this,Find.dept(rn),null,1);
        b=db.outsubject_sem(sql);

       if(b[0].getSubtype()==null)
        {

            Subject_sem[] c =new Subject_sem[100];
            Query_execute qe= null;
            try {
                qe = new Query_execute(Find.dept(rn),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(Note_query.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
                c[0]=new Subject_sem();
                return c;
            }
            c=qe.onsubject_sem(sql);
            if(c[0].getSubtype()!=null)
            {
                for(int i=0;c[i].getSubcode()!=null;i++)
                    db.addsubject_sem(c[i]);

                return callsubject_sem(sql);


            }
            else
            {
            //    comm.setText("not_found");
                return c;

            }
        }
        else {
          //  comm.setText(b[0].getSubcode());
            return b;
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