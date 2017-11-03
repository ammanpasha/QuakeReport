package com.example.android.quakereport;

/**
 * Created by pasha on 19/08/2017.
 */

public class earthquake {

    private double Magnitude;

    private String Location;

    private long TimeInMS;

    public earthquake(double mag, String loc, long time)
    {
        Magnitude = mag;
        Location = loc;
        TimeInMS = time;
    }

    public double getMagnitude()
    {
        return Magnitude;
    }

    public String getLocation()
    {
        return Location;
    }

    public long getTimeInMS()
    {
        return TimeInMS;
    }

}
