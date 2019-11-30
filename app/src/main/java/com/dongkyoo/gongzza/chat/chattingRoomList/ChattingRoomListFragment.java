package com.dongkyoo.gongzza.chat.chattingRoomList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 작성자: 이동규
 * 채팅방 리스트를 보여주는 프래그먼트
 */
public class ChattingRoomListFragment extends Fragment {

    private static final String TAG = "ChattingRoomListFrag";
    private User me;
    private ChattingRoomListAdapter adapter;
    private ChattingRoomListViewModel viewModel;

    private ChattingRoomListFragment() {
        // Required empty public constructor
    }

    public static ChattingRoomListFragment newInstance(User me) {
        ChattingRoomListFragment chattingRoomListFragment = new ChattingRoomListFragment();
        chattingRoomListFragment.me = me;
        return chattingRoomListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatting_room_list, container, false);

        initView(view);

        viewModel = new ChattingRoomListViewModel(getActivity().getApplication(), me);

        viewModel.postChatList.observe(this, new Observer<List<PostChatDto>>() {
            @Override
            public void onChanged(List<PostChatDto> newPostChatDtos) {
                Log.i(TAG, "채팅데이터 로딩");
                adapter.setPostChatDtoList(newPostChatDtos);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.loadEnrolledPostList();
        viewModel.loadRemoteChatLog(viewModel.getLastChatReceivedDatetime());
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.chatting_room_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ChattingRoomListAdapter(getActivity(), new ArrayList<>(), me);
        recyclerView.setAdapter(adapter);
    }

    public void receiveChat(ChatLog chatLog) {
        if (viewModel != null) {
            viewModel.receiveChat(chatLog);
        }
    }
}
