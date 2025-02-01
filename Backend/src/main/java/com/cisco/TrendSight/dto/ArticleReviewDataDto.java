package com.cisco.TrendSight.dto;

import com.cisco.TrendSight.model.ArticleStatus;

public class ArticleReviewDataDto {
    private String commentBody;
    private String reviewerName;
    private ArticleStatus articleStatus;

    public ArticleReviewDataDto() {
    }

    public ArticleReviewDataDto(ArticleStatus articleStatus, String reviewerName) {
        this.articleStatus = articleStatus;
        this.reviewerName = reviewerName;
    }

    public ArticleReviewDataDto(ArticleStatus articleStatus, String reviewerName, String commentBody) {
        this.articleStatus = articleStatus;
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

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}
