package com.dongkyoo.gongzza.customViews.speechBalloonView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dongkyoo.gongzza.R;

/**
 * 작성자 : 이동규
 * 말풍선 뷰
 */
public class SpeechBalloonView extends RelativeLayout {

    private static final int TYPE_ME = 0;
    private static final int TYPE_YOU = 1;

    private Context context;
    private TextView contentTextView;

    public SpeechBalloonView(@NonNull Context context) {
        super(context);
        this.context = context;

        init();
    }

    public SpeechBalloonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init(attrs);
    }

    public SpeechBalloonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        init(attrs);
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_speech_balloon_me, this, true);

        setupView();
    }

    private void init(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpeechBalloonView);
        int type = typedArray.getInt(R.styleable.SpeechBalloonView_type, 0);
        switch (type) {
            case TYPE_ME:
                inflater.inflate(R.layout.view_speech_balloon_me, this, true);
                break;

            case TYPE_YOU:
                inflater.inflate(R.layout.view_speech_balloon_you, this, true);
                break;

            default:
                return;
        }

        setupView();

        String text = typedArray.getString(R.styleable.SpeechBalloonView_text);
        contentTextView.setText(text);
    }

    private void setupView() {
        contentTextView = findViewById(R.id.speech_balloon_content_textView);
    }

    public void setText(String text) {
        contentTextView.setText(text);
    }
}
