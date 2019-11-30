package com.dongkyoo.gongzza.network;

import com.dongkyoo.gongzza.vos.AuthMail;
import com.dongkyoo.gongzza.vos.User;

import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    /**
     * 로그인 요청
     * @param id 유저 아이디
     * @return 로그인 성공 시 해당 유저의 정보가 담긴 객체를 리턴
     *      로그인 실패 시 500 에러
     */
    @GET("/gongzza/users")
    Call<User> getUserById(@Query("id") String id);

    /**
     * 로그인 요청
     * @param id 유저 아이디
     * @param password 유저 비밀번호
     * @return 로그인 성공 시 해당 유저의 정보가 담긴 객체를 리턴
     *      로그인 실패 시 500 에러
     */
    @GET("/gongzza/users/login")
    Call<User> login(@Query("id") String id, @Query("password") String password);

    /**
     * 회원가입 요청
     * @param user 유저 회원 정보가 담긴 User 객체
     * @return 회원가입 성공 시 가입된 User 정보가 담긴 객체 리턴
     */
    @POST("/gongzza/users")
    Call<User> signUp(@Body User user);
  
    /**
     * 아이디 찾기
     * @param name      회원가입 시 사용했던 유저 이름
     * @param birthday  회원가입 시 사용했던 유저 생일
     * @return          이름과 생일이 일치하는 유저가 있다면 해당 User id 리턴
     *                  없다면 null 리턴
     */
    @GET("/gongzza/users/find/id")
    Call<String> findId(@Query("name") String name, @Query("birthday") Date birthday);

    /**
     * 비밀번호 찾기 권한 획득
     * @param id            유저 아이디
     * @param name          유저 이름
     * @param birthday      유저 생일
     * @return              세 정보 모두 일치하는 유저가 있을 시 200 리턴
     *                      일치하는 유저 없으면 0 리턴
     */
    @GET("/gongzza/users/find/pw")
    Call<Integer> getFindPasswordAuthority(@Query("id") String id, @Query("name") String name,
                                           @Query("birthday") Date birthday);

    /**
     * 비밀번호 재설정
     * @param id            재설정할 id
     * @param password      id의 비밀번호를 재설정
     * @param authorityCode 200이면 재설정 가능
     *                      이외의 수는 불가능
     * @return              비밀번호 변경 성공 시 200
     *                      실패 시 0
     */
    @PUT("/gongzza/users/find/password")
    Call<Integer> findPassword(@Query("id") String id, @Query("password") String password,
                               @Query("authorityCode") int authorityCode);

    /**
     * 인증 메일을 보냄
     *
     * @param userId    인증할 아이디
     * @param email     인증할 메일
     * @return AuthMail 이 null 이 아닌 경우만 인증메일 전송에 성공
     */
    @POST("/gongzza/authMails")
    Call<AuthMail> sendAuthenticateEmail(@Query("userId") String userId, @Query("email") String email);

    // For 테스트
    @GET("/gongzza/authMails/code/{code}")
    Call<Boolean> authMail(@Path("code") String code, @Query("userId") String userId, @Query("email") String email);
}
