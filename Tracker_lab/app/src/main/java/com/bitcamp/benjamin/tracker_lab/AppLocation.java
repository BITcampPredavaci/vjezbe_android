package com.bitcamp.benjamin.tracker_lab;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

/**
 * Created by benjamin on 21/04/15.
 */
public class AppLocation {


    public static final String ACTION_TAG =
            "com.bitcamp.benjamin.location_update";

    private Context mAppContext;
    private LocationManager mLocationManager;
    private boolean mTracking;

    private static AppLocation sAppLocation;

    private AppLocation(Context c) {
        mAppContext = c;
        mLocationManager = (LocationManager)
                mAppContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public static AppLocation get(Context c){

        if(sAppLocation == null){
                sAppLocation = new AppLocation(c);
        }
            return sAppLocation;
    }

    public void startLocationTrack(){

        int flag = mTracking ? 0 : PendingIntent.FLAG_NO_CREATE;

        Intent broadcast = new Intent(ACTION_TAG);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(mAppContext,
                        0, broadcast, flag);

        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                pendingIntent);
        mTracking = true;
    }

    public void stopLocationTrack(){
        Intent broadcast = new Intent(ACTION_TAG);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(mAppContext,
                        0, broadcast, PendingIntent.FLAG_NO_CREATE);

        if(pendingIntent != null){
            mLocationManager.removeUpdates(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public boolean isTracking(){
        return mTracking;
    }
}
