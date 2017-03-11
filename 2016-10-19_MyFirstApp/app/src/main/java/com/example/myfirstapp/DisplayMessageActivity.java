package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message + "-" + getValueFromSharedPreferences("HIGH_SCORE") + "-" + getValueFromSharedPreferences("PLANET_CHOSEN"));

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }

    private String getValueFromSharedPreferences(String key) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SHARED_PREF_1", Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, null);
        return value;
    }
}
