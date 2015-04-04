package com.bitcamp.benjamin.criminalintent_prep;

import java.util.UUID;

/**
 * Created by benjamin on 02/04/15.
 */
public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime(){
        mTitle = null;
        mId = UUID.randomUUID();
    }

    public Crime(String title) {
        mTitle = title;
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
