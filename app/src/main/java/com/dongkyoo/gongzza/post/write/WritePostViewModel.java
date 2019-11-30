package com.dongkyoo.gongzza.post.write;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.Post;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritePostViewModel extends ViewModel {

    private static final String TAG = "WritePostViewModel";

    private MutableLiveData<Post> _post = new MutableLiveData<>();
    private MutableLiveData<WriteState> _writeState = new MutableLiveData<>();
    private MutableLiveData<Date> _meetDatetime = new MutableLiveData<>();

    public LiveData<Post> post = _post;
    public LiveData<WriteState> writeState = _writeState;
    public LiveData<Date> meetDatetime = _meetDatetime;

    private WritePostModel model;

    public WritePostViewModel(Context context) {
        _post.setValue(new Post());
        _writeState.setValue(new WriteState());
        _meetDatetime.setValue(new Date());

        model = new WritePostModel(context);
    }

    void setMeetDatetime(Date meetDatetime) {
        _meetDatetime.setValue(meetDatetime);
    }

    void submit(String title, String content, String numOfParticipantString, String userId,
                List<HashTag> hashTagList) {
        if (TextUtils.isEmpty(title)) {
            _writeState.setValue(new WriteState(0, "제목을 입력하세요"));
            return;
        }

        if (TextUtils.isEmpty(content)) {
            _writeState.setValue(new WriteState(0, "내용을 입력하세요"));
            return;
        }

        if (TextUtils.isEmpty(numOfParticipantString)) {
            _writeState.setValue(new WriteState(0, "참가자 수를 입력하세요"));
            return;
        }

        int numOfParticipants = Integer.parseInt(numOfParticipantString);
        if (numOfParticipants <= 1) {
            _writeState.setValue(new WriteState(0, "참가자는 2명 이상이여야 합니다"));
            return;
        }

        Date meetDatetime = this.meetDatetime.getValue();
        if (meetDatetime.getTime() < System.currentTimeMillis()) {
            _writeState.setValue(new WriteState(0, "과거를 지정할 수는 없습니다"));
            return;
        }

        PostDto p = new PostDto(new Post(new Post(userId, title, content, meetDatetime, numOfParticipants, "")));
        p.setHashTagList(hashTagList);

        model.writePost(p, new Callback<PostDto>() {
            @Override
            public void onResponse(Call<PostDto> call, Response<PostDto> response) {
                if (response.isSuccessful()) {
                    _writeState.setValue(new WriteState(200, response.body()));
                } else {
                    Log.e(TAG, "writePost 실패");
                }
            }

            @Override
            public void onFailure(Call<PostDto> call, Throwable t) {
                Log.e(TAG, "writePost 실패", t);
            }
        });
    }

    static class WriteState {
        public int state;
        public String errorMessage;
        public PostDto postDto;

        public WriteState() {
            this.errorMessage = null;
        }

        public WriteState(int state) {
            this.state = state;
            this.errorMessage = null;
        }

        public WriteState(int state, String errorMessage) {
            this.state = state;
            this.errorMessage = errorMessage;
        }

        public WriteState(int state, PostDto postDto) {
            this.state = state;
            this.postDto = postDto;
        }
    }
}
