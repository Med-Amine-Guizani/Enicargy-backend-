package org.example.backendenicargy.Controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.backendenicargy.Dto.ArticleDTO;
import org.example.backendenicargy.Models.Article;
import org.example.backendenicargy.Services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200" , allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public List<Article> getAllArticles(){
        System.out.println(articleService.getAllArticles());
        return articleService.getAllArticles();
    }

    @PostMapping("")
    public Article addArticle(@Valid @RequestBody ArticleDTO articleDto){
        Article article = articleService.createArticle(articleDto);
        return article ;
    }

    @PatchMapping("")
    public Article updateArticle(@Valid @RequestBody ArticleDTO articleDto){
        Article article = articleService.updateArticle(articleDto);
        return article ;
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@NotNull @PathVariable Long id){
        articleService.deleteArticleById(id);
    }




}
