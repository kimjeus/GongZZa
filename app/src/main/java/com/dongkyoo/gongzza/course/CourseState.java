package com.dongkyoo.gongzza.course;

import com.dongkyoo.gongzza.dtos.CourseDto;

public class CourseState {

    public int state;
    public String errorMessage;
    public CourseDto courseDto;

    public CourseState() {
    }

    public CourseState(int state) {
        this.state = state;
        this.errorMessage = null;
    }

    public CourseState(int state, String errorMessage) {
        this.state = state;
        this.errorMessage = errorMessage;
    }

    public CourseState(int state, CourseDto courseDto) {
        this.state = state;
        this.courseDto = courseDto;
    }
}
