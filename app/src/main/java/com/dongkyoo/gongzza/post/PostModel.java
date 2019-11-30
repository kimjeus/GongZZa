package com.dongkyoo.gongzza.post;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.dongkyoo.gongzza.cache.AppDatabase;
import com.dongkyoo.gongzza.cache.CacheCallback;
import com.dongkyoo.gongzza.cache.PostDao;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.ParticipantApi;
import com.dongkyoo.gongzza.vos.CacheState;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.Post;
import com.dongkyoo.gongzza.vos.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostModel {

    private ParticipantApi participantApi;
    private PostDao postDao;

    public PostModel(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
        postDao = db.postDao();
        participantApi = Networks.retrofit.create(ParticipantApi.class);
    }

    void enroll(Post post, User user, Callback<Participant> callback) {
        Call<Participant> call = participantApi.insertParticipant(new Participant(post.getId(), user));
        call.enqueue(new Callback<Participant>() {
            @Override
            public void onResponse(Call<Participant> call, Response<Participant> response) {
                if (response.code() == 200) {
                    enrollLocally(post);
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Participant> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    void enrollLocally(Post post) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postDao.enrollPost(post);
            }
        }).start();
    }

    void leaveLocally(Post post) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postDao.leavePost(post);
            }
        }).start();
    }

    void leave(Post post, String userId, Callback<Boolean> callback) {
        Call<Boolean> call = participantApi.deleteParticipant(post.getId(), userId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    leaveLocally(post);
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void selectPostById(int postId, CacheCallback<Post> callback) {
        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Post post = postDao.selectPostById(postId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onReceive(post);
                    }
                });
            }
        }).start();
    }
}
