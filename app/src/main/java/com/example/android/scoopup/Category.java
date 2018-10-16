package com.example.android.scoopup;

import android.widget.TextView;

import org.w3c.dom.Text;

public class Category {

    private String title;
    private int imageId;

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public Category(String title, int imageId) {

        this.title = title;
        this.imageId = imageId;
    }
}
