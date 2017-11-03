package com.example.android.quakereport;

/**
 * Created by pasha on 19/08/2017.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public final class QueryUtils {


   //private constructor cuz no need to make an object
    private QueryUtils() {
    }

/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //this is the main method that combines and calls all other methods
    //this method will be called in our MainActivity/EarthquakeActivity in doInBackground method of AsyncTask

    public static List<earthquake> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl); //calling createUrl method that is defined below


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url); //calling makeHttpsRequest method that is defined felow
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<earthquake> earthquakes = extractEarthquakes(jsonResponse); //calling method defined below to extract earthquake info by passing JSONreponse as parameter

        // Return the list of earthquakes
        return earthquakes;
    }

/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //this method accepts raw JSON response from the website and extracts data from the response,
    //makes an earthquake object, adds the objects to an ArrayList of type earthquake
    //and then returns the ArrayList to fetchEarthquakeData() method, which will then return this list to MainActivity/EarthquakeActivity

    public static ArrayList<earthquake> extractEarthquakes(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject BasejsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = BasejsonObject.getJSONArray("features");
            String  _loc;
            Double _mag;
            long timeInMilliseconds;

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonArrayObject = jsonArray.getJSONObject(i);
                JSONObject PropertiesJSONObject = jsonArrayObject.getJSONObject("properties");
                _mag = PropertiesJSONObject.getDouble("mag");
                _loc = PropertiesJSONObject.getString("place");
                timeInMilliseconds = PropertiesJSONObject.getLong("time");
                earthquake eq = new earthquake(_mag, _loc, timeInMilliseconds);
                earthquakes.add(eq);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    /*----------------------------------------------------------------------------------------------------------------------------------------------------------*/

   //this methods makes URL out of the given string and returns URL object to fetchEarthquakeData() method
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------*/

    //this method takes URL object as arg and returns raw JSON reponse from the website
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //this is a helper method for makeHttpRequest() method
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}

    /*----------------------------------------------------------------------------------------------------------------------------------------------------------*/