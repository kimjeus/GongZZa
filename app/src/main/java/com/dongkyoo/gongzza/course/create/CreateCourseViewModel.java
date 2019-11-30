package com.dongkyoo.gongzza.course.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dongkyoo.gongzza.course.CourseState;
import com.dongkyoo.gongzza.dtos.CourseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCourseViewModel extends ViewModel {

    private MutableLiveData<CourseState> _state = new MutableLiveData<>();

    public LiveData<CourseState> state = _state;

    private CreateCourseModel createCourseModel;


    public CreateCourseViewModel() {
        _state.setValue(new CourseState());
        createCourseModel = new CreateCourseModel();
    }

    void createCourse(CourseDto courseDto) {
        createCourseModel.createCourse(courseDto, new Callback<CourseDto>() {
            @Override
            public void onResponse(Call<CourseDto> call, Response<CourseDto> response) {
                if (response.isSuccessful()) {
                    _state.setValue(new CourseState(200, response.body()));
                } else {
                    _state.setValue(new CourseState(0, "수업 생성 실패"));
                }
            }

            @Override
            public void onFailure(Call<CourseDto> call, Throwable t) {
                _state.setValue(new CourseState(0, "수업 생성 실패"));
            }
        });
    }
}
