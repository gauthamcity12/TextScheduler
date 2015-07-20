package com.gauthamcity12.textscheduler;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by gauthamcity12 on 7/19/15.
 */
public class TextDataViewHolder extends RecyclerView.ViewHolder {

    protected TextView contactName;
    protected TextView date;
    protected TextView time;
    protected TextView content;
    protected Button status;
    protected Button delete;
    protected CardView card;


    public TextDataViewHolder(View itemView) {
        super(itemView);
        contactName = (TextView)itemView.findViewById(R.id.contact);
        date = (TextView)itemView.findViewById(R.id.date);
        time = (TextView)itemView.findViewById(R.id.time);
        content = (TextView)itemView.findViewById(R.id.content);
        status = (Button)itemView.findViewById(R.id.statusbutton);
        delete = (Button)itemView.findViewById(R.id.deletebutton);
        card = (CardView)itemView;
    }
}
