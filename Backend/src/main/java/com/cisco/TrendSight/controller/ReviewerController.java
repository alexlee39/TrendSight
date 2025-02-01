package com.cisco.TrendSight.controller;

import com.cisco.TrendSight.dto.ArticleReviewDataDto;
import com.cisco.TrendSight.dto.AuthorArticleDto;
import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.repository.ArticleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@EnableMethodSecurity
@RestController
public class ReviewerController {

    private final ArticleRepository articleRepository;

    public ReviewerController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/article/review")
    public ResponseEntity<List<Article>> getPendingArticles(){
        return ResponseEntity.ok(articleRepository.findAllByArticleStatus(ArticleStatus.PENDING));
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/article/review/{id}")
    public ResponseEntity<Article> getPendingArticle(@PathVariable long id){
        if(articleRepository.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Article article = articleRepository.findById(id).get();
        return new ResponseEntity<>(article, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @PostMapping("/article/review/{id}")
    public ResponseEntity<Article> reviewArticle(@PathVariable long id, @RequestBody ArticleReviewDataDto reviewDataDto){

        Optional<Article> articleOptional = articleRepository.findById(id);
        if(articleOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Article article = articleOptional.get();
        article.setArticleStatus(reviewDataDto.getArticleStatus());
        article.setReviewerName(reviewDataDto.getReviewerName());
        article.setCommentBody(reviewDataDto.getCommentBody());
        article.setReviewedDateInEpochMS(Instant.now().toEpochMilli());
        articleRepository.save(article);
        return new ResponseEntity<>(article,HttpStatus.OK);
    }

}
