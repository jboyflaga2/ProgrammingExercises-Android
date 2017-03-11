package com.example.a2016_10_28switchbetweenactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        Intent intentThatStartedThisActivity = getIntent();
        String name = intentThatStartedThisActivity.getStringExtra("name");

        TextView txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText("Your name is " + name);


        Button btnPreviousScreen = (Button) findViewById(R.id.btnPreviousScreen);
        btnPreviousScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
