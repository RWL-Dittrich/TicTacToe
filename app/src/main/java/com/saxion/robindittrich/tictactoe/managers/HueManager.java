package com.saxion.robindittrich.tictactoe.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.saxion.robindittrich.tictactoe.activities.MainActivity;
import com.saxion.robindittrich.tictactoe.game.Game;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;

public class HueManager {
    public static HueBridge bridge;
    public static int[][] lightIds = new int[3][3];

    public static void init(Context context) {
        //Get the shared preferences (app storage)
        SharedPreferences pref = context.getSharedPreferences("Settings", 0);

        int countX = 0, countY = 0;

        for (int i = 0; i < 3*3; i++) {
            if (countX == 3) {
                countY++;
                countX = 0;
            }
            lightIds[countX][countY] = pref.getInt(Integer.toString(i), 0);
            countX++;
        }

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
