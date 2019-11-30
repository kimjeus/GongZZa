package com.dongkyoo.gongzza.course;

import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.network.CourseApi;
import com.dongkyoo.gongzza.network.Networks;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CourseModel {

    private CourseApi courseApi;

    public CourseModel() {
        courseApi = Networks.retrofit.create(CourseApi.class);
    }

    void createCourse(CourseDto courseDto, Callback<CourseDto> callback) {
        Call<CourseDto> call = courseApi.insertCourse(courseDto);
        call.enqueue(callback);
    }

    void loadAllCourseList(String userId, Callback<List<CourseDto>> callback) {
        Call<List<CourseDto>> call = courseApi.selectCourseDtoListByUserId(userId);
        call.enqueue(callback);
    }

    void deleteCourse(int courseId, Callback<Boolean> callback) {
        Call<Boolean> call = courseApi.deleteCourse(courseId);
        call.enqueue(callback);
    }
}
