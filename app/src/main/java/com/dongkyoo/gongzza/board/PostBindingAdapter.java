package com.dongkyoo.gongzza.board;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.vos.Post;

import java.text.SimpleDateFormat;

public class PostBindingAdapter {
    @BindingAdapter("post_startAt")
    public static void startAt(TextView textView, Post post) {
        SimpleDateFormat format = new SimpleDateFormat("MM월 dd일 HH시 mm분");
        textView.setText(format.format(post.getMeetDateTime()));
    }

    @BindingAdapter("post_num_of_participants")
    public static void numOfParticipants(TextView textView, PostDto postDto) {
        textView.setText(postDto.getParticipantList().size() + "명 / " + postDto.getTotalNumParticipants() + "명");
    }
}
