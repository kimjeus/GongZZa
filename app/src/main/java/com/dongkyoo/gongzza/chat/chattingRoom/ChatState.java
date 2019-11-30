package com.dongkyoo.gongzza.chat.chattingRoom;

import com.dongkyoo.gongzza.vos.ChatLog;

public class ChatState {

    public static final int CREATE = 0;
    public static final int MODIFY = 1;
    public static final int DELETE = 2;

    public int state;
    public int position;

    public ChatState(int state) {
        this.state = state;
    }

    public ChatState(int state, int position) {
        this.state = state;
        this.position = position;
    }


}
