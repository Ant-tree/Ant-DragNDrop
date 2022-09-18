package com.anttree.dragndrop.dragndrop;

import android.content.Context;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anttree.dragndrop.R;
import com.anttree.dragndrop.dragndrop.helper.ItemTouchHelper;
import com.anttree.dragndrop.model.Card;

import java.util.ArrayList;


/**
 * Created by Hyunseok on 2018-03-20.
 * <p>
 * Drag N Drop RecyclerView 를 초기화하기 위한 클래스 입니다.
 */
public abstract class DragNDropRecyclerView {
    //private static final String TAG = "DragNDropRecyclerView";
    private Context context;

    protected DragNDropRecyclerView(Context context) {
        this.context = context;
    }

    /**
     * Drag N Drop Recycler View 를 초기화 합니다.
     * 초기화된 RecyclerView 는 가로 스크롤이 지원되며, ItemTouchHelper 를 RecyclerView 에 적용시키기 위한
     * 동작을 수행합니다.
     *
     * @param recyclerView 초기화 대상이 되는 recyclerView 입니다.
     * @param cardList     데이터로 사용되는 CardList 입니다.
     */
    public void initView(RecyclerView recyclerView, ArrayList<Card> cardList) {
        ItemTouchRecyclerAdapter adapter = new ItemTouchRecyclerAdapter(context, cardList, R.layout.item_drag_n_drop_recycler);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(30));

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        adapter.setonListChangeListener(mListChangeListener);
    }

    /**
     * ItemTouchRecyclerAdapter 에서 List 가 변경될 때마다 리스터를 통해 가져오지만,
     * 개발자의 편의를 위해 매번 리스트가 갱신될 때마다 Abstract 메서드를 통해 데이터가 변경되었음을 알려줍니다.
     * 해당 리스터와 Abstract 메서드가 아래 두가지 입니다.
     */
    private ItemTouchRecyclerAdapter.onListChangeListener mListChangeListener
            = new ItemTouchRecyclerAdapter.onListChangeListener() {

        @Override
        public void onListChanged(ArrayList<Card> changeCardList) {
            onDataChanged(changeCardList);
        }

        @Override
        public void onDrop(float x, float y) {
            onDragEnded(x, y);
        }
        
        @Override
        public void onEnter(boolean flag){ onCardEnter(flag); }

        @Override
        public void onSelected(String name) {
            onCardSelected(name);
        }

        @Override
        public void onItemDeleted(Card card) {
            onDeleted(card);
        }
    };

    public abstract void onDataChanged(ArrayList<Card> result);
    public abstract void onDragEnded(float x, float y);
    public abstract void onDeleted(Card card);
    public abstract void onCardEnter(boolean flag);

    /**
     * card LongClick 시 selected 로 전달
     * */
    public abstract void onCardSelected(String name);

}
