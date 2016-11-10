package com.sjitportal.home.portal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Alagesh on 11-07-2016.
 */
public class date_adapter extends ArrayAdapter{
    List<String> data;
    Context context;
    String months[] = { "Sunday",
            "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday"};
    public date_adapter(Context context, List<String> date) {
        super(context, R.layout.attendance_date, R.id.day,date);
        this.context=context;
        this.data=date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.attendance_date,parent,false);
        TextView date,day,year;
        date= (TextView) row.findViewById(R.id.date);
        year= (TextView) row.findViewById(R.id.cal_year);
        day= (TextView) row.findViewById(R.id.day);

        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dates=df.parse(data.get(position));
            String s1=dates.toString();
            String[] s2=s1.split(" ");
            GregorianCalendar gc=new GregorianCalendar();
            gc.setTime(dates);
            date.setText(String.valueOf(gc.get(Calendar.DATE)));
            day.setText(String.valueOf(months[gc.get(Calendar.DAY_OF_WEEK)]));
            String set_year=String.valueOf(s2[1])+','+String.valueOf(gc.get(Calendar.YEAR));
            year.setText(set_year);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return row;
    }
}
