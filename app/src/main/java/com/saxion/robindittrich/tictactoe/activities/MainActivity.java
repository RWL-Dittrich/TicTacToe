package com.saxion.robindittrich.tictactoe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.saxion.robindittrich.tictactoe.R;

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

    public void setRed(View view) {
        for (HueLight light : bridge.getLights()) {
            try {
                light.setRGB(255, 0,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setGreen(View view) {
        for (HueLight light : bridge.getLights()) {
            try {
                light.setRGB(0, 255,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setBlue(View view) {
        for (HueLight light : bridge.getLights()) {
            try {
                light.setRGB(0, 0,255);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
