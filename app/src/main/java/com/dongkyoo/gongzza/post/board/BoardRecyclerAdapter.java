package com.dongkyoo.gongzza.post.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.databinding.ItemBoardBinding;
import com.dongkyoo.gongzza.dtos.PostDto;

import java.util.List;

public class BoardRecyclerAdapter extends RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 0;
    private List<PostDto> postList;

    public BoardRecyclerAdapter(@NonNull List<PostDto> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DEFAULT_VIEW_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return DEFAULT_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPost(this.postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView hashTagRecyclerView;
        private HashTagListAdapter adapter;
        private View itemView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            hashTagRecyclerView = itemView.findViewById(R.id.hash_tag_recyclerView);
            hashTagRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    RecyclerView.HORIZONTAL, false));
            hashTagRecyclerView.addItemDecoration(new ItemMargin(16));
            adapter = new HashTagListAdapter(itemView.getContext());
            hashTagRecyclerView.setAdapter(adapter);
        }

        void setPost(PostDto post) {
            ItemBoardBinding binding = DataBindingUtil.bind(itemView);
            binding.setPost(post);

            adapter.setHashTagList(post.getHashTagList());
        }
    }
}
