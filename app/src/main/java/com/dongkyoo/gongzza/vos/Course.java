package com.dongkyoo.gongzza.vos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * 작성자 : 이동규
 * 시간표 내의 수업 하나를 의미함
 * {@link CourseInfo} 보다 상위 개념의 수업임
 *
 * see also {@link com.dongkyoo.gongzza.dtos.CourseDto}
 *
 * Course : 객체지향 - 최지웅
 *  * CourseInfo 1 : 수요일 1시 30분 ~ 2시 45분
 *  * CourseInfo 2 : 금요일 1시 30분 ~ 2시 45분
 */
public class Course implements Parcelable {

    private int id;
    private String userId;
    private String name;
    private String professor;

    public Course() {
    }

    public Course(int id, String userId, String name, String professor) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.professor = professor;
    }

    public Course(Parcel parcel) {
        id = parcel.readInt();
        userId = parcel.readString();
        name = parcel.readString();
        professor = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", professor='" + professor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id == course.id &&
                Objects.equals(userId, course.userId) &&
                Objects.equals(name, course.name) &&
                Objects.equals(professor, course.professor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, professor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(professor);
    }

    public static Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
