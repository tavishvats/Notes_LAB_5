package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ServiceConfigurationError;

public class MainActivity3 extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        EditText text = (EditText) findViewById(R.id.note);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);
        if (noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String note_content = note.getContent();
            text.setText(note_content);
        }
    }

    public void save_click(View view) {
        EditText text = (EditText) findViewById(R.id.note);
        String content = text.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(sqLiteDatabase);
        SharedPreferences pref = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String username = pref.getString("username", "");
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        if (noteid == -1) {
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            helper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            helper.updateNote(title, date, content, username);
        }
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}