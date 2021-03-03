package com.example.newandroidnotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    private static final String TAG = "";
    private final ArrayList<Note> noteArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteListAdapter nAdapter;
    private static final int REQUEST_CODE_A = 1;
    private static final int REQUEST_CODE_B = 2;
    public View v;
    Note n;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        noteArrayList.addAll(loadFile());
        nAdapter = new NoteListAdapter(this,noteArrayList);
        recyclerView.setAdapter(nAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setTitle("Android Notes " + "(" + noteArrayList.size() + ")");

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void buildapp(){
        noteArrayList.addAll(loadFile());
        //recyclerView.setAdapter(nAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nAdapter.notifyDataSetChanged();
        setTitle("Android Notes " + "("+ noteArrayList.size()/2 +")");
        
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        noteArrayList.clear();
        noteArrayList.addAll(loadFile());
        super.onResume();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private ArrayList<Note> loadFile() {
        Log.d(TAG, "loadFile: loading the Json file");
        ArrayList<Note> noteList = new ArrayList<>();
        try {

            InputStream is = getApplicationContext().openFileInput("Note.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }


            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("Title");
                String notecontent = jsonObject.getString("NoteContent");
                String timestamp = (jsonObject.getString("timestamp"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    noteList.add(new Note(title, notecontent, timestamp));
                }

            }


        } catch (FileNotFoundException e) {
            Log.d(TAG, "no json file found");
            Toast.makeText(this, "No previous notes found. Welcome!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return noteList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.notesmenu, menu);

        menu.findItem(R.id.infobutton).setTitle("Info");
        menu.findItem(R.id.addnotebutton).setTitle("AddNote");

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: optionsitem selected");
        int id = item.getItemId();
        if (id == R.id.infobutton) {
            openinfopage();
            return true;
        }
        if (id == R.id.addnotebutton) {

            openAddPage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveNote() {
        Log.d(TAG, "saveNote: saving to json file");

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(noteArrayList);
            printWriter.close();
            fos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void openAddPage() {
        Intent intent = new Intent(this, AddNotesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_B);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_A) {
            if (resultCode == RESULT_OK) {
                if(data != null && data.hasExtra("Note")){
                    Note updatednote = (Note) data.getSerializableExtra("Note");
                    int pos = noteArrayList.indexOf(n);
                    removeFromList(pos);
                    updateToFront(updatednote);
                    saveNote();
                    buildapp();

                }


            }
            if(requestCode == RESULT_CANCELED){
                buildapp();
            }
        }
        if (requestCode == REQUEST_CODE_B) {
            if (resultCode == RESULT_OK) {
                String notetitle = data.getStringExtra("returnednotetitle");
                String notecontent = data.getStringExtra("returnednotecontent");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Note newnote = new Note(notetitle, notecontent);
                    noteArrayList.add(newnote);
                    saveNote();
                    buildapp();
                }
            }
            if(requestCode == RESULT_CANCELED){

                buildapp();
            }
        }


    }

    public void openinfopage() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);

    }

    public void onClick(View v){
        Log.d(TAG, "onNoteClick: note clicked!");
        int pos = recyclerView.getChildLayoutPosition(v);
        n = noteArrayList.get(pos);
        Intent intent = new Intent(this, AddNotesActivity.class);
        intent.putExtra("note",n);
        intent.putExtra("pos",pos);
        startActivityForResult(intent, REQUEST_CODE_A);
    }

    public void removeFromList(int n){
        if(!noteArrayList.isEmpty()){
            noteArrayList.remove(n);
            nAdapter.notifyDataSetChanged();
        }
    }

    public void updateToFront(Note n){
        noteArrayList.add(0,n);
        nAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean onLongClick(View view) {

        Log.d(TAG, "onLongClick: on long click happened");
        int pos = recyclerView.getChildLayoutPosition(view);
        n = noteArrayList.get(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noteArrayList.remove(n);
                saveNote();
                nAdapter.notifyDataSetChanged();
                setTitle("Android Notes " + "(" + noteArrayList.size() + ")");

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

       return false;
    }



}



