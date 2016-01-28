package com.ryde.chris.ryde;

import android.app.Application;


import com.firebase.client.Firebase;

/**
 * Created by chris on 1/20/2016.
 */
public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

    }


}
