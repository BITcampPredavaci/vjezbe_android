package com.bitcamp.benjamin.tracker_lab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationReceiver extends BroadcastReceiver {

    private static final String TAG = "LocationReceiver";

    public LocationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Location location = (Location) intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);

        if(location != null) {
            locationReceived(context, location);
            return;
        }

        if(intent.hasExtra(LocationManager.KEY_PROVIDER_ENABLED)){
            boolean gpsEnabled = intent.getBooleanExtra(LocationManager.KEY_PROVIDER_ENABLED, false);
            locationNull(context, gpsEnabled);
        }


    }

    public void locationReceived(Context context, Location location){
        Log.d(TAG, "Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
    }

    public void locationNull(Context context, boolean gpsEnabled){
        Log.d(TAG, "Provider Enabled:  " + gpsEnabled);
    }
}
