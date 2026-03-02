package com.bernhard.personal_blog.service;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import com.bernhard.personal_blog.status.ArticleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

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

    public Article createAndPublish(String title, String content) {
        String cleanTitle = title == null ? "" : title.trim();
        String cleanContent = content == null ? "" : content.trim();

        if (cleanTitle.isEmpty()) {
            throw new IllegalArgumentException("missingTitle");
        }

        if (cleanContent.isEmpty()) {
            throw new IllegalArgumentException("missingContent");
        }

        LocalDateTime now = LocalDateTime.now();

        String baseSlug = slugify(cleanTitle);
        String uniqueSlug = makeUniqueSlug(baseSlug);

        Article article = new Article();
        article.setTitle(cleanTitle);
        article.setContent(cleanContent);
        article.setSlug(uniqueSlug);
        article.setCreatedAt(now);
        article.setPublishedAt(now);
        article.setArticleStatus(ArticleStatus.PUBLISHED);

        return articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    private String slugify(String input) {
        String inputToSlug = input.toLowerCase(Locale.ROOT).trim();

        inputToSlug = Normalizer.normalize(inputToSlug, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        inputToSlug = inputToSlug.replaceAll("[^a-z0-9\\s-]", "");
        inputToSlug = inputToSlug.replaceAll("\\s+", "-");
        inputToSlug = inputToSlug.replaceAll("-{2,}", "-");
        inputToSlug = inputToSlug.replaceAll("^-|-$", "");

        return inputToSlug;
    }

    private String makeUniqueSlug(String baseSlug) {
        String root = baseSlug.isBlank() ? "article" : baseSlug;
        String candidate = root;

        int suffix = 2;

        while (articleRepository.existsBySlug(candidate)) {
            candidate = root + "-" + suffix;
            suffix++;
        }

        return candidate;
    }
}
