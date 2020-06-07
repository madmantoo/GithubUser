package com.madmantoo.githubuser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FollowingItems implements Parcelable {
    private int idFollowing;
    private String usernameFollowing;
    private String avatarFollowing;
    private String typeFollowing;

    public FollowingItems() {

    }

    public int getIdFollowing() {
        return idFollowing;
    }

    public void setIdFollowing(int idFollowing) {
        this.idFollowing = idFollowing;
    }

    public String getUsernameFollowing() {
        return usernameFollowing;
    }

    public void setUsernameFollowing(String usernameFollowing) {
        this.usernameFollowing = usernameFollowing;
    }

    public String getAvatarFollowing() {
        return avatarFollowing;
    }

    public void setAvatarFollowing(String avatarFollowing) {
        this.avatarFollowing = avatarFollowing;
    }

    public String getTypeFollowing() {
        return typeFollowing;
    }

    public void setTypeFollowing(String typeFollowing) {
        this.typeFollowing = typeFollowing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idFollowing);
        dest.writeString(usernameFollowing);
        dest.writeString(avatarFollowing);
        dest.writeString(typeFollowing);
    }

    public FollowingItems(Parcel in) {
        idFollowing = in.readInt();
        usernameFollowing = in.readString();
        avatarFollowing = in.readString();
        typeFollowing = in.readString();
    }

    public static final Creator<FollowingItems> CREATOR = new Creator<FollowingItems>() {
        @Override
        public FollowingItems createFromParcel(Parcel in) {
            return new FollowingItems(in);
        }

        @Override
        public FollowingItems[] newArray(int size) {
            return new FollowingItems[size];
        }
    };

}
