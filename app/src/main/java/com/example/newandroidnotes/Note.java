package com.example.newandroidnotes;
import android.os.Build;
import android.util.JsonWriter;

import androidx.annotation.RequiresApi;


import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.LocalDateTime;
public class Note implements Serializable {
    private final String title;
    private final String noteText;
    private final String lastSaveDate;

    private static int notecount =0;
    public Note() {
        title = "";
        noteText = "";
        lastSaveDate = null;
        notecount++;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(String title, String noteText, String timestamp){
        LocalDateTime myDateObj = LocalDateTime.now();
        this.title = title;
        this.noteText = noteText;
        lastSaveDate =  timestamp;
        notecount++;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Note(String title, String noteText){
        LocalDateTime myDateObj = LocalDateTime.now();
        this.title = title;
        this.noteText = noteText;
        lastSaveDate =  myDateObj.toString();
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







}







