package com.saxion.robindittrich.tictactoe;

import android.app.Application;
import android.util.Log;

import com.saxion.robindittrich.tictactoe.managers.HueManager;

public class App extends Application {

    public App() {
        Log.i("Main", "App constructor fired! Hello world :)");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HueManager.init(this);
        Log.i("Main", "onCreate fired!");
    }
}
