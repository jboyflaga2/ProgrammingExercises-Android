package com.example.flowmortarexample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MyndDev on 10/26/2016.
 */

public class SomeResult implements Parcelable {

    private String _result;

    public SomeResult(String result) {
        _result = result;
    }

    @Override
    public int describeContents() {
        //TODO
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //TODO
    }

    public String getText() {
        return _result;
    }

    public static final Creator<SomeResult> CREATOR = new Creator<SomeResult>() {
        @Override public SomeResult createFromParcel(Parcel parcel) {
            return new SomeResult(parcel.readString());
        }

        @Override public SomeResult[] newArray(int size) {
            return new SomeResult[size];
        }
    };
}
