package com.gauthamcity12.textscheduler;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by gauthamcity12 on 7/12/15.
 */
public class TextSenderService extends Service {

    private NotificationManager nManager;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        super.onStartCommand(intent, flags, startID);

        nManager = (NotificationManager)this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        return 0;

    }



    public void onDestroy(){
        super.onDestroy();
    }

}
