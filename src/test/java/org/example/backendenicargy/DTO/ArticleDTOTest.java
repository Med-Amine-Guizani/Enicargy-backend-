package org.example.backendenicargy.DTO;

import org.example.backendenicargy.Dto.ArticleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArticleDTOTest {
    @Test
    void testArticleDTO() {
        // Arrange
        ArticleDTO dto = new ArticleDTO();
        dto.setId(1L);
        dto.setTitle("Test Title");
        dto.setBody("Test Body");

        // Act & Assert
        assertEquals(1L, dto.getId());
        assertEquals("Test Title", dto.getTitle());
        assertEquals("Test Body", dto.getBody());
    }
}
