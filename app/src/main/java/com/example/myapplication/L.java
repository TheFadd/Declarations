package com.example.myapplication;

import android.util.Log;

public class L {

    private static final String TAG = "TAG";

    public static void d(Object message) {
        Log.d(TAG, "" + message);
    }
}
