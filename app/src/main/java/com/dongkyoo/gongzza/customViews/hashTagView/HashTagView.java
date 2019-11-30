package com.dongkyoo.gongzza.customViews.hashTagView;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.vos.HashTag;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * 작성자 : 이동규
 * 해쉬태그를 보여주는 뷰
 */
public class HashTagView extends MaterialCardView {

    private Context context;
    private AppCompatAutoCompleteTextView autoCompleteTextView;
    private TextView textView;
    private ImageView cancelImageView;
    private HashTag hashTag;
    private List<OnCloseListener> closeListenerList = new ArrayList<>();
    private List<OnEditModeChangeListener> editModeChangeListenerList = new ArrayList<>();


    public HashTagView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HashTagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HashTagView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_hash_tag, this, true);

        setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        setRadius(context.getResources().getDimension(R.dimen.margin3));

        autoCompleteTextView = findViewById(R.id.view_hash_tag_autoCompleteTextView);
        textView = findViewById(R.id.view_hash_tag_textView);
        cancelImageView = findViewById(R.id.view_hash_tag_cancel_imageView);

        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setEditMode(false);
                }
                return false;
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    setEditMode(false);
                }

                if (hashTag != null) {
                    hashTag.setTitle(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancelImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        setEditMode(true);
    }

    public void setHashTag(HashTag hashTag) {
        this.hashTag = hashTag;

        autoCompleteTextView.setText(hashTag.getTitle());
        textView.setText(hashTag.getTitle());
    }

    public void setText(String text) {
        autoCompleteTextView.setText(text);
        textView.setText(text);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setColor(String color) {
        if (color != null)
            setCardBackgroundColor(Color.parseColor(color));
    }

    /**
     * 수정모드 변경
     * @param flag true: 수정 가능, false: 수정 불가
     */
    public void setEditMode(boolean flag) {
        for (OnEditModeChangeListener listener : editModeChangeListenerList) {
            listener.onChange(flag);
        }

        if (flag) {
            setVisibility(VISIBLE);
            autoCompleteTextView.setVisibility(VISIBLE);
            textView.setVisibility(GONE);
            cancelImageView.setVisibility(VISIBLE);
        } else {
            if (autoCompleteTextView.getText().toString().trim().length() == 0) {
                close();
                return;
            }

            for (OnEditModeChangeListener listener : editModeChangeListenerList) {
                listener.onPinned();
            }
            textView.setText(autoCompleteTextView.getText());

            autoCompleteTextView.setVisibility(GONE);
            textView.setVisibility(VISIBLE);
            cancelImageView.setVisibility(GONE);
        }
    }

    private void close() {
        for (OnCloseListener onCloseListener : closeListenerList) {
            onCloseListener.onClose();
        }
        closeListenerList = new ArrayList<>();
        editModeChangeListenerList = new ArrayList<>();
        setVisibility(GONE);
    }

    /**
     * 해시태그 작성 시 내용에 들어 갈 수 있는 목록을 설정하는 메소드
     * @param contentList 자동완성 목록에 뜰 컨텐츠 리스트
     */
    public void setContentList(List<String> contentList) {
        HashSearchAdapter adapter = new HashSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, contentList);
        autoCompleteTextView.setAdapter(adapter);
    }

    public void setCloseListenerList(OnCloseListener onCloseListener) {
        closeListenerList.add(onCloseListener);
    }

    public void setOnEditModeChangeListener(OnEditModeChangeListener onEditModeChangeListener) {
        editModeChangeListenerList.add(onEditModeChangeListener);
    }

    public interface OnCloseListener {
        void onClose();
    }

    public interface OnEditModeChangeListener {
        void onChange(boolean editMode);
        void onPinned();
    }
}
