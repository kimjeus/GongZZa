package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.vos.ChatLog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 작성자 : 이동규
 * 채팅 관련 서버 통신 모듈
 */
public interface ChatLogApi {

    @GET("/gongzza/chats/posts/{postId}")
    Call<List<ChatLog>> selectChatLogByPostAfterDatetime(@Path("postId") int postId, @Query("datetime") String datetime);

    @GET("/gongzza/chats/users")
    Call<List<PostChatDto>> selectPostChatListByUserAfterDatetime(@Query("userId") String userId,
                                                                  @Query("datetime") String datetime);

    @POST("/gongzza/chats")
    Call<ChatLog> sendChat(@Body ChatLog chatLog);
}
