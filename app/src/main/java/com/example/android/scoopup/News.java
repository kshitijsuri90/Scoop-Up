package com.example.android.scoopup;

public class News {

    private String news;
    private String author;
    private String url;

    public String getNews() {
        return news;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public News(String news, String author, String url) {
        this.news = news;
        this.author = author;
        this.url = url;
    }
}
