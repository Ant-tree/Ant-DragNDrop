package com.anttree.dragndrop.dragndrop;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hyunseok on 2018-03-21.
 * <p>
 * RecyclerView 의 Item 간 가로 간격을 정의합니다.
 */

@SuppressWarnings("WeakerAccess")
public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private final int divWidth;

    public RecyclerViewDecoration(int divWidth) {
        this.divWidth = divWidth;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect
            , @NonNull View view
            , @NonNull RecyclerView parent
            , @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = divWidth/2;
        outRect.right = divWidth/2;
    }
}
