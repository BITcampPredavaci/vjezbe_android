package com.bitcamp.benjamin.viewpager_lab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OddFragment extends Fragment {

    public static final String ODD_KEY = "com.benjamin.bitcamp.odd_key";

    public OddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_odd, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(ODD_KEY);

        TextView showText = (TextView)v.findViewById(R.id.text_view_show2);
        showText.setText("My tab: " + position);
        return v;
    }


}
