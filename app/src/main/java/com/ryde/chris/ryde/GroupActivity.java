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
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void setUpGroupData() {
        Intent fromMainActivity = getIntent();
        Group theGroup = fromMainActivity.getParcelableExtra("group");
                mGroup = theGroup;
                final MemberAdapter memberAdapter = new MemberAdapter(this, R.layout.member_layout, theGroup.getUsers());
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
        ((TextView)findViewById(R.id.membersTextView)).setText("Group ID: " + theGroup.getId());
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
            Map<Date, User> drivingSchedule = new HashMap<Date, User>();
            List<User> members = mGroup.getUsers();
            Calendar cal = Calendar.getInstance();
            int[] datesToTest = {11, 12, 17, 22, 26};
            for(int k=0; k<datesToTest.length; k++) {
                cal.set(2016, 1, datesToTest[k]);
                drivingSchedule.put(cal.getTime(), members.get((int) (Math.random() * members.size())));
            }
            mGroup.setSchedule(drivingSchedule);
            startCalendarActivity.putExtra("group", mGroup);
            this.startActivity(startCalendarActivity);
        } else if(id == R.id.groupChat) {
            Intent startGroupChatActivity = new Intent(this, GroupChatActivity.class);
            startGroupChatActivity.putExtra("group", getIntent().getParcelableExtra("group"));
            this.startActivity(startGroupChatActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    class MemberAdapter extends ArrayAdapter<User> {
        private int resource;
        public MemberAdapter(Context context, int resource, List<User> items) {
            super(context, resource, items);
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View memberView = convertView;
            if (memberView == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                memberView = vi.inflate(resource, null);
            }
            User user = getItem(position);
            ((TextView)memberView.findViewById(R.id.memberName)).setText(user.getName());
            ((ImageView)memberView.findViewById(R.id.profileImage)).setImageResource(user.getProfileImage());
            return memberView;
        }
    }
}
