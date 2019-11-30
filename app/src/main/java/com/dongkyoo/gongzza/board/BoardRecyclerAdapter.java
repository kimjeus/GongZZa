package com.dongkyoo.gongzza.board;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.customViews.hashTagRecyclerView.HashTagRecyclerAdapter;
import com.dongkyoo.gongzza.customViews.hashTagRecyclerView.HashTagRecyclerView;
import com.dongkyoo.gongzza.databinding.ItemBoardBinding;
import com.dongkyoo.gongzza.dtos.PostDto;

import java.util.List;

public class BoardRecyclerAdapter extends RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE = 0;
    private List<PostDto> postList;
    private OnClickPost onClickPostListener;

    public void setPostList(List<PostDto> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    public void addPost(PostDto postDto) {
        postList.add(0, postDto);
        notifyDataSetChanged();
    }

    interface OnClickPost {
        void onClick(PostDto postDto);
    }

    public BoardRecyclerAdapter(@NonNull List<PostDto> postList, OnClickPost onClickPostListener) {
        this.postList = postList;
        this.onClickPostListener = onClickPostListener;
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

        private View itemView;
        private PostDto postDto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (postDto != null) {
                        onClickPostListener.onClick(postDto);
                    }
                }
            });
        }

        void setPost(PostDto post) {
            this.postDto = post;

            ItemBoardBinding binding = DataBindingUtil.bind(itemView);
            binding.setPost(post);

            binding.hashTagRecyclerView.setHashTagList(post.getHashTagList());
            binding.hashTagRecyclerView.setAppendable(false);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    notifyDataSetChanged();
//                }
//            }, 0);
        }
    }
}
