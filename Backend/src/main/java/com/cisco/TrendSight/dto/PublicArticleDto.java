package com.cisco.TrendSight.dto;

/** This class is used to create objects that all users/readers to see Articles
 *  This should be restricted article data such as Title, Body, Author, Date
 */
public class PublicArticleDto {
    private String title;
    private String body;
    private String author;
    private long dateInEpochMS;

    public PublicArticleDto(){}

    public PublicArticleDto(String title, String author, String body, long dateInEpochMS) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.dateInEpochMS = dateInEpochMS;
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

    public long getDateInEpochMS() {
        return dateInEpochMS;
    }

    public void setDateInEpochMS(long dateInEpochMS) {
        this.dateInEpochMS = dateInEpochMS;
    }
}
