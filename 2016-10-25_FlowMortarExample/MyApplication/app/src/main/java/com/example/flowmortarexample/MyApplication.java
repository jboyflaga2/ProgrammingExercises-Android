package com.example.flowmortarexample;

import android.app.Application;

import mortar.Mortar;
import mortar.MortarScope;

/**
 * Created by MyndDev on 10/26/2016.
 */

public class MyApplication extends Application {
    private MortarScope applicationScope;

    @Override public void onCreate() {
        super.onCreate();
        // Eagerly validate development builds (too slow for production).
        applicationScope = Mortar.createRootScope(BuildConfig.DEBUG);
    }

    @Override public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return applicationScope;
        }
        return super.getSystemService(name);
    }

    public MortarScope getRootScope() {
        return applicationScope;
    }
}