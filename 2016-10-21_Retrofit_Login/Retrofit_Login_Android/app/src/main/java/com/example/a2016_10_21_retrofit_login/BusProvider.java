package com.example.a2016_10_21_retrofit_login;

import com.squareup.otto.Bus;

/**
 * Created by MyndDev on 10/21/2016.
 */

public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}
