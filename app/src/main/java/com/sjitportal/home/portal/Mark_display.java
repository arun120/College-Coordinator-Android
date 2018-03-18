package com.sjitportal.home.portal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Mark_display extends AppCompatActivity{

    String rn,u, exam,sem,sql;
    TextView comm;
    GridView disp;
    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_display);


        rn = getIntent().getExtras().getString("rollno");
        u=getIntent().getExtras().getString("Usr");
        exam = getIntent().getExtras().getString("exam");
        sem=getIntent().getExtras().getString("sem");
        sql=getIntent().getExtras().getString("sql");
        comm=(TextView) findViewById(R.id.comm);
        disp=(GridView) findViewById(R.id.displist);

        Marks[] c;
        Log.i("Request ","Started");
        c=callmarks(sql);


        //Query_execute q=new Query_execute(Find.dept(rn));
        //c=q.onmarks("select * from marks_table where subcode='cs2351'");
        int i=0;
        List<String> list=new ArrayList<>();



        if(c[i].getRollno()==null)
            list.add("Not Found");
        else {
            //   comm.setText(c[0].getModel1() + "  " + c[0].getCycle1());
            while(c[i].getRollno()!=null)
            {
                Subject_sem[] d;
                d=callsubject_sem("select * from subject_sem_table where subcode='"+c[i].getSubcode()+"'");

                if(c[i].mark(exam)!=null) {
                    list.add(d[0].getSubname());
                    list.add(c[i].mark(exam));
                }
                i++;
            }

        }


        MyAdapter adapter = new MyAdapter(this,
                R.layout.grid_text, list);
        disp.setAdapter(adapter);


    }


    public class MyAdapter extends ArrayAdapter<String> {

        List<String> objects;
        Context context;

        public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.objects = objects;
        }

        int m = 0, n;

        @Override
        public View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                tv = (TextView) inflater.inflate(R.layout.grid_text, parent, false);
            } else {
                tv = (TextView) convertView;
            }
            tv.setText(objects.get(position));

            if (position == 0 || position == 1) {
                //tv.setBackgroundColor(Color.WHITE);
                tv.setBackgroundResource(R.color.disp_head_gray);
            } else {

                if (m < 2) {
                    m++;
                    tv.setBackgroundResource(R.color.disp_tab_1);
                    //    tv.setBackgroundColor(Color.BLUE);

                } else {
                    m++;
                    if (m >= 2) {
                        tv.setBackgroundResource(R.color.disp_tab_2);
                        //tv.setBackgroundColor(Color.YELLOW);
                        if (m == 4) {
                            m = 0;
                        }
                    }

                }
            }
            return tv;
        }
    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mark_display, menu);
        menu.clear();
        menu.add("Refresh");
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

        if(item.getTitle().equals("Refresh")) {

            callmarks(sql, 1);
            onStart();

            Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public Marks[] callmarks(String sql) {
        return callmarks(sql,0);
    }

    public Marks[] callmarks(String sql,int refresh) {
        Marks b[] ;
        Localdb db = new Localdb(this, Find.dept(rn), null, 1);
        b = db.outmarks(sql);
       // Log.i("Local DB","Over");
        if (b[0].getSem() == null || refresh==1) {

            Marks[] c = new Marks[100];
            Query_execute qe = null;
            try {
                qe = new Query_execute(Find.dept(rn),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(Mark_display.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
                c[0]=new Marks();
                return c;
            }
            Log.i("Internet call","start");
            c = qe.onmarks(sql);
            Log.i("Internet call","finished");
            if (c[0].getSem() != null) {


               // Log.d("delete","requested");

                db.delmarks(sem);


                for (int i = 0; c[i].getSem() != null; i++)
                    db.addmarks(c[i]);
              //  Log.i("delerte N add ","over");

                return c;


            } else {
                //  comm.setText("not_found");
                return c;

            }
        }
        else {
            // comm.setText(b[0].getModel1());

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
                Intent intent=new Intent(Mark_display.this,Retry.class);
                intent.putExtra("rollno",rn);
                intent.putExtra("Usr",u);
                startActivity(intent);
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



}