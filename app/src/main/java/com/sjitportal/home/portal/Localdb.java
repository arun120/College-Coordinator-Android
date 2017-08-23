package com.sjitportal.home.portal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

/**
 * Created by Home on 18-08-2015.
 */
public class Localdb extends SQLiteOpenHelper {

//UPDATION OF ANY COLUMN NAME SHOULD BE DONE IN 4 PLACES ...CREATE_LINE,CLASS,INSERT PLACES
    //public static String sdbname;
    //public static String db_name=sdbname+".db";
    public static Integer db_ver=2;

    public Localdb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name+".db", factory, db_ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //super.onCreate(db);
        String q=new String();

        q="CREATE TABLE IF NOT EXISTS dpicture (dp blob);";
        db.execSQL(q);

         //for downloaded items
        q="CREATE TABLE IF NOT EXISTS downloads (type varchar(20),path varchar(20),name varchar(20));";
        db.execSQL(q);
         //for academic year
        q="CREATE TABLE IF NOT EXISTS acadamic_yr (year varchar(20));";
        db.execSQL(q);
        //for notes type
        q="CREATE TABLE IF NOT EXISTS notetype (type varchar(20));";
        db.execSQL(q);

        //Subject for each semester
         q="CREATE TABLE IF NOT EXISTS subject_sem_table (subcode varchar(10) DEFAULT NULL,regulation varchar(20) DEFAULT NULL,subname varchar(40) DEFAULT NULL,sem varchar(10) DEFAULT NULL,subtype varchar(10) DEFAULT NULL);";
         db.execSQL(q);
        //Student personal details
        q="CREATE TABLE IF NOT EXISTS  student_personal (rollno varchar(20) DEFAULT NULL," +
                "  regno varchar(20) DEFAULT NULL," +
                "  name varchar(40) DEFAULT NULL," +
                "  gender varchar(10) DEFAULT NULL," +
                "  bloodgrp varchar(10) DEFAULT NULL," +
                "  batch varchar(10) DEFAULT NULL," +
                "  course varchar(20) DEFAULT NULL," +
                "  dept varchar(20) DEFAULT NULL," +
                "  sec varchar(2) DEFAULT NULL," +
                "  mobileno varchar(15) DEFAULT NULL," +
                "  mailid varchar(50) DEFAULT NULL," +
                "  food varchar(10) DEFAULT NULL," +
                "  accomodation varchar(20) DEFAULT NULL," +
                "  initial varchar(5) DEFAULT NULL"+");";
        db.execSQL(q);

        //Notes for dept
        q="CREATE TABLE IF NOT EXISTS  notes (  acadamic_yr varchar(20) DEFAULT NULL,  sem varchar(4) DEFAULT NULL,  subcode varchar(10) DEFAULT NULL,  notes_type varchar(40) DEFAULT NULL,  filename varchar(40) DEFAULT NULL,  path varchar(300) DEFAULT NULL);";
        db.execSQL(q);

