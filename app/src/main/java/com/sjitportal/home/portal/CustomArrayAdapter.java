package com.sjitportal.home.portal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abishek on 11/16/2015.
 */
public class CustomArrayAdapter extends ArrayAdapter {

    public static int[] Images={R.drawable.unk,R.drawable.pdf,R.drawable.doc,R.drawable.jpg,R.drawable.txt,R.drawable.xls,R.drawable.rar,R.drawable.zip,R.drawable.png};
    Context context;
    ArrayList<Integer> images;
    Activity activity;
    ArrayList<String> main_text;

    ListView listView;


    public CustomArrayAdapter(Context context, ArrayList<String> main_txt,ArrayList<Integer> img,ListView listView){//,String [] sub_txt) {
        super(context, R.layout.list_view_resources,R.id.list_text,main_txt);
        this.context=context;


        this.listView=listView;
        this.main_text=main_txt;

        this.images=img;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.list_view_resources,parent,false);
        ImageView imageView= (ImageView) row.findViewById(R.id.image_list_res);
        final TextView textView1= (TextView) row.findViewById(R.id.list_text);

        imageView.setImageResource(Images[images.get(position)]);
        textView1.setText(main_text.get(position));

        return row;
    }
}