package com.ryde.chris.ryde;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupActivity extends TouchActivity {
    private Group mGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpGroupData();
        Button pickupDest = (Button) findViewById(R.id.pickupDestButton);
        setTouchNClick(pickupDest);
        pickupDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMaps = new Intent(GroupActivity.this, MapsActivity.class);
                GroupActivity.this.startActivity(startMaps);
            }
        });
    }

    private void setUpGroupData() {
        Intent fromMainActivity = getIntent();
        mGroup = ((RydeApplication)getApplicationContext()).getCurrUserGroup().get(
                fromMainActivity.getIntExtra("group", 0));
        final MemberAdapter memberAdapter = new MemberAdapter(this, R.layout.member_layout, mGroup.getUsers());
        ListView members = (ListView)this.findViewById(R.id.members);
        members.setAdapter(memberAdapter);
        members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startProfileActivity = new Intent(GroupActivity.this, ProfileActivity.class);
                startProfileActivity.putExtra("profile", memberAdapter.getItem(position));
                GroupActivity.this.startActivity(startProfileActivity);

            }
        });
        ((TextView)findViewById(R.id.membersTextView)).setText("Group ID: " + mGroup.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.calendar) {
            Intent startCalendarActivity = new Intent(this, CalendarActivity.class);
            startCalendarActivity.putExtra("group", getIntent().getIntExtra("group", 0));
            this.startActivity(startCalendarActivity);
        } else if(id == R.id.groupChat) {
            Intent startGroupChatActivity = new Intent(this, GroupChatActivity.class);
            startGroupChatActivity.putExtra("group", getIntent().getParcelableExtra("group"));
            this.startActivity(startGroupChatActivity);
        }
        return super.onOptionsItemSelected(item);
    }

}
