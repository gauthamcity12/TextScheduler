package com.gauthamcity12.TextYaLater;

/**
 * Created by gauthamcity12 on 08/03/15.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver{

    @Override // TODO: NEED TO ADD DB RETRIEVAL AND RESETTING ALARM
    public void onReceive(Context context, Intent intent) {

        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent resetService = new Intent(context, RescheduleService.class);
//            TextApp myApp = new TextApp();
//            myApp.setContext(context);

            context.startService(resetService);
        }
    }
}
