package com.gauthamcity12.textscheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by gauthamcity12 on 6/16/15.
 */
public class TextInfoStore extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "TextSchedulerDB.db";
    private static final String TABLE_NAME = "TextInfo";
    private static final String KEY_ID = "MessageID";
    private static final String KEY_DATE = "Date";
    private static final String KEY_TIME = "Time";
    private static final String KEY_CONTENT = "Content";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL, " +
            KEY_DATE + " DATE NOT NULL, " + KEY_TIME + " TIME NOT NULL, " + KEY_CONTENT + " VARCHAR(120) NOT NULL);";

    public TextInfoStore(Context context){
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
        db.execSQL(CREATE_TABLE);
        onCreate(db);
    }
}

