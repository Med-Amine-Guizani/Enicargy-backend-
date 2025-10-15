package org.example.backendenicargy.Services;

import org.example.backendenicargy.Dto.ArticleDTO;
import org.example.backendenicargy.Models.Article;
import org.example.backendenicargy.Repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article article;
    private ArticleDTO articleDTO;

    @BeforeEach
    void setUp() {
        article = new Article(1L, "Test Title", "Test Body", "2023-01-01");
        articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setTitle("Test Title");
        articleDTO.setBody("Test Body");
    }

    @Test
    void getAllArticles_ShouldReturnAllArticles() {
        // Arrange
        when(articleRepository.findAll()).thenReturn(Arrays.asList(article));

        // Act
        List<Article> articles = articleService.getAllArticles();

        // Assert
        assertEquals(1, articles.size());
        assertEquals(article, articles.get(0));
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void getArticleById_ShouldReturnArticle_WhenExists() {
        // Arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        // Act
        Optional<Article> foundArticle = articleService.getArticleById(1L);

        // Assert
        assertTrue(foundArticle.isPresent());
        assertEquals(article, foundArticle.get());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void getArticleById_ShouldReturnEmpty_WhenNotExists() {
        // Arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Article> foundArticle = articleService.getArticleById(1L);

        // Assert
        assertFalse(foundArticle.isPresent());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void createArticle_ShouldSaveNewArticle() {
        // Arrange
        ArticleDTO newArticleDTO = new ArticleDTO();
        newArticleDTO.setTitle("New Title");
        newArticleDTO.setBody("New Body");

        Article newArticle = new Article();
        newArticle.setTitle("New Title");
        newArticle.setBody("New Body");

        when(articleRepository.save(any(Article.class))).thenReturn(article);

        // Act
        Article createdArticle = articleService.createArticle(newArticleDTO);

        // Assert
        assertNotNull(createdArticle);
        assertEquals(article, createdArticle);
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void updateArticle_ShouldUpdateExistingArticle() {
        // Arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        // Act
        Article updatedArticle = articleService.updateArticle(articleDTO);

        // Assert
        assertNotNull(updatedArticle);
        assertEquals(article, updatedArticle);
        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void updateArticle_ShouldReturnNull_WhenIdNotProvided() {
        // Arrange
        ArticleDTO noIdDTO = new ArticleDTO();
        noIdDTO.setTitle("No ID");
        noIdDTO.setBody("No ID Body");

        // Act
        Article result = articleService.updateArticle(noIdDTO);

        // Assert
        assertNull(result);
        verify(articleRepository, never()).findById(anyLong());
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void updateArticle_ShouldReturnNull_WhenArticleNotFound() {
        // Arrange
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Article result = articleService.updateArticle(articleDTO);

        // Assert
        assertNull(result);
        verify(articleRepository, times(1)).findById(1L);
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void deleteArticleById_ShouldCallRepositoryDelete() {
        // Arrange - no when needed as deleteById is void

        // Act
        articleService.deleteArticleById(1L);

        // Assert
        verify(articleRepository, times(1)).deleteById(1L);
    }
}
