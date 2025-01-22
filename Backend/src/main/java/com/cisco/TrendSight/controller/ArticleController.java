package com.cisco.TrendSight.controller;

import java.util.List;
import java.util.Optional;

import com.cisco.TrendSight.dto.ArticleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Article> postArticle(@RequestBody ArticleDto newArticle) {
        Article article = new Article(newArticle.getTitle(),newArticle.getBody(), newArticle.getAuthor());
        repository.save(article);
        return new ResponseEntity<>(article,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle(@RequestBody ArticleDto newArticle, @PathVariable Long id){

        Optional<Article> article = repository.findById(id);
        if(article.isPresent()){
            Article updatedArticle = article.get();
            updatedArticle.setTitle(newArticle.getTitle());
            updatedArticle.setBody(newArticle.getBody());
            updatedArticle.setAuthor(newArticle.getAuthor());
            updatedArticle.setUpdatedDate();
            return ResponseEntity.ok(updatedArticle);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("/article/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id){
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>("Article Deleted",HttpStatus.ACCEPTED);
    }
}
