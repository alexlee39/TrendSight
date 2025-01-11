package com.cisco.TrendSight.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.TrendSight.exception.ArticleNotFoundException;
import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.repository.ArticleRepository;


@RestController
public class ArticleController {
    
    private final ArticleRepository repository;

    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/article")
    public List<Article> findAll(){
        return repository.findAll();
    }

    @GetMapping("/article/{id}")
    public Article getArticleById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PostMapping("/article")
    public Article postArticle(@RequestBody Article newArticle) {
        return repository.save(newArticle);
    }
    
    @PutMapping("/article/{id}")
    public Article replaceArticle(@RequestBody Article newArticle, @PathVariable Long id){
        return repository.findById(id)
        .map(article -> {
            article.setTitle(newArticle.getTitle());
            article.setBody(newArticle.getBody());
            article.updateArticle();
            return repository.save(article);
        })
        .orElseGet(() -> {
          return repository.save(newArticle);
        });
        // Optional<Article> oldArticle = repository.findById(id);
        // if(oldArticle == null){
        //     throw new IllegalArgumentException("Can't find the article with the id" + id);
        // }
        // oldArticle = (Article) oldArticle;
        // oldArticle.map(article -> {
        //     article.setTitle(newArticle.getTitle());

        // });
    }

    @DeleteMapping("/article/{id}")
    public void deleteArticle(@PathVariable Long id){
        repository.deleteById(id);
    }
}
