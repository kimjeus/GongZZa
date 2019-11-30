package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.UserApi;
import com.dongkyoo.gongzza.vos.AuthMail;
import com.dongkyoo.gongzza.vos.User;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserApiUnitTest {

    @Test
    public void sendAuthenticationEmailTest() throws Exception {
        UserApi userApi = Networks.retrofit.create(UserApi.class);
        Call<AuthMail> call = userApi.sendAuthenticateEmail("qwerty6", "dkenl135@naver.com");
        Response<AuthMail> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }

    private AuthMail sendAuthenticationEmail() throws Exception {
        UserApi userApi = Networks.retrofit.create(UserApi.class);
        Call<AuthMail> call = userApi.sendAuthenticateEmail(MockData.getMockUser().getId(), "wind.dong.dream@gmail.com");
        Response<AuthMail> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        return response.body();
    }

    @Test
    public void authMail() throws Exception {
        AuthMail authMail = sendAuthenticationEmail();
        UserApi userApi = Networks.retrofit.create(UserApi.class);
        Call<Boolean> call = userApi.authMail(authMail.getCode(), authMail.getUserId(), authMail.getEmail());
        Response<Boolean> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }
  
    @Test
    public void signUpTest() throws Exception {
        UserApi userApi = Networks.retrofit.create(UserApi.class);
        User user = MockData.getMockUser();
        user.setId("qwerty6");
        user.setEmail("dkenl135@naver.com");
        Call<User> call = userApi.signUp(user);
        Response<User> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }

    /**
     * 로그인 성공
     * @throws Exception
     */
    @Test
    public void getUserByIdPwTest() throws Exception {
        UserApi userApi = Networks.retrofit.create(UserApi.class);
        Call<User> call = userApi.login(MockData.getMockUser().getId(), "testPassword");
        Response<User> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }

    /**
     * 로그인 실패
     * @throws Exception
     */
    @Test
    public void getUserByIdPwFailure() throws Exception {
        UserApi userApi = Networks.retrofit.create(UserApi.class);
        Call<User> call = userApi.login(MockData.getMockUser().getId(), "testPasswordasdfasdf");
        Response<User> response = call.execute();
        assertEquals(500, response.code());
    }
}
