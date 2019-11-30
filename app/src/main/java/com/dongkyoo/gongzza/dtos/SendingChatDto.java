package com.dongkyoo.gongzza.dtos;

import androidx.annotation.Nullable;

import com.dongkyoo.gongzza.vos.ChatLog;

public class SendingChatDto extends ChatLog {

    private String errorMessage;

    public SendingChatDto() {
    }

    public SendingChatDto(ChatLog chatLog) {
        super(chatLog);
    }

    public SendingChatDto(ChatLog chatLog, String errorMessage) {
        super(chatLog);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof ChatLog) {
            ChatLog c = (ChatLog) obj;
            if (c.getId() == getId())
                return true;

            return getContent().equals(c.getContent()) &&
                    getPostId() == c.getPostId() &&
                    getSenderId().equals(c.getSenderId()) &&
                    getSentAt().getTime() == c.getSentAt().getTime();
        }
        return false;
    }
}
