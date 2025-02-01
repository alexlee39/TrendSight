package com.cisco.TrendSight.dto;

import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.model.MyUser;


// All information/details that the author themselves should have about own article

/** This object is just utilized to create objects that should
 *  be sent to Authors from the Articles Object
 *
 */
public class AuthorArticleDto {
    private String title;
    private String body;
    private String author;
    private long dateInEpochMS;
    private ArticleStatus articleStatus;

    private MyUser reviewer;
    private String reviewerName;
    private long reviewDateInEpochMS;
    private String commentBody;

    public AuthorArticleDto(String title, ArticleStatus articleStatus) {
        this.title = title;
        this.articleStatus = articleStatus;
    }
    public AuthorArticleDto(String title, ArticleStatus articleStatus, String commentBody) {
        this.title = title;
        this.articleStatus = articleStatus;
        this.commentBody = commentBody;
    }

    public AuthorArticleDto(String title, String body, String author, long dateInEpochMS, ArticleStatus articleStatus, long reviewDateInEpochMS, String reviewerName, String commentBody) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.dateInEpochMS = dateInEpochMS;
        this.articleStatus = articleStatus;
        this.reviewDateInEpochMS = reviewDateInEpochMS;
        this.reviewerName = reviewerName;
        this.commentBody = commentBody;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }

    public AuthorArticleDto(String title){
        this.title = title;
    }


    public long getReviewDateInEpochMS() {
        return reviewDateInEpochMS;
    }

    public void setReviewDateInEpochMS(long reviewDateInEpochMS) {
        this.reviewDateInEpochMS = reviewDateInEpochMS;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public long getDateInEpochMS() {
        return dateInEpochMS;
    }

    public void setDateInEpochMS(long dateInEpochMS) {
        this.dateInEpochMS = dateInEpochMS;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
