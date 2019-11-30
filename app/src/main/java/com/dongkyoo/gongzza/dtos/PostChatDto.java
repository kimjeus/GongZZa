package com.dongkyoo.gongzza.dtos;

import android.os.Parcel;
import android.os.Parcelable;

import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.Post;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PostChatDto extends PostDto implements Parcelable, Comparable<PostChatDto> {

    private List<ChatLog> chatLogList;

    public PostChatDto() {
    }

    public PostChatDto(List<ChatLog> chatLogList, PostDto postDto) {
        super(postDto);
        this.chatLogList = chatLogList;
    }

    public PostChatDto(Post post) {
        super(post);
        chatLogList = new LinkedList<>();
    }

    public PostChatDto(Parcel parcel) {
        super(parcel);
        chatLogList = new LinkedList<>();
        parcel.readTypedList(chatLogList, ChatLog.CREATOR);
    }

    public List<ChatLog> getChatLogList() {
        return chatLogList;
    }

    public ChatLog getLatestChatLog() {
        if (chatLogList == null || chatLogList.size() == 0)
            return null;
        return chatLogList.get(chatLogList.size() - 1);
    }

    public void sortChatLogList() {
        Collections.sort(chatLogList);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(chatLogList);
    }

    public static final Creator<PostChatDto> CREATOR = new Creator<PostChatDto>() {
        @Override
        public PostChatDto createFromParcel(Parcel source) {
            return new PostChatDto(source);
        }

        @Override
        public PostChatDto[] newArray(int size) {
            return new PostChatDto[size];
        }
    };

    @Override
    public int compareTo(PostChatDto o) {
        if (getLatestChatLog() == null) {
            if (o.getLatestChatLog() == null)
                return -Integer.MAX_VALUE;
            return (int) -o.getLatestChatLog().getSentAt().getTime();
        }
        if (o.getLatestChatLog() == null)
            return (int) getLatestChatLog().getSentAt().getTime();
        return (int) (o.getLatestChatLog().getSentAt().getTime() - getLatestChatLog().getSentAt().getTime());
    }
}
