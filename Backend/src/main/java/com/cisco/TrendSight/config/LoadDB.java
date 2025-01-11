package com.cisco.TrendSight.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cisco.TrendSight.model.Article;
import com.cisco.TrendSight.repository.ArticleRepository;

@Configuration
public class LoadDB {
  private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

  @Bean
  CommandLineRunner preloadDB(ArticleRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Article("Article 1", "Article 1 Body", "Bob Ross")));
      log.info("Preloading " + repository.save(new Article("Article 2", "Article 2 Body", "Joe Biden")));
    };
  }
}
