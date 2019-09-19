package com.saxion.robindittrich.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;
import nl.mesoplz.hue.models.HueLight;

public class MainActivity extends AppCompatActivity {

    public static HueBridge bridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            bridge = new HueBridge("10.13.37.228", "4lHzCIy-2gZKkUKD75tHXWhvWvDEOCxpJP9YILGF");
//            System.out.println(bridge.getLights());
//        } catch (HueException e) {
//            e.printStackTrace();
//        }
    }


    public void lightsGreen(View view) {
        try {
            bridge = new HueBridge("10.13.37.228", "4lHzCIy-2gZKkUKD75tHXWhvWvDEOCxpJP9YILGF");
            System.out.println(bridge.getLights());
        } catch (HueException e) {
            e.printStackTrace();
        }
    }

    public void lightsCyan(View view) {
        for (HueLight light : bridge.getLights()) {
            try {
                light.setRGB(0, 255, 255);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
