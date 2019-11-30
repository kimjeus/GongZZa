package com.dongkyoo.gongzza.chat.chattingRoomList;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dongkyoo.gongzza.cache.CacheCallback;
import com.dongkyoo.gongzza.chat.chattingRoom.ChatModel;
import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.dtos.PostChatDtos;
import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChattingRoomListViewModel extends AndroidViewModel {

    private static final String TAG = "ChattingRoomListVM";

    private ChattingRoomListModel chattingRoomListModel;
    private ChatModel chatModel;
    private MutableLiveData<List<PostChatDto>> _postChatList = new MutableLiveData<>();

    public LiveData<List<PostChatDto>> postChatList = _postChatList;
    private User me;


    /**
     * 객체를 생성하면 바로 로컬 DB에 캐시된 데이터를 로딩
     * @param application
     * @param me
     */
    public ChattingRoomListViewModel(@NonNull Application application, User me) {
        super(application);

        this.me = me;
        _postChatList.setValue(new ArrayList<>());
        chattingRoomListModel = new ChattingRoomListModel(application);
        chatModel = new ChatModel(application);
    }

    public void loadEnrolledPostList() {
        chattingRoomListModel.loadEnrolledPostList(new CacheCallback<List<PostChatDto>>() {
            @Override
            public void onReceive(List<PostChatDto> postChatDtos) {
                if (postChatDtos == null)
                    return;

                _postChatList.setValue(postChatDtos);
            }
        });
    }

    /**
     * 서버에 저장된 최신 채팅 로그 불러오기
     * @param afterDate     해당 날짜 이후의 데이터만 불러옴
     */
    void loadRemoteChatLog(Date afterDate) {
        chattingRoomListModel.loadRemoteChatLog(me.getId(), afterDate, new Callback<List<PostChatDto>>() {
            @Override
            public void onResponse(Call<List<PostChatDto>> call, Response<List<PostChatDto>> response) {
                if (response.code() == 200) {
                    if (response.body() == null)
                        return;

                    Log.i(TAG, "최신 채팅 정보 로딩 성공");
                    chattingRoomListModel.insertLocalPostChatDto(response.body());
                    List<PostChatDto> postChatDtoList = postChatList.getValue();
                    PostChatDtos.merge(response.body(), postChatDtoList);
                    _postChatList.setValue(postChatDtoList);
                } else {
                    Log.e(TAG, "최신 채팅 정보 로딩 실패");
                }
            }

            @Override
            public void onFailure(Call<List<PostChatDto>> call, Throwable t) {
                Log.e(TAG, "최신 채팅 정보 로딩 실패", t);
            }
        });
    }

    /**
     * 마지막 메세지 수신 시간을 구해주는 메소드
     * @return
     */
    Date getLastChatReceivedDatetime() {
        return chattingRoomListModel.getLastChatReceivedDatetime();
    }

    void receiveChat(ChatLog chatLog) {
        List<PostChatDto> postChatDtoList = postChatList.getValue();
        for (PostChatDto postChatDto : postChatDtoList) {
            if (postChatDto.getId() == chatLog.getPostId()) {
                postChatDto.getChatLogList().add(chatLog);
                chatModel.insertChatLog(chatLog);
                _postChatList.setValue(postChatDtoList);
                return;
            }
        }
    }
}
