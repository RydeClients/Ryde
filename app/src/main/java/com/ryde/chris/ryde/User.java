package com.ryde.chris.ryde;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chris on 1/16/2016.
 */
public class User implements Parcelable {
    private String name;
    //path to profile image
    private int profileImage;
    private List<Group> groupsIn;
    private List<User> blockedUsers;
    private String about;

    public User(String name) {
        this.name = name;
    }

    public User(Parcel parcel) {

        name = parcel.readString();
     profileImage = parcel.readInt();
    }
    public User(String name, int profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public int getProfileImage() {
        return profileImage;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(profileImage);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
