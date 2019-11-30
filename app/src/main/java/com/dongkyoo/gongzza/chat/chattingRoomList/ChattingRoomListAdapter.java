package com.dongkyoo.gongzza.chat.chattingRoomList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.chat.chattingRoom.ChatActivity;
import com.dongkyoo.gongzza.databinding.ItemChattingRoomBinding;
import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.User;

import java.util.List;

public class ChattingRoomListAdapter extends RecyclerView.Adapter<ChattingRoomListAdapter.ViewHolder> {

    private List<PostChatDto> postChatDtoList;
    private User me;
    private Context context;

    public ChattingRoomListAdapter(Context context, List<PostChatDto> postChatDtoList, User me) {
        this.context = context;
        this.postChatDtoList = postChatDtoList;
        this.me = me;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chatting_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPostChatDto(postChatDtoList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(Config.POST, postChatDtoList.get(position));
                intent.putExtra(Config.USER, me);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postChatDtoList.size();
    }

    public void setPostChatDtoList(List<PostChatDto> postChatDtoList) {
        this.postChatDtoList = postChatDtoList;
        notifyDataSetChanged();
    }

    public List<PostChatDto> getPostChatDtoList() {
        return postChatDtoList;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ItemChattingRoomBinding binding;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            binding = DataBindingUtil.bind(itemView);
        }

        void setPostChatDto(PostChatDto postChatDto) {
            binding.setPostChatDto(postChatDto);
        }
    }
}
