package com.anttree.dragndrop.dragndrop;

import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hyunseok on 2018-03-20.
 * <p>
 * ItemTouchHelperInterface
 * Interface
 */
public interface ItemTouchHelperInterface {
    void onItemMove(RecyclerView.ViewHolder viewHolder, int fromPosition, int toPosition);
    void onItemMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos,
                     RecyclerView.ViewHolder target, int toPos, int x, int y);
    void onItemDismiss(int position);
    void onTouchMovement(MotionEvent touch_event, int directionFlags, int pointerIndex);
    void onStateChanged(RecyclerView.ViewHolder viewHolder, int actionState);
}
