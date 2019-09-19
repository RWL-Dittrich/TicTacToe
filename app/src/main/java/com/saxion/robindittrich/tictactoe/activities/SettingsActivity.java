package com.saxion.robindittrich.tictactoe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.saxion.robindittrich.tictactoe.R;
import com.saxion.robindittrich.tictactoe.views.InputView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;

import static com.saxion.robindittrich.tictactoe.managers.HueManager.bridge;

public class SettingsActivity extends AppCompatActivity {

    private InputView ipInput;
    private InputView userInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Reference inputs
        ipInput = findViewById(R.id.ivIpInput);
        userInput = findViewById(R.id.ivUserInput);
        //Set titles
        ipInput.setmTitle("Hue IP Address:");
        userInput.setmTitle("Hue User String:");
        //Set the values of the current Hue creds
        ipInput.setmEditText(bridge.getIp());
        userInput.setmEditText(bridge.getUser());
    }

    public void saveSettings(View view) {
        String ip = ipInput.getmEditTextString();
        String user = userInput.getmEditTextString();

        //IP address regex
        Pattern p = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
        Matcher m = p.matcher(ip);
        if (!m.matches()) {
            //IP address is bad!
            ipInput.setError("You entered an invalid IP address!");
            return;
        }

        try {
            bridge = new HueBridge(ip, user);
        } catch (HueException e) {
            e.printStackTrace();
        }

        //TODO: Save credentials to android storage

        SharedPreferences pref = getApplication().getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ip", ip);
        editor.putString("user", user);
        editor.apply();
        finish();
    }
}
