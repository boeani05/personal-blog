package com.bernhard.personal_blog.repo;

import com.bernhard.personal_blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsBySlug(String slug);

    List<Article> findAllByOrderByPublishedAtDesc();
}
