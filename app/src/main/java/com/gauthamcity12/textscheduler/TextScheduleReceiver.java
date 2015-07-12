package com.gauthamcity12.textscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by gauthamcity12 on 7/12/15.
 */
public class TextScheduleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        String[] textInfo = new String[5];
        for(int i = 0; i < 5; i++){
            textInfo[i] = intent.getStringExtra("Text Info: "+i);
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(textInfo[1], null, textInfo[4], null, null);
        //Toast.makeText(MainActivity.class, "Sending text now...", Toast.LENGTH_SHORT).show();
    }

}
