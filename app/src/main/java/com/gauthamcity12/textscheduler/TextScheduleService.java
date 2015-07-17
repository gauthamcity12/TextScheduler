package com.gauthamcity12.textscheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by gauthamcity12 on 7/12/15.
 */
public class TextScheduleService extends IntentService {

    /*public void onReceive(Context context, Intent intent){
        String[] textInfo = new String[5];
        for(int i = 0; i < 5; i++){
            textInfo[i] = intent.getStringExtra("Text Info: "+i);
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(textInfo[1], null, textInfo[4], null, null);
        //Toast.makeText(MainActivity.class, "Sending text now...", Toast.LENGTH_SHORT).show();
    }
    */

    public TextScheduleService(){
        super("TextScheduleServiceStarted");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String[] textInfo = new String[7];
        for(int i = 0; i < 7; i++){
            textInfo[i] = intent.getStringExtra("Text Info: "+i);
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(textInfo[1], null, textInfo[4], null, null); // sends the actual text
        textInfo[6] = "true";

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_check);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification finished = new Notification.Builder(this).setContentTitle("Scheduled Text sent to " + textInfo[5]).setSmallIcon(R.drawable.ic_launcher).build();
        Random rand = new Random(10000);
        manager.notify(rand.nextInt(), finished);



        WakeLocker.completeWakefulIntent(intent); // Closes the WakeLock after the service is performed
    }
}
