package nl.mesoplz.hue.models;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saxion.robindittrich.tictactoe.game.Game;

import nl.mesoplz.hue.compare.LightComparator;
import nl.mesoplz.hue.exceptions.BridgeNotFoundException;
import nl.mesoplz.hue.exceptions.HueException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class HueBridge {

    private ArrayList<HueLight> lights = new ArrayList<>();
    private String ip;
    private String user;
    private int transitionTime;

    private RequestQueue requestQueue;

    public HueBridge(String ip, String user, int transitionTime, Context context) throws HueException {
        this.ip = ip;
        this.user = user;
        this.transitionTime = transitionTime;

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = Volley.newRequestQueue(context);
        // Start the queue
        requestQueue.start();

        discoverLights();
    }

    public HueBridge(String ip, String user, Context context) throws HueException {
        this(ip, user, 10, context);
    }


    //A bunch of getter methods
    public ArrayList<HueLight> getLights() {
        return lights;
    }

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    /**
     * Makes a PUT command to the hue bridge
     *
     * @param jsonCommand this is the message body that should be sent
     * @param subURL      this is the subURL that the command should use eg. /lights/1
     * @throws IOException throws IOException when something went wrong with the connection
     */
    void putCommand(final String jsonCommand, String subURL) throws IOException {
        String url = "http://" + ip + "/api/" + user + subURL;

        StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR!", Arrays.toString(error.getStackTrace()));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonCommand.getBytes(StandardCharsets.UTF_8);
            }
        };

        requestQueue.add(request);

    }

    /**
     * Discovers lights in this Hue
     */
    private void discoverLights() {
        //Create the Http connection
        String url = "http://" + ip + "/api/" + user + "/lights";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);

                    //Start reading the lights
                    Iterator<String> keys = object.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (object.get(key) instanceof JSONObject) {
                            JSONObject lightObject = (JSONObject) object.get(key);
                            lights.add(new HueLight(Integer.parseInt(key), transitionTime, HueBridge.this, Integer.parseInt(key), lightObject.getString("name")));
                        }
                    }

                    //Sort the lights by name
                    Collections.sort(lights, new LightComparator());
                    Game.instantiateLights();

                    Log.i("HueBridge", "Created bridge with lights: " + lights);
                } catch (JSONException e) {
                    Log.e("Response Error", "Lights JSON could not be read!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR!", Arrays.toString(error.getStackTrace()));
            }
        });

        requestQueue.add(request);
    }



}
