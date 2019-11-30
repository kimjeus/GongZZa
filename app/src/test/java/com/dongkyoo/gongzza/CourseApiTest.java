package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.network.CourseApi;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.vos.Course;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CourseApiTest {

    public static CourseDto insertCourse() throws Exception {
        CourseApi courseApi = Networks.retrofit.create(CourseApi.class);
        Call<CourseDto> call = courseApi.insertCourse(MockData.getMockCourseDto());
        Response<CourseDto> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        return response.body();
    }

    @Test
    public void updateCourse() throws Exception {
        CourseDto courseDto = insertCourse();
        CourseApi courseApi = Networks.retrofit.create(CourseApi.class);

        Course course = MockData.getMockCourse();
        course.setName("update@");
        course.setProfessor("update!");
        Call<Boolean> call = courseApi.updateCourse(courseDto.getId(), course);
        Response<Boolean> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }

    @Test
    public void deleteCourse() throws Exception {
        CourseDto courseDto = insertCourse();

        CourseApi courseApi = Networks.retrofit.create(CourseApi.class);
        Call<Boolean> call = courseApi.deleteCourse(courseDto.getId());
        Response<Boolean> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }

    @Test
    public void selectCourseListByUserId() throws Exception {
        CourseApi courseApi = Networks.retrofit.create(CourseApi.class);
        Call<List<CourseDto>> call = courseApi.selectCourseDtoListByUserId(MockData.getMockUser().getId());
        Response<List<CourseDto>> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }
}
