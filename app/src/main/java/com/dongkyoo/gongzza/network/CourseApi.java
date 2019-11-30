package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.dtos.CourseDto;
import com.dongkyoo.gongzza.vos.AuthMail;
import com.dongkyoo.gongzza.vos.Course;
import com.dongkyoo.gongzza.vos.User;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 작성자 : 이동규
 * 시간표 관련 서버 통신 모듈
 */
public interface CourseApi {

    /**
     * 시간표에 수업 추가
     * @param courseDto 수업 데이터
     * @return 수업 추가 성공 시 추가된 수업 객체가 리턴
     */
    @POST("/courses")
    Call<CourseDto> insertCourse(@Body CourseDto courseDto);

    /**
     * 수업 수정
     * @param courseId  수정할 수업의 id
     * @param course    수정될 데이터
     * @return          성공 시 true, 실패 시 false
     */
    @PUT("/courses/{id}")
    Call<Boolean> updateCourse(@Path("id") int courseId, @Body Course course);

    /**
     * 수업 삭제
     * @param courseId  삭제할 수업의 id
     * @return          성공 시 true, 실패 시  false
     */
    @DELETE("/courses/{id}")
    Call<Boolean> deleteCourse(@Path("id") int courseId);

    /**
     * 유저의 모든 수업 데이터를 불러옴
     * @param userId    유저 아이디
     * @return          성공 시 유저가 등록한 모든 시간표 데이터 리스트를 리턴
     */
    @GET("/courses")
    Call<List<CourseDto>> selectCourseDtoListByUserId(@Query("userId") String userId);

}
