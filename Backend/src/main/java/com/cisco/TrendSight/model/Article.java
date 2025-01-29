package com.cisco.TrendSight.model;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

// Can't use a record, since Records are immutable 
// And we would want to update/change article content
@Entity //fact check if this is supposed to be correct
public class Article {
    // Should be final
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myUser_id")
    @JsonBackReference
    private MyUser myUser;
    private String title;
    private String body;
    private String author;
    private final Instant createdDate;
    private Instant updatedDate;
    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;
    private long epochMillis;

    public Article(){
        this.createdDate = Instant.now();
        this.epochMillis = this.createdDate.toEpochMilli();
    }

    public Article(String title, String body, String author, MyUser myUser){
        this.title = title;
        this.body = body;
        this.author = author;
        this.createdDate = Instant.now();
        this.epochMillis = Instant.now().toEpochMilli();
        this.myUser = myUser;
        this.articleStatus = ArticleStatus.PENDING;
    }

    public Article(String title, String body, String author, MyUser myUser, ArticleStatus articleStatus){
        this.title = title;
        this.body = body;
        this.author = author;
        this.createdDate = Instant.now();
        this.epochMillis = Instant.now().toEpochMilli();
        this.myUser = myUser;
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
    public Instant getCreatedDate(){
        return this.createdDate;
    }
    public Instant getUpdatedDate(){
        return this.updatedDate;
    }
    public long getEpochMillis() {
        return this.epochMillis;
    }
    public MyUser getMyUser() { return myUser; }

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
        this.updatedDate = Instant.now();
        this.epochMillis = this.updatedDate.toEpochMilli();
    }
    public void setEpochMillis(long epochMillis) {
        this.epochMillis = epochMillis;
    }
    public void setAuthor(MyUser myUser) { this.myUser = myUser; }

    public ArticleStatus getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(ArticleStatus articleStatus) {
        this.articleStatus = articleStatus;
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

}

