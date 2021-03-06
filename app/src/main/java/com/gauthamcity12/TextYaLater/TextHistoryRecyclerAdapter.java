package com.gauthamcity12.TextYaLater;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gauthamcity12 on 7/19/15.
 */
public class TextHistoryRecyclerAdapter extends RecyclerView.Adapter<TextDataViewHolder> {
    private List<TextData> texts;
    private Activity context;

    public TextHistoryRecyclerAdapter(List<TextData> texts, Activity activity){
        this.texts = new ArrayList<TextData>();
        this.texts.addAll(texts); // populates all of the texts that were passed in
        context = activity;
    }
    @Override
    public TextDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new TextDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextDataViewHolder textDataViewHolder, int i) {
        TextData textData = texts.get(i);
        final int i2 = i;
        textDataViewHolder.contactName.setText(textData.getContactName());
        textDataViewHolder.date.setText(textData.getDate());
        textDataViewHolder.time.setText(textData.getTime());
        textDataViewHolder.content.setText(textData.getContent());
        textDataViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextData tdat = texts.remove(i2);
                Handler mHandler = new Handler();

                final int index = i2;
                final int size = getItemCount();


                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, size);
                    }
                });

                SQLiteDatabase db = TextInfoStore.getInstance().getWritableDatabase();
                SharedPreferences settings = context.getSharedPreferences("HASHMAPVALS", 0);
                long rowId = settings.getLong(tdat.getSessionID()+"long", 0);
                try{
                    db.delete(TextInfoStore.TABLE_NAME, TextInfoStore.KEY_ID+"="+rowId, null);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                db.close();

                Intent toStop = new Intent(context, WakeLocker.class);
                PendingIntent pStop = PendingIntent.getBroadcast(context, tdat.getSessionID(), toStop, 0);

                AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pStop);
                pStop.cancel();

            }
        });

        if(textData.getStatus()){ // the text has already been sent
            textDataViewHolder.status.setText("Sent!");
            textDataViewHolder.delete.setVisibility(View.INVISIBLE);
            textDataViewHolder.status.setVisibility(View.VISIBLE);
        }
        else if(getItemCount() == 1 && textData.getSessionID() == 0){ // if no texts are in the list
            textDataViewHolder.status.setVisibility(View.INVISIBLE);
            textDataViewHolder.delete.setVisibility(View.INVISIBLE);
        }
        else{ // if the text has not been sent yet.
            textDataViewHolder.delete.setVisibility(View.VISIBLE);
            textDataViewHolder.status.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

}
