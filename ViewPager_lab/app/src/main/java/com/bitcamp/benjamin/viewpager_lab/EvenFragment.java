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
public class EvenFragment extends Fragment {

    public static final String VALUE_KEY = "benjamin.bitcamp.value_key";

    public EvenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_even, container, false);

        Bundle arguments = getArguments();
        int position = arguments.getInt(VALUE_KEY);

        TextView showText = (TextView)v.findViewById(R.id.text_view_show);
        showText.setText("My tab: " + position);


        return v;
    }


}
