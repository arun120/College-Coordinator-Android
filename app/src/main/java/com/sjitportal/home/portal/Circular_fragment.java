package com.sjitportal.home.portal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class Circular_fragment extends  android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerViewAdapternew myadapter;
    RecyclerView myrecycler;
    Download[] c,d;

    public static int [] Images={R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload,R.drawable.drawerdownload};

    // TODO: Rename and change types of parameters
    // private String mParam1;
    //private String mParam2;


    ListView circularlist;

    String rn,u;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notes_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Circular_fragment newInstance(String param1, String param2) {
        Circular_fragment fragment = new Circular_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Circular_fragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            Download_view.searchclick=new Download_view.searchClick(){
                @Override
                public boolean search(String s) {
                    c=d.clone();
                    if (s.length() == 0) {
                        myrecycler.setAdapter(myadapter);
                    } else if (s.length() != 0) {
                        int i = 0;

                        int change=0;
                        Download[] copy = new Download[500];
                        List<Download> remlist;
                        remlist=new ArrayList<>();
                        int Images;
                        RecyclerViewAdapternew myadapter1;
                        ArrayList<String> list = new ArrayList<String>();
                        myadapter1=new RecyclerViewAdapternew(getActivity());
                        change=0;
                        while (i < myadapter.getItemCount() && i<c.length) {
                            //Ends Here
                        Images=getImage(c[i].getName());

                            list.add(c[i].getName());
                            if(c[i].getName().replace("»","").contains(s))
                            {
                                change=1;
                                remlist.add(c[i]);
                                myadapter1.add(myadapter1.getItemCount(),
                                        c[i].getName(), Images);
                            }

                            i++;

                            }

                        int j;
                        j=0;
                        for(Download dum:remlist){
                            // Toast.makeText(getContext(),dum.getName(),Toast.LENGTH_SHORT).show();
                            copy[j++]=dum;

                               // c=copy.clone();
                        }




                        if(change==1){
                            c=copy;
                        }

                        myrecycler.setAdapter(myadapter1);
                    }
                    return true;
                }
            };

    }


public int getImage(String name){

    if(name.endsWith(".pdf"))
        return 1;
    if (name.endsWith(".doc")||name.endsWith(".docx"))
        return 2;
    if(name.endsWith(".jpg") || name.endsWith(".png")||name.endsWith(".gif"))
        return 3;
    if(name.endsWith(".txt"))
        return 4;
    if(name.endsWith(".xls"))
        return 5;
    if (name.endsWith(".rar")||name.endsWith(".zip"))
        return 6;

    return 0;
}

    @Override
    public void onStart() {
        super.onStart();

        Localdb db=new Localdb(getActivity().getApplicationContext(),Find.dept(rn),null,1);
        c=db.outdownload("select * from downloads where type='circular'");
        ArrayList<String> notelist=new ArrayList<>();
        int Images=0;
        CustomArrayAdapter adapter;
        myadapter=new RecyclerViewAdapternew(getActivity());
        myrecycler.setAdapter(myadapter);
        myrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecycler.addOnItemTouchListener(new RecyclerViewAdapternew(getActivity().getApplicationContext(), myrecycler, new RecyclerViewAdapternew.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent load = new Intent(Intent.ACTION_VIEW);
                String path = new String(),Basepath=new String();
                Basepath = c[position].getName().toString();
                path=Basepath.substring(Basepath.lastIndexOf("»")).substring(1);
                //path = noteslist.getItemAtPosition(position).toString();
                //Toast.makeText(getActivity(),path,Toast.LENGTH_SHORT).show();

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Portal/"+path);
                String TYPE=MimeTypeMap.getSingleton().getMimeTypeFromExtension(Basepath.substring(Basepath.lastIndexOf(".")).substring(1));
                if(TYPE==null)
                TYPE="*/*";
               // Toast.makeText(getActivity(),TYPE,Toast.LENGTH_SHORT).show();
                load.setDataAndType(Uri.fromFile(file), TYPE);
                startActivity(load);

            }

            @Override
            public void onLongClick(View view, int position) {
                final int pos=position;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm");
                builder.setMessage("Delete this file?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            String path = new String(),Basepath=new String();
                            Basepath = c[pos].getName().toString();
                            path=Basepath.substring(Basepath.lastIndexOf("»")+1);
                            Localdb db = new Localdb(getActivity().getApplicationContext(), Find.dept(rn), null, 1);
                            //TODO uncomment after completing
                            db.delnotes("delete from downloads where name='"+Basepath+"';");
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Portal/"+path);
                            file.delete();
                            Toast.makeText(getActivity(), "Successfully Deleted!!", Toast.LENGTH_SHORT).show();
                            onStart();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        }));

        int i=0;
        if(c[i].getName()==null)
            notelist.add("Nothing Found");
        while(c[i].getName()!=null)
        {
            //Setting Card Resources
            Images=getImage(c[i].getName());
            //Ends Here
            notelist.add(c[i].getName());
          myadapter.add(myadapter.getItemCount(),
                    c[i].getName(), Images);
            i++;
        }

        d=c.clone();


        //new CustomArrayAdapter(getActivity(),notelist,Images,circularlist);

        myrecycler.setAdapter(myadapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            rn = getArguments().getString("rollno");
            u = getArguments().getString("Usr");
        }

        myrecycler=(RecyclerView) getActivity().findViewById(R.id.circularlist);
        //circularlist=(ListView) getActivity().findViewById(R.id.circularlist);
        //rn=getActivity().getIntent().getExtras().getString("rollno");
        // rn="12cs1203";







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circular_fragment, container, false);



    }


}
