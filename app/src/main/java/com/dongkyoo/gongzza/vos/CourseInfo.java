package com.dongkyoo.gongzza.vos;

import java.util.Objects;

/**
 * 작성자 : 이동규
 * 수업의 상세 정보를 담기위한 객체
 *
 * You must see also {@link Course}
 */
public class CourseInfo {

    private int id;
    private int courseId;
    private String startTime;
    private String endTime;
    private int day;

    public CourseInfo() {
    }

    public CourseInfo(int id, int courseId, String startTime, String endTime, int day) {
        this.id = id;
        this.courseId = courseId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}
