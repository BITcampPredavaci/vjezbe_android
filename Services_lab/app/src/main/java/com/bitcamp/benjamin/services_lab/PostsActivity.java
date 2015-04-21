package com.bitcamp.benjamin.services_lab;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import com.bitcamp.benjamin.services_lab.singletons.PostFeed;

import java.util.ArrayList;
import java.util.List;


public class PostsActivity extends ActionBarActivity {

    private ListView mPostList;
    private EditText mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        PostFeed postFeed = PostFeed.getInstance();
        postFeed.getFeed(getString(R.string.service_posts));

        mPostList = (ListView) findViewById(R.id.list_view_posts);

        PostsAdapter postsAdapter = new PostsAdapter(
          this,
          android.R.layout.simple_list_item_1,
                (ArrayList<Post>)postFeed.getFeed()
        );
        mPostList.setAdapter(postsAdapter);

        mFilter = (EditText) findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((PostsAdapter) mPostList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private class PostsAdapter extends ArrayAdapter<Post> {

        private ArrayList<Post> mListToShow;
        private Filter mFilter;

        private PostsAdapter(Context context, int resource, ArrayList<Post> origin) {
            super(context, resource);
            mListToShow = origin;
        }

        @Override
        public Filter getFilter(){
            if(mFilter == null) {
                mFilter = new PostsFilter();
            }
            return mFilter;
        }

        @Override
        public int getCount(){
            return mListToShow.size();
        }

        @Override
        public Post getItem(int position){
            return mListToShow.get(position);
        }

        private class PostsFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                if(constraint == null || constraint.length() == 0){
                    List<Post> origin = PostFeed.getInstance().getFeed();
                    results.values = origin;
                    results.count = origin.size();
                } else {

                    String searchString = constraint.toString().toLowerCase();

                    ArrayList<Post> filteredList = new ArrayList<Post>();
                    for(int i = 0; i < mListToShow.size(); i++){
                        Post p = mListToShow.get(i);
                        String postTitle = p.getTitle().toLowerCase();

                        if(postTitle.contains(searchString)){
                            filteredList.add(p);
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListToShow = (ArrayList<Post>)results.values;
                notifyDataSetChanged();
            }
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
