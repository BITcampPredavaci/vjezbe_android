package com.bitcamp.benjamin.serviceintro_lab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class MainActivity extends ActionBarActivity {



    private Button mLogin;
    private static final String TAG = "bitcamp.se.main_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        String email = sharedPref.getString(
                getString(R.string.user_email_key), null);

        String password = sharedPref.getString(
                getString(R.string.user_password_key), null);

        if(email != null && password != null){
            //sendLogin(email, password);
            goToPost();
            //TODO
            //Send user to other activity
        }

        mLogin = (Button) findViewById(R.id.button_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEdit = (EditText) findViewById(R.id.edit_text_email);
                EditText passwordEdit = (EditText) findViewById(R.id.edit_text_password);

                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(getString(R.string.user_email_key), email);
                editor.putString(getString(R.string.user_password_key), password);
                editor.commit();

                sendLogin(email, password);


            }
        });

    }

    private void sendLogin(String email, String password){
        String url = "http://10.0.2.2:9000/login";
        String json = String.format("{ " +
                " \"email\" : \"%s\", " +
                " \"password\": \"%s\" " +
                " }", email, password);

        Log.e(TAG, json);
           PostRequest.post(
                   url,
                   json,
                   logResponse()
           );
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
                goToPost();
            }
        };
    }

    private void goToPost()
    {
        Intent postInt = new Intent(this, SendPostActivity.class);
        startActivity(postInt);
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
