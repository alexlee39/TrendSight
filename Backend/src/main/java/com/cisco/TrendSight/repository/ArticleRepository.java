package com.cisco.TrendSight.repository;

import com.cisco.TrendSight.model.ArticleStatus;
import com.cisco.TrendSight.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cisco.TrendSight.model.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>{
    List<Article> findAllByMyUser(MyUser myUser);
    List<Article> findAllByArticleStatus(ArticleStatus articleStatus);
}
