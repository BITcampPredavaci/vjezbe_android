package com.bitcamp.benjamin.tracker_lab;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {


    private TextView mLatitudeText, mLongitudeText, mAltitudeText;
    private ListView mAddressesList;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_location, container, false);

        mLatitudeText = (TextView) fragmentView.findViewById(R.id.text_view_latitude);
        mLongitudeText = (TextView) fragmentView.findViewById(R.id.text_view_longitude);
        mAltitudeText = (TextView) fragmentView.findViewById(R.id.text_view_altitude);

        mAddressesList = (ListView) fragmentView.findViewById(R.id.list_view_addresses);
        //TODO
        //add adapter and data



        return fragmentView;
    }

    private void showList(Location loc){

        String url = String.format(
                getActivity().getString(R.string.google_geocoder)
                , loc.getLatitude(), loc.getLongitude()
        );

        ServiceRequest.request(
                url,
                new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        try {
                            JSONObject result = new JSONObject(response.body().string());
                            JSONArray resultsArray = result.getJSONArray("results");
                            for(int i = 0; i < resultsArray.length(); i++){
                                JSONObject address = resultsArray.getJSONObject(i);
                                Log.d("LocationApp", address.getString("formatted_address"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }


    private BroadcastReceiver mLocationReceiver = new LocationReceiver(){

        @Override
        public void locationReceived(Context c, Location location){

            mLatitudeText.setText(
                    String.format("%.3f", location.getLatitude())
            );

            mLongitudeText.setText(
                    String.format("%.3f", location.getLongitude())
            );

            mAltitudeText.setText(
                    String.format("%.3f", location.getAltitude())
            );
            showList(location);
        }

        @Override
        public void locationNull(Context c, boolean gpsEnabled){
               int messagedId = gpsEnabled == true
                       ? R.string.gps_enabled : R.string.gps_disabled;

            Toast.makeText(c, messagedId, Toast.LENGTH_SHORT);
        }
    };

    @Override
public void onStart(){
    super.onStart();
        getActivity().registerReceiver(
                mLocationReceiver,
                new IntentFilter(AppLocation.ACTION_TAG)
        );
}
}
