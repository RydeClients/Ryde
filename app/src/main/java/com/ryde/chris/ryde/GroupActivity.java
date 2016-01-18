package com.ryde.chris.ryde;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpMembersListView();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return true;
    }

    private void setUpMembersListView() {
        Intent fromMainActivity = getIntent();
        Group theGroup = fromMainActivity.getParcelableExtra("group");
        MemberAdapter memberAdapter = new MemberAdapter(this, R.layout.member_layout, theGroup.getUsers());
        ((ListView)this.findViewById(R.id.members)).setAdapter(memberAdapter);
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
