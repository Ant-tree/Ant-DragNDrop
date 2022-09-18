package com.anttree.dragndrop.dragndrop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anttree.dragndrop.R;
import com.anttree.dragndrop.dragndrop.helper.ItemTouchHelper;
import com.anttree.dragndrop.model.Card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Hyunseok on 2018-03-20.
 * <p>
 * Item 에 대해 Drag N Drop 을 수행할 수 있는 RecyclerView Adapter 입니다.
 * ItemTouchHelperInterface 를 implement 하고 있으며, 삭제 버튼이 있는 레이아웃 입니다.
 * 롱클릭 후 아이템을 재배열할 수 있으며, 롱 클릭 전에 아이템을 위 아래로 스와이프하여 삭제할 수 있습니다.
 */
@SuppressWarnings("WeakerAccess")
public class ItemTouchRecyclerAdapter
        extends RecyclerView.Adapter<ItemTouchRecyclerAdapter.ViewHolder>
        implements ItemTouchHelperInterface {

    private ArrayList<Card> cardList;
    private static final String TAG = "ITRecyclerAdapter";
    private final int itemLayout;
    private final Context context;
    private onListChangeListener mListener = null;
    private int LONG_CLICKED_ITEM;

    /**
     * List 가 변경되었을 때마다 Listener 를 통해 변경된 List 를 반환합니다.
     */
    public interface onListChangeListener {
        void onListChanged(ArrayList<Card> cardList);

        void onDrop(float x, float y);

        void onSelected(String name);

        void onEnter(boolean flag);

        void onItemDeleted(Card card);
    }

    /**
     * Listener 를 부착합니다.
     */
    public void setonListChangeListener(onListChangeListener iListener) {
        mListener = iListener;
    }

    public ItemTouchRecyclerAdapter(Context context, ArrayList<Card> cardList, int itemLayout) {
        this.context = context;
        this.cardList = cardList;
        this.itemLayout = itemLayout;
        setonListChangeListener(mListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * ViewHolder 를 Binding 합니다.
     * childSize 를 호출하여 동적으로 Item 의 크기를 결정합니다.
     * Long Click 시 애니메이션의 정의와
     * btnDelete 를 클릭하였을 때 기능을 정의합니다.
     */
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setChildSize(holder);

        holder.card.setImageDrawable(cardList.get(position).getImage());

        /*
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transform(new CenterInside())
                .transform(new RotateTransformation(context, 90));

        Glide.with(context).load(cardList.init(position).getImageUrl())
                .apply(options)
                .into(holder.card);*/

        //holder.card.setImageDrawable(cardCodeList.init(position).getCardImage());
        holder.itemView.setTag(position);

        holder.btnDelete.setOnClickListener(view -> Log.d(TAG, "User clicked delete button"));
    }

    /**
     * 아이템, 즉 카드의 크기를 동적으로 결정합니다.
     * 카드의 개수에 따라 모든 카드의 배열이 화면 밖으로 벗어나지 않도록 합니다.
     *
     * @param holder holder
     */
    private void setChildSize(ViewHolder holder) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        /*
         * 4개 기준으로 Max 픽스 해놨음`
         * */
        if(cardList.size() > 3) {
            layoutParams.width = (int) (width / 2.5);
        } else {
            layoutParams.width = width / (cardList.size() + 1);
        }
        holder.itemView.requestLayout();
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    /**
     * Item 이 drag 될 때 반투명한 shadow 를 설정합니다.
     * 이 메서드 호출 시 기능이 정상적으로 동작하지 않습니다.
     *
     * @param isShadow shadow 를 설정할 것인지 결정합니다.
     *                 false 시 shadow 를 해제합니다.
     * @param view     shadow 가 적용될 view 입니다.
     */
    @SuppressWarnings("unused")
    private void setShadow(boolean isShadow, View view) {
        if (isShadow) {
            ClipData.Item item = new ClipData.Item(view.getTag().toString());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(),
                    mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);

            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Item 이 이동될 때마다 호출됩니다.
     * 매 drag 시 호출되며, 마지막에 결과값만 받는 것이 아니라 이동되는 시점에 계속 호출됩니다.
     *
     * @param viewHolder   viewHolder
     * @param fromPosition 이동이 시작된 위치
     * @param toPosition   이동이 끝난 위치
     */
    @Override
    public void onItemMove(RecyclerView.ViewHolder viewHolder, int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(cardList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(cardList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        mListener.onListChanged(cardList);
    }

    @Override
    public void onItemMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos,
                            RecyclerView.ViewHolder target, int toPos, int x, int y) {
        Log.d(TAG, "Moved to position x : " + x + ", y : " + y);
    }

    /**
     * Item 이 Swipe 되었을 때 호출됩니다.
     *
     * @param position Swipe 된 Item 의 포지션
     */
    @Override
    public void onItemDismiss(int position) {
        onItemDelete(position);
    }

    float pos_x = 0;
    float pos_y = 0;

    @Override
    public void onTouchMovement(MotionEvent touch_event, int directionFlags, int pointerIndex) {
        final float x = touch_event.getX();
        final float y = touch_event.getY();

        pos_x = x;
        pos_y = y;

        Log.d(TAG, "onMovedCoordinate to position x : " + x + ", y : " + y);
        Log.d(TAG, "onMovedCoordinate " + touch_event.getAction());
        if (y > 1000) {
            mListener.onEnter(true);
        } else {
            mListener.onEnter(false);
        }
    }

    @Override
    public void onStateChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        mListener.onDrop(pos_x, pos_y);
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            if (pos_y > 1000) {
                onConfirmDelete(LONG_CLICKED_ITEM);
            }
            pos_x = 0;
            pos_y = 0;
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            LONG_CLICKED_ITEM = viewHolder.getAdapterPosition();
            mListener.onSelected(cardList.get(LONG_CLICKED_ITEM).getName());
        }
    }

    private void onConfirmDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Delete??")
                .setPositiveButton("OK", (dialog, id) -> onItemDelete(position))
                .setNegativeButton("Cancel", (dialog, id) -> { });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Item 을 List 에서 삭제합니다.
     *
     * @param position 삭제할 Item 의 position
     */
    private void onItemDelete(int position) {
        Card card = cardList.get(position);
        cardList.remove(position);
        notifyItemRemoved(position);
        mListener.onListChanged(cardList);
        mListener.onItemDeleted(card);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView card;
        LinearLayout cardBase;
        Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            card = itemView.findViewById(R.id.card);
            cardBase = itemView.findViewById(R.id.cardBase);

            btnDelete.setVisibility(View.GONE);
        }
    }
}