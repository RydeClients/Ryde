package com.ryde.chris.ryde;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by chris on 1/20/2016.
 */
public class GroupChatHelper extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
           Parse.initialize(this, "8nPw2If9bzltO4rMgosd2RkP7jd54xNmcQ1YyYnI", "zv0aiHaaqWYhe3GKLyR6hoMbYXjBPBdBYCraApsq");

    }


}
