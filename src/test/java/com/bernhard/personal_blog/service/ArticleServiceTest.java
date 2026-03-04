package com.bernhard.personal_blog.service;

import com.bernhard.personal_blog.entity.Article;
import com.bernhard.personal_blog.repo.ArticleRepository;
import com.bernhard.personal_blog.status.ArticleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article testArticle;

    @BeforeEach
    void setUp() {
        testArticle = new Article();
        testArticle.setId(1L);
        testArticle.setTitle("Test Artikel");
        testArticle.setContent("Test Inhalt");
        testArticle.setSlug("test-artikel");
        testArticle.setArticleStatus(ArticleStatus.PUBLISHED);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));

        // Act
        Article result = articleService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Artikel", result.getTitle());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(articleRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            articleService.findById(999L);
        });
        verify(articleRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateAndPublish_Success() {
        // Arrange
        when(articleRepository.existsBySlug(anyString())).thenReturn(false);
        when(articleRepository.save(any(Article.class))).thenReturn(testArticle);

        // Act
        Article result = articleService.createAndPublish("Test Artikel", "Test Inhalt");

        // Assert
        assertNotNull(result);
        assertEquals(ArticleStatus.PUBLISHED, result.getArticleStatus());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void testCreateAndPublish_EmptyTitle() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            articleService.createAndPublish("", "Test Inhalt");
        });
        assertEquals("missingTitle", exception.getMessage());
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void testCreateAndPublish_EmptyContent() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            articleService.createAndPublish("Test Titel", "");
        });
        assertEquals("missingContent", exception.getMessage());
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void testDeleteArticleById_Success() {
        // Arrange
        when(articleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(articleRepository).deleteById(1L);

        // Act
        articleService.deleteArticleById(1L);

        // Assert
        verify(articleRepository, times(1)).existsById(1L);
        verify(articleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteArticleById_NotFound() {
        // Arrange
        when(articleRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            articleService.deleteArticleById(999L);
        });
        verify(articleRepository, times(1)).existsById(999L);
        verify(articleRepository, never()).deleteById(999L);
    }

    @Test
    void testUpdateArticle_Success() {
        // Arrange
        Article updatedArticle = new Article();
        updatedArticle.setId(1L);
        updatedArticle.setTitle("Aktualisierter Titel");
        updatedArticle.setContent("Aktualisierter Inhalt");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(testArticle));
        when(articleRepository.save(any(Article.class))).thenReturn(testArticle);

        // Act
        articleService.updateArticle(updatedArticle);

        // Assert
        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).save(any(Article.class));
        assertEquals("Aktualisierter Titel", testArticle.getTitle());
        assertEquals("Aktualisierter Inhalt", testArticle.getContent());
        assertNotNull(testArticle.getUpdatedAt());
    }
}

