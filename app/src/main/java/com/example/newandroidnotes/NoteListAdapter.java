package com.example.newandroidnotes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteListAdapter  extends RecyclerView.Adapter<NoteViewHolder> {

    private ArrayList<Note> nlist;
    private MainActivity mainActivity;
    public NoteListAdapter(MainActivity mainActivity,ArrayList<Note> nlist){
        this.nlist = nlist;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);


        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note n = nlist.get(position);
        holder.title.setText(n.getTitle());
        String contentdisplay = n.getNoteText();
        if(contentdisplay.length()>80){
            String newdisplay = contentdisplay.substring(0,80)+"...";
            holder.content.setText(newdisplay);
        }
        else{
            holder.content.setText(n.getNoteText());
        }

        holder.datetime.setText(n.getLastSaveDate());

    }

    @Override
    public int getItemCount() {
        return nlist.size();
    }

    




}