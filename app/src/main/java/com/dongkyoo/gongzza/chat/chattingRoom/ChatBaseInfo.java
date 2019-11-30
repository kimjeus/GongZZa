package com.dongkyoo.gongzza.chat.chattingRoom;

import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.vos.User;

public class ChatBaseInfo {

    public Integer state;
    public User me;
    public PostChatDto postChatDto;

    public ChatBaseInfo(Integer state) {
        this.state = state;
    }

    public ChatBaseInfo(Integer state, User me, PostChatDto postChatDto) {
        this.state = state;
        this.me = me;
        this.postChatDto = postChatDto;
    }
}
