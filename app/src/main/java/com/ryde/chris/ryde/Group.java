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
public class Group implements Parcelable{
    private String groupName;
    private List<User> users;
    private boolean isDiscoverable;
    private int id;
    private Map<Date, User> schedule;

    public Group(String nameOfGroup) {
        this(nameOfGroup, null, new Random().nextInt(1000));
    }
    public Group(String nameOfGroup, List<User> usersList, int id) {
        this(nameOfGroup, usersList, id, new HashMap<Date, User>());
    }
    public Group(String nameOfGroup, List<User> usersList, int id, Map<Date, User> theSchedule) {
        groupName = nameOfGroup;
        users = usersList;
        this.id = id;
        schedule = theSchedule;
    }
    public Group(Parcel parcel) {
        groupName = parcel.readString();
        id = parcel.readInt();
        if (users == null) {
            users = new ArrayList<User>();
        }
        schedule = new HashMap<Date, User>();
        parcel.readTypedList(users, User.CREATOR);
        int scheduleSize = parcel.readInt();
        for(int i=0; i<scheduleSize; i++) {
            Date date = new Date(parcel.readLong());
            User user = parcel.readParcelable(User.class.getClassLoader());
            schedule.put(date, user);
        }
    }
    public List<User> getUsers() {
        return users;
    }
    public Map<Date, User> getSchedule() {
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
    public void setSchedule(Map<Date, User> schedule) {
        this.schedule = schedule;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeInt(id);
        dest.writeTypedList(users);
        dest.writeInt(schedule.size());
        for(Map.Entry<Date, User> entry: schedule.entrySet()) {
            dest.writeLong(entry.getKey().getTime());
            dest.writeParcelable(entry.getValue(), 0);
        }
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
