package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.PostApi;
import com.dongkyoo.gongzza.network.UserApi;
import com.dongkyoo.gongzza.vos.User;

import retrofit2.Call;
import retrofit2.Callback;

public class BaseModel {

    private UserApi userApi = Networks.retrofit.create(UserApi.class);
    private PostApi postApi = Networks.retrofit.create(PostApi.class);

    public void loadUserById(String id, Callback<User> callback) {
        Call<User> call = userApi.getUserById(id);
        call.enqueue(callback);
    }

    public void loadPostById(int postId, Callback<PostDto> callback) {
        Call<PostDto> call = postApi.selectPostDtoById(postId);
        call.enqueue(callback);
    }
}
