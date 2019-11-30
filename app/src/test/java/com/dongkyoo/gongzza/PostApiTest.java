package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.PostApi;
import com.dongkyoo.gongzza.network.UserApi;
import com.dongkyoo.gongzza.vos.AuthMail;
import com.dongkyoo.gongzza.vos.User;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PostApiTest {

    public static PostDto insertPost() throws Exception {
        PostApi postApi = Networks.retrofit.create(PostApi.class);
        Call<PostDto> call = postApi.insertPost(MockData.getMockPostDto());
        Response<PostDto> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        return response.body();
    }

    @Test
    public void updateCourse() throws Exception {
        PostDto postDto = insertPost();
        PostApi postApi = Networks.retrofit.create(PostApi.class);

        postDto.setContent("update");
        Call<Boolean> call = postApi.updatePost(postDto.getId(), postDto);
        Response<Boolean> response = call.execute();

        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }

    @Test
    public void deleteCourse() throws Exception {
        PostDto postDto = insertPost();
        PostApi postApi = Networks.retrofit.create(PostApi.class);

        Call<Boolean> call = postApi.deletePost(postDto.getId());
        Response<Boolean> response = call.execute();

        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }

    @Test
    public void selectRecentPostDtoList() throws Exception {
        PostApi postApi = Networks.retrofit.create(PostApi.class);
        Call<List<PostDto>> call = postApi.selectRecentPostDtoList(MockData.getMockUser().getSchoolId(), MockData.getMockUser().getId(), 15);
        Response<List<PostDto>> response = call.execute();

        assertEquals(200, response.code());
        assertNotNull(response.body());
    }
}
