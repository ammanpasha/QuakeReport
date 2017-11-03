package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.ArrayList;

import static android.R.attr.format;

/**
 * Created by pasha on 19/08/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<earthquake> earthquake_obj) {
        super(context, 0, earthquake_obj);
    }



    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double mag)
    {
       if( mag>=0 && mag<=2)
       {
           return R.color.magnitude1;
       }
       else if( mag>2 && mag<=3 )
       {
           return R.color.magnitude2;
       }
       else if( mag>3 && mag<=4 )
       {
           return R.color.magnitude3;
       }
       else if( mag>4 && mag<=5 )
       {
           return R.color.magnitude4;
       }
       else if( mag>5 && mag<=6 )
       {
           return R.color.magnitude5;
       }
       else if( mag>6 && mag<=7 )
       {
           return R.color.magnitude6;
       }
       else if( mag>7 && mag<=8 )
       {
           return R.color.magnitude7;
       }
       else if( mag>8 && mag<=9 )
       {
           return R.color.magnitude8;
       }
       else if( mag>9 && mag<=10 )
       {
           return R.color.magnitude9;
       }
       else
       {
           return R.color.magnitude10plus;
       }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        earthquake currentEarthquake = getItem(position);

        TextView MagnitudeTextview = (TextView) listItemView.findViewById(R.id.mag_txtview);
        MagnitudeTextview.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) MagnitudeTextview.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle

        magnitudeCircle.setColor( ContextCompat.getColor(getContext(), magnitudeColor));


        String OriginalLocation = currentEarthquake.getLocation();
        String PrimaryLocation, SecondaryLocation;
        final String LOCATION_SEPARATOR = " of ";

        if (OriginalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = OriginalLocation.split(LOCATION_SEPARATOR);
            SecondaryLocation = parts[0] + LOCATION_SEPARATOR;
            PrimaryLocation = parts[1];
        } else {
            SecondaryLocation = getContext().getString(R.string.near_the);
            PrimaryLocation = OriginalLocation;
        }

        TextView PrimaryLocationTextview = (TextView) listItemView.findViewById(R.id.primary_loc_txtview);
        PrimaryLocationTextview.setText(PrimaryLocation);

        TextView SecondaryLocationTextview = (TextView) listItemView.findViewById(R.id.seconday_loc_txtview);
        SecondaryLocationTextview.setText(SecondaryLocation);

        TextView DateTextview = (TextView) listItemView.findViewById(R.id.date_txtview);
        Date dateObject = new Date(currentEarthquake.getTimeInMS());
        DateTextview.setText(formatDate(dateObject));

        TextView TimeTextView = (TextView) listItemView.findViewById(R.id.time_txtview);
        TimeTextView.setText(formatTime(dateObject));

        return listItemView;
    }
}
