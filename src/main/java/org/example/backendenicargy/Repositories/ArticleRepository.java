package org.example.backendenicargy.Repositories;

import org.example.backendenicargy.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
