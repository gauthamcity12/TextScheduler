package com.gauthamcity12.textscheduler;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class TextHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_history);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerList);
        LinearLayoutManager llmanager = new LinearLayoutManager(this);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmanager);

        recyclerView.setAdapter(new TextHistoryRecyclerAdapter(makeTexts()));
    }

    //TODO: Removed this method when done testing
    private ArrayList<TextData> makeTexts(){
        ArrayList<TextData> list = new ArrayList<>();
        list.add(new TextData("Bobby Shmurda", "July 4th", "4 AM", "ALL I DO IS CASH OUT"));
        list.add(new TextData("Wiz Khalifa", "July 4th", "4 AM", "Work Hard Play Hard"));
        list.add(new TextData("A$AP Rocky", "July 4th", "4 AM", "Long Live A$AP"));
        list.add(new TextData("A$AP Ferg", "July 4th", "4 AM", "Dump Dump"));

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
