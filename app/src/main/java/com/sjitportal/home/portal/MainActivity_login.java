package com.sjitportal.home.portal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity_login extends ActionBarActivity{


    FloatingActionButton login;
    String Usr, Pass;
    //TextView cmnt;
    EditText usr,pass;
    String abc="start";
    TextInputLayout user,pwd;
    ProgressView myprog;
    ProgressDialog mProgressDialog;

    private class Dbconnect extends AsyncTask<String,Void,String>
    {
        public String ret=new String();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myprog.setProgress(0);
            myprog.start();
        }


        @Override
        protected String doInBackground(String... params) {
            Dbdetails db=new Dbdetails("login");

            Connection conn=null;
            try
            {
                Class.forName(db.getDriver());

                conn= DriverManager.getConnection(db.getUrl(), db.getUserName(), db.getPass());
                Statement stmt=null;
                stmt = conn.createStatement();
                String sql="Select * from student_login_details where rollno like '"+params[0]+"' and password like '"+params[1]+"'";

                ResultSet rs=stmt.executeQuery(sql);


                //rs.next();
                //String usr,pas;
                //usr  = rs.getString("rollno");
                //pas= rs.getString("password");

                //if(usr.equals(params[0]) && pas.equals(params[1]))
                  if(rs.next())
                    abc="found";
                else
                    abc="not";

                stmt.close();

            }catch(SQLException se)
            {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                try{
                    if(conn!=null)
                        conn.close();
                     }catch(SQLException se)
                {
                    se.printStackTrace();
                }
            }
            return abc;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("found")) {
                new AsyncTask<Void,Void,Void>(){

                    Notes[] c ;
                    Query_execute qe ;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        Log.i("Login","onPre");

                        mProgressDialog.setMessage("Setting up Your Profile for First Use...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.setCancelable(false);
                        try {
                            qe = new Query_execute(Find.dept(Usr),StaticApp.getContext());
                        } catch (Internet_Exception e) {
                            e.printStackTrace();
                            Intent intent=new Intent(MainActivity_login.this,Retry.class);
                            startActivity(intent);
                            this.cancel(true);
                            return;
                        }
                        callstudent("select * from student_personal where rollno='" + Usr + "'");


                        mProgressDialog.show();

                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Log.i("Login","onPost");
                        //Toast.makeText(getApplicationContext(),"Move to profile",Toast.LENGTH_SHORT).show();
                        // Log.i("Move","Profile");
                        mProgressDialog.dismiss();
                        Intent home = new Intent(MainActivity_login.this, Profile.class);
                        //String rollno=null;
                        home.putExtra("rollno", Usr);
                        home.putExtra("Usr", "");
                        startActivity(home);
                        //Intent home=new Intent(MainActivity_login.this,Dummy.class);
                        //startActivity(home);

                        finish();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {

                        if(isCancelled())
                            return null;



                        c = qe.onnotes("select * from notes group by notes_type");


                        int i = 0;
                        Localdb db = new Localdb(getApplicationContext(), Find.dept(Usr), null, 1);
                        while (c[i].getNotes_type() != null) {
                            db.addnotetype(c[i].getNotes_type());
                            i++;
                        }


                        c = qe.onnotes("select * from notes group by acadamic_yr");
                        i = 0;
                        while (c[i].getAcadamic_yr() != null) {
                            db.addacadamic_yr(c[i].getAcadamic_yr());
                            i++;
                        }

                        return null;
                    }
                }.execute();




            } else if (abc.equals("not")) {

                Log.i("Login","not Found");
                //user.setError("Invalid UserName");
                //pwd.setError("Invalid Password");
                myprog.stop();
                login.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();


            }

            Log.i("Login","ProgStop");
            myprog.stop();

        }


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_login);
       // LinearLayout ln;
       // ln = (LinearLayout) findViewById(R.id.lay_anim);
       // ln.setVisibility(View.INVISIBLE);
        login = (FloatingActionButton) findViewById(R.id.login);
        //cmnt = (TextView) findViewById(R.id.comment);
        usr = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.pass);
        user= (TextInputLayout) findViewById(R.id.layout_usr);
        pwd= (TextInputLayout) findViewById(R.id.layout_pwd);
        Student b[];
        myprog= (ProgressView) findViewById(R.id.prog);
        Localdb db=new Localdb(this,"login",null,1);
        b=db.outstudent("select * from student_personal");

        mProgressDialog=new ProgressDialog(this);
        if(b[0].getRegno()!=null)

        {
            Dbdetails.dept=Find.dept(b[0].getRollno());
            Intent home=new Intent(MainActivity_login.this,Profile.class);
            //String rollno=null;
            home.putExtra("rollno",b[0].getRollno());
            home.putExtra("Usr",b[0].getName());
            startActivity(home);
            //Intent home=new Intent(MainActivity_login.this,Dummy.class);
            //startActivity(home);

            finish();

        }





        usr.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int keyCode,
                                          KeyEvent event) {
                if (keyCode==EditorInfo.IME_ACTION_DONE) {

                    pass.requestFocus();
                    return true;
                }
                return false;
            }
        });


        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int keyCode,
                                          KeyEvent event) {
                if (keyCode == EditorInfo.IME_ACTION_DONE) {
                    login.performClick();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cmnt.setText("yyyy");



                login.setVisibility(View.INVISIBLE);
                myprog.setProgress(0);
                myprog.start();
                final Editable u = usr.getText();
                final Editable p = pass.getText();

                Usr = u.toString().trim();
                Pass = p.toString();
                Dbconnect log = new Dbconnect();
                if (Usr.length() > 5)
                    Dbdetails.dept = Find.dept(Usr);

                Network_State n=new Network_State();
                if(n.isNetworkAvailable(getApplicationContext())==false)
                {
                    try {
                        throw new Internet_Exception();
                    } catch (Internet_Exception e) {
                        e.printStackTrace();
                        Intent intent=new Intent(MainActivity_login.this,Retry.class);
                        startActivity(intent);
                        return ;
                    }
                }

                log.execute(Usr, Pass);



            }
        });






    }


    public Student[] callstudent(String sql)
    {
        Student b[];
        Localdb db=new Localdb(this,"login",null,1);
        b=db.outstudent(sql);
//        Log.i("localdb ",b[0].getRollno());
        //online find

        if(b[0].getRollno()== null)
        {

            Student[] c =new Student[100];
            Query_execute qe;

            try {


                qe = new Query_execute(Find.dept(Usr),getApplicationContext());
            } catch (Internet_Exception e) {
                e.printStackTrace();
                Intent intent=new Intent(MainActivity_login.this,Retry.class);
                startActivity(intent);
                return c;
            }
            c=qe.onstudent(sql);
            if(c[0].getRollno()!=null)
            {
                for(int i=0;c[i].getRollno()!=null;i++) {
                    Log.i("localdb", c[0].getRollno());
                    db.addstudent(c[0]);
                }
                return c;


            }
            else
            {
                //  comm.setText("not_found");
                return c;

            }
        }
        else {
            //comm.setText(b[0].getRegno());
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
