package com.cisco.TrendSight.model;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_user_id")
    @JsonBackReference
    private MyUser authorUser;
    private String title;
    private String body;
    private String author;
    private long dateInEpochMS;
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;

//    @OneToOne(fetch = FetchType.LAZY)
//    private MyUser reviewer;
    private String reviewerName;
    private String commentBody;
    private long reviewedDateInEpochMS;

    public Article(){}
    public Article(String title, String body, String author, MyUser authorUser){
        this.title = title;
        this.body = body;
        this.author = author;
        this.dateInEpochMS = Instant.now().toEpochMilli();
        this.authorUser = authorUser;
        this.articleStatus = ArticleStatus.PENDING;
    }

    public Article(String title, String body, String author, MyUser authorUser, ArticleStatus articleStatus){
        this.title = title;
        this.body = body;
        this.author = author;
        this.dateInEpochMS = Instant.now().toEpochMilli();
        this.authorUser = authorUser;
        this.articleStatus = articleStatus;
    }

    public long getId(){ return this.id;}
    public String getTitle(){ return this.title;}
    public String getBody(){
        return this.body;
    }
    public String getAuthor(){
        return this.author;
    }
    public long getDateInEpochMS() {
        return this.dateInEpochMS;
    }
    public MyUser getAuthorUser() { return authorUser; }

    public void setTitle(String title){
        this.title = title;
    }
    public void setBody(String body){
        this.body = body;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setUpdatedDate(){
        this.dateInEpochMS = Instant.now().toEpochMilli();
    }
    public void setDateInEpochMS(long dateInEpochMS) {
        this.dateInEpochMS = dateInEpochMS;
    }
    public void setAuthor(MyUser authorUser) { this.authorUser = authorUser; }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof Article article)){
            return false;
        }
        return Objects.equals(this.id, article.id) && Objects.equals(this.title, article.title)
        && Objects.equals(this.body, article.body);
    }

    @Override 
    public int hashCode(){
        return Objects.hash(this.id, this.title, this.body);
    }

    @Override 
    public String toString(){
        return "Article{id=" + this.id +", title='" + this.title + "'', body='" + this.body + "''}";
    }

    public long getReviewedDateInEpochMS() {
        return reviewedDateInEpochMS;
    }

    public void setReviewedDateInEpochMS(long reviewedDateInEpochMS) {
        this.reviewedDateInEpochMS = reviewedDateInEpochMS;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
}

