package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.vos.Participant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 작성자 : 이동규
 * 게시글 참여자 관련 서버 통신 모듈
 */
public interface ParticipantApi {

    @POST("/gongzza/participants")
    Call<Participant> insertParticipant(@Body Participant participant);

    @DELETE("/gongzza/participants/posts/{postId}")
    Call<Boolean> deleteParticipant(@Path("postId") int postId, @Query("id") String userId);

    @GET("/gongzza/participants/posts/{postId}")
    Call<List<Participant>> selectParticipantListByPostId(@Path("postId") int postId);
}
