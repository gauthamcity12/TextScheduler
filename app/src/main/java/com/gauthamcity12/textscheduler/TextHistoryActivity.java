package com.gauthamcity12.textscheduler;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;


public class TextHistoryActivity extends Activity {

    private RecyclerView recyclerView;
    private LinearLayoutManager llmanager;
    private TextHistoryRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_history);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerList);
        llmanager = new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanager);

        adapter = new TextHistoryRecyclerAdapter(getTexts(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() { // updates the scrolling list without needing to restart the activity
        super.onResume();
        recyclerView.setAdapter(new TextHistoryRecyclerAdapter(getTexts(), this));
    }

    private ArrayList<TextData> getTexts(){
        ArrayList<TextData> list = new ArrayList<>();

        String query = "SELECT * FROM "+TextInfoStore.TABLE_NAME;
        SQLiteDatabase db = MainActivity.getDB();
        Cursor cursor = db.rawQuery(query, null);

        TextData tdat;
        if(cursor.moveToFirst()){ // checks the first row
            tdat = new TextData(cursor.getString(5), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(0));
            if(cursor.getString(6).equals("true")){
                tdat.sent();
            }
            list.add(tdat);
        }
        while (cursor.moveToNext()){ // checks the rest of the rows
            tdat = new TextData(cursor.getString(5), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(0));
            if(cursor.getString(6).equals("true")){
                tdat.sent();
            }
            list.add(tdat);
        }
        if(list.isEmpty()){ // if no texts in db
            list.add(new TextData("No Scheduled Texts Yet!", "", "", "", 0));
        }
        Collections.sort(list);
        return list;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_history, menu);
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
}
