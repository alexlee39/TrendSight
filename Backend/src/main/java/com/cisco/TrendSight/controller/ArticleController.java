package com.cisco.TrendSight.controller;

import java.util.List;
import java.util.Optional;

import com.cisco.TrendSight.dto.ArticleDto;
import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.service.MyUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
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
    
    private final ArticleRepository articleRepository;
    private final MyUserDetailService myUserDetailService;

    public ArticleController(ArticleRepository articleRepository, MyUserDetailService myUserDetailService) {
        this.articleRepository = articleRepository;
        this.myUserDetailService = myUserDetailService;
    }

    @GetMapping("/article")
    public List<Article> findAllPublishedArticles(){
        return articleRepository.findAllByArticleStatus(ArticleStatus.PUBLISHED);
    }

    @GetMapping("/article/{id}")
    public Article getArticleById(@PathVariable Long id){
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @GetMapping("/user/article")
    public ResponseEntity<List<Article>> findAllArticlesFromUser(Authentication authentication){
        String email = authentication.getName();
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        if(myUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(articleRepository.findAllByMyUser(myUser));
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping("/article")
    public ResponseEntity<Article> postArticle(@RequestBody ArticleDto newArticle, Authentication authentication) {
        String email = authentication.getName();
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        Article article = new Article(newArticle.getTitle(),newArticle.getBody(), newArticle.getAuthor(),myUser);
        articleRepository.save(article);
        return new ResponseEntity<>(article,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle(@RequestBody ArticleDto newArticle, @PathVariable Long id, Authentication authentication){

        Optional<Article> article = articleRepository.findById(id);
        String email = authentication.getName();
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        if(article.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(!article.get().getMyUser().equals(myUser)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Article updatedArticle = article.get();
        updatedArticle.setTitle(newArticle.getTitle());
        updatedArticle.setBody(newArticle.getBody());
        updatedArticle.setAuthor(newArticle.getAuthor());
        updatedArticle.setUpdatedDate();
        articleRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);

    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("/article/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, Authentication authentication){
        String email = authentication.getName();
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        if (articleRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(!articleRepository.findById(id).get().getMyUser().equals(myUser)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        articleRepository.deleteById(id);
        return new ResponseEntity<>("Article Deleted",HttpStatus.ACCEPTED);
    }
}
