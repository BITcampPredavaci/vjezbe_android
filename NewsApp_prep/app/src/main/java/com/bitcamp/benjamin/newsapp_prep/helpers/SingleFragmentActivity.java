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

        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();


        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()

                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

}
