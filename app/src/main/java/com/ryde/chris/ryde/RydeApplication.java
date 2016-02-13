package com.ryde.chris.ryde;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by chris on 1/20/2016.
 */
public class RydeApplication extends Application {
    //groups that the current user is in
    private List<Group> userGroups;
    public List<Group> getCurrUserGroup() {
        return userGroups;
    }
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
        initializeCurrUserGroups();
    }
    private void initializeCurrUserGroups() {
        userGroups = new ArrayList<Group>();
        String[] mockGroupNames = {"DayCare","Soccer Practice", "Computer Science Study", "School", "Work"};
        User[] stubUsers = { new User("Chris Sun", R.drawable.chris), new User("Barack Obama",R.drawable.barack_obama),
                new User("Ariana Grande",R.drawable.ariana), new User("Shaheen Sharifian", R.drawable.shariwizard),
                new User("Fion Chan", R.drawable.fion)};
        List<User> usersList = new ArrayList<User>(Arrays.asList(stubUsers));
        for(String name:mockGroupNames) {
            List<User> testUsers = new ArrayList<>();
            testUsers.addAll(usersList);
            int trySize = new Random().nextInt(5) + 1;
            for(int k=0;k<usersList.size() - trySize; k++) {
                testUsers.remove(new Random().nextInt(testUsers.size()));
            }
            Group toAdd = new Group(name);
            toAdd.setUsersList(testUsers);
            toAdd.setSchedule(generateSchedule(toAdd));
            userGroups.add(toAdd);
        }
    }
    private Map<Date, DriveInfo> generateSchedule(Group generateScheduleFor) {
        Map<Date, DriveInfo> drivingSchedule = new HashMap<Date, DriveInfo>();
        Calendar cal = Calendar.getInstance();
        int[] datesToTest = {11, 12, 17, 22, 26};
        for(int k=0; k<datesToTest.length; k++) {
            cal.set(2016, 1, datesToTest[k]);
            List<User> groupMembers = generateScheduleFor.getUsers();
            DriveInfo driveInfo = new DriveInfo(groupMembers.get((int) (Math.random() * groupMembers.size())),
                    8, 37, true, 5, 14, false);
            drivingSchedule.put(cal.getTime(), driveInfo);
        }
        generateScheduleFor.setSchedule(drivingSchedule);
        return drivingSchedule;
    }

}
