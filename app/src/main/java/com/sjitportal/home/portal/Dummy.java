package com.sjitportal.home.portal;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;

public class Dummy extends ActionBarActivity {


    TextView comm;

 Resources getContext(){

     return getApplicationContext().getResources();
 }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
      /*  comm = (TextView) findViewById(R.id.cmt);

        Subject_sem[] c=new Subject_sem[10];

        String rn="12cs1203";
        try {
            c=callsubject_sem("select * from subject_sem_table where regulation='"+Find.regulation(rn)+"' and sem='"+"05"+"'");
        } catch (Internet_Exception e) {
            e.printStackTrace();
        }
*/

        // callnotes("select * from notes where sem='04'");
       // comm.setText("end");


      /*
        Student[] c =new Student[10];
        Query_execute qe = new Query_execute("cse");

        c=qe.onstudent("select * from student_personal where rollno='12cs1203'");
        if(c[0].getRegno()== null) {

            comm.setText("not_found");
        }
        else {
            comm.setText(c[0].getRegno());
            //comm.setText("end");
        }

      */


    }


    public Student[] callstudent(String sql)
    {
        Student b[]=new Student[10];
        Localdb db=new Localdb(this,"cse",null,1);
         b=db.outstudent(sql);

        if(b[0].getRegno()== null)
        {

            Student[] c =new Student[10];
            Query_execute qe= null;
            try {
                qe = new Query_execute("cse",getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
            }
            c=qe.onstudent(sql);
            if(c[0].getRegno()!=null)
            {
                for(int i=0;c[i].getRegno()!=null;i++)
             db.addstudent(c[i]);

                return callstudent(sql);


        }
        else
            {
                comm.setText("not_found");
            return c;

            }
        }
            else {
            comm.setText(b[0].getRegno());
        return b;
        }

    }



    public Notes[] callnotes(String sql)
    {
        Notes b[]=new Notes[10];
        Localdb db=new Localdb(this,"cse",null,1);
        b=db.outnotes(sql);

        if(b[0].getSem()== null)
        {

            Notes[] c =new Notes[10];
            Query_execute qe= null;
            try {
                qe = new Query_execute("cse",getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
            }
            c=qe.onnotes(sql);
            if(c[0].getSem()!=null)
            {
                for(int i=0;c[i]!=null;i++)
                  db.addnotes(c[i]);

                return callnotes(sql);


            }
            else
            {
                comm.setText("not_found");
                return c;

            }
        }
        else {
            comm.setText(b[0].getFilename());
            return b;
        }

    }




    public Marks[] callmarks(String sql)
    {
        Marks b[]=new Marks[10];
        Localdb db=new Localdb(this,"cse",null,1);
        b=db.outmarks(sql);

        if(b[0].getSem()== null)
        {

            Marks[] c =new Marks[10];
            Query_execute qe= null;
            try {
                qe = new Query_execute("cse",getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
            }
            c=qe.onmarks(sql);
            if(c[0].getSem()!=null)
            {
                for(int i=0;c[i].getSem()!=null;i++)
                    db.addmarks(c[i]);

                return callmarks(sql);


            }
            else
            {
                comm.setText("not_found");
                return c;

            }
        }
        else {
            comm.setText(b[0].getModel1());
            return b;
        }

    }



    public Subject_sem[] callsubject_sem(String sql) throws Internet_Exception {
        Subject_sem b[]=new Subject_sem[10];
        Localdb db=new Localdb(this,"cse",null,1);
        b=db.outsubject_sem(sql);

        if(b[0].getSem()== null)
        {

            Subject_sem[] c =new Subject_sem[10];
            Query_execute qe=new Query_execute("cse",getApplicationContext());
            c=qe.onsubject_sem(sql);
            if(c[0].getSem()!=null)
            {
                for(int i=0;c[i].getSem()!=null;i++)
                    db.addsubject_sem(c[i]);

                return callsubject_sem(sql);


            }
            else
            {
                comm.setText("not_found");
                return c;

            }
        }
        else {
            comm.setText(b[0].getSubcode());
            return b;
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
