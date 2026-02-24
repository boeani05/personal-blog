package com.bernhard.personal_blog.repo;

import com.bernhard.personal_blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
