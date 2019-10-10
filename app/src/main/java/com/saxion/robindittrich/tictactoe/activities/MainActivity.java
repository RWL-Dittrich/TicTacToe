package com.saxion.robindittrich.tictactoe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.saxion.robindittrich.tictactoe.R;
import com.saxion.robindittrich.tictactoe.game.Game;

import java.io.IOException;

import nl.mesoplz.hue.models.HueLight;

import static com.saxion.robindittrich.tictactoe.managers.HueManager.bridge;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void lightsOn(View view) {
        for (HueLight l : bridge.getLights()) {
            try {
                l.setPower(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lightsOff(View view) {
        for (HueLight l : bridge.getLights()) {
            try {
                l.setPower(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void flashy(View view) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int countX = 0, countY = 0;

                for (int i = 0; i < 3 * 3; i++) {
                    if (countX == 3) {
                        countY++;
                        countX = 0;
                    }
                    try {
                        Game.lights[countX][countY].setPower(false);
                        Thread.sleep(300);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                    countX++;
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countX = 0;
                countY = 0;
                for (int i = 0; i < 3 * 3; i++) {
                    if (countX == 3) {
                        countY++;
                        countX = 0;
                    }
                    try {
                        Game.lights[countX][countY].setPower(true);
                        Thread.sleep(150);
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                    countX++;
                }
            }

        });
        t.start();
    }
}
