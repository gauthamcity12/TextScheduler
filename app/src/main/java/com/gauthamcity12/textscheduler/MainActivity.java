package com.gauthamcity12.textscheduler;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;


public class MainActivity extends Activity {
    TextInfoStore textDB;
    SQLiteDatabase db;
    Object[] textInfo = new Object[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDB = new TextInfoStore(getBaseContext()); // initializing the db helper
        db = textDB.getWritableDatabase(); // getting the database in a writeable state

        TimePicker tpicker = (TimePicker)(findViewById(R.id.timepicker));
        tpicker.setIs24HourView(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveTime(View view){
        TimePicker tpicker = (TimePicker)findViewById(R.id.timepicker);
        int hour = tpicker.getCurrentHour();
        int minute = tpicker.getCurrentMinute();
        textInfo[3] = hour + ":" + minute;
    }
}
