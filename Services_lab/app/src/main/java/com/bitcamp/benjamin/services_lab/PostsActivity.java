package com.bitcamp.benjamin.services_lab;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.bitcamp.benjamin.services_lab.singletons.PostFeed;


public class PostsActivity extends ActionBarActivity {

    private ListView mPostList;
    private EditText mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        PostFeed postFeed = PostFeed.getInstance();
        postFeed.getFeed( getString(R.string.service_posts) );

        mPostList = (ListView)findViewById(R.id.list_view_posts);
        ArrayAdapter<Post> listAdapter = new ArrayAdapter<Post>(
                this,
                android.R.layout.simple_list_item_1,
                postFeed.getFeed()
        );
        mPostList.setAdapter(listAdapter);

        mFilter = (EditText)findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ( (ArrayAdapter<Post>)mPostList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts, menu);
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
