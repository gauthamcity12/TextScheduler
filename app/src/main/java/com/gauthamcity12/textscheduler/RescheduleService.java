package com.gauthamcity12.textscheduler;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by gauthamcity12 on 8/5/15.
 */
public class RescheduleService extends Service {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String query = "SELECT * FROM " + TextInfoStore.TABLE_NAME + " WHERE " + TextInfoStore.KEY_SENTSTATUS + " = 'false'";
        TextApp myApp = new TextApp();
        TextInfoStore textDB = TextInfoStore.getInstance(); // initializing the db helper
        SQLiteDatabase db = textDB.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        Object[] textInfo = new Object[8];

        Intent textIntent = new Intent(this, WakeLocker.class);

        if(cursor.moveToFirst()){ // checks the first row
            textInfo = setup(textInfo, cursor, textIntent);
            Log.d((String) textInfo[1], "textInfo");
            Log.d("TextApp", textInfo[0] + " " + textInfo[1] + " " + textInfo[7]);

            if(Long.parseLong((String)textInfo[7]) < Calendar.getInstance().getTimeInMillis()){ //if the scheduled time has already passed, send the text.
                sendText(textInfo);
                Log.d("Reboot received bruh", "sent text");
            }
            else{
                populateIntent(textIntent, textInfo);
                PendingIntent pendingText = PendingIntent.getBroadcast(this, (int)textInfo[0], textIntent, 0); // creates a new intent with unique request codes
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(alarmManager.RTC_WAKEUP, Long.parseLong((String)textInfo[7]), pendingText);
                Log.d("Reboot received bruh", "set alarm");

            }
        }
        while (cursor.moveToNext()){ // checks the rest of the rows
            textIntent = new Intent(this, WakeLocker.class);
            textInfo = setup(textInfo, cursor, textIntent);

            if(Long.parseLong((String)textInfo[7]) < Calendar.getInstance().getTimeInMillis()){ // if the time has already passed, send the text.
                sendText(textInfo);
            }
            else{
                populateIntent(textIntent, textInfo);
                PendingIntent pendingText = PendingIntent.getBroadcast(this, (int)textInfo[0], textIntent, 0); // creates a new intent with unique request codes
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(alarmManager.RTC_WAKEUP, Long.parseLong((String)textInfo[7]), pendingText);
            }
        }

        db.close();
        stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Object[] setup(Object[] textInfo, Cursor cursor, Intent textIntent){
        textInfo = new Object[8];
        textInfo[0] = cursor.getInt(0);
        textInfo[1] = cursor.getString(1);
        textInfo[2] = cursor.getString(2);
        textInfo[3] = cursor.getString(3);
        textInfo[4] = cursor.getString(4);
        textInfo[5] = cursor.getString(5);
        textInfo[6] = cursor.getString(6);
        textInfo[7] = cursor.getString(7);

        int counter = 0;
        for(Object s : textInfo){ // append all textInformation to Intent
            if(counter == 0){
                textIntent.putExtra("Text Info: "+counter, String.valueOf((int)s));
            }
            else if(counter == 7){ //appends the timestamp value to the textInfo array
                textIntent.putExtra("Text Info: "+counter, (String)s);
            }
            else{
                textIntent.putExtra("Text Info: "+counter, (String)s);
            }
            counter++;
        }
        return textInfo;
    }

    public void sendText(Object[] textInfo){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage((String) textInfo[1], null, (String) textInfo[4], null, null); // sends the actual text
        textInfo[6] = "true";
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification finished = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Since device was off: scheduled text was just sent to " + textInfo[5]).setSmallIcon(R.drawable.ic_done_white_24dp).build();

        Intent notificationIntent = new Intent(this, TextHistoryActivity.class);
        PendingIntent pNot = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        finished.contentIntent = pNot;

        Random rand = new Random();
        manager.notify(rand.nextInt(), finished);

        // Update database with sent status
        SQLiteDatabase db = TextInfoStore.getInstance().getWritableDatabase();
        ContentValues newValue = new ContentValues();
        newValue.put(TextInfoStore.KEY_SENTSTATUS, (String) textInfo[6]);

        settings = this.getSharedPreferences("HASHMAPVALS", 0); // gets the shared preferences file
        editor = settings.edit();

        int key = (int)textInfo[0];
        long rowId = getRowID(key);

        db.update(TextInfoStore.TABLE_NAME, newValue, TextInfoStore.KEY_ID + "=" + rowId, null); // updated database since text was sent

        //remove mapping from hash now that text is sent
        deleteMapping(key);
        db.close();
    }

    public long getRowID(int key){
        long val = settings.getLong(key + "long", 0);
        return val;
    }

    // text has been sent, no need to store value in preferences file
    public void deleteMapping(int key){
        editor.remove(key+"int");
        editor.remove(key+"long");
        editor.commit();
    }

    public void populateIntent(Intent textIntent, Object[] textInfo){
        int counter = 0;
        for(Object s : textInfo){ // append all textInformation to Intent
            if(counter == 0){
                textIntent.putExtra("Text Info: "+counter, String.valueOf((int)s));
            }
            else if(counter == 7){ //appends the timestamp value to the textInfo array
                textIntent.putExtra("Text Info: "+counter, (String)s);
            }
            else{
                textIntent.putExtra("Text Info: "+counter, (String)s);
            }

            counter++;
        }
    }
}
