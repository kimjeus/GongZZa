package com.dongkyoo.gongzza.customViews.speechBalloonView;

import androidx.databinding.BindingAdapter;

public class SpeechBalloonBindingAdapter {

    @BindingAdapter("text")
    public static void setText(SpeechBalloonView view, String text) {
        view.setText(text);
    }
}
