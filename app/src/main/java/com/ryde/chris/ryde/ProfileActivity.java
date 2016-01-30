package com.ryde.chris.ryde;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent grabUser = getIntent();
        User user = grabUser.getParcelableExtra("profile");
        ((ImageView)findViewById(R.id.profileImageView)).setImageResource(user.getProfileImage());
        ((TextView)findViewById(R.id.name_view)).setText(user.getName());
    }

}
