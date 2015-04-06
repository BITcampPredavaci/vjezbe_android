package com.bitcamp.benjamin.newsapp_prep.helpers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.bitcamp.benjamin.newsapp_prep.R;

/**
 * This is a helper class we will be using to load our fragments
 * Every class using this will have to implement @see createFragment
 * Created by benjamin on 06/04/15.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO add the a reference to your layout file
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();

        //TODO add reference to your fragment container
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    //TODO another reference to the fragment container
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

}
