package com.bitcamp.benjamin.services_lab.singletons;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by benjamin on 14/04/15.
 */
public class UserData {
    private static UserData ourInstance = new UserData();

    public static UserData getInstance() {
        return ourInstance;
    }

    private int mId;
    private String mEmail;
    private String mPassword;
    private String mUsername;

    private UserData() {
        mId = -1;
    }

    public String getBaseAuth(){
        return "Basic "+ Base64.encodeToString(
                String.format("%s:%s", mEmail, mPassword)
                        .getBytes(),
                Base64.NO_WRAP);
    }

    public boolean isAuthenticated(){
        return mId > 0;
    }

    public String toJson(){
        JSONObject obj = new JSONObject();

        try {
            obj.put("email", mEmail);
            obj.put("password", mPassword);

            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
