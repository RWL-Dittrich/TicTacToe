package nl.mesoplz.hue.models;


import android.graphics.Color;

import java.io.IOException;

public class HueLight {
    private int lightID;
    private HueBridge bridge;
    private int transitionTime;

    private int id;
    private String name;

    HueLight(int lightID, int transitionTime ,HueBridge bridge, int id, String name) {
        this.lightID = lightID;
        this.bridge = bridge;
        this.transitionTime = transitionTime;
        this.id = id;
        this.name = name;
    }


    /**
     * Sets the RGB of a light of this light
     * @param r r value
     * @param g g value
     * @param b b value
     * @throws IOException IOException when something went wrong with sending the command
     */
    public void setRGB(int r, int g, int b) throws IOException{
        setRGB(r, g, b, transitionTime);
    }

    /**
     * Sets the RGB of a light of this light with transitionTime
     * @param r r value
     * @param g g value
     * @param b b value
     * @param transitionTime time in seconds to transition
     * @throws IOException IOException when something went wrong with sending the command
     */
    public void setRGB(int r, int g, int b, int transitionTime) throws IOException{
        if(r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0 || transitionTime < 0) {
            throw new IllegalArgumentException("Value of r g or b cannot be more than 255 and less than 0. Or TransitionTime is less than 0");
        }
        float[] HSB = new float[3];
        Color.RGBToHSV(r, g, b, HSB);
        int H = (int) (HSB[0] / 360f * 65535f);
        int S = (int) (HSB[1] * 254f);
        int B = Math.max(r, Math.max(b, g));
        bridge.putCommand("{\"hue\": " + H + ",\"sat\": " + S + ", \"bri\": " + B + ",\"transitiontime\": " + transitionTime + "}","/lights/" + lightID + "/state");

    }

    /**
     * Sets the brightness of a light of this light
     * @param bri brightness 0 - 254
     * @throws IOException IOException when something went wrong with sending the command
     */
    public void setBri(int bri) throws IOException{
        setBri(bri, transitionTime);
    }

    /**
     * Sets the brightness of a light of this light with transitionTime
     * @param bri brightness 0 - 254
     * @param transitionTime time in seconds to transition
     * @throws IOException IOException when something went wrong with sending the command
     */
    public void setBri(int bri, int transitionTime) throws IOException{
        if(bri > 254 || bri < 0 || transitionTime < 0) {
            throw new IllegalArgumentException("Value of brightness cannot be more than 254 or less than 0. Or TransitionTime is less than 0");
        }
        bridge.putCommand("{\"bri\": " + bri + ", \"transitiontime\": " + transitionTime + "}","/lights/" + lightID + "/state");

    }

    /**
     * Sets the power of the light
     * @param power true = turn on light, false = turn off light
     * @throws IOException IOException when something went wrong with sending the command
     */
    public void setPower(boolean power) throws IOException{
        setPower(power, transitionTime);
    }

    /**
     * Sets the power of the light with transitionTime
     * @param power true = turn on light, false = turn off light
     * @param transitionTime The transitiontime to change the light with
     * @throws IOException IOException when something went wrong with sending the command
     */
    public void setPower(boolean power, int transitionTime) throws IOException{
        if (power) {
            bridge.putCommand("{\"on\": " + true + ", \"transitiontime\": " + transitionTime + "}", "/lights/" + lightID + "/state");
        } else {
            bridge.putCommand("{\"on\": " + false + "}", "/lights/" + lightID + "/state");

        }
    }

    public void setTransitionTime(int transitionTime) {
        this.transitionTime = transitionTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "id:" + id + " name: " + name;
    }
}
