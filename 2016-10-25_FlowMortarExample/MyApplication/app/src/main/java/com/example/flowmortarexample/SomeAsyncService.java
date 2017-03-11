package com.example.flowmortarexample;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by MyndDev on 10/26/2016.
 */

public class SomeAsyncService {

    @Inject
    public SomeAsyncService() {

    }

    public void doSomethingAsync(String someText, AsyncCallback<SomeResult> asyncCallback) {
        // // TODO_JBOY: 11/7/2016 
    }

    public void doSomething(String text) {
        Log.i("info", "" + SomeAsyncService.class.toString() + ".doSomething(): " + text);
    }
}
