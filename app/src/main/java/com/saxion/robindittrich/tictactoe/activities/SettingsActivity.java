package com.saxion.robindittrich.tictactoe.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.saxion.robindittrich.tictactoe.R;
import com.saxion.robindittrich.tictactoe.adapters.LampListAdapter;
import com.saxion.robindittrich.tictactoe.views.InputView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.mesoplz.hue.exceptions.HueException;
import nl.mesoplz.hue.models.HueBridge;

import static com.saxion.robindittrich.tictactoe.managers.HueManager.bridge;

public class SettingsActivity extends AppCompatActivity {

    private InputView ipInput;
    private InputView userInput;
    private RecyclerView recyclerView;

    private LampListAdapter adapter;

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

        recyclerView = findViewById(R.id.rvLightsView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new LampListAdapter(this);
        adapter.setClickListener(new LampListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bridge.getLights().get(position).setPower(false);
                            Thread.sleep(1000);
                            bridge.getLights().get(position).setPower(true);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(runnable).start();
            }
        });

        recyclerView.setAdapter(adapter);

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
            bridge = new HueBridge(ip, user, this);
        } catch (HueException e) {
            e.printStackTrace();
        }

        SharedPreferences pref = getApplication().getSharedPreferences("Settings", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ip", ip);
        editor.putString("user", user);
        editor.apply();
        finish();
    }
}
