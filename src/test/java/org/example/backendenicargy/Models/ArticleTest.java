package org.example.backendenicargy.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleTest {
    @Test
    void testArticleEntity() {
        // Arrange
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Title");
        article.setBody("Test Body Content");

        // Act & Assert
        assertEquals(1L, article.getId());
        assertEquals("Test Title", article.getTitle());
        assertEquals("Test Body Content", article.getBody());
        assertNotNull(article.getDate()); // Should be set by @CreationTimestamp
    }

    @Test
    void testArticleAllArgsConstructor() {
        // Arrange & Act
        Article article = new Article(1L, "Test Title", "Test Body", "2023-01-01");

        // Assert
        assertEquals(1L, article.getId());
        assertEquals("Test Title", article.getTitle());
        assertEquals("Test Body", article.getBody());
        assertEquals("2023-01-01", article.getDate());
    }
}
