package com.cisco.TrendSight.controller;

import java.util.List;
import java.util.Optional;

import com.cisco.TrendSight.dto.AuthorArticleDto;
import com.cisco.TrendSight.dto.PublicArticleDto;
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
    public List<PublicArticleDto> findAllPublishedArticles(){
        return articleRepository.findAllByArticleStatus(ArticleStatus.PUBLISHED).stream()
            .map(article -> new PublicArticleDto(
                    article.getTitle(),
                    article.getAuthor(),
                    article.getBody(),
                    article.getDateInEpochMS()
            ))
            .toList();
    }

    @GetMapping("/article/{id}")
    public PublicArticleDto getArticleById(@PathVariable Long id){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(optionalArticle.isEmpty()){
            throw new ArticleNotFoundException(id);
        }
        Article article = optionalArticle.get();
        return new PublicArticleDto(
                article.getTitle(),
                article.getAuthor(),
                article.getBody(),
                article.getDateInEpochMS()
            );
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @GetMapping("/article/author")
    public ResponseEntity<List<AuthorArticleDto>> findAllArticlesFromUser(Authentication authentication){
        String email = authentication.getName();
        MyUser authorUser = myUserDetailService.getUserFromEmail(email);
        if(authorUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(
                articleRepository.findAllByAuthorUser(authorUser).stream()
                        .map(article -> new AuthorArticleDto(
                                article.getTitle(),
                                article.getAuthor(),
                                article.getBody(),
                                article.getDateInEpochMS(),
                                article.getArticleStatus(),
                                article.getReviewedDateInEpochMS(),
                                article.getReviewerName(),
                                article.getCommentBody()
                        ))
                        .toList()
        );
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping("/article/author")
    public ResponseEntity<Article> postArticle(@RequestBody PublicArticleDto newArticle, Authentication authentication) {
        String email = authentication.getName();
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        Article article = new Article(newArticle.getTitle(),newArticle.getBody(), newArticle.getAuthor(),myUser);
        articleRepository.save(article);
        return new ResponseEntity<>(article,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("/article/author/{id}")
    public ResponseEntity<Article> updateArticle(@RequestBody PublicArticleDto newArticle, @PathVariable Long id, Authentication authentication){

        Optional<Article> article = articleRepository.findById(id);
        String email = authentication.getName();
        MyUser authorUser = myUserDetailService.getUserFromEmail(email);
        if(article.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(!article.get().getAuthorUser().equals(authorUser)){
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
    @DeleteMapping("/article/author/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id, Authentication authentication){
        String email = authentication.getName();
        MyUser authorUser = myUserDetailService.getUserFromEmail(email);
        if (articleRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(!articleRepository.findById(id).get().getAuthorUser().equals(authorUser)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        articleRepository.deleteById(id);
        return new ResponseEntity<>("Article Deleted",HttpStatus.ACCEPTED);
    }
}
