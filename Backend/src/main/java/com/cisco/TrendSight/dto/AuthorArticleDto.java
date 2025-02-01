package com.cisco.TrendSight.dto;

import com.cisco.TrendSight.model.ArticleStatus;

public class ArticleReviewDto {
    private ArticleStatus articleStatus;
    private String commentBody;

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

    public ArticleReviewDto(){}

    public ArticleReviewDto(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }
    public ArticleReviewDto(ArticleStatus articleStatus, String commentBody) {
        this.articleStatus = articleStatus;
        this.commentBody = commentBody;
    }
}
