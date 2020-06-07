package com.madmantoo.githubuserconsumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserItems implements Parcelable {
private int id;
private String username;
private String name;
private String location;
private String followers;
private String following;
private String imgPhoto;
private String type;

public UserItems() {

}

public UserItems(int id, String username, String name, String type, String avatar, String location) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.type = type;
    this.imgPhoto = avatar;
    this.location = location;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public String getFollowers() {
    return followers;
}

public void setFollowers(String followers) {
    this.followers = followers;
}

public String getFollowing() {
    return following;
}

public void setFollowing(String following) {
    this.following = following;
}

public String getImgPhoto() {
    return imgPhoto;
}

public void setImgPhoto(String imgPhoto) {
    this.imgPhoto = imgPhoto;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

@Override
public int describeContents() {
    return 0;
}

@Override
public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeString(username);
    dest.writeString(name);
    dest.writeString(location);
    dest.writeString(followers);
    dest.writeString(following);
    dest.writeString(imgPhoto);
    dest.writeString(type);
}

public UserItems(Parcel in) {
    id = in.readInt();
    username = in.readString();
    name = in.readString();
    location = in.readString();
    followers = in.readString();
    following = in.readString();
    imgPhoto = in.readString();
    type = in.readString();
}

public static final Creator<UserItems> CREATOR = new Creator<UserItems>() {
    @Override
    public UserItems createFromParcel(Parcel in) {
        return new UserItems(in);
    }

    @Override
    public UserItems[] newArray(int size) {
        return new UserItems[size];
    }
};
}
