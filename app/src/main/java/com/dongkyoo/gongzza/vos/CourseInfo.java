package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

/**
 * 작성자 : 이동규
 * 수업의 상세 정보를 담기위한 객체
 *
 * You must see also {@link Course}
 */
public class CourseInfo implements Parcelable {

    private int id;
    private int courseId;
    private Date startTime;
    private Date endTime;
    private int day;

    public CourseInfo() {
        startTime = new Date();
        endTime = new Date();
    }

    public CourseInfo(int id, int courseId, Date startTime, Date endTime, int day) {
        this.id = id;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public CourseInfo(Parcel parcel) {
        id = parcel.readInt();
        courseId = parcel.readInt();
        startTime = (Date) parcel.readSerializable();
        endTime = (Date) parcel.readSerializable();
        day = parcel.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", day=" + day +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseInfo)) return false;
        CourseInfo that = (CourseInfo) o;
        return id == that.id &&
                courseId == that.courseId &&
                day == that.day &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, startTime, endTime, day);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(courseId);
        dest.writeSerializable(startTime);
        dest.writeSerializable(endTime);
        dest.writeInt(day);
    }

    public static Creator<CourseInfo> CREATOR = new Creator<CourseInfo>() {
        @Override
        public CourseInfo createFromParcel(Parcel source) {
            return new CourseInfo(source);
        }

        @Override
        public CourseInfo[] newArray(int size) {
            return new CourseInfo[size];
        }
    };
}
