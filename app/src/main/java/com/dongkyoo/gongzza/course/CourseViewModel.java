package com.dongkyoo.gongzza.course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dongkyoo.gongzza.dtos.CourseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseViewModel extends ViewModel {

    private MutableLiveData<CourseState> _state = new MutableLiveData<>();
    private MutableLiveData<List<CourseDto>> _courseList = new MutableLiveData<>();

    public LiveData<CourseState> state = _state;
    public LiveData<List<CourseDto>> courseList = _courseList;

    private CourseModel courseModel;


    public CourseViewModel() {
        _state.setValue(new CourseState());
        courseModel = new CourseModel();
    }

    void insertCourse(CourseDto courseDto) {
        List<CourseDto> courseDtoList = courseList.getValue();
        courseDtoList.add(courseDto);
        _courseList.setValue(courseDtoList);
    }

    void deleteCourse(int courseId) {
        courseModel.deleteCourse(courseId, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    List<CourseDto> courseDtoList = courseList.getValue();
                    for (int i = 0; i < courseDtoList.size(); i++) {
                        if (courseDtoList.get(i).getId() == courseId) {
                            courseDtoList.remove(i);
                            break;
                        }
                    }
                    _courseList.setValue(courseDtoList);
                } else {
                    _state.setValue(new CourseState(0, "수업 삭제 실패"));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                _state.setValue(new CourseState(0, "수업 삭제 실패"));
            }
        });
    }

    void loadCourseList(String userId) {
        courseModel.loadAllCourseList(userId, new Callback<List<CourseDto>>() {
            @Override
            public void onResponse(Call<List<CourseDto>> call, Response<List<CourseDto>> response) {
                if (response.isSuccessful()) {
                    _courseList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CourseDto>> call, Throwable t) {

            }
        });
    }
}
