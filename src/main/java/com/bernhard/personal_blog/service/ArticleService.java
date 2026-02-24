package com.bernhard.personal_blog.service;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found with ID " + id
                ));
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
