package org.example.backendenicargy.Services;


import org.example.backendenicargy.Dto.ArticleDTO;
import org.example.backendenicargy.Models.Article;
import org.example.backendenicargy.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;



    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id){
        return articleRepository.findById(id);
    }

    public Article createArticle(ArticleDTO articleDTO){
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setBody(articleDTO.getBody());
        return articleRepository.save(article);
    }

    public Article updateArticle(ArticleDTO articleDTO){
        if(articleDTO.getId() == null){
            return null;
        }
        Optional<Article> article = articleRepository.findById(articleDTO.getId());
        if(article.isPresent()){
            Article a = article.get();
            a.setBody(articleDTO.getBody());
            a.setTitle(articleDTO.getTitle());
            return articleRepository.save(a);
        }else{
            return null;
        }
    }

    public void deleteArticleById(Long id){
        articleRepository.deleteById(id);
    }




}
