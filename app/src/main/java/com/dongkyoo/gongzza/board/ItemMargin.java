package com.dongkyoo.gongzza.board;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemMargin extends RecyclerView.ItemDecoration {

    private final int marginRight;

    public ItemMargin(int marginRight) {
        this.marginRight = marginRight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.right = marginRight;
    }
}
