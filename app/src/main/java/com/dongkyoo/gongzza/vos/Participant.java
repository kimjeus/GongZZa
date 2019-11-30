package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Participant implements Parcelable {

    private int postId;
    private User user;

    public Participant() {
    }

    public Participant(Parcel parcel) {
        postId = parcel.readInt();
        user = parcel.readParcelable(User.class.getClassLoader());
    }

    public Participant(int postId, User user) {
        this.postId = postId;
        this.user = user;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Participant) {
            Participant p = (Participant) obj;
            return p.postId == postId && p.user.getId().equals(user.getId());
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(postId);
        dest.writeParcelable(user, flags);
    }

    public static final Creator<Participant> CREATOR = new Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel source) {
            return new Participant(source);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };
}
