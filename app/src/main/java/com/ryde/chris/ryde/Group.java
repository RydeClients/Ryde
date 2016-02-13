package com.ryde.chris.ryde;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by chris on 1/15/2016.
 */
public class Group {
    private String groupName;
    private List<User> users;
    private boolean isDiscoverable;
    private int id;
    private Map<Date, DriveInfo> schedule;

    public Group(String nameOfGroup) {
        this(nameOfGroup, null, new Random().nextInt(1000));
    }
    public Group(String nameOfGroup, List<User> usersList, int id) {
        this(nameOfGroup, usersList, id, new HashMap<Date, DriveInfo>());
    }
    public Group(String nameOfGroup, List<User> usersList, int id, Map<Date, DriveInfo> theSchedule) {
        groupName = nameOfGroup;
        users = usersList;
        this.id = id;
        schedule = theSchedule;
    }
    public List<User> getUsers() {
        return users;
    }
    public Map<Date, DriveInfo> getSchedule() {
        return schedule;
    }
    public String getGroupName() {
        return groupName;
    }
    public int getId() {
        return id;
    }

    public void setUsersList(List<User> users) {
        this.users = users;
    }
    public void setSchedule(Map<Date, DriveInfo> schedule) {
        this.schedule = schedule;
    }
}
