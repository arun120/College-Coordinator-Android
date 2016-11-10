package com.sjitportal.home.portal;

/**
 * Created by Home on 18-08-2015.
 */
public class Notes {


     private String  acadamic_yr;
    private String  sem;
    private String  subcode;
    private String notes_type;
    private String  filename;
    private String  path;
    private String descp;

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getAcadamic_yr() {
        return acadamic_yr;
    }

    public String getSem() {
        return sem;
    }

    public String getSubcode() {
        return subcode;
    }

    public String getNotes_type() {
        return notes_type;
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public void setAcadamic_yr(String acadamic_yr) {
        this.acadamic_yr = acadamic_yr;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public void setNotes_type(String notes_type) {
        this.notes_type = notes_type;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
