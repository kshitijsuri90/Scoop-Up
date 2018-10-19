package com.example.android.scoopup.model;

public class News {

    private String mTitle;
    private String mCategory;
    private String mDate;
    private String mUrl;
    private String mAuthor;
    private String thumbnailUrl;

    public News(String mTitle, String mCategory, String mDate, String mUrl, String mAuthor, String thumbnailUrl) {
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mDate = mDate;
        this.mUrl = mUrl;
        this.mAuthor = mAuthor;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmCategory() {
        return mCategory;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


}
