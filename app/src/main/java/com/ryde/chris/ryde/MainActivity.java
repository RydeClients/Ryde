package com.ryde.chris.ryde;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpGroupsList();

    }

    private void setUpGroupsList() {
        ListView groupsList = (ListView)findViewById(R.id.grouplist);
        String[] mockGroupNames = {"DayCare-4 person","Soccer Practice-3 people", "Computer Science Study-2 people",
                 "School-3 people", "Work-5 people"};
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mockGroupNames);
        groupsList.setAdapter(groupAdapter);
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
}
