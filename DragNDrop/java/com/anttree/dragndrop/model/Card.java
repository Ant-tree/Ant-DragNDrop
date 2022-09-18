package com.anttree.dragndrop.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Hyunseok on 2018-02-14.
 * <p>
 * Card
 */

public class Card implements Serializable{

    private int cardId;
    private String name;
    private Drawable image;

    public Card(int cardId, String name, Drawable image) {
        this.cardId = cardId;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
