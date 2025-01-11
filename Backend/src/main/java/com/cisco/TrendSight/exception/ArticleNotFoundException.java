package com.cisco.TrendSight.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException(Long id){
        super("Couldn't find article " + id);
    }
}
