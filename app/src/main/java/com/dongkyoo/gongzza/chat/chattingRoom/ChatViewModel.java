package com.dongkyoo.gongzza.chat.chattingRoom;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dongkyoo.gongzza.BaseModel;
import com.dongkyoo.gongzza.cache.CacheCallback;
import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.dtos.SendingChatDto;
import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.User;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends ViewModel {

    private static final String TAG = "ChatViewModel";

    private MutableLiveData<ChatState> _chatState = new MutableLiveData<>();
    private MutableLiveData<ChatBaseInfo> _baseInfoState = new MutableLiveData<>();
    private MutableLiveData<List<Participant>> _participantList = new MutableLiveData<>();

    public LiveData<ChatState> chatState = _chatState;
    public LiveData<ChatBaseInfo> baseInfoState = _baseInfoState;
    public LiveData<List<Participant>> participantList = _participantList;

    private ChatModel chatModel;
    private BaseModel baseModel;
    private User me;
    private PostChatDto postChatDto;

    public ChatViewModel(Context context, PostChatDto postChatDto, User me) {
        this.me = me;
        this.postChatDto = postChatDto;

        chatModel = new ChatModel(context);
        baseModel = new BaseModel();
        loadParticipantList(postChatDto.getId());
    }

    public ChatViewModel(Context context, int postId, String userId) {
        chatModel = new ChatModel(context);

        baseModel.loadUserById(userId, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    me = response.body();

                    baseModel.loadPostById(postId, new Callback<PostDto>() {
                        @Override
                        public void onResponse(Call<PostDto> call, Response<PostDto> response) {
                            if (response.isSuccessful()) {
                                postChatDto = new PostChatDto(response.body());
                                chatModel.loadRecentChatBeforeDatetime(
                                        postChatDto.getId(),
                                        new Date(),
                                        0,
                                        15, new CacheCallback<List<ChatLog>>() {
                                            @Override
                                            public void onReceive(List<ChatLog> chatLogList) {
                                                if (chatLogList != null)
                                                    postChatDto.getChatLogList().addAll(chatLogList);
                                                _baseInfoState.setValue(new ChatBaseInfo(200, me, postChatDto));
                                            }
                                        });
                            } else {
                                _baseInfoState.setValue(new ChatBaseInfo(0));
                            }
                        }

                        @Override
                        public void onFailure(Call<PostDto> call, Throwable t) {
                            _baseInfoState.setValue(new ChatBaseInfo(0));
                        }
                    });
                } else {
                    _baseInfoState.setValue(new ChatBaseInfo(0));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                _baseInfoState.setValue(new ChatBaseInfo(0));
            }
        });
    }

    public void receiveChat(int chatId, String senderId, String content) {
        ChatLog chatLog = new ChatLog(chatId, postChatDto.getId(), senderId, content, new Date(System.currentTimeMillis()));
        chatModel.insertChatLog(chatLog);
        postChatDto.getChatLogList().add(chatLog);
        _chatState.setValue(new ChatState(ChatState.CREATE));
    }

    public void sendChat(String content) {
        if (postChatDto == null)
            return;

        ChatLog chatLog = new ChatLog(0, postChatDto.getId(), me.getId(), content, new Date(System.currentTimeMillis()));
        SendingChatDto sendingChatDto = new SendingChatDto(chatLog);

        postChatDto.getChatLogList().add(sendingChatDto);
        _chatState.setValue(new ChatState(ChatState.CREATE));

        chatModel.sendChat(chatLog, new Callback<ChatLog>() {
            @Override
            public void onResponse(Call<ChatLog> call, Response<ChatLog> response) {
                if (response.isSuccessful()) {
                    int index = postChatDto.getChatLogList().indexOf(sendingChatDto);
                    postChatDto.getChatLogList().set(index, chatLog);
                    _chatState.setValue(new ChatState(ChatState.MODIFY));
                } else {
                    int index = postChatDto.getChatLogList().indexOf(sendingChatDto);
                    sendingChatDto.setErrorMessage("실패");
                    postChatDto.getChatLogList().set(index, sendingChatDto);
                    _chatState.setValue(new ChatState(ChatState.MODIFY, index));
                }
            }

            @Override
            public void onFailure(Call<ChatLog> call, Throwable t) {
                int index = postChatDto.getChatLogList().indexOf(sendingChatDto);
                sendingChatDto.setErrorMessage("실패");
                postChatDto.getChatLogList().set(index, sendingChatDto);
                _chatState.setValue(new ChatState(ChatState.MODIFY, index));
            }
        });
    }

    public void loadParticipantList(int postId) {
        chatModel.loadParticipantList(postId, new Callback<List<Participant>>() {
            @Override
            public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                if (response.isSuccessful()) {
                    _participantList.setValue(response.body());
                } else {
                    Log.e(TAG, "참여자 목록 로딩 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Participant>> call, Throwable t) {
                Log.e(TAG, "참여자 목록 로딩 실패", t);
            }
        });
    }
}
