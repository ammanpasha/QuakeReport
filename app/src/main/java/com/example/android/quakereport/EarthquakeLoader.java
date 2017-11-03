package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by pasha on 29/08/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<earthquake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;

    //constructor
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl); //this func makes earthquake data from internet n shit
        return earthquakes;
    }
}
