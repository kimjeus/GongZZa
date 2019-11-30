package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.Post;

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
 * 게시글 관련 서버 통신 모듈
 */
public interface PostApi {

    /**
     * 게시글 작성
     * @param postDto   작성할 게시글
     * @return          성공 시 작성된 게시글 객체를 리턴
     */
    @POST("/gongzza/posts")
    Call<PostDto> insertPost(@Body PostDto postDto);

    @GET("/gongzza/posts/{id}")
    Call<PostDto> selectPostDtoById(@Path("id") int postId);

    /**
     * 학교에 대한 최신 글 리스트
     * @param schoolId      최신글을 받아올 학교 정보
     * @param userId        유저의 공강시간에 대한 최신 글만 받아와야 하므로 해당 유저의 아이디
     * @param limit         최신글 갯수
     * @return              최신글 리스트
     */
    @GET("/gongzza/posts/schools/{schoolId}/recent")
    Call<List<PostDto>> selectRecentPostDtoList(@Path("schoolId") int schoolId,
                                                @Query("userId") String userId,
                                                @Query("limit") int limit,
                                                @Query("searchKeyword") String searchKeyword,
                                                @Query("hashTagList")List<String> hashTagList);

    @GET("/gongzza/posts/users")
    Call<List<PostDto>> selectUserEnrolledPost(@Query("userId") String userId);

    /**
     * 게시글 수정
     * @param postId    수정할 게시글 아이디
     * @param post      수정할 게시글 정보
     * @return          성공 시 true, 실패 시 false
     */
    @PUT("/gongzza/posts/{id}")
    Call<Boolean> updatePost(@Path("id") int postId, @Body Post post);

    /**
     * 게시글 삭제
     * @param postId    삭제할 게시글 아이디
     * @return          성공 시 true, 실패 시 false
     */
    @DELETE("/gongzza/posts/{id}")
    Call<Boolean> deletePost(@Path("id") int postId);
}
