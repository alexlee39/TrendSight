package com.cisco.TrendSight.dto;

import org.springframework.web.multipart.MultipartFile;

/** This class is used to create objects that all users/readers to see Articles
 *  This should be restricted article data such as Title, Body, Author, Date
 */
public class PublicArticleDto {
    private String title;
    private String body;
    private String author;
    private String filePath;
    private MultipartFile file;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
