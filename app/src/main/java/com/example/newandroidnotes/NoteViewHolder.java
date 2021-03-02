package com.example.newandroidnotes;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView datetime;
    TextView content;
    public NoteViewHolder(View itemView){

        super(itemView);
        title = itemView.findViewById(R.id.noteLayoutTitle);
        datetime = itemView.findViewById(R.id.noteLayoutDatetime);
        content = itemView.findViewById(R.id.notelayoutcontent);
    }





}