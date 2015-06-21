package com.gauthamcity12.textscheduler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;


public class MainActivity extends Activity {
    TextInfoStore textDB;
    SQLiteDatabase db;
    Object[] textInfo = new Object[5];
    DatePickerDialog dpDialog;
    TimePickerDialog tpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDB = new TextInfoStore(getBaseContext()); // initializing the db helper
        db = textDB.getWritableDatabase(); // getting the database in a writeable state

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
        Calendar newCal = Calendar.getInstance();
        tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EditText timeSet = (EditText)findViewById(R.id.timeText);
                int hour = hourOfDay;
                textInfo[3] = hourOfDay+":"+minute; // sets the time portion of the text
                if(hour > 12){
                    hour -= 12;
                }
                timeSet.setText(hour+":"+minute);
                timeSet.setVisibility(View.VISIBLE);
                timeSet.setFocusable(false);
            }
        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), false);
        tpDialog.show();
    }

    public void saveDate(View view){
        Calendar newCal = Calendar.getInstance();
        dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                EditText dateSet = (EditText)findViewById(R.id.dateText);
                String dateInfo = monthOfYear+" "+dayOfMonth+"'"+year;
                textInfo[2] = dateInfo;
                dateSet.setText(monthOfYear+"-"+dayOfMonth+"-"+year);
                dateSet.setVisibility(View.VISIBLE);
                dateSet.setFocusable(false);
            }
        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));
        dpDialog.show();
    }

}
