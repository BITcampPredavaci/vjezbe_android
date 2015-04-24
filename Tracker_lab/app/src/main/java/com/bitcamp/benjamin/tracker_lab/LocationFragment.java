package com.bitcamp.benjamin.tracker_lab;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {


    private TextView mLatitudeText, mLongitudeText, mAltitudeText;
    private ListView mAddressesList;

    private ArrayList<String> mAddresses;

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

        mAddresses = new ArrayList<String>();
        mAddressesList = (ListView) fragmentView.findViewById(R.id.list_view_addresses);

        mAddressesList.setAdapter(
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        mAddresses
                )
        );

        mAddressesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userLocation = (String) parent.getAdapter().getItem(position);
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:123456"));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("sms_body", "I am at " + userLocation);
                startActivity(smsIntent);
            }
        });



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
                                mAddresses.add(address.getString("formatted_address"));
                            }
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ArrayAdapter<String>)mAddressesList.getAdapter()).notifyDataSetChanged();
                                }
                            });
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
