package com.bernhard.personal_blog.service;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import com.bernhard.personal_blog.status.ArticleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleRepository articleRepository;

    public Article findById(Long id) {
        logger.debug("Suche Artikel mit ID: {}", id);
        return articleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Article with ID {} could not been found", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Article not found with ID " + id
                    );
                });
    }

    @Transactional
    public void deleteArticleById(Long id) {
        logger.info("Lösche Artikel mit ID: {}", id);
        if (!articleRepository.existsById(id)) {
            logger.warn("Can't delete non-existent article: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with ID " + id);
        }
        articleRepository.deleteById(id);
        logger.info("Article with ID {} deleted successfully", id);
    }

    @Transactional
    public void updateArticle(Article article) {
        logger.info("Updating article with ID: {}", article.getId());
        Article articleToUpdate = articleRepository.findById(article.getId())
                .orElseThrow(() -> {
                    logger.warn("Couldn't find article to update: {}", article.getId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found");
                });

        articleToUpdate.setTitle(article.getTitle());
        articleToUpdate.setContent(article.getContent());
        articleToUpdate.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(articleToUpdate);
        logger.info("Article with ID {} updated successfully", article.getId());
    }

    public Article createAndPublish(String title, String content) {
        logger.info("Creating new article with title: {}", title);
        String cleanTitle = title == null ? "" : title.trim();
        String cleanContent = content == null ? "" : content.trim();

        if (cleanTitle.isEmpty()) {
            logger.warn("Trying to create article with no title");
            throw new IllegalArgumentException("missingTitle");
        }

        if (cleanContent.isEmpty()) {
            logger.warn("Trying to create article with no content");
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

        Article savedArticle = articleRepository.save(article);
        logger.info("Article created with ID: {} Slug: {}", savedArticle.getId(), uniqueSlug);
        return savedArticle;
    }

    public List<Article> findAll() {
        logger.debug("Loading articles...");
        return articleRepository.findAll();
    }

    public List<Article> findAllPublished() {
        logger.debug("Loading all published articles...");
        return articleRepository.findAllByArticleStatusOrderByPublishedAtDesc(ArticleStatus.PUBLISHED);
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
