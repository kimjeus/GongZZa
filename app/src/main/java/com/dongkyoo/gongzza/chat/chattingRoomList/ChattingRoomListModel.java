package com.dongkyoo.gongzza.chat.chattingRoomList;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.room.Room;

import com.dongkyoo.gongzza.Utils;
import com.dongkyoo.gongzza.cache.AppDatabase;
import com.dongkyoo.gongzza.cache.CacheCallback;
import com.dongkyoo.gongzza.cache.ChatDao;
import com.dongkyoo.gongzza.cache.PostDao;
import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.network.ChatLogApi;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ChattingRoomListModel {

    private static final String TAG = "ChattingRoomListModel";
    private ChatLogApi chatLogApi;
    private ChatDao chatDao;
    private PostDao postDao;
    private boolean isLoadEnrolledPostListRunning;
    private boolean isLoadChatLogListRunning;

    public ChattingRoomListModel(Context context) {
        chatLogApi = Networks.retrofit.create(ChatLogApi.class);
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries().build();
        chatDao = db.chatDao();
        postDao = db.postDao();
    }

    void loadEnrolledPostList(CacheCallback<List<PostChatDto>> callback) {
        if (isLoadEnrolledPostListRunning)
            return;

        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoadEnrolledPostListRunning = true;
                List<PostChatDto> postChatDtoList = new ArrayList<>();
                List<Post> postList = postDao.selectEnrolledPostList();
                for (Post post : postList) {
                    PostChatDto postChatDto = new PostChatDto(post);
                    List<ChatLog> chatLogList = chatDao.loadRecentChatBeforeDatetime(
                            postChatDto.getId(),
                            new Date(),
                            0,
                            15);
                    if (chatLogList != null)
                        postChatDto.getChatLogList().addAll(chatLogList);
                    postChatDtoList.add(postChatDto);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onReceive(postChatDtoList);
                    }
                });
                isLoadEnrolledPostListRunning = false;
            }
        }).start();
    }

    void insertLocalPostChatDto(List<PostChatDto> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (PostChatDto postChatDto : list) {
                    try {
                        postDao.enrollPost(postChatDto);
                    } catch (SQLiteConstraintException e) {
                    }
                    for (ChatLog chatLog : postChatDto.getChatLogList()) {
                        try {
                            chatDao.insertChat(chatLog);
                        } catch (Exception e) {
                        }
                    }
                }
                Log.i(TAG, "PostChatDto 로컬 캐시 완료");
            }
        }).start();
    }

    /**
     * DB에 저장된 채팅 로그 불러오기
     * @param postId        불러올 채팅 그룹(post) 아이디
     * @param afterDate     해당 날짜 이후의 데이터만 불러옴
     * @param offset        offset
     * @param limit         갯수
     */
    void loadLocalChatLog(int postId, Date afterDate, int offset, int limit, CacheCallback<List<ChatLog>> callback) {
        if (isLoadChatLogListRunning)
            return;

        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoadChatLogListRunning = true;
                List<ChatLog> chatLogList = chatDao.loadRecentChatBeforeDatetime(postId, afterDate, offset, limit);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onReceive(chatLogList);
                    }
                });
                isLoadChatLogListRunning = false;
            }
        }).start();
    }


    /**
     * 서버에 저장된 최신 채팅 로그 불러오기
     * @param userId        유저가 받아야할 최신 채팅 정보
     * @param afterDate     해당 날짜 이후의 데이터만 불러옴
     * @param callback      콜백 메소드
     */
    void loadRemoteChatLog(String userId, Date afterDate, Callback<List<PostChatDto>> callback) {
        Call<List<PostChatDto>> call = chatLogApi.selectPostChatListByUserAfterDatetime(userId, Utils.convertDateToString(afterDate));
        call.enqueue(callback);
    }

    Date getLastChatReceivedDatetime() {
        ChatLog chatLog = chatDao.loadLatestReceivedChatLog();
        if (chatLog == null)
            return new Date();
        return chatLog.getSentAt();
    }
}
