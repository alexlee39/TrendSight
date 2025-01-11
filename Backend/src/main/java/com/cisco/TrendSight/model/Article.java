package com.cisco.TrendSight.model;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// Can't use a record, since Records are immutable 
// And we would want to update/change article content
@Entity //fact check if this is suppose to be correct
public class Article {
    // Should be final
    private @Id 
    @GeneratedValue long id;
    // private List<Integer> authorIds;
    private String title;
    private String body;
    private String authorName;
    private final Instant createdDate;
    private Instant updatedDate;
    private long epochMillis;

    public Article(){
        this.createdDate = Instant.now();
    }

    public Article(String title, String body, String authorName){
        // this.articleId = id;
        this.title = title;
        this.body = body;
        this.authorName = authorName;
        this.createdDate = Instant.now();
        this.epochMillis = this.createdDate.toEpochMilli();
    }

    
    public long getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getBody(){
        return this.body;
    }
    public String getAuthor(){
        return this.authorName;
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
    
    public void setTitle(String title){
        this.title = title;
    }
    public void setBody(String body){
        this.body = body;
    }
    public void setAuthor(String author){
        this.authorName = author;
    }
    public void updateArticle(){
        this.updatedDate = Instant.now();
        this.epochMillis = this.updatedDate.toEpochMilli();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (!(o instanceof Article)){
            return false;
        }
        Article article = (Article) o;
        return Objects.equals(this.id, article.id) && Objects.equals(this.title, article.title)
        && Objects.equals(this.body, article.body);
    }

    @Override 
    public int hashCode(){
        return Objects.hash(this.id, this.title, this.body);
    }

    @Override 
    public String toString(){
        return "Article{id=" + this.id +", title='" + this.title + "'\', body='" + this.body +"'\'}"; 
    }

}

