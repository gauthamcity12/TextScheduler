package com.gauthamcity12.textscheduler;

/**
 * Created by gauthamcity12 on 7/14/15.
 */
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class WakeLocker extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, TextScheduleService.class);
        for(int i = 0; i < 6; i++){
            service.putExtra("Text Info: "+i, intent.getStringExtra("Text Info: "+i)); // repopulating the extras into the new intent
        }
        startWakefulService(context, service); // Holds a wake lock until the service is complete
        }
}
