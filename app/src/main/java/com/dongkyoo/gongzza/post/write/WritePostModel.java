package com.dongkyoo.gongzza.post.write;

import android.content.Context;

import androidx.room.Room;

import com.dongkyoo.gongzza.cache.AppDatabase;
import com.dongkyoo.gongzza.cache.PostDao;
import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.PostApi;
import com.dongkyoo.gongzza.vos.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritePostModel {

    private PostApi postApi;
    private PostDao postDao;

    public WritePostModel(Context context) {
        postApi = Networks.retrofit.create(PostApi.class);
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
        postDao = db.postDao();
    }

    public void writePost(PostDto post, Callback<PostDto> callback) {
        Call<PostDto> call = postApi.insertPost(post);
        call.enqueue(new Callback<PostDto>() {
            @Override
            public void onResponse(Call<PostDto> call, Response<PostDto> response) {
                if (response.isSuccessful()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            postDao.enrollPost(response.body());
                        }
                    }).start();
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<PostDto> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
}
