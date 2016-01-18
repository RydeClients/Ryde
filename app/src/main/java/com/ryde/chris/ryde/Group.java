package com.ryde.chris.ryde;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chris on 1/15/2016.
 */
public class Group implements Parcelable{
    private String groupName;
    private List<User> users;
    private boolean isDiscoverable;

    public Group(String nameOfGroup) {
        this(nameOfGroup, null);
    }
    public Group(String nameOfGroup, List<User> usersList) {
        groupName = nameOfGroup;
        users = usersList;
    }
    public Group(Parcel parcel) {
        groupName = parcel.readString();
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
