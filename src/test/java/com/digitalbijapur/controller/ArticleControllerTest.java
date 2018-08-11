package com.digitalbijapur.controller;

import com.digitalbijapur.domain.Article;
import com.digitalbijapur.domain.ArticleStatus;
import com.digitalbijapur.domain.Category;
import com.digitalbijapur.exception.ArticleNotFoundException;
import com.digitalbijapur.service.ArticleService;
import com.digitalbijapur.test.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ArticleController.class)
public class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleServiceMock;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(articleServiceMock);
	}

	@Test
	public void findAllArticles_ArticlesFound_ShouldReturnAllFoundArticles() throws Exception {

		List<Article> articles = Arrays.asList(
												Article.builder().setId(1L).setTitle("Title 1").setContent("Content 1").setStatus(ArticleStatus.APPROVED).build(),
												Article.builder().setId(1L).setTitle("Title 2").setContent("Content 2").build()
											  );
		
		when(articleServiceMock.findAllArticles()).thenReturn(articles);

		mockMvc.perform(get("/api/v1/articles"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", is(notNullValue())))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$.[0].id", is(1)))
				.andExpect(jsonPath("$.[0].title", is("Title 1")))
				.andExpect(jsonPath("$.[0].content", is("Content 1")))
				.andExpect(jsonPath("$.[0].status", is("APPROVED")));
		
		verify(articleServiceMock,times(1)).findAllArticles();
		verifyNoMoreInteractions(articleServiceMock);
		
	}

	@Test
	public void findArticleById_ArticleFound_ShouldReturnFoundArticle() throws Exception {

		Article article = Article.builder().setId(1L).setTitle("Title 1").setContent("Content 1").build();
		
		when(articleServiceMock.findArticleById(1L)).thenReturn(article);
		
		
		mockMvc.perform(get("/api/v1/articles/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", is(notNullValue())))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is("Title 1")))
				.andExpect(jsonPath("$.content", is("Content 1")))
				.andExpect(jsonPath("$.status", is("PENDING")))
				;
		
		verify(articleServiceMock, times(1)).findArticleById(1L);
		verifyNoMoreInteractions(articleServiceMock);
	}
	
	@Test
	public void findArticleById_ArticleNotFoundException_ShouldReturnHttpStatusCode404() throws Exception {
		
		when(articleServiceMock.findArticleById(1L)).thenThrow(new ArticleNotFoundException("Article not found with id : 1"));
		
		mockMvc.perform(get("/api/v1/articles/{id}", 1L))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title", is("Resource not found")))
				.andExpect(jsonPath("$.status", is(404)))
				.andExpect(jsonPath("$.detail", is("Article not found with id : 1")))
				.andExpect(jsonPath("$.timestamp", is(notNullValue())))
				.andExpect(jsonPath("$.developerMessage", is("com.digitalbijapur.exception.ArticleNotFoundException")))
				.andExpect(jsonPath("$.errors").isEmpty())
				;
		
		verify(articleServiceMock, times(1)).findArticleById(1L);
		verifyNoMoreInteractions(articleServiceMock);
	}
	
	@Test
	public void createArticle_TitleAndContentErrors_ShouldReturnValidationErrorsForTitleAndContent() throws Exception {

		Article article = Article.builder().setTitle("").setContent("content").build();

		mockMvc.perform(post("/api/v1/articles").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(article)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title", is("Validation failed")))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.detail", is("Input validation failed")))
				.andExpect(jsonPath("$.timestamp", is(notNullValue())))
				.andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")))
				.andExpect(jsonPath("$.errors").isNotEmpty())
				.andExpect(jsonPath("$.errors.title[*].code", containsInAnyOrder("NotEmpty")))
				.andExpect(jsonPath("$.errors.title[*].message", containsInAnyOrder("Title should not be empty")))
				.andExpect(jsonPath("$.errors.content[*].code", containsInAnyOrder("Length")))
				.andExpect(jsonPath("$.errors.content[*].message", containsInAnyOrder("Content should be greater than or equal to 100 characters")))
				;

		verifyZeroInteractions(articleServiceMock);
	}

	@Test
	public void createArticle_ShouldCreateNewArticleAndReturnCreatedArticleWithId() throws Exception {
		String content = TestUtil.createStringWithLength(100);
		List<String> images = Arrays.asList("http://example.com/images/article.jpg");
		List<Category> categories = Arrays.asList(new Category("Example"));

		Article article = Article.builder().setTitle("title").setContent(content).setStatus(ArticleStatus.PENDING).setImages(images).setCategories(categories).build();
		Article createdArticle = Article.builder().setId(1L).setTitle("title").setContent(content).setStatus(ArticleStatus.PENDING).setImages(images).setCategories(categories).build();

		when(articleServiceMock.createArticle(Mockito.any(Article.class))).thenReturn(createdArticle);

		mockMvc.perform(post("/api/v1/articles").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(article)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is("title")))
				.andExpect(jsonPath("$.content", is(content)))
				.andExpect(jsonPath("$.status", is("PENDING")))
				.andExpect(jsonPath("$.images[*]", containsInAnyOrder("http://example.com/images/article.jpg")))
				.andExpect(jsonPath("$.categories[*].name", containsInAnyOrder("Example")))
				;

		ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
		verify(articleServiceMock, times(1)).createArticle(articleCaptor.capture());
		verifyNoMoreInteractions(articleServiceMock);

		Article articleArgument = articleCaptor.getValue();
		assertNull(articleArgument.getId());
		assertThat(articleArgument.getTitle(), is("title"));
		assertThat(articleArgument.getContent(), is(content));
	}

	@Test
    public void deleteArticleById_ArticleFound_ShouldDeleteArticleAndReturnIt() throws Exception {
        Article deletedArticle = Article.builder().setId(1L).setTitle("Title 1").setContent("Content 1").build();

        when(articleServiceMock.deleteArticleById(1L)).thenReturn(deletedArticle);

        mockMvc.perform(delete("/api/v1/articles/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title 1")))
                .andExpect(jsonPath("$.content", is("Content 1")))
                ;

        verify(articleServiceMock, times(1)).deleteArticleById(1L);
        verifyNoMoreInteractions(articleServiceMock);
    }

    @Test
    public void deleteArticleById_ArticleNotFoundException_ShouldReturnHttpStatusCode404() throws Exception {
        when(articleServiceMock.deleteArticleById(1L)).thenThrow(new ArticleNotFoundException("Article not found with id : 1"));

        mockMvc.perform(delete("/api/v1/articles/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("Resource not found")))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.detail", is("Article not found with id : 1")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.developerMessage", is("com.digitalbijapur.exception.ArticleNotFoundException")))
                .andExpect(jsonPath("$.errors").isEmpty())
                ;

        verify(articleServiceMock, times(1)).deleteArticleById(1L);
        verifyNoMoreInteractions(articleServiceMock);
    }

    @Test
    public void updateArticle_EmptyArticle_ShouldReturnValidationErrorForTitle() throws Exception {
	    Article article = Article.builder().setId(1L).build();

	    mockMvc.perform(put("/api/v1/articles/{id}",1L).contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("Validation failed")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.detail", is("Input validation failed")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors.title[*].code", containsInAnyOrder("NotEmpty")))
                .andExpect(jsonPath("$.errors.title[*].message", containsInAnyOrder("Title should not be empty")))
                ;

        verifyZeroInteractions(articleServiceMock);
    }

    @Test
    public void updateArticle_TitleAndContentErrors_ShouldReturnValidationErrorsForTitleAndContent() throws Exception {
	    String sample = TestUtil.createStringWithLength(90);
	    Article article = Article.builder().setId(1L).setTitle(sample).setContent(sample).build();

	    mockMvc.perform(put("/api/v1/articles/{id}",1L).contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("Validation failed")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.detail", is("Input validation failed")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.developerMessage", is("org.springframework.web.bind.MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors").isNotEmpty())
                .andExpect(jsonPath("$.errors.title[*].code", containsInAnyOrder("Length")))
                .andExpect(jsonPath("$.errors.title[*].message", containsInAnyOrder("Title should not be more than 80 characters")))
                .andExpect(jsonPath("$.errors.content[*].code", containsInAnyOrder("Length")))
                .andExpect(jsonPath("$.errors.content[*].message", containsInAnyOrder("Content should be greater than or equal to 100 characters")))
                ;

        verifyZeroInteractions(articleServiceMock);
    }

    @Test
    public void updateArticle_ArticleNotFoundException_ShouldReturnHttpStatusCode404() throws Exception {

        String title = TestUtil.createStringWithLength(70);
        String content = TestUtil.createStringWithLength(110);
        Article article = Article.builder().setId(1L).setTitle(title).setContent(content).build();

        when(articleServiceMock.updateArticle(Mockito.any(Article.class))).thenThrow(new ArticleNotFoundException("Article not found with id : 1"));

        mockMvc.perform(put("/api/v1/articles/{id}",1L).contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("Resource not found")))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.detail", is("Article not found with id : 1")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.developerMessage", is("com.digitalbijapur.exception.ArticleNotFoundException")))
                .andExpect(jsonPath("$.errors").isEmpty())
                ;

        ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleServiceMock, times(1)).updateArticle(articleCaptor.capture());
        verifyNoMoreInteractions(articleServiceMock);

        Article articleArgument = articleCaptor.getValue();
        assertThat(articleArgument.getId(), is(1L));
        assertThat(articleArgument.getTitle(), is(title));
        assertThat(articleArgument.getContent(), is(content));
    }

    @Test
    public void updateArticle_ArticleFound_ShouldUpdateArticleAndReturnIt() throws Exception {
		String title = TestUtil.createStringWithLength(70);
		String content = TestUtil.createStringWithLength(110);
		Article article = Article.builder().setId(1L).setTitle(title).setContent(content).setStatus(ArticleStatus.PENDING).setImages(new ArrayList<>()).setCategories(new ArrayList<>()).build();
		Article updatedArticle  = Article.builder().setId(1L).setTitle(title).setContent(content).setStatus(ArticleStatus.PENDING).setImages(new ArrayList<>()).setCategories(new ArrayList<>()).build();

		when(articleServiceMock.updateArticle(Mockito.any(Article.class))).thenReturn(updatedArticle);

		mockMvc.perform(put("/api/v1/articles/{id}",1L).contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(article)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", is(notNullValue())))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is(title)))
				.andExpect(jsonPath("$.content", is(content)))
				.andExpect(jsonPath("$.status", is("PENDING")))
				.andExpect(jsonPath("$.images", is(notNullValue())))
				.andExpect(jsonPath("$.categories", is(notNullValue())))
				;

		ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
		verify(articleServiceMock, times(1)).updateArticle(articleCaptor.capture());
		verifyNoMoreInteractions(articleServiceMock);

		Article articleArgument = articleCaptor.getValue();
		assertThat(articleArgument.getId(), is(1L));
		assertThat(articleArgument.getTitle(), is(title));
		assertThat(articleArgument.getContent(), is(content));
    }
}
