package com.zzt.blog.service;

import com.zzt.blog.dto.ArticleDTO;
import com.zzt.blog.entity.Article;

import java.util.List;

/**
 * @author 227
 */


public interface ArticleService {
    List<Article> listArticles();
    Article getArticleById(Long id);
    void saveArticle(ArticleDTO article);
    void updateArticle(Article article);
    void deleteArticle(Long id);
    List<Article> getTopArticles();
}