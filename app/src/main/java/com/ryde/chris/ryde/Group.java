package com.ryde.chris.ryde;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by chris on 1/15/2016.
 */
public class Group implements Parcelable{
    private String groupName;
    private List<User> users;
    private boolean isDiscoverable;
    private int id;

    public Group(String nameOfGroup) {
        this(nameOfGroup, null, new Random().nextInt(1000));
    }
    public Group(String nameOfGroup, List<User> usersList, int id) {
        groupName = nameOfGroup;
        users = usersList;
        this.id = id;
    }
    public Group(Parcel parcel) {
        groupName = parcel.readString();
        id = parcel.readInt();
        if (users == null) {
            users = new ArrayList<User>();
        }
        parcel.readTypedList(users, User.CREATOR);
    }
    public List<User> getUsers() {
        return users;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeInt(id);
        dest.writeTypedList(users);
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
