package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.vos.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 작성자 : 이동규
 * 푸시 토큰 관련 서버 통신 모듈
 */
public interface TokenApi {


    @POST("/gongzza/tokens")
    Call<Token> registerToken(@Body Token token);

}
