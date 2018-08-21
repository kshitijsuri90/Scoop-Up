package com.example.android.scoopup;

public class News {

    private String news;
    private String category;
    private String url;
    private String author;
    private String publishDate;

    News(String news, String category, String url, String author, String publishDate) {
        this.news = news;
        this.category = category;
        this.url = url;
        this.author = author;
        this.publishDate = publishDate;
    }

    public String getNews() {
        return news;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
