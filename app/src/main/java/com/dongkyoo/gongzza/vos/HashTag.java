package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class HashTag implements Parcelable {

    private int id;
    private int postId;
    private String color;
    private String title;

    public HashTag() {
    }

    public HashTag(Parcel parcel) {
        id = parcel.readInt();
        postId = parcel.readInt();
        color = parcel.readString();
        title = parcel.readString();
    }

    public HashTag(String color, String title) {
        this.color = color;
        this.title = title;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTag)) return false;
        HashTag hashTag = (HashTag) o;
        return id == hashTag.id &&
                postId == hashTag.postId &&
                Objects.equals(color, hashTag.color) &&
                Objects.equals(title, hashTag.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, title, id, postId);
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "color='" + color + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(postId);
        dest.writeString(color);
        dest.writeString(title);
    }

    public static final Creator<HashTag> CREATOR = new Creator<HashTag>() {
        @Override
        public HashTag createFromParcel(Parcel source) {
            return new HashTag(source);
        }

        @Override
        public HashTag[] newArray(int size) {
            return new HashTag[size];
        }
    };
}
