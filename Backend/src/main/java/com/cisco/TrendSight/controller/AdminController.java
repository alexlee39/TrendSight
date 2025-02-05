package com.cisco.TrendSight.controller;

import com.cisco.TrendSight.dto.PublicArticleDto;
import com.cisco.TrendSight.dto.RegisterUserDto;
import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.ArticleRepository;
import com.cisco.TrendSight.repository.MyUserRepository;
import com.cisco.TrendSight.service.MyUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {
    private final MyUserRepository myUserRepository;
    private final ArticleRepository articleRepository;
    private final MyUserDetailService myUserDetailService;

    public AdminController(MyUserRepository myUserRepository, ArticleRepository articleRepository, MyUserDetailService myUserDetailService) {
        this.myUserRepository = myUserRepository;
        this.articleRepository = articleRepository;
        this.myUserDetailService = myUserDetailService;
    }

    @GetMapping("/admin/user")
    public List<MyUser> getAllUsers(){
        return myUserRepository.findAll();
    }

    @PostMapping("admin/user/register")
    public ResponseEntity<MyUser> createUserAccount(@RequestBody RegisterUserDto userDto){
        MyUser user;
        if(myUserRepository.findByEmail(userDto.getEmail()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if(userDto.getRole().isEmpty()){
            user = new MyUser(userDto.getEmail(), userDto.getPassword());
        }
        else{
            user = new MyUser(userDto.getEmail(), userDto.getPassword(), userDto.getRole());
        }
        myUserRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("admin/user/{id}")
    public ResponseEntity<MyUser> updateUserAccount(@PathVariable long userId, @RequestBody RegisterUserDto userDto){
        if(myUserRepository.findById(userId).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MyUser user = myUserRepository.findById(userId).get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        myUserRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("admin/user/{id}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable long userId){
        if(myUserRepository.findById(userId).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        myUserRepository.deleteById(userId);
        return ResponseEntity.ok("Deleted User Successfully");
    }

    @GetMapping("/admin/article")
    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    @GetMapping("/admin/article/published")
    public List<Article> getAllPublishedArticles(){
        return articleRepository.findAllByArticleStatus(ArticleStatus.PUBLISHED);
    }

    @GetMapping("admin/article/pending")
    public List<Article> getAllPendingArticles(){
        return articleRepository.findAllByArticleStatus(ArticleStatus.PENDING);
    }
    @PostMapping("/admin/article")
    public ResponseEntity<Article> postArticle(@RequestBody Article newArticle, Authentication authentication) {
        String email = authentication.getName();
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        Article article = new Article(newArticle.getTitle(),newArticle.getBody(), newArticle.getAuthor(),myUser);
        articleRepository.save(article);
        return new ResponseEntity<>(article,HttpStatus.CREATED);
    }

    @PutMapping("/admin/article/{id}")
    public ResponseEntity<Article> updateArticle(@RequestBody PublicArticleDto newArticle, @PathVariable Long id){

        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Article updatedArticle = article.get();
        updatedArticle.setTitle(newArticle.getTitle());
        updatedArticle.setBody(newArticle.getBody());
        updatedArticle.setAuthor(newArticle.getAuthor());
        updatedArticle.setUpdatedDate();
        articleRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);

    }

    @DeleteMapping("/admin/article/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id){
        if (articleRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        articleRepository.deleteById(id);
        return new ResponseEntity<>("Article Deleted",HttpStatus.ACCEPTED);
    }
}
