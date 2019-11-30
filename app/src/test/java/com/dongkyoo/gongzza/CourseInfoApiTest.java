package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.network.CourseApi;
import com.dongkyoo.gongzza.network.CourseInfoApi;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.CourseInfo;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CourseInfoApiTest {

    public CourseInfo insertCourseInfo() throws Exception {
        CourseDto courseDto = CourseApiTest.insertCourse();
        CourseInfoApi courseInfoApi = Networks.retrofit.create(CourseInfoApi.class);
        Call<CourseInfo> call = courseInfoApi.insertCourseInfo(courseDto.getId(), MockData.getMockCourseInfo());
        Response<CourseInfo> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        return response.body();
    }

    @Test
    public void updateCourseInfo() throws Exception {
        CourseInfo courseInfo = insertCourseInfo();
        CourseInfoApi courseInfoApi = Networks.retrofit.create(CourseInfoApi.class);

        courseInfo.setEndTime("00:00");
        Call<Boolean> call = courseInfoApi.updateCourseInfo(courseInfo.getId(), courseInfo);
        Response<Boolean> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }

    @Test
    public void deleteCourse() throws Exception {
        CourseInfo courseInfo = insertCourseInfo();
        CourseInfoApi courseInfoApi = Networks.retrofit.create(CourseInfoApi.class);

        Call<Boolean> call = courseInfoApi.deleteCourseInfo(courseInfo.getId());
        Response<Boolean> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }
}
