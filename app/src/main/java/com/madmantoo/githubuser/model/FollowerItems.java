package com.madmantoo.githubuser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FollowerItems implements Parcelable {
    private int idFollower;
    private String usernameFollower;
    private String avatarFollower;
    private String typeFollower;

    public FollowerItems() {

    }

    public int getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(int idFollower) {
        this.idFollower = idFollower;
    }

    public String getUsernameFollower() {
        return usernameFollower;
    }

    public void setUsernameFollower(String usernameFollower) {
        this.usernameFollower = usernameFollower;
    }

    public String getAvatarFollower() {
        return avatarFollower;
    }

    public void setAvatarFollower(String avatarFollower) {
        this.avatarFollower = avatarFollower;
    }

    public String getTypeFollower() {
        return typeFollower;
    }

    public void setTypeFollower(String typeFollower) {
        this.typeFollower = typeFollower;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idFollower);
        dest.writeString(usernameFollower);
        dest.writeString(avatarFollower);
        dest.writeString(typeFollower);
    }

    public FollowerItems(Parcel in) {
        idFollower = in.readInt();
        usernameFollower = in.readString();
        avatarFollower = in.readString();
        typeFollower = in.readString();
    }

    public static final Creator<FollowerItems> CREATOR = new Creator<FollowerItems>() {
        @Override
        public FollowerItems createFromParcel(Parcel in) {
            return new FollowerItems(in);
        }

        @Override
        public FollowerItems[] newArray(int size) {
            return new FollowerItems[size];
        }
    };
}
