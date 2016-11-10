package com.sjitportal.home.portal;

/**
 * Created by Lenovo on 9/2/2015.
 */
public class Find {

    static String dept(String rollno)
    {

        String dept=null,dep1=null,cut=null;
        char[] dep=new char[3];
        cut=rollno.substring(2,5);

        if(cut.charAt(0)=='L'||cut.charAt(0)=='l') {
            dep[0]=cut.charAt(1);
            dep[1]=cut.charAt(2);

        }
        else
        {

            dep[0]=cut.charAt(0);
            dep[1]=cut.charAt(1);

        }
        dep1=String.valueOf(dep[0])+String.valueOf(dep[1]);
        dep1=dep1.toLowerCase();
        System.out.println(dep);

        System.out.println(dep1);
        System.out.println(cut);
        if(dep1.equals("cs"))
            dept="cse";
        else if (dep1.equals("ec"))
            dept="ece";
        else if(dep1.equals("ee"))
            dept="eee";
        else if(dep1.equals("me"))
            dept="mech";
        else if(dep1.equals("cv")||dep1.equals("ce"))
            dept="civil";
        else if(dep1.equals("it"))
            dept="it";


        return dept;

    }

static String regulation(String rollno)
{

    String regulation=null;
    if(rollno.charAt(1)=='2')
        regulation="2008";
        else
        regulation="2013";
    return regulation;
}





}
