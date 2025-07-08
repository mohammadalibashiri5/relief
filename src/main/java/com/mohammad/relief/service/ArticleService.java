package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.ArticleRequestDto;
import com.mohammad.relief.data.dto.response.ArticleResponseDto;
import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.Article;
import com.mohammad.relief.data.entity.CategoryType;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.ArticleMapper;
import com.mohammad.relief.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserService userService;
    private final CategoryTypeService categoryTypeService;

    public ArticleResponseDto addArticle(ArticleRequestDto articleRequestDto,String categoryName, String username) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(username);
        if (admin == null) {
            throw new ReliefApplicationException("Admin not found or you don't have the permission to add an article");
        }
        CategoryType categoryType = categoryTypeService.findByName(categoryName, username);
        Article article = articleMapper.toEntity(articleRequestDto);
        article.setAdmin(admin);
        article.setCategory(categoryType);
        Article savedArticle = articleRepository.save(article);
        return articleMapper.toDto(savedArticle);
    }
    public List<ArticleResponseDto> getArticleByCategory(String category) throws ReliefApplicationException {
        List<Article> article = articleRepository.findArticlesByCategory_Name(category);
        if (article.isEmpty()) {
            throw new ReliefApplicationException("No content found for this category: " + category);
        }
        return article
                .stream()
                .map(articleMapper::toDto)
                .toList();
    }
    public ArticleResponseDto updateArticleById(ArticleRequestDto requestDto, Long id, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        if (admin == null) {
            throw new ReliefApplicationException("Admin not found or you don't have the permission to update an article");
        }
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ReliefApplicationException("Article not found"));

        if (article.getAdmin().getId() != admin.getId()) {
            throw new ReliefApplicationException("You don't have the permission to update this article");
        }
        boolean isUpdated = false;
        if (requestDto.title() != null) {
            article.setTitle(requestDto.title());
            isUpdated = true;
        }
        if (requestDto.content() != null) {
            article.setContent(requestDto.content());
            isUpdated = true;
        }
        if (isUpdated) {
            articleRepository.save(article);
        }
        return articleMapper.toDto(article);

    }
    public void deleteArticleById(Long id, String email) throws ReliefApplicationException {
        Admin admin = userService.findAdminByEmail(email);
        if (admin == null) {
            throw new ReliefApplicationException("Admin not found or you don't have the permission to delete an article");
        }
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ReliefApplicationException("Article not found"));
        if (article.getAdmin().getId() != admin.getId()) {
            throw new ReliefApplicationException("You don't have the permission to delete this article");
        }
        articleRepository.delete(article);
    }

    public List<ArticleResponseDto> getAllTenArticles() {
        List<Article> articles = articleRepository.findAll(Pageable.ofSize(10)).toList();
        return articles
                .stream()
                .map(articleMapper::toDto)
                .toList();
    }
}
