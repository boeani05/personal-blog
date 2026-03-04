package com.bernhard.personal_blog.repo;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.status.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsBySlug(String slug);

    List<Article> findAllByOrderByPublishedAtDesc();

    Page<Article> findAllByArticleStatusOrderByPublishedAtDesc(ArticleStatus status, Pageable pageable);

    List<Article> findAllByArticleStatusOrderByPublishedAtDesc(ArticleStatus status);
}
