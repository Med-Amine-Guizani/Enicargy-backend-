package org.example.backendenicargy.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendenicargy.Dto.ArticleDTO;
import org.example.backendenicargy.Models.Article;
import org.example.backendenicargy.Services.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    private Article article;
    private ArticleDTO articleDTO;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();

        article = new Article(1L, "Test Title", "Test Body", "2023-01-01");
        articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setTitle("Test Title");
        articleDTO.setBody("Test Body");
    }

    @Test
    void getAllArticles_ShouldReturnArticles() throws Exception {
        // Arrange
        List<Article> articles = Arrays.asList(article);
        when(articleService.getAllArticles()).thenReturn(articles);

        // Act & Assert
        mockMvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(article.getId().intValue()))
                .andExpect(jsonPath("$[0].title").value(article.getTitle()))
                .andExpect(jsonPath("$[0].body").value(article.getBody()))
                .andExpect(jsonPath("$.length()").value(articles.size()));
    }

    @Test
    void addArticle_ShouldCreateNewArticle() throws Exception {
        // Arrange
        when(articleService.createArticle(any(ArticleDTO.class))).thenReturn(article);

        // Act & Assert
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.body").value("Test Body"));
    }

    @Test
    void updateArticle_ShouldUpdateExistingArticle() throws Exception {
        // Arrange
        when(articleService.updateArticle(any(ArticleDTO.class))).thenReturn(article);

        // Act & Assert
        mockMvc.perform(patch("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.body").value("Test Body"));
    }

    @Test
    void deleteArticle_ShouldCallServiceDelete() throws Exception {
        // Arrange - no when needed as delete is void

        // Act & Assert
        mockMvc.perform(delete("/api/v1/articles/1"))
                .andExpect(status().isOk());

        // Verify service was called
        verify(articleService, times(1)).deleteArticleById(1L);
    }

    @Test
    void addArticle_ShouldValidateInput() throws Exception {
        // Arrange - create invalid DTO (empty title)
        ArticleDTO invalidDTO = new ArticleDTO();
        invalidDTO.setBody("Body only");

        // Act & Assert
        mockMvc.perform(post("/api/v1/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }
}
