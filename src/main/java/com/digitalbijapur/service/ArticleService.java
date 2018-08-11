package com.digitalbijapur.service;

import java.util.List;

import com.digitalbijapur.domain.Article;

public interface ArticleService {

	List<Article> findAllArticles();

	Article findArticleById(Long id);

	Article createArticle(Article article);
}
