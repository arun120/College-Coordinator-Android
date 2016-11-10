package com.sjitportal.home.portal;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;

public class Home_Page extends ActionBarActivity{

    TextView hel;
    String usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
        usr= getIntent().getExtras().getString("rollno");

        hel=(TextView) findViewById(R.id.hi);
        hel.setText("Welcome "+usr+"!");







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
