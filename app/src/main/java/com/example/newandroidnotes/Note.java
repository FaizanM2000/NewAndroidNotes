package com.example.newandroidnotes;
import android.os.Build;
import android.util.JsonWriter;

import androidx.annotation.RequiresApi;


import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable {
    private String title;
    private  String noteText;
    private  String lastSaveDate;

    private static int notecount =0;
    public Note() {
        title = "";
        noteText = "";
        lastSaveDate = null;
        notecount++;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(String title, String noteText, String timestamp){

        this.title = title;
        this.noteText = noteText;
        lastSaveDate =  timestamp;


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(String title, String noteText){
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(new Date()).toString();
        SimpleDateFormat time = new SimpleDateFormat("EEE, dd/MM/yyyy");
        String daystring = time.format(new Date());
        this.title = title;
        this.noteText = noteText;
        lastSaveDate =  daystring+" "+dateString;
        notecount++;

    }

    public String getTitle(){return title;}
    public String getNoteText(){return noteText;}
    public String getLastSaveDate(){return lastSaveDate;}

    public String toString(){
        try{
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.beginObject();
            jsonWriter.setIndent(" ");
            jsonWriter.name("Title").value(getTitle());
            jsonWriter.name("NoteContent").value(getNoteText());
            jsonWriter.name("timestamp").value(getLastSaveDate());
            jsonWriter.endObject();
            return sw.toString();

        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public void setTitle(String t){
        this.title = t;
    }

    public void setNoteText(String txt){
        this.noteText = txt;
    }

    public void setLastSaveDate(String date){
        this.lastSaveDate = date;
    }





}







