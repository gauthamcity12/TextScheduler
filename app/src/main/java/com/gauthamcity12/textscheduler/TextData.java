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
    private int sessionID;

    public TextData(String contact, String date, String time, String content, int sessionID){
        this.contactName = contact;
        this.date = date;
        this.time = time;
        this.content = content;
        this.sessionID = sessionID;
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

    public int getSessionID(){
        return sessionID;
    }

    public void sent(){
        status = true;
    }

    @Override
    public int compareTo(Object another) {
        TextData other = ((TextData)another);
        String date1 = this.getDate();
        String date2 = other.getDate();
        int val1 = Integer.parseInt(date1.substring(0,1));
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
                    if(date1.contains("-")){
                        int firstIndex = date1.indexOf("-");
                        int secondIndex = date1.lastIndexOf("-");
                        val1 = Integer.parseInt(date1.substring(firstIndex + 1, secondIndex));

                        firstIndex = date2.indexOf("-");
                        secondIndex = date2.lastIndexOf("-");
                        val2 = Integer.parseInt(date2.substring(firstIndex+1, secondIndex));

                        if(val1 < val2){
                            return -1;
                        }
                        else if(val2 < val1){
                            return 1;
                        }
                        else{
                            firstIndex = this.getTime().indexOf(":");
                            secondIndex = other.getTime().indexOf(":");
                            val1 = Integer.parseInt(this.getTime().substring(0, firstIndex));
                            val2 = Integer.parseInt(other.getTime().substring(0,secondIndex));
                            if(val1 < val2){
                                return -1;
                            }
                            else{
                                return 1;
                            }
                        }
                    }
                    return 0;
                }
            }
            return 1; // if the text has already been sent, send it to the back
        }
        else if(this.getStatus() == false){
            if(other.getStatus() == false){
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
                else {
                    if (date1.contains("-")) {
                        int firstIndex = date1.indexOf("-");
                        int secondIndex = date1.lastIndexOf("-");
                        val1 = Integer.parseInt(date1.substring(firstIndex + 1, secondIndex));

                        firstIndex = date2.indexOf("-");
                        secondIndex = date2.lastIndexOf("-");
                        val2 = Integer.parseInt(date2.substring(firstIndex + 1, secondIndex));

                        if(val1 < val2){
                            return -1;
                        }
                        else if(val2 < val1){
                            return 1;
                        }
                        else{
                            firstIndex = this.getTime().indexOf(":");
                            secondIndex = other.getTime().indexOf(":");
                            val1 = Integer.parseInt(this.getTime().substring(0, firstIndex));
                            val2 = Integer.parseInt(other.getTime().substring(0,secondIndex));
                            if(val1 < val2){
                                return -1;
                            }
                            else{
                                return 1;
                            }
                        }
                    }
                    return 0;
                }
            }
            return -1; // if text hasn't been sent yet, then send it to the front
        }
        return 1;
    }
}

