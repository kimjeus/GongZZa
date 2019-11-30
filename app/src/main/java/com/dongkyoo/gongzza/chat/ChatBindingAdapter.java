package com.dongkyoo.gongzza.chat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.dongkyoo.gongzza.dtos.SendingChatDto;
import com.dongkyoo.gongzza.vos.ChatLog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBindingAdapter {

    @BindingAdapter("chat_sentAt")
    public static void sentAt(TextView textView, ChatLog chatLog) {
        if (chatLog == null) {
            textView.setText("");
            return;
        }

        long sentAt = chatLog.getSentAt().getTime();
        long now = new Date().getTime();
        long diff = now - sentAt;

        if (diff < 1000 * 60) {
            textView.setText("방금 전");
        } else if (diff < 1000 * 60 * 60) {
            textView.setText((diff / (1000 * 60)) + "분 전");
        } else if (diff < 1000 * 60 * 60 * 24) {
            textView.setText((diff / (1000 * 60 * 24)) + "시간 전");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM월 dd일");
            textView.setText(format.format(chatLog.getSentAt()));
        }
    }

    @BindingAdapter("chat_absoluteSentAt")
    public static void absoluteSentAt(TextView textView, ChatLog chatLog) {
        if (chatLog == null)
            return;

        long sentAt = chatLog.getSentAt().getTime();
        long now = new Date().getTime();
        long diff = now - sentAt;

        if (diff < 1000 * 60 * 60 * 24) {
            SimpleDateFormat format = new SimpleDateFormat("HH: mm");
            textView.setText(format.format(chatLog.getSentAt()));
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM: dd");
            textView.setText(format.format(chatLog.getSentAt()));
        }
    }

    @BindingAdapter("chat_isError")
    public static void isError(ViewGroup viewGroup, ChatLog chatLog) {
        if (chatLog instanceof SendingChatDto) {
            SendingChatDto c = (SendingChatDto) chatLog;
            if (c.getErrorMessage() != null)
                viewGroup.setVisibility(View.VISIBLE);
            else
                viewGroup.setVisibility(View.GONE);
            return;
        }
        viewGroup.setVisibility(View.GONE);
    }
}
