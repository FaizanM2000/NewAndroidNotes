package com.example.newandroidnotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNotesActivity extends AppCompatActivity {

    private static final String TAG = "";

    private EditText notetitle;
    private EditText notecontent;
    public Note newnote;
    public int pos;
    View v;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        notetitle = findViewById(R.id.inputNoteTitle);
        notecontent = findViewById(R.id.inputNoteContent);
        notecontent.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
        Intent intent = getIntent();

        if(intent.hasExtra("note"))
        {

            newnote = (Note) intent.getSerializableExtra("note");
            if(newnote!=null){
                String newtitle = newnote.getTitle();
                String newcontent = newnote.getNoteText();

                notetitle.setText(newtitle);
                notecontent.setText(newcontent);


            }else{
                Toast.makeText(this,"none found",Toast.LENGTH_LONG).show();
            }

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void doReturn(View v) {

        String editedTitle = notetitle.getText().toString();
        String editedcontent = notecontent.getText().toString();

        Note newN = new Note(editedTitle, editedcontent);

        Intent intent = new Intent();
        intent.putExtra("Note", newN);

        setResult(RESULT_OK, intent);
        finish();
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void savenote(){
        if(newnote== null){

            if(notetitle.getText().toString().isEmpty()){
                showAlertDialog();
            }
            else{
                Log.d(TAG, "savenote: proceeding to save note");
                Intent data = new Intent();
                data.putExtra("returnednotetitle",notetitle.getText().toString());
                data.putExtra("returnednotecontent",notecontent.getText().toString());
                setResult(RESULT_OK,data);
                finish();
            }

        }
        else{
           doReturn(v);
        }


    }

    @Override
    public void onBackPressed() {
        if(notetitle.getText().toString().isEmpty()){
            showAlertDialog();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Exit without making changes?");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(notetitle.getText().toString().isEmpty()){
                        showAlertDialog();
                    }
                    else{
                        savenote();
                    }

                }
            });
            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    returntomain();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();



        }



    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error");
        builder.setMessage("Note will not be saved without title!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                returntomain();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                notetitle.setText("");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void returntomain(){
        Log.d(TAG, "returntomain: no note made");
        Intent data = new Intent();
        setResult(RESULT_CANCELED,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addnotemenu, menu);
        menu.findItem(R.id.savebutton).setTitle("savenote");
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: optionsitem selected");
        int id = item.getItemId();
        if (id == R.id.savebutton) {
            savenote();
            return true;
        }
        return true;
    }


}
