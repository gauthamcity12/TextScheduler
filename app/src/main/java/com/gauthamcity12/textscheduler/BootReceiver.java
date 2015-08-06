package com.gauthamcity12.textscheduler;

/**
 * Created by gauthamcity12 on 08/03/15.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends WakefulBroadcastReceiver{

    @Override // TODO: NEED TO ADD DB RETRIEVAL AND RESETTING ALARM
    public void onReceive(Context context, Intent intent) {

        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent resetService = new Intent(context, RescheduleService.class);
            startWakefulService(context, resetService);
        }
//        Intent service = new Intent(context, TextScheduleService.class);
//        for(int i = 0; i < 8; i++){
//            service.putExtra("Text Info: "+i, intent.getStringExtra("Text Info: "+i)); // repopulating the extras into the new intent
//        }
//        startWakefulService(context, service); // Holds a wake lock until the service is complete
    }
}
