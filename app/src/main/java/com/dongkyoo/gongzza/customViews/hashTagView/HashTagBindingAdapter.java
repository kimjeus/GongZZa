package com.dongkyoo.gongzza.customViews.hashTagView;

import androidx.databinding.BindingAdapter;

import com.dongkyoo.gongzza.customViews.hashTagView.HashTagView;
import com.dongkyoo.gongzza.vos.HashTag;

public class HashTagBindingAdapter {

    @BindingAdapter("hashTag")
    public static void hashTag(HashTagView hashTagView, HashTag tag) {
        hashTagView.setText(tag.getTitle());
        hashTagView.setColor(tag.getColor());
        hashTagView.setHashTag(tag);
    }
}
