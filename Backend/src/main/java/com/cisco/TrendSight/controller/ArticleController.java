package com.cisco.TrendSight.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.logging.*;

import com.cisco.TrendSight.dto.AuthorArticleDto;
import com.cisco.TrendSight.dto.PublicArticleDto;
import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.service.MyUserDetailService;
import com.cisco.TrendSight.service.PDFService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cisco.TrendSight.exception.ArticleNotFoundException;
import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.repository.ArticleRepository;
import org.springframework.web.multipart.MultipartFile;

@EnableWebSecurity
@RestController
public class ArticleController {
    
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
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

    @Transactional
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

}
