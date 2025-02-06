package com.cisco.TrendSight.config;

import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.model.MyUser;
import com.cisco.TrendSight.repository.MyUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.repository.ArticleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDB {
  private static final Logger log = LoggerFactory.getLogger(LoadDB.class);
  private final AuthConfig authConfig;

    public LoadDB(AuthConfig authConfig, PasswordEncoder passwordEncoder) {
        this.authConfig = authConfig;
    }

    @Bean
  CommandLineRunner preloadDB(ArticleRepository repository, MyUserRepository myUserRepository) {

    return args -> {
        PasswordEncoder passwordEncoder = authConfig.passwordEncoder();
        log.info("Preloading User: {}", myUserRepository.save(new MyUser("test@gmail.com", passwordEncoder.encode("test"))));
        log.info("Preloading User: {}", myUserRepository.save(new MyUser("league@gmail.com", passwordEncoder.encode("lol"))));
        log.info("Preloading Reviewer: {}", myUserRepository.save(new MyUser("review@gmail.com", passwordEncoder.encode("review"),"REVIEWER")));
        log.info("Preloading Reviewer: {}", myUserRepository.save(new MyUser("admin@gmail.com", passwordEncoder.encode("admin"),"ADMIN")));


        log.info("Preloading Article: {}", repository.save(new Article("Article 1", "Article 1 Body", "Bob Ross", myUserRepository.findByEmail("test@gmail.com").get(), ArticleStatus.PUBLISHED)));
        log.info("Preloading Article: {}", repository.save(new Article("Article 2", "Article 2 Body", "Joe Biden", myUserRepository.findByEmail("test@gmail.com").get(), ArticleStatus.PUBLISHED)));
        log.info("Preloading Article: {}", repository.save(new Article("I LOVE LEAGUE", "Article 2 Body", "Tyler1", myUserRepository.findByEmail("league@gmail.com").get(), ArticleStatus.PUBLISHED)));
        log.info("Preloading Article: {}", repository.save(new Article("Life as a unemployed person", "It sucks", "Alex", myUserRepository.findByEmail("league@gmail.com").get(), ArticleStatus.PENDING)));
//        for(int i = 0; i < 50; i++){
//            log.info("Preloading Article: {}", repository.save(new Article("Article " + i, "Article " + i + " Body", "SameAuthor" , myUserRepository.findByEmail("test@gmail.com").get(), ArticleStatus.PUBLISHED)));
//        }

    };
  }
}
