package com.sjitportal.home.portal;


import java.sql.Date;


/**
 * Created by Lenovo on 9/9/2015.
 */
public class Attendance {

    Date date;
    String sem;
    String rollno;
    String reason;


    public String getDate() {
        return date.toString();

    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
