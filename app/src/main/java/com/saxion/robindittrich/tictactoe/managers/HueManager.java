package com.saxion.robindittrich.tictactoe.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.saxion.robindittrich.tictactoe.activities.MainActivity;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;

public class HueManager {
    public static HueBridge bridge;

    public static void init(Context context) {
        //Get the shared preferences (app storage)
        SharedPreferences pref = context.getSharedPreferences("Settings", 0);

        //Extract variables out of this storage
        String ip = pref.getString("ip", "127.0.0.1");
        String user = pref.getString("user", "Hue user string");
        try {
            //Instantiate a Bridge with these variables
            bridge = new HueBridge(ip, user, context);
        } catch (HueException e) {
            e.printStackTrace();
        }
    }
}
