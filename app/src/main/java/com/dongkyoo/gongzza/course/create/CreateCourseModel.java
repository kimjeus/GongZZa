package com.dongkyoo.gongzza.course.create;

import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.network.CourseApi;
import com.dongkyoo.gongzza.network.Networks;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateCourseModel {

    private CourseApi courseApi;

    public CreateCourseModel() {
        courseApi = Networks.retrofit.create(CourseApi.class);
    }

    void createCourse(CourseDto courseDto, Callback<CourseDto> callback) {
        Call<CourseDto> call = courseApi.insertCourse(courseDto);
        call.enqueue(callback);
    }
}
