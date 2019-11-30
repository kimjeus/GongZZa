package com.dongkyoo.gongzza.post.board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.MockData;
import com.dongkyoo.gongzza.R;

import java.util.Arrays;

/**
 * 작성자: 이동규
 * 게시판 목록을 보여주는 프래그먼트
 */
public class BoardFragment extends Fragment {

    public BoardFragment() {
        // Required empty public constructor
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
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

//        MaterialCardView cardView = view.findViewById(R.id.cardView);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = 4;
//                int max = 4;
//
//                if (count == max) {
//                    new AlertDialog.Builder(getActivity())
//                            .setTitle("주의")
//                            .setMessage("인원이 다 찼습니다")
//                            .setPositiveButton("확인", null)
//                            .show();
//                }
//            }
//        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BoardRecyclerAdapter(
                Arrays.asList(MockData.getMockPostDto())
        ));
    }

}
