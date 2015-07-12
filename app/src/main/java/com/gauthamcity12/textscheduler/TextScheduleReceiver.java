package com.gauthamcity12.textscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by gauthamcity12 on 7/12/15.
 */
public class TextScheduleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Intent sendText = new Intent(context, textSenderService.class);
        context.startService(sendText);
    }

}
