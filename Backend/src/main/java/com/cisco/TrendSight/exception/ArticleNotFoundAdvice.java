package com.cisco.TrendSight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArticleNotFoundAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String articleNotFoundHandler(ArticleNotFoundException ex){
        return ex.getMessage();
    }
}
