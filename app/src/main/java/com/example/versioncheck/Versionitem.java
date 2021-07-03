package com.example.versioncheck;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by taewoo on 2019-11-16.
 */

public class Versionitem {




    @SerializedName("versioncode")
    @Expose
    int versioncode;

    @SerializedName("versionname")
    @Expose
    String versionname;
    @SerializedName("dialogtext")
    @Expose
    String dialogtext;
    @SerializedName("url")
    @Expose
    String url;




    public Versionitem(int versioncode, String versionname, String dialogtext,
                       String url) {
        this.versioncode = versioncode;
        this.versionname = versionname;
        this.dialogtext = dialogtext;
        this.url = url;


    }

    public int getVersioncode() {
        return versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public String getDialogtext() {
        return dialogtext;
    }

    public String getUrl() {
        return url;
    }
    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public void setDialogtext(String dialogtext) {
        this.dialogtext = dialogtext;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
