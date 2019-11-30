package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {

    private String id;
    private String name;
    private String password;
    private Date birthday;
    private Date signedInAt;
    private int schoolId;
    private String email;

    public User() {
    }

    public User(String id, String name, String password, Date birthday, int schoolId, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.schoolId = schoolId;
        this.email = email;
    }

    public User(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.password = parcel.readString();
        this.birthday = (Date) parcel.readSerializable();
        this.signedInAt = (Date) parcel.readSerializable();
        this.schoolId = parcel.readInt();
        this.email = parcel.readString();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getSignedInAt() {
        return signedInAt;
    }

    public void setSignedInAt(Date signedInAt) {
        this.signedInAt = signedInAt;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeSerializable(birthday);
        dest.writeSerializable(signedInAt);
        dest.writeInt(schoolId);
        dest.writeString(email);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}