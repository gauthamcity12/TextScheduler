package com.gauthamcity12.textscheduler;

/**
 * Created by gauthamcity12 on 7/19/15.
 */
public class TextData implements Comparable{
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

    @Override
    public int compareTo(Object another) {
        TextData other = ((TextData)another);
        String date1 = this.getDate();
        String date2 = other.getDate();
        int val1 = Integer.parseInt(date1.substring(0, 1));
        int val2 = Integer.parseInt(date2.substring(0,1));

        if(this.getStatus() == true){
            if(other.getStatus() == true){

                if(Character.isDigit(date1.charAt(1))){
                    val1 = Integer.parseInt(date1.substring(0,2));
                }
                if(Character.isDigit(date2.charAt(1))){
                    val2 = Integer.parseInt(date2.substring(0,2));
                }
                if(val1 < val2){
                    return -1;
                }
                else if(val1 > val2){
                    return 1;
                }
                else{
                    return 0;
                }
            }
            return 1; // if the text has already been sent, send it to the back
        }
        else if(this.getStatus() == false){
            if(other.getStatus() == false){
                return 0;
            }
            return -1; // if text hasn't been sent yet, then send it to the front
        }
        return 1;
    }
}

