package com.gauthamcity12.textscheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.textservice.TextInfo;
import android.widget.Toast;

/**
 * Created by gauthamcity12 on 6/16/15.
 */
public class TextInfoStore extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TextSchedulerDB.db";
    public static final String TABLE_NAME = "TextInfo";
    public static final String KEY_ID = "MessageID";
    public static final String KEY_PHONE = "PhoneNumber";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TIME = "Time";
    public static final String KEY_CONTENT = "Content";
    public static final String KEY_CONTACT = "Contact";
    public static final String KEY_SENTSTATUS = "SentStatus";
    public static final String KEY_TIMESTAMP = "TimeStamp";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, " + KEY_PHONE + " VARCHAR(11) NOT NULL, " +
            KEY_DATE + " VARCHAR(20) NOT NULL, " + KEY_TIME + " VARCHAR(10) NOT NULL, " + KEY_CONTENT + " VARCHAR(120) NOT NULL, " + KEY_CONTACT + " VARCHAR(30) NOT NULL, "+
            KEY_SENTSTATUS + " VARCHAR(5) NOT NULL, " + KEY_TIMESTAMP + " VARCHAR(30) NOT NULL);";

    private static TextInfoStore helperInstance;

    public static synchronized TextInfoStore getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (helperInstance == null) {
            helperInstance = new TextInfoStore(context.getApplicationContext());
        }
        return helperInstance;
    }

    private TextInfoStore(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        Log.i("TextScheduler", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // no version changes for now
        db.execSQL("DROP TABLE IF EXISTS TextSchedulerDB");
        db.execSQL(CREATE_TABLE);
        onCreate(db);
    }
}

