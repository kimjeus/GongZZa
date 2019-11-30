package com.dongkyoo.gongzza.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.CourseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 작성자 : 이동규
 * CourseDto 클래스는 {@link Course} 에 {@link CourseInfo} 리스트가 추가된 형태
 *
 * Course : 객체지향 - 최지웅
 * CourseInfo 1 : 수요일 1시 30분 ~ 2시 45분
 * CourseInfo 2 : 금요일 1시 30분 ~ 2시 45분
 * 이런 데이터를 한 번에 표현하기 위함임
 * {@link com.dongkyoo.gongzza.network.CourseApi} 에서 파라미터가 Course인지 CourseDto 인지 잘 보면서 작업하길 바람
 */
public class CourseDto extends Course implements Parcelable {

    private List<CourseInfo> courseInfoList;

    public CourseDto() {
    }

    public CourseDto(int id, String userId, String name, String professor, List<CourseInfo> courseInfoList) {
        super(id, userId, name, professor);
        this.courseInfoList = courseInfoList;
    }

    public CourseDto(Parcel parcel) {
        super(parcel);

        courseInfoList = new ArrayList<>();
        parcel.readTypedList(courseInfoList, CourseInfo.CREATOR);
    }

    public List<CourseInfo> getCourseInfoList() {
        return courseInfoList;
    }

    public void setCourseInfoList(List<CourseInfo> courseInfoList) {
        this.courseInfoList = courseInfoList;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseInfoList=" + courseInfoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseDto)) return false;
        if (!super.equals(o)) return false;
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(courseInfoList, courseDto.courseInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), courseInfoList);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeTypedList(courseInfoList);
    }

    public static Creator<CourseDto> CREATOR = new Creator<CourseDto>() {
        @Override
        public CourseDto createFromParcel(Parcel source) {
            return new CourseDto(source);
        }

        @Override
        public CourseDto[] newArray(int size) {
            return new CourseDto[size];
        }
    };
}
