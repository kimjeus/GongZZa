package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.vos.CourseInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 작성자 : 이동규
 * {@link com.dongkyoo.gongzza.vos.CourseInfo} 관련 서버 통신 모듈
 */
public interface CourseInfoApi {

    /**
     * 수업 상세 정보를 새로 추가함
     * @param courseId      상세 정보를 추가할 수업 아이디
     * @param courseInfo    추가할 상세 정보
     * @return              성공 시 추가된 상세 정보가 담긴 객체를 리턴
     */
    @POST("/courseInfos/courses/{courseId}")
    Call<CourseInfo> insertCourseInfo(@Path("courseId") int courseId, @Body CourseInfo courseInfo);

    /**
     * 수업 상세정보 수정
     * @param courseInfoId  수정할 상세정보 아이디
     * @param courseInfo    수정할 상세정보
     * @return              성공 시 true, 실패 시 false
     */
    @PUT("/courseInfos/{id}")
    Call<Boolean> updateCourseInfo(@Path("id") int courseInfoId, @Body CourseInfo courseInfo);

    /**
     * 수업 상세정보 삭제
     * @param courseInfoId  삭제 할 상세정보 아이디
     * @return              성공 시 true, 실패 시 false
     */
    @DELETE("/courseInfos/{id}")
    Call<Boolean> deleteCourseInfo(@Path("id") int courseInfoId);
}
