package com.gauthamcity12.textscheduler;

import android.app.Application;
import android.content.Context;

/**
 * Created by gauthamcity12 on 8/7/15.
 */
public class TextApp extends Application {
    private static Context context = null;

    public void onCreate(){
        super.onCreate();
        TextApp.context = getApplicationContext();
    }

    public void setContext(Context c){
        TextApp.context = c;
    }

    public static Context getAppContext(){
        return TextApp.context;
    }
}
