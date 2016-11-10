package com.sjitportal.home.portal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int i,j=0;
        String[] exec={".pdf",".doc",".docx",".jpg",".rar",".txt",".zip"};
        TextView txt= (TextView) findViewById(R.id.myid);
        String [] NameList={"Incident1.pdf","Incident2.jpg","Incident3.doc","Incident4.docx","Incident5.pdf","Incident6.doc","Incident7.img","Incident8.img"};
        for(i=0;i<5;i++)
        {
        if(NameList[i].endsWith(".pdf"))
        {
            j=1;
            txt.setText("poda");
        }
        if(NameList[i].endsWith(".pdf"))
        {
            j=1;
            txt.setText("poda");
        }
        if(NameList[i].endsWith(".pdf"))
        {
            j=1;
            txt.setText("poda");
        }
        if(NameList[i].endsWith(".pdf"))
        {
            j=1;
            txt.setText("poda");
        }
        if(NameList[i].endsWith(".pdf"))
        {
            j=1;
            txt.setText("poda");
        }
        if(NameList[0].endsWith(".pdf"))
        {
            j=1;
            txt.setText("poda");
        }

        }

    }
}
