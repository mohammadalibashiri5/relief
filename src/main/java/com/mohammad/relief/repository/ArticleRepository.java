package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByCategory_Name(String category);

    List<Article> findAllByAdmin(Admin admin);
}
