package com.gauthamcity12.textscheduler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;


public class MainActivity extends Activity {
    TextInfoStore textDB;
    SQLiteDatabase db;
    Object[] textInfo = new Object[5];
    DatePickerDialog dpDialog;
    TimePickerDialog tpDialog;
    int PICK_CONTACT = 1;

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
                int yearSet = year;
                int monthSet = monthOfYear;
                int daySet = dayOfMonth;



                if(dayOfMonth < Calendar.getInstance().get(Calendar.DAY_OF_MONTH) || monthOfYear < Calendar.getInstance().get(Calendar.MONTH) || year < Calendar.getInstance().get(Calendar.YEAR)){
                    yearSet = Calendar.getInstance().get(Calendar.YEAR);
                    monthSet = Calendar.getInstance().get(Calendar.MONTH);
                    daySet = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    Toast.makeText(getBaseContext(), "Not a valid date, please set again", Toast.LENGTH_SHORT).show();
                }
                String dateInfo = monthSet+" "+daySet+"'"+yearSet;

                textInfo[2] = dateInfo;
                dateSet.setText(monthSet+"-"+daySet+"-"+yearSet);
                dateSet.setVisibility(View.VISIBLE);
                dateSet.setFocusable(false);
            }
        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));
        dpDialog.show();
    }

    public void saveContact(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        EditText cText = (EditText)findViewById(R.id.contactText);
        String name = "";
        String contactID = "";

        if(requestCode == PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Uri contactUri = data.getData();
                Cursor c = getContentResolver().query(contactUri, null, null, null, null);
                if(c.moveToFirst())
                {
                    contactID = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                }

                cText.setText(name); /// sets the contact name on the edit text box
                cText.setVisibility(View.VISIBLE);
                cText.setFocusable(false);

                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);
                while (phones.moveToNext()) {
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    switch (type) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            textInfo[1] = number;
                            Toast.makeText(getBaseContext(), number, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                phones.close();
                c.close();
            }
        }
    }
}
