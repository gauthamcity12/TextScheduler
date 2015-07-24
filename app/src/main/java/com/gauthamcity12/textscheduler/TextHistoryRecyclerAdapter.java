package com.gauthamcity12.textscheduler;

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

    public TextHistoryRecyclerAdapter(List<TextData> texts){
        this.texts = new ArrayList<TextData>();
        this.texts.addAll(texts); // populates all of the texts that were passed in
    }
    @Override
    public TextDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new TextDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextDataViewHolder textDataViewHolder, int i) {
        TextData textData = texts.get(i);
        textDataViewHolder.contactName.setText(textData.getContactName());
        textDataViewHolder.date.setText(textData.getDate());
        textDataViewHolder.time.setText(textData.getTime());
        textDataViewHolder.content.setText(textData.getContent());

        if(textData.getStatus()){ // the text has already been sent
            textDataViewHolder.status.setText("Sent!");
            textDataViewHolder.delete.setVisibility(View.INVISIBLE);
            textDataViewHolder.status.setVisibility(View.VISIBLE);
        }
        else if(getItemCount() == 1){ // if no texts are in the list
            textDataViewHolder.status.setVisibility(View.INVISIBLE);
            textDataViewHolder.delete.setVisibility(View.INVISIBLE);
        }
        else{ // if the text has not been sent yet.
            textDataViewHolder.status.setText("Scheduled");
            textDataViewHolder.delete.setVisibility(View.VISIBLE);
            textDataViewHolder.status.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }
}
