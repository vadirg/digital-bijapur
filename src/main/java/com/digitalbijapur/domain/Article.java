package com.digitalbijapur.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Article {

    private Long id;

    @NotEmpty
    @Length(max = 80)
    private String title;
    @Length(min = 100)
    private String content;
    private ArticleStatus status;
    private List<String> images;
    private List<Category> categories;

    private Article() {

    }

    private Article(Long id, String title, String content, ArticleStatus status, List<String> images,
                    List<Category> categories) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.images = images;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static ArticleBuilder builder() {
        return new ArticleBuilder();
    }

    public static class ArticleBuilder {

        private Long id;
        private String title;
        private String content;
        private ArticleStatus status = ArticleStatus.PENDING;
        private List<String> images;
        private List<Category> categories;

        private ArticleBuilder() {

        }

        public ArticleBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ArticleBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public ArticleBuilder setStatus(ArticleStatus status) {
            this.status = status;
            return this;
        }

        public ArticleBuilder setImages(List<String> images) {
            this.images = images;
            return this;
        }

        public ArticleBuilder setCategories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Article build() {
            return new Article(id, title, content, status, images, categories);
        }
    }
}
