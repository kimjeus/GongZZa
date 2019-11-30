package com.dongkyoo.gongzza.board;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.customViews.hashTagRecyclerView.HashTagRecyclerAdapter;
import com.dongkyoo.gongzza.customViews.hashTagRecyclerView.HashTagRecyclerView;
import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.PostApi;
import com.dongkyoo.gongzza.post.PostActivity;
import com.dongkyoo.gongzza.post.write.WritePostActivity;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.HashTag;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 작성자: 이동규
 * 게시판 목록을 보여주는 프래그먼트
 */
public class BoardFragment extends Fragment implements BoardRecyclerAdapter.OnClickPost {

    private static final String TAG = "BoardFragment";
    private User me;
    private BoardRecyclerAdapter adapter = new BoardRecyclerAdapter(
            Arrays.asList(),
            this
    );
    private String searchKeyword;
    private HashTagRecyclerView hashTagRecyclerView;

    private BoardFragment() {
        // Required empty public constructor
    }

    public static BoardFragment newInstance(User me) {
        BoardFragment boardFragment = new BoardFragment();
        boardFragment.me = me;
        return boardFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        hashTagRecyclerView = view.findViewById(R.id.hashTagRecyclerView);
        hashTagRecyclerView.setOnHashTagChangedListener(new HashTagRecyclerAdapter.OnHashTagChangedListener() {
            @Override
            public void onChange() {
                loadPost(searchKeyword, hashTagRecyclerView.getHashTagList());
            }
        });

        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchKeyword = query;
                loadPost(query, hashTagRecyclerView.getHashTagList());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    searchKeyword = "";
                    loadPost("", hashTagRecyclerView.getHashTagList());
                }
                return false;
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton writePostButton = view.findViewById(R.id.write_post_button);
        writePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WritePostActivity.class);
                intent.putExtra(Config.USER, me);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        loadPost("", hashTagRecyclerView.getHashTagList());
    }

    @Override
    public void onClick(PostDto postDto) {
        Intent intent = new Intent(getActivity(), PostActivity.class);
        intent.putExtra(Config.USER, me);
        intent.putExtra(Config.POST, postDto);
        startActivity(intent);
    }

    private void loadPost(String searchKeyword, List<HashTag> hashTagList) {
        List<String> hashTagStrList = new ArrayList<>();
        if (hashTagList != null) {
            for (HashTag hashTag : hashTagList) {
                hashTagStrList.add(hashTag.getTitle());
            }
        }

        PostApi postApi = Networks.retrofit.create(PostApi.class);
        Call<List<PostDto>> call = postApi.selectRecentPostDtoList(me.getSchoolId(), me.getId(), Config.POST_LIMIT, searchKeyword, hashTagStrList);
        call.enqueue(new Callback<List<PostDto>>() {
            @Override
            public void onResponse(Call<List<PostDto>> call, Response<List<PostDto>> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "Post 불러오기 성공");
                    adapter.setPostList(response.body());
                } else {
                    Log.e(TAG, "Post 불러오기 실패");
                }
            }

            @Override
            public void onFailure(Call<List<PostDto>> call, Throwable t) {
                Log.e(TAG, "Post 불러오기 실패", t);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                PostDto postDto = data.getParcelableExtra(Config.POST);
                adapter.addPost(postDto);
            }
        }
    }
}
