package com.cisco.TrendSight.config;

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
  private final PasswordEncoder passwordEncoder;

    public LoadDB(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
  CommandLineRunner preloadDB(ArticleRepository repository, MyUserRepository myUserRepository) {

    return args -> {
        log.info("Preloading User: {}", myUserRepository.save(new MyUser("test@gmail.com", passwordEncoder.encode("test"))));
        log.info("Preloading User: {}", myUserRepository.save(new MyUser("league@gmail.com", passwordEncoder.encode("oflegends"))));

//      MyUser myUser =
        log.info("Preloading Article: {}", repository.save(new Article("Article 1", "Article 1 Body", "Bob Ross", myUserRepository.findByEmail("test@gmail.com").get())));
        log.info("Preloading Article: {}", repository.save(new Article("Article 2", "Article 2 Body", "Joe Biden", myUserRepository.findByEmail("test@gmail.com").get())));
        log.info("Preloading Article: {}", repository.save(new Article("I LOVE LEAGUE", "Article 2 Body", "Tyler1", myUserRepository.findByEmail("league@gmail.com").get())));

    };
  }
}
