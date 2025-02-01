package com.cisco.TrendSight.dto;

public class ArticleDto {
    private String title;
    private String body;
    private String author;

    public ArticleDto(){}

    public ArticleDto(String title, String body, String author) {
        this.body = body;
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
