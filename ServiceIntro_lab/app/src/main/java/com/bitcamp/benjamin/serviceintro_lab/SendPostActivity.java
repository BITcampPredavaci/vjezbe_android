package com.bitcamp.benjamin.serviceintro_lab;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class SendPostActivity extends ActionBarActivity {

    Button mSend;

    private static final String TAG ="tindrli";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_post);

        mSend = (Button) findViewById(R.id.button_post);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEdit = (EditText)findViewById(R.id.edit_text_title);
                EditText contentEdit = (EditText)findViewById(R.id.edit_text_content);

                String title = titleEdit.getText().toString();
                String content = contentEdit.getText().toString();

                String url = "http://10.0.2.2:9000/api/addPost";
                String jsonObject = String.format("{" +
                        " \"title\": \"%s\", " +
                        " \"content\": \"%s\" " +
                        "}", title, content);

                PostRequest.post(
                        url,
                        jsonObject,
                        logResponse()
                );

            }
        });
    }

    private Callback logResponse(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, request.toString());
                Log.d(TAG, e.getMessage());
                Log.d(TAG, "Epic fail");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, response.body().string());
                Log.d(TAG, response.message().toString());
                Log.d(TAG, "worked");
                //goToPost();
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_post, menu);
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
