package com.gauthamcity12.TextYaLater;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends Activity {
    private static TextInfoStore textDB;
    private static SQLiteDatabase db;
    private Object[] textInfo = new Object[8];
    /* Information Stored at Each Index
    * 0) Session ID
    * 1) Phone #
    * 2) Date
    * 3) Time
    * 4) Message Content
    * 5) Message Recipient
    * 6) Text Sent (True or False)
    * 7) Timestamp of Scheduled Text (long)
     */
    private Button messageSet;
    private boolean isToday = false;
    private boolean isNow = false;
    private int yearSet;
    private int monthSet;
    private int daySet;
    private Calendar infoCal = Calendar.getInstance();
    private Random rand = new Random();
    private DatePickerDialog dpDialog;
    private TimePickerDialog tpDialog;
    private int PICK_CONTACT = 1;
    protected static String PREFS_NAME = "HASHMAPVALS";
    private SharedPreferences settings;
    private AdView mAdView;
    private AdRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextApp myApp = new TextApp();
        textDB = TextInfoStore.getInstance(); // initializing the db helper

        Button dateSet = (Button)findViewById(R.id.dateButton);
        Button timeSet = (Button)findViewById(R.id.timeButton);
        Button contactSet = (Button)findViewById(R.id.contactButton);
        messageSet = (Button)findViewById(R.id.messageButton);
        textInfo[6] = "false"; // presets the text status as not Sent

        // sets the onTouch coloring for each of the buttons on the screen
        dateSet.setOnTouchListener(new ButtonTouchListener());
        timeSet.setOnTouchListener(new ButtonTouchListener());
        contactSet.setOnTouchListener(new ButtonTouchListener());
        messageSet.setOnTouchListener(new ButtonTouchListener());

        //Create Preferences File
        settings = this.getSharedPreferences(PREFS_NAME, 0);

        mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators// My Galaxy Nexus test phone
                .build();
        mAdView.loadAd(request);
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
        if (id == R.id.viewScheduled) {
            Intent intent = new Intent(this, TextHistoryActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveTime(View view){
        if(mAdView.getVisibility() != View.VISIBLE) {
            mAdView.loadAd(request);
            mAdView.setVisibility(View.VISIBLE);
        }
        Calendar newCal = Calendar.getInstance();
        tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                isNow = false;
                EditText timeSet = (EditText)findViewById(R.id.timeText);
                Calendar currentCal = Calendar.getInstance();
                String phase = "";
                if(textInfo[2] == null){
                    Toast.makeText(getBaseContext(), "Please choose a date first", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isToday && (hourOfDay < currentCal.get(Calendar.HOUR_OF_DAY) || (hourOfDay == currentCal.get(Calendar.HOUR_OF_DAY) && minute < currentCal.get(Calendar.MINUTE)))){
                        hourOfDay = Calendar.getInstance().get(Calendar.HOUR);
                        minute = Calendar.getInstance().get(Calendar.MINUTE);
                        Toast.makeText(getBaseContext(), "Not a valid time, please set again.", Toast.LENGTH_SHORT).show();
                    }
                    int hour = hourOfDay;
                    String min = "";
                    if(minute < 10){
                        min = "0"+minute; // formats the time properly if minute int is less than 10
                    }
                    else{
                        min = ""+minute;
                    }
                    textInfo[3] = hourOfDay+":"+minute; // sets the time portion of the text
                    if(hour > 12){
                        phase = " PM";
                        hour -= 12;
                    }
                    else{
                        phase = " AM";
                        if(hour == 12){
                            phase = " PM";
                        }
                    }
                    if(hour == 0){
                        phase =  " AM";
                        hour = 12;
                    }
                    if(isToday && hour == currentCal.get(Calendar.HOUR_OF_DAY) && minute == currentCal.get(Calendar.MINUTE)){
                        isNow = true;
                    }
                    infoCal.set(yearSet, monthSet, daySet, hourOfDay, minute, 0); // sets global calendar for alarm

                    timeSet.setText(hour + ":" + min + phase);
                    textInfo[3] = hour + ":"+min+phase;
                    timeSet.setVisibility(View.VISIBLE);
                    timeSet.setFocusable(false);
                }

            }
        }, newCal.get(Calendar.HOUR_OF_DAY), newCal.get(Calendar.MINUTE), false);
        tpDialog.show();
    }

    public void saveDate(View view){
        if(mAdView.getVisibility() != View.VISIBLE) {
            mAdView.loadAd(request);
            mAdView.setVisibility(View.VISIBLE);
        }
        Calendar newCal = Calendar.getInstance();
        dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar currentCal = Calendar.getInstance();
                EditText dateSet = (EditText)findViewById(R.id.dateText);
                yearSet = year;
                monthSet = monthOfYear;
                daySet = dayOfMonth;
                if(yearSet == currentCal.get(Calendar.YEAR) && monthOfYear == currentCal.get(Calendar.MONTH) && daySet == currentCal.get(Calendar.DAY_OF_MONTH)){
                    isToday = true;
                }
                else{
                    isToday = false;
                }
                infoCal.set(yearSet, monthSet, daySet, currentCal.get(Calendar.HOUR_OF_DAY), currentCal.get(Calendar.MINUTE), 0);
                if(!isToday && infoCal.getTimeInMillis() < currentCal.getTimeInMillis()){ // check if its before today based on millisecondtime
                    yearSet = currentCal.get(Calendar.YEAR);
                    monthSet = currentCal.get(Calendar.MONTH);
                    daySet = currentCal.get(Calendar.DAY_OF_MONTH);
                    isToday = true;
                    Toast.makeText(getBaseContext(), "Not a valid date, please set again", Toast.LENGTH_SHORT).show();
                }
                String dateInfo = (monthSet+1)+"-"+daySet+"-"+yearSet;

                textInfo[2] = dateInfo;
                dateSet.setText((monthSet+1)+"-"+daySet+"-"+yearSet);
                dateSet.setVisibility(View.VISIBLE);
                dateSet.setFocusable(false);
            }
        }, newCal.get(Calendar.YEAR), newCal.get(Calendar.MONTH), newCal.get(Calendar.DAY_OF_MONTH));
        dpDialog.show();
    }

    public void saveMessage(View view){
        if(mAdView.getVisibility() != View.VISIBLE) {
            mAdView.loadAd(request);
            mAdView.setVisibility(View.VISIBLE);
        }
        messageSet.setFocusable(true); // pulls the focus off of the edit text
        if(textInfo[1] == null){ // Error check to verify a contact was chosen before message is sent
            Toast.makeText(getBaseContext(), "Please Choose a Contact to Text", Toast.LENGTH_SHORT).show();
        }
        else if(textInfo[2] == null || textInfo[3] == null){ // Erorr check to verify that date and time are set as well
            Toast.makeText(getBaseContext(), "Please Enter All the Necessary Information", Toast.LENGTH_SHORT).show();
        }
        else{ // if all the proper information is inputted by the user
            EditText messageText = (EditText)findViewById(R.id.messageText);
            textInfo[4] = messageText.getText().toString(); // save the message text
            SmsManager smsManager = SmsManager.getDefault();

            // Schedule the Text Message
            textInfo[0] = rand.nextInt();
            if(isNow){ // no need to schedule text, text is being sent now
                smsManager.sendTextMessage((String)textInfo[1], null, messageText.getText().toString(), null, null);
                Toast.makeText(getBaseContext(), "Sending text now...", Toast.LENGTH_SHORT).show();
                isNow = false; // resets the variable
                textInfo[6] = "true";
                textInfo[7] = 0;
            }
            else{ // Text message is being scheduled using Alarm Manager
                Intent textIntent = new Intent(this, WakeLocker.class); // Intent to go to the wakeful broadcast receiver
                int counter = 0;
                textInfo[7] = infoCal.getTimeInMillis();
                for(Object s : textInfo){ // append all textInformation to Intent
                    if(counter == 0){
                        textIntent.putExtra("Text Info: "+counter, String.valueOf((int)s));
                    }
                    else if(counter == 7){ //appends the timestamp value to the textInfo array
                        textIntent.putExtra("Text Info: "+counter, String.valueOf((long)s));
                    }
                    else{
                        textIntent.putExtra("Text Info: "+counter, (String)s);
                    }

                    counter++;
                }

                PendingIntent pendingText = PendingIntent.getBroadcast(this, (int)textInfo[0], textIntent, 0); // creates a new intent with unique request codes

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(alarmManager.RTC_WAKEUP, infoCal.getTimeInMillis(), pendingText); // sets the alarm at the specified date and time
                Toast.makeText(getBaseContext(), "Text has been scheduled", Toast.LENGTH_SHORT).show(); // Indicate Message has been schedule
            }

            ///// SAVING TO DATABASE
            ContentValues values = new ContentValues();
            values.put(TextInfoStore.KEY_ID, (int)textInfo[0]); // saves session ID as primary key
            values.put(TextInfoStore.KEY_PHONE, (String)textInfo[1]); // saves Phone Number
            values.put(TextInfoStore.KEY_DATE, (String)textInfo[2]); // saves Date
            values.put(TextInfoStore.KEY_TIME, (String)textInfo[3]); // saves Time
            values.put(TextInfoStore.KEY_CONTENT, (String)textInfo[4]); // saves Text Message Itself
            values.put(TextInfoStore.KEY_CONTACT, (String)textInfo[5]); // saves Contact
            values.put(TextInfoStore.KEY_SENTSTATUS, (String)textInfo[6]); // saves whether the text has been sent or not
            values.put(TextInfoStore.KEY_TIMESTAMP, String.valueOf(textInfo[7])); // saves the TimeStamp

            db = textDB.getWritableDatabase(); // getting the database in a writeable state
            long newRowId = db.insert(TextInfoStore.TABLE_NAME, null, values); // inserts into the database
            //rowMap.put((int)textInfo[0], newRowId); // maps the Text Schedule ID with the Row ID for updating the database later
            db.close();

            saveMap((int)textInfo[0], newRowId);
        }
    }

    public void saveMap(int val, long id){
        SharedPreferences.Editor editor = settings.edit();
        String identifier = textInfo[0]+"";
        editor.putInt(identifier+"int", val);
        editor.putLong(identifier + "long", id);
        boolean result = editor.commit();
    }

    public void saveContact(View view){
        if(mAdView.getVisibility() != View.VISIBLE) {
            mAdView.loadAd(request);
            mAdView.setVisibility(View.VISIBLE);
        }
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
                    textInfo[5] = name;
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
                            textInfo[1] = number; // saves the contact's phone number
                            break;
                    }
                }
                phones.close();
                c.close();
            }
        }
    }

    public void pauseAd(View view){
        mAdView.destroy();
        mAdView.setVisibility(View.GONE);
    }

}
