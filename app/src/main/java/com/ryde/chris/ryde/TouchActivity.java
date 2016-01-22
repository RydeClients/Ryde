package com.ryde.chris.ryde;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by chris on 1/22/2016.
 */
public class TouchActivity extends AppCompatActivity {
    public static final TouchEffect TOUCH = new TouchEffect();
    public void  setTouchNClick(View v )
    {
        if (v != null)
            v.setOnTouchListener(TOUCH);
    }
}
