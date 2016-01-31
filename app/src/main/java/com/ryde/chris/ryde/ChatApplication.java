package com.ryde.chris.ryde;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import com.firebase.client.Firebase;

/**
 * Created by chris on 1/20/2016.
 */
public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity,
                    Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
            @Override
            public void onActivityResumed(Activity activity) {
            }
            @Override
            public void onActivityStopped(Activity activity) {

            }
            @Override
            public void onActivityStarted(Activity activity) {

            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }
        });

    }


}
