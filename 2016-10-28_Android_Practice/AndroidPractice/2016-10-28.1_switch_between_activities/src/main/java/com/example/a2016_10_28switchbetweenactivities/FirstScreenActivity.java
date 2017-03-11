package com.example.a2016_10_28switchbetweenactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        final EditText inputName = (EditText) findViewById(R.id.inputName);
        Button btnNextScreen = (Button) findViewById(R.id.btnNextScreen);

        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //NOTE: look at your notes on "Application context vs. Activity context" so you will
                // understand why you passed v.getContext() where creating your intent that will show
                // the next screen.

                Intent openSecondScreenIntent = new Intent(v.getContext(), SecondScreenActivity.class);
                openSecondScreenIntent.putExtra("name", inputName.getText().toString());
                startActivity(openSecondScreenIntent);
            }
        });
    }
}
