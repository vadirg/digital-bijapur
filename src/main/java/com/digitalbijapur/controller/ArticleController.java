package com.digitalbijapur.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.digitalbijapur.domain.Article;
import com.digitalbijapur.exception.ArticleNotFoundException;
import com.digitalbijapur.service.ArticleService;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping
	public ResponseEntity<List<Article>> findAllArticles() {
		List<Article> articles = articleService.findAllArticles();
		return new ResponseEntity<>(articles, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Article> findArticleById(@PathVariable("id") Long id) throws ArticleNotFoundException {

		Article article = articleService.findArticleById(id);
		return new ResponseEntity<>(article, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) {

		Article createdArticle = articleService.createArticle(article);
		
		return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
	}
}
