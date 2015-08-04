package com.gauthamcity12.textscheduler;

/**
 * Created by gauthamcity12 on 08/03/15.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class BootReceiver extends WakefulBroadcastReceiver{

    @Override // TODO: NEED TO ADD DB PARSING AND RESETTING ALARM
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, TextScheduleService.class);
        for(int i = 0; i < 8; i++){
            service.putExtra("Text Info: "+i, intent.getStringExtra("Text Info: "+i)); // repopulating the extras into the new intent
        }
        startWakefulService(context, service); // Holds a wake lock until the service is complete
    }
}
