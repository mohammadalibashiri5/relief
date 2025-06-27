package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.ArticleRequestDto;
import com.mohammad.relief.data.dto.response.ArticleResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("article")
    public ResponseEntity<ArticleResponseDto> addArticle(@RequestBody @Valid ArticleRequestDto requestDto, Principal principal) throws Exception {
        String username = principal.getName();
        ArticleResponseDto addedArticle = articleService.addArticle(requestDto, username);
        return ResponseEntity.ok(addedArticle);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("articles")
    public ResponseEntity<List<ArticleResponseDto>> getAllArticlesByCategory(@RequestParam String category) throws ReliefApplicationException {
        return new ResponseEntity<>(articleService.getArticleByCategory(category), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PutMapping("article")
    public ResponseEntity<ArticleResponseDto> updateArticle(@RequestBody @Valid ArticleRequestDto requestDto, @RequestParam Long articleId, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        return ResponseEntity.ok(articleService.updateArticleById(requestDto, articleId, username));
    }
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @DeleteMapping("article")
    public ResponseEntity<Void> deleteArticle(Long articleId, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        articleService.deleteArticleById(articleId, username);
        return ResponseEntity.noContent().build();
    }
}
