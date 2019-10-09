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

    public void logOutput(View view) {
        System.out.println(view.getId());
        switch(view.getId()) {

        }
    }
}
