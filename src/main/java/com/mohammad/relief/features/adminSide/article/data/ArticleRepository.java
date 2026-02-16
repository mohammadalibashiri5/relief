package com.mohammad.relief.features.adminSide.article.data;

import com.mohammad.relief.features.userManagement.domain.Admin;
import com.mohammad.relief.features.adminSide.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByCategory_Name(String category);

    List<Article> findAllByAdmin(Admin admin);
}
