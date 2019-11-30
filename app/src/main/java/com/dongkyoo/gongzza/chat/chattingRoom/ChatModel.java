package com.dongkyoo.gongzza.chat.chattingRoom;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.room.Room;

import com.dongkyoo.gongzza.cache.AppDatabase;
import com.dongkyoo.gongzza.cache.CacheCallback;
import com.dongkyoo.gongzza.cache.ChatDao;
import com.dongkyoo.gongzza.network.ChatLogApi;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.ParticipantApi;
import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.Participant;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatModel {

    private static final String TAG = "ChatModel";

    private ChatLogApi chatLogApi;
    private ChatDao chatDao;
    private ParticipantApi participantApi;

    public ChatModel(Context context) {
        chatLogApi = Networks.retrofit.create(ChatLogApi.class);
        participantApi = Networks.retrofit.create(ParticipantApi.class);
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME).build();
        chatDao = db.chatDao();
    }

    public void insertChatLog(ChatLog chatLog) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                chatDao.insertChat(chatLog);
            }
        }).start();
    }

    void sendChat(ChatLog chatLog, Callback<ChatLog> callback) {
        Call<ChatLog> call = chatLogApi.sendChat(chatLog);
        call.enqueue(new Callback<ChatLog>() {
            @Override
            public void onResponse(Call<ChatLog> call, Response<ChatLog> response) {
                if (response.code() == 200) {
                    insertChatLog(response.body());
                    Log.e(TAG, "채팅 전송 성공 " + response.body());
                } else {
                    Log.e(TAG, "채팅 전송 실패");
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ChatLog> call, Throwable t) {
                Log.e(TAG, "채팅 전송 실패", t);
                callback.onFailure(call, t);
            }
        });
    }

    void loadRecentChatBeforeDatetime(int postId, Date beforeDatetime, int offset, int limit, CacheCallback<List<ChatLog>> callback) {
        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ChatLog> chatLogList = chatDao.loadRecentChatBeforeDatetime(postId, beforeDatetime, offset, limit);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onReceive(chatLogList);
                    }
                });
            }
        }).start();
    }

    void loadParticipantList(int postId, Callback<List<Participant>> callback) {
        Call<List<Participant>> call = participantApi.selectParticipantListByPostId(postId);
        call.enqueue(callback);
    }
}
