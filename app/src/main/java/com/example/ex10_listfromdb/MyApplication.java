package com.example.ex10_listfromdb;

import android.app.Application;

import com.example.ex10_listfromdb.database.SharedPredferences;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPredferences.getInstance(this);
    }
}
