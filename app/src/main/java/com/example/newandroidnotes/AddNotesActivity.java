package com.example.newandroidnotes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddNotesActivity extends AppCompatActivity {

    private static final String TAG = "";

    private EditText notetitle;
    private EditText notecontent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        notetitle = findViewById(R.id.inputNoteTitle);
        notecontent = findViewById(R.id.inputNoteContent);
        Intent intent = getIntent();

        if((intent.hasExtra("passedtitle"))&&(intent.hasExtra("passedcontent")))
        {
            String text = intent.getStringExtra("passedtitle");
            notetitle.setText(String.format(text));
            text = intent.getStringExtra("passedcontent");
            notecontent.setText(String.format(text));
        }

    }
    public void savenote(){
        Log.d(TAG, "savenote: proceeding to save note");
        Intent data = new Intent();
        data.putExtra("returnednotetitle",notetitle.getText().toString());
        data.putExtra("returnednotecontent",notecontent.getText().toString());
        setResult(RESULT_OK,data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addnotemenu, menu);
        menu.findItem(R.id.savebutton).setTitle("savenote");
        return true;

    }

    public boolean onOptionsItemSelected( MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: optionsitem selected");
        int id = item.getItemId();
        if(id==R.id.savebutton){
            savenote();
            return true;
        }
        return true;
    }



}
