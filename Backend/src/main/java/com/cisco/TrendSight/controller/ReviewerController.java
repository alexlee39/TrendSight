package com.cisco.TrendSight.controller;

import com.cisco.TrendSight.dto.ArticleReviewDto;
import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.repository.ArticleRepository;
import com.cisco.TrendSight.repository.MyUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewerController {

    private final ArticleRepository articleRepository;

    public ReviewerController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/review/article")
    public ResponseEntity<List<Article>> getPendingArticles(){
        return ResponseEntity.ok(articleRepository.findAllByArticleStatus(ArticleStatus.PENDING));
    }

    @GetMapping("/review/article/{id}")
    public ResponseEntity<Article> getPendingArticle(@PathVariable long id){
        if(articleRepository.findById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Article article = articleRepository.findById(id).get();
        return new ResponseEntity<>(article, HttpStatus.ACCEPTED);
    }

    @PostMapping("/review/article/{id}")
    public ResponseEntity<Article> reviewArticle(@PathVariable long id, @RequestBody ArticleReviewDto reviewerComments){

        Optional<Article> articleOptional = articleRepository.findById(id);
        if(articleOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Article article = articleOptional.get();
        article.setArticleStatus(reviewerComments.getArticleStatus());
        article.setCommentBody(reviewerComments.getCommentBody());
        articleRepository.save(article);
        return new ResponseEntity<>(article,HttpStatus.OK);
    }

}
