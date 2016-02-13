package com.ryde.chris.ryde;

import
        android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class MainActivity extends TouchActivity {
    private FloatingActionButton addToGroups;
    private ListView groupViewList;
    private List<Group> currUserGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currUserGroups =  ((RydeApplication)getApplicationContext()).getCurrUserGroup();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpGroupsList();
        FloatingActionButton addToGroups = (FloatingActionButton)findViewById(R.id.add_group_button);
        setTouchNClick(addToGroups);
        addToGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder addGroupDialog = new AlertDialog.Builder(MainActivity.this, R.style.AppTheme);
                LayoutInflater inflateDialogLayout = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflateDialogLayout.inflate(R.layout.add_group_dialog, null, false);
                addGroupDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText newGroup = (EditText) dialogView.findViewById(R.id.new_group_name);
                        String addToGroup = newGroup.getText().toString();
                        currUserGroups.add(new Group(addToGroup));
                        ((BaseAdapter) groupViewList.getAdapter()).notifyDataSetChanged();
                    }
                });
                addGroupDialog.setNegativeButton("Cancel", null);
                addGroupDialog.setView(dialogView);
                AlertDialog addGroupDia = addGroupDialog.create();
                addGroupDia.show();
                WindowManager.LayoutParams windowParams = addGroupDia.getWindow().getAttributes();
                windowParams.dimAmount = 0.75f;
                windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                addGroupDia.getWindow().setLayout(1000, 1450);
            }
        });
    }

    private void setUpGroupsList() {
        groupViewList = (ListView)findViewById(R.id.grouplist);
        ArrayAdapter<Group> groupAdapter = new GroupAdapter(this,R.layout.simple_group_list_item, currUserGroups);
        groupViewList.setAdapter(groupAdapter);
        FloatingActionButton addToGroups = (FloatingActionButton)findViewById(R.id.add_group_button);
        addToGroups.attachToListView(groupViewList);
        groupViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startGroupActivity = new Intent(MainActivity.this, GroupActivity.class);
                startGroupActivity.putExtra("group", position);
                MainActivity.this.startActivity(startGroupActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_profile) {
            Intent startProfileActivity = new Intent(this, ProfileActivity.class);
            startProfileActivity.putExtra("profile", new User("Chris Sun", R.drawable.chris));
            this.startActivity(startProfileActivity);
        } else if(id == R.id.find ) {
            AlertDialog.Builder findGroupDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AppTheme);
            LayoutInflater inflateDialogLayout = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            final View findGroupView = inflateDialogLayout.inflate(R.layout.find_group, null, false);
            setTouchNClick(findGroupView.findViewById(R.id.findButton));
            findGroupDialogBuilder.setView(findGroupView);
            AlertDialog findGroupDialog = findGroupDialogBuilder.create();
            Window window = findGroupDialog.getWindow();
            window.getAttributes().y = -900;
            window.setBackgroundDrawableResource(R.color.white);
            window.setLayout(1500, 560);
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.75f;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            findGroupDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    class GroupAdapter extends ArrayAdapter<Group>  {
        private int resource;
        public GroupAdapter(Context context, int resource, List<Group> items) {
            super(context, resource, items);
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(resource, null);
            Group p = getItem(position);
            ((TextView)convertView.findViewById(R.id.group_name)).setText(p.getGroupName());
            LinearLayout memberImages = (LinearLayout) convertView.findViewById(R.id.group_members);
            List<User> members = p.getUsers();
            for(int k=0; k<members.size(); k++) {
                final RoundedImageView memberImage = new RoundedImageView(MainActivity.this);
                memberImage.setImageResource(members.get(k).getProfileImage());
                memberImage.setPadding(0,0,0,0);
                memberImages.addView(memberImage);
                memberImage.getLayoutParams().width=180;
            }
            return convertView;
        }
    }

}
