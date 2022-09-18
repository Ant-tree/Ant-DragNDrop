package com.anttree.dragndrop.dragndrop;

import android.util.Log;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.anttree.dragndrop.dragndrop.helper.ItemTouchHelper;

/**
 * Created by Hyunseok on 2018-03-20.
 * <p>
 * ItemTouchHelper 에 대한 콜백 함수입니다.
 */
@SuppressWarnings("WeakerAccess")
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private static final String TAG = "ITHelperCallback";

    private final ItemTouchHelperInterface mAdapter;

    public ItemTouchHelperCallback(ItemTouchHelperInterface adapter) {
        mAdapter = adapter;
    }

    /**
     * LongPressDrag 를 Enable 시킬지 결정합니다.
     *
     * @return false 시 disable
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * Swipe 를 Enable 시킬지 결정합니다.
     *
     * @return false 시 disable
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * Item 의 드래그 혹은 스와이프의 방향을 결정합니다.
     * dragFlag 에 정의된 방향대로 drag 가 가능하며
     * swipeFlag 에 정의된 방향대로 swipe 가 가능합니다.
     *
     * @param recyclerView recyclerView
     * @param viewHolder   viewHolder
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * Item 이 이동되었을 때 adapter 를 통해 어느 위치로 어떤 것이 움직였는지 전달합니다.
     *
     * @param recyclerView recyclerView
     * @param viewHolder   이동된 source 입니다.
     * @param target       이동된 위치 입니다.
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        Log.d(TAG, "onItemMove");
        mAdapter.onItemMove(viewHolder, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos,
                        RecyclerView.ViewHolder target, int toPos, int x, int y) {
        mAdapter.onItemMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public void onTouchMovement(MotionEvent touch_event, int directionFlags, int pointerIndex) {
        mAdapter.onTouchMovement(touch_event, directionFlags, pointerIndex);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        mAdapter.onStateChanged(viewHolder, actionState);
    }

    /**
     * Item 이 Swipe 되었을 때 adapter 를 통해 어느 방향으로 swipe 되었는지 전달합니다.
     *
     * @param viewHolder 이동된 Item
     * @param direction  swipe 된 방향
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped");
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
