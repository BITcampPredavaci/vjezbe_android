package com.bitcamp.benjamin.services_lab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitcamp.benjamin.services_lab.service.ServiceRequest;
import com.bitcamp.benjamin.services_lab.singletons.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);


        String email = mSharedPreferences.getString(
                getString(R.string.key_user_email),
                null
        );

        String password = mSharedPreferences.getString(
          getString(R.string.key_user_password),
                null
        );

        if(email != null && password != null){
            setUserData(email, password);
            loginUser();
        }

        final EditText editEmail = (EditText) findViewById(R.id.edit_text_email);
        final EditText editPassword = (EditText)findViewById(R.id.edit_text_password);

        Button buttonLogin = (Button) findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                setUserData(email, password);
                loginUser();
            }
        });


    }

    private void loginUser(){
        String url = getString(R.string.service_login);
        Callback callback = loginVerification();
        String json = UserData.getInstance().toJson();

        ServiceRequest.post(url, json, callback);
    }

    private Callback loginVerification(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJson = response.body().string();
                try {
                    JSONObject user = new JSONObject(responseJson);
                    int id = user.getInt("id");
                    if(id > 0){
                        String username = user.getString("name");
                        UserData userData = UserData.getInstance();
                        userData.setId(id);
                        userData.setUsername(username);
                        saveUserCredentials();
                        goToPosts();
                    }
                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

    private void saveUserCredentials(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        UserData userData = UserData.getInstance();

        editor.putString(
                getString(R.string.key_user_email),
                userData.getEmail()
        );

        editor.putString(
                getString(R.string.key_user_password),
                userData.getPassword()
        );
        editor.commit();
    }

    private void makeToast(final int messageId){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,
                        messageId,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goToPosts(){
        Intent posts = new Intent(this, PostsActivity.class);
        startActivity(posts);
    }

    private void setUserData(String email, String password){
        UserData userData = UserData.getInstance();
        userData.setEmail(email);
        userData.setPassword(password);
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
