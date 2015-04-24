package com.bitcamp.benjamin.viewpager_lab;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BitAdapter adapter = new BitAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

    }

    private class BitAdapter extends FragmentStatePagerAdapter{

        public BitAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment show;

            if(position % 2 == 0) {
                show = new EvenFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(EvenFragment.VALUE_KEY,
                        position);
                show.setArguments(arguments);
            } else {
                show = new OddFragment();
                Bundle arguments = new Bundle();
                arguments.putInt(OddFragment.ODD_KEY,
                        position);
                show.setArguments(arguments);
            }

            return show;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position){
            if(position % 2 == 0)
            return "Even Tab " + position;
            else
                return "Odd Tab " + position;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
