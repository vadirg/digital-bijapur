package com.digitalbijapur.service;

import java.util.List;

import com.digitalbijapur.domain.Article;
import com.digitalbijapur.exception.ArticleNotFoundException;

public interface ArticleService {

	List<Article> findAllArticles();

	Article findArticleById(Long id) throws ArticleNotFoundException;

	Article createArticle(Article article);

	Article updateArticle(Article article) throws ArticleNotFoundException;

	Article deleteArticleById(Long id) throws ArticleNotFoundException;
}
