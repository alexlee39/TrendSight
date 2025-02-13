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
    private final MyUserDetailService myUserDetailService;
    private final PDFService pdfService;

    public ArticleController(ArticleRepository articleRepository, MyUserDetailService myUserDetailService, PDFService pdfService) {
        this.articleRepository = articleRepository;
        this.myUserDetailService = myUserDetailService;
        this.pdfService = pdfService;
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

    @PreAuthorize("hasRole('AUTHOR')")
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

    @PreAuthorize("hasRole('AUTHOR')")
    @PostMapping(value ="/article/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void postFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("body") String body) throws IOException {
        if (file != null) {
            // Securely create a temp file
            Path tempFilePath = Files.createTempFile("uploaded-", ".pdf");
            Files.write(tempFilePath, file.getBytes());

            // Extract text from PDF
            String pdf = pdfService.extractTextFromPDF(tempFilePath.toFile());
            System.out.println(pdf);
            System.out.println("\n" + body);
            // Optional: Delete temp file after extraction
//            Files.deleteIfExists(tempFilePath);
        }
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @PostMapping(value = "/article/author" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Article> postArticle(
            @RequestParam("title") String title,
            @RequestParam("body") String body,
            @RequestParam("author") String author,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Authentication authentication) throws IOException {
        String email = authentication.getName();
        String newArticleBody = body;
        MyUser myUser = myUserDetailService.getUserFromEmail(email);
        if(file != null && !file.isEmpty()){
            Path tempFilePath = Files.createTempFile("uploaded-", ".pdf");
            file.transferTo(tempFilePath);
            String pdfBody = pdfService.extractTextFromPDF(tempFilePath.toFile());
            newArticleBody = pdfBody;
            System.out.println(pdfBody);
            Files.deleteIfExists(tempFilePath);
        }
        Article article = new Article(title,newArticleBody,author,myUser);
        articleRepository.save(article);
        return new ResponseEntity<>(article,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('AUTHOR')")
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

    @PreAuthorize("hasRole('AUTHOR')")
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
