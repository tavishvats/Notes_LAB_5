package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        if (!pref.getString("username", "").equals("")) {
            String username = pref.getString("username", "");
            goToActivity2(username);
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    public void login(View view) {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        SharedPreferences pref = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        pref.edit().putString("username", username.getText().toString()).apply();
        goToActivity2(username.getText().toString());
    }

    public void goToActivity2(String str) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", str);
        startActivity(intent);
    }
}
