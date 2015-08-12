package com.gauthamcity12.TextYaLater;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;


public class TextHistoryActivity extends Activity {

    private RecyclerView recyclerView;
    private LinearLayoutManager llmanager;
    private TextHistoryRecyclerAdapter adapter;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_history);
        getActionBar().setDisplayHomeAsUpEnabled(true);

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
        SQLiteDatabase db = TextInfoStore.getInstance().getWritableDatabase();
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

        db.close();
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
        if (id == R.id.action_refresh) { // refreshes the adapter to show texts sent status
            recyclerView.setAdapter(new TextHistoryRecyclerAdapter(getTexts(), this));
            return true;
        }

        if(id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
