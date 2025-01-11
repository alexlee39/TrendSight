package com.cisco.TrendSight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.TrendSight.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
    
}
