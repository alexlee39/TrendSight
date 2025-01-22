package com.cisco.TrendSight.controller;

import java.util.List;

import com.cisco.TrendSight.dto.ArticleDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

@EnableWebSecurity
@RestController
public class ArticleController {
    
    private final ArticleRepository repository;

    public ArticleController(ArticleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/article")
    public List<Article> findAllArticles(){
        return repository.findAll();
    }

    @GetMapping("/article/{id}")
    public Article getArticleById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping("/article")
    public Article postArticle(@RequestBody ArticleDto newArticle) {
        Article article = new Article(newArticle.getTitle(),newArticle.getBody(), newArticle.getAuthor());
        return repository.save(article);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("/article/{id}")
    public Article updateArticle(@RequestBody ArticleDto newArticle, @PathVariable Long id){
        return repository.findById(id)
        .map(article -> {
            article.setTitle(newArticle.getTitle());
            article.setBody(newArticle.getBody());
            article.setAuthor(newArticle.getAuthor());
            article.setUpdatedDate();
            return repository.save(article);
        })
        .orElseThrow(() ->
                new ArticleNotFoundException(id)
        );

    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("/article/{id}")
    public void deleteArticle(@PathVariable Long id){
        if (!repository.existsById(id)) {
            throw new ArticleNotFoundException(id); // Throw exception if not found
        }repository.deleteById(id);
    }
}