        //Cycle and Model marks
        q="CREATE TABLE IF NOT EXISTS  marks_table (rollno varchar(10) DEFAULT NULL,sem varchar(2) DEFAULT NULL,  subcode varchar(10) DEFAULT NULL,cycle1 varchar(10) DEFAULT NULL,  model1 varchar(10) DEFAULT NULL,cycle2 varchar(10) DEFAULT NULL,  model2 varchar(10) DEFAULT NULL,  cycle3 varchar(10) DEFAULT NULL,  model3 varchar(10) DEFAULT NULL);";
        db.execSQL(q);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            String q = "ALTER TABLE  marks_table add column unit1 varchar(10) DEFAULT NULL;";
            db.execSQL(q);
            q = "ALTER TABLE  marks_table add column unit2 varchar(10) DEFAULT NULL;";
            db.execSQL(q);
            q = "ALTER TABLE  marks_table add column unit3 varchar(10) DEFAULT NULL;";
            db.execSQL(q);
        }
    }

    public void adddpicture(byte[] s )
    {



        ContentValues values=new ContentValues();
        values.put("dp", s);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF  EXISTS dpicture;");
        db.execSQL("CREATE TABLE IF NOT EXISTS dpicture (dp blob);");
        db.insert("dpicture",null,values);
        db.close();


    }


    public void addnotetype(String s)
    {
        ContentValues values=new ContentValues();
        values.put("type", s);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("notetype",null,values);
        db.close();


    }



    public void addacadamic_yr(String s)
    {
        ContentValues values=new ContentValues();
        values.put("year",s);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("acadamic_yr",null,values);
        db.close();


    }



    public void adddownload(Download s)
    {
        ContentValues values=new ContentValues();
        values.put("type",s.getType());
        values.put("name",s.getName());
        values.put("path",s.getPath());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("downloads",null,values);
        db.close();


    }



    public void addnotes(Notes s)
    {
        ContentValues values=new ContentValues();
        values.put("sem",s.getSem());
        values.put("subcode",s.getSubcode());
        values.put("acadamic_yr",s.getAcadamic_yr());
        values.put("filename", s.getFilename());
        values.put("notes_type",s.getNotes_type());
        values.put("path",s.getPath());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("notes",null,values);
        db.close();


    }




    public void addmarks(Marks s)
    {
        ContentValues values=new ContentValues();
        values.put("sem",s.getSem());
        values.put("subcode",s.getSubcode());
        values.put("cycle1",s.getCycle1());
        values.put("rollno", s.getRollno());
        values.put("model1",s.getModel1());
        values.put("model2",s.getModel2());
        values.put("cycle3",s.getCycle3());
        values.put("cycle2",s.getCycle2());
        values.put("model3", s.getModel3());
        values.put("unit1",s.getUnit1());
        values.put("unit2",s.getUnit2());
        values.put("unit3", s.getUnit3());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("marks_table",null,values);
        db.close();


    }


    public void addsubject_sem(Subject_sem s)
    {
        ContentValues values=new ContentValues();
        values.put("sem",s.getSem());
        values.put("subcode",s.getSubcode());
        values.put("regulation",s.getRegulation());
        values.put("subname", s.getSubname());
        values.put("subtype",s.getSubtype());
         SQLiteDatabase db = getWritableDatabase();
        db.insert("subject_sem_table",null,values);
        db.close();


    }




    public void addstudent(Student s)
    {
        ContentValues values=new ContentValues();
       // values.put("accomodation",s.getAccomodation());
        values.put("batch",s.getBatch());
        //values.put("bloodgrp",s.getBloodgrp());
        values.put("course",s.getCourse());
        values.put("dept",s.getDept());
        //values.put("gender",s.getGender());
        //values.put("initial",s.getInitial());
        //values.put("mailid",s.getMailid());
        //values.put("mobileno",s.getMobileno());
        values.put("name",s.getName());
        values.put("regno",s.getRegno());
        values.put("rollno",s.getRollno());
        values.put("sec",s.getSec());
        //values.put("food",s.getFood());

        Log.i("localdb","added");

        SQLiteDatabase db = getWritableDatabase();
        db.insert("student_personal",null,values);
        db.close();


    }

    public void delstudent()
    {
        SQLiteDatabase db=getWritableDatabase();

        db.delete("student_personal",null,null);
        //db.execSQL(sql);
        db.close();
    }

      public Student[] outstudent(String sql)
      {
          Student [] s=new Student[100];
          SQLiteDatabase db = getWritableDatabase();
          Cursor c =db.rawQuery(sql,null);
          c.moveToFirst();
           int i=0,b=0;
                  s[0]=new Student();
                if (!c.isAfterLast()&&b==0) {

                    if(c.isLast())
                        b=15;

                    if (c.getString(c.getColumnIndex("regno")) != null) {

                        s[i].setRegno(c.getString(c.getColumnIndex("regno")));
                    }

                    if (c.getString(c.getColumnIndex("rollno")) != null) {

                        s[i].setRollno(c.getString(c.getColumnIndex("rollno")));
                    }

                    if (c.getString(c.getColumnIndex("name")) != null) {

                        s[i].setName(c.getString(c.getColumnIndex("name")));

                        if(c.getString(c.getColumnIndex("sec"))!=null) {

                            s[i].setSec(c.getString(c.getColumnIndex("sec")));
                        }
                        if(c.getString(c.getColumnIndex("course"))!=null) {

                            s[i].setCourse(c.getString(c.getColumnIndex("course")));
                        }
                        if(c.getString(c.getColumnIndex("dept"))!=null) {

                            s[i].setDept(c.getString(c.getColumnIndex("dept")));
                        }
                        if(c.getString(c.getColumnIndex("batch"))!=null) {

                            s[i].setBatch(c.getString(c.getColumnIndex("batch")));
                        }
                    }
                    c.moveToNext();
                    i++;
                    s[i] = new Student() ;
                }

          c.close();
          db.close();
          return s;

      }



    public Subject_sem[] outsubject_sem(String sql)
    {
        Subject_sem [] s=new Subject_sem[100];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        int i=0,b=0;
        s[0]=new Subject_sem();
        while (!c.isAfterLast() && b==0) {
            if(c.isLast())
                b=15;

            if (c.getString(c.getColumnIndex("subcode")) != null) {

                s[i].setSubcode(c.getString(c.getColumnIndex("subcode")));
            }

            if (c.getString(c.getColumnIndex("subname")) != null) {

                s[i].setSubname(c.getString(c.getColumnIndex("subname")));
            }

            if (c.getString(c.getColumnIndex("regulation")) != null) {

                s[i].setRegulation(c.getString(c.getColumnIndex("regulation")));
            }

                if(c.getString(c.getColumnIndex("sem"))!=null) {

                    s[i].setSem(c.getString(c.getColumnIndex("sem")));
                }
                if(c.getString(c.getColumnIndex("subtype"))!=null) {

                    s[i].setSubtype(c.getString(c.getColumnIndex("subtype")));
                }


                c.moveToNext();
                i++;
                s[i] = new Subject_sem();



        }
        c.close();
        //s[i]=new Subject_sem();

        db.close();
        return s;

    }

    public void delnotes(String sql)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql);

        db.close();
    }

    public void delmarks(String sem)
    {
        SQLiteDatabase db=getWritableDatabase();

        db.delete("marks_table","sem = '"+sem+"'" ,null);
        db.close();
    }

    public Marks[] outmarks(String sql)
    {
        Marks [] s=new Marks[100];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        int i=0,b=0;
        s[0]=new Marks();
        while (!c.isAfterLast()&&b==0) {
            if(c.isLast())
                b=15;
            if (c.getString(c.getColumnIndex("subcode")) != null) {

                s[i].setSubcode(c.getString(c.getColumnIndex("subcode")));
            }

            if (c.getString(c.getColumnIndex("rollno")) != null) {

                s[i].setRollno(c.getString(c.getColumnIndex("rollno")));
            }

            if (c.getString(c.getColumnIndex("model1")) != null) {

                s[i].setModel1(c.getString(c.getColumnIndex("model1")));

                if(c.getString(c.getColumnIndex("model2"))!=null) {

                    s[i].setModel2(c.getString(c.getColumnIndex("model2")));
                }
                if(c.getString(c.getColumnIndex("model3"))!=null) {

                    s[i].setModel3(c.getString(c.getColumnIndex("model3")));
                }
                if(c.getString(c.getColumnIndex("cycle1"))!=null) {

                    s[i].setCycle1(c.getString(c.getColumnIndex("cycle1")));
                }
                if(c.getString(c.getColumnIndex("cycle2"))!=null) {

                    s[i].setCycle2(c.getString(c.getColumnIndex("cycle2")));
                }

                if(c.getString(c.getColumnIndex("cycle3"))!=null) {

                    s[i].setCycle3(c.getString(c.getColumnIndex("cycle3")));
                }
                if(c.getString(c.getColumnIndex("sem"))!=null) {

                    s[i].setSem(c.getString(c.getColumnIndex("sem")));
                }
                if(c.getString(c.getColumnIndex("unit1"))!=null) {

                    s[i].setCycle1(c.getString(c.getColumnIndex("unit1")));
                }
                if(c.getString(c.getColumnIndex("unit2"))!=null) {

                    s[i].setCycle2(c.getString(c.getColumnIndex("unit2")));
                }

                if(c.getString(c.getColumnIndex("unit3"))!=null) {

                    s[i].setCycle3(c.getString(c.getColumnIndex("unit3")));
                }
            }
            c.moveToNext();
            i++;
            s[i] = new Marks();
        }
        c.close();

        db.close();
        return s;

    }
    public Notes[] outnotes(String sql)
    {
        Notes[] s=new Notes[100];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        int i=0,b=0;
        s[0]=new Notes();
        while (!c.isAfterLast()&&b==0) {

            if(c.isLast())
                b=15;
            if (c.getString(c.getColumnIndex("subcode")) != null) {

                s[i].setSubcode(c.getString(c.getColumnIndex("subcode")));
            }

            if (c.getString(c.getColumnIndex("path")) != null) {

                s[i].setPath(c.getString(c.getColumnIndex("path")));
            }

            if (c.getString(c.getColumnIndex("sem")) != null) {

                s[i].setSem(c.getString(c.getColumnIndex("sem")));

                if(c.getString(c.getColumnIndex("acadamic_yr"))!=null) {

                    s[i].setAcadamic_yr(c.getString(c.getColumnIndex("acadamic_yr")));
                }
                if(c.getString(c.getColumnIndex("notes_type"))!=null) {

                    s[i].setNotes_type(c.getString(c.getColumnIndex("notes_type")));
                }


                if(c.getString(c.getColumnIndex("filename"))!=null) {

                    s[i].setFilename(c.getString(c.getColumnIndex("filename")));
                }
            }
            c.moveToNext();
            i++;
            s[i] = new Notes();
        }
        c.close();

        db.close();
        return s;

    }





    public Download[] outdownload(String sql)
    {
        Download[] s=new Download[100];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c =db.rawQuery(sql,null);
        c.moveToFirst();
        int i=0,b=0;
        s[0]=new Download();



        while (!c.isAfterLast()&&b==0) {




            if(c.isLast())
                b=15;
            if (c.getString(c.getColumnIndex("type")) != null) {

                s[i].setType(c.getString(c.getColumnIndex("type")));
            }

            if (c.getString(c.getColumnIndex("path")) != null) {

                s[i].setPath(c.getString(c.getColumnIndex("path")));
            }

            if (c.getString(c.getColumnIndex("name")) != null) {

                s[i].setName(c.getString(c.getColumnIndex("name")));
            }

            c.moveToNext();
            i++;
            s[i] = new Download();

        }
        c.close();

        db.close();
        return s;

    }



    public String[] outacadamic_yr(String sql) {
        String[] s = new String[100];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        int i = 0, b = 0;
        s[0] = new String();
        while (!c.isAfterLast() && b == 0) {

            s[i] = new String();
            if (c.isLast())
                b = 15;
            if (c.getString(c.getColumnIndex("year")) != null) {

                s[i] = (c.getString(c.getColumnIndex("year")));
            }

                c.moveToNext();
        i++;



        }
        c.close();
        db.close();
        return s;

    }


    public String[] outnotetype(String sql) {
        String[] s = new String[100];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        int i = 0, b = 0;
        s[0] = new String();
        while (!c.isAfterLast() && b == 0) {
            s[i] = new String();
            if (c.isLast())
                b = 15;
            if (c.getString(c.getColumnIndex("type")) != null) {

                s[i] = (c.getString(c.getColumnIndex("type")));
            }

            c.moveToNext();
            i++;



        }
        c.close();
        db.close();
        return s;

    }


    public byte[] outdpicture() {
        byte[] s = new byte[16 *1024];
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from dpicture;", null);
        //c.moveToFirst();
        s=null;

        if(c.moveToFirst()) {
            int i = 0, b = 0;
            if (c.getBlob(c.getColumnIndex("dp")) != null) {

                s = (c.getBlob(c.getColumnIndex("dp")));
            }
        }




        c.close();
        db.close();
        return s;

    }







/*    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("productname")) != null) {
                dbString += c.getString(c.getColumnIndex("productname"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}


*/














}
