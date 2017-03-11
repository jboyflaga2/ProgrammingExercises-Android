package com.example.flowmortarexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

public class MainActivity extends MyBaseActivity {

//    @Override protected void attachBaseContext(Context baseContext) {
//        baseContext = Flow.configure(baseContext, this) //
//                .dispatcher(new BasicDispatcher(this)) //
//                .defaultKey(new WelcomeScreen()) //
//                .keyParceler(new BasicKeyParceler()) //
//                .install();
//        super.attachBaseContext(baseContext);
//    }
//
//    @Override public void onBackPressed() {
//        if (!Flow.get(this).goBack()) {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view);
    }

    @Override
    protected Blueprint getBlueprint() {
        return new MyScreen();
    }
}
