package com.agus.submission3.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FavUser implements Parcelable {
    private int id;
    private String username;
    private String name;
    private String avatar_url;
    private String company;
    private int followers;
    private int following;

    public FavUser(int id, String username, String name, String avatar_url, String company, int followers, int following) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.avatar_url = avatar_url;
        this.company = company;
        this.followers = followers;
        this.following = following;
    }

    protected FavUser(Parcel in) {
        id = in.readInt();
        username = in.readString();
        name = in.readString();
        avatar_url = in.readString();
        company = in.readString();
        followers = in.readInt();
        following = in.readInt();
    }

    public static final Creator<FavUser> CREATOR = new Creator<FavUser>() {
        @Override
        public FavUser createFromParcel(Parcel in) {
            return new FavUser(in);
        }

        @Override
        public FavUser[] newArray(int size) {
            return new FavUser[size];
        }
    };

    public FavUser() {

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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeString(avatar_url);
        parcel.writeString(company);
        parcel.writeInt(followers);
        parcel.writeInt(following);
    }
}
