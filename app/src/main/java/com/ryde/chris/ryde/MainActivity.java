package com.ryde.chris.ryde;

import
        android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addToGroups;
    private ListView groupViewList;
    private List<Group> groupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpGroupsList();
        FloatingActionButton addToGroups = (FloatingActionButton)findViewById(R.id.add_group_button);
        addToGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder addGroupDialog = new AlertDialog.Builder(MainActivity.this, R.style.AppTheme);
                LayoutInflater inflateDialogLayout = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflateDialogLayout.inflate(R.layout.add_group_dialog, null, false);
                addGroupDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText newGroup = (EditText) dialogView.findViewById(R.id.new_group_name);
                        String addToGroup = newGroup.getText().toString();
                        groupsList.add(new Group(addToGroup));
                        ((BaseAdapter) groupViewList.getAdapter()).notifyDataSetChanged();
                    }
                });
                addGroupDialog.setNegativeButton("Cancel", null);
                addGroupDialog.setView(dialogView);
                AlertDialog addGroupDia = addGroupDialog.create();
                addGroupDia.show();
                addGroupDia.getWindow().setLayout(1000,1600);
            }
        });
    }

    private void setUpGroupsList() {
        groupViewList = (ListView)findViewById(R.id.grouplist);
        groupsList = new ArrayList<Group>();
        String[] mockGroupNames = {"DayCare-4 person","Soccer Practice-3 people", "Computer Science Study-2 people",
        "School-3 people", "Work-5 people"};
        User[] stubUsers = { new User("Chris Sun", R.drawable.chris), new User("Barack Obama",R.drawable.barack_obama),
                new User("Ariana Grande",R.drawable.ariana), new User("Shaheen Sharifian", R.drawable.shariwizard),
                    new User("Fion Chan", R.drawable.fion)};
        List<User> usersList = new ArrayList<>(Arrays.asList(stubUsers));
        for(String name:mockGroupNames) {
            Group toAdd = new Group(name);
            toAdd.setUsersList(usersList);
            groupsList.add(toAdd);
        }
        ArrayAdapter<Group> groupAdapter = new GroupAdapter(this,android.R.layout.simple_list_item_1, groupsList);
        groupViewList.setAdapter(groupAdapter);
        FloatingActionButton addToGroups = (FloatingActionButton)findViewById(R.id.add_group_button);
        addToGroups.attachToListView(groupViewList);
        groupViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent startGroupActivity = new Intent(MainActivity.this, GroupActivity.class);
                startGroupActivity.putExtra("group", groupsList.get(position));
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
            this.startActivity(startProfileActivity);
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
            TextView groupNameView = (TextView)convertView;
            if (groupNameView == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                groupNameView = (TextView)vi.inflate(resource, null);
            }
            Group p = getItem(position);
            groupNameView.setText(p.getGroupName());
            return groupNameView;
        }
    }

}
