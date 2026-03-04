package com.bernhard.personal_blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ArticleUpdateRequest {

    @NotNull(message = "Article-ID must not be empty")
    private Long id;

    @NotBlank(message = "Title must not be empty")
    @Size(min = 3, max = 255, message = "Title has to be between 3 and 255 characters")
    private String title;

    @NotBlank(message = "Content must not be empty")
    @Size(min = 10, message = "Content hast be at least 10 characters long")
    private String content;

    public ArticleUpdateRequest() {
    }

    public ArticleUpdateRequest(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
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
}

