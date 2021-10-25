package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView msg = (TextView) findViewById(R.id.welcome_msg);
        Intent intent = getIntent();
        SharedPreferences pref = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        msg.setText("Welcome " + intent.getStringExtra("message") + "!");
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(sqLiteDatabase);
        helper.readNotes(pref.getString("username", ""));
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\n%s", note.getTitle(), note.getDate()));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notes_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity3.class);
                intent1.putExtra("noteid", i);
                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                goToActivity1();
                return true;
            case R.id.add_note:
                goToActivity3();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goToActivity1() {
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences pref = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        pref.edit().remove("username").apply();
        startActivity(intent);
    }

    public void goToActivity3() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}