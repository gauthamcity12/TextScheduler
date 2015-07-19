package com.gauthamcity12.textscheduler;

/**
 * Created by gauthamcity12 on 7/19/15.
 */
public class TextData {
    private String contactName;
    private String date;
    private String time;
    private String content;
    private boolean status = false;

    public TextData(String contact, String date, String time, String content){
        this.contactName = contact;
        this.date = date;
        this.time = time;
        this.content = content;
    }

    public String getContactName(){
        return contactName;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getContent(){
        return content;
    }

    public boolean getStatus(){
        return status;
    }

    public void sent(){
        status = true;
    }
}

