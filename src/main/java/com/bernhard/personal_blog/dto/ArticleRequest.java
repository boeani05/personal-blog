package com.bernhard.personal_blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ArticleRequest {

    @NotBlank(message = "Title must not be empt")
    @Size(min = 3, max = 255, message = "Title has to be between 3 and 255 characters")
    private String title;

    @NotBlank(message = "Content must not be empty")
    @Size(min = 10, message = "Content hast to be at least 10 characters")
    private String content;

    public ArticleRequest() {
    }

    public ArticleRequest(String title, String content) {
        this.title = title;
        this.content = content;
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

