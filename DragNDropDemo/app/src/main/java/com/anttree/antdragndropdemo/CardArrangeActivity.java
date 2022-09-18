package com.anttree.antdragndropdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.anttree.dragndrop.dragndrop.DragNDropRecyclerView;
import com.anttree.dragndrop.model.Card;

import java.util.ArrayList;

public class CardArrangeActivity extends AppCompatActivity {

    private ArrayList<Card> tmpResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_arrange);

        initView();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private ArrayList<Card> getCardList() {
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(new Card(0, "Card1", getResources().getDrawable(R.drawable.card1)));
        cardList.add(new Card(0, "Card2", getResources().getDrawable(R.drawable.card2)));
        cardList.add(new Card(0, "Card3", getResources().getDrawable(R.drawable.card3)));
        cardList.add(new Card(0, "Card4", getResources().getDrawable(R.drawable.card4)));

        return cardList;
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        tmpResult = getCardList();
        new DragNDropRecyclerView(CardArrangeActivity.this) {
            @Override
            public void onDataChanged(ArrayList<Card> arrayList) {

            }

            @Override
            public void onDragEnded(float v, float v1) {

            }

            @Override
            public void onDeleted(Card card) {

            }

            @Override
            public void onCardEnter(boolean b) {

            }

            @Override
            public void onCardSelected(String s) {

            }
        }.initView(recyclerView, tmpResult);
    }

    private void initListener() {

    }
}
