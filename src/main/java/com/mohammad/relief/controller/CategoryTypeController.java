package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.request.CategoryTypeRequestDto;
import com.mohammad.relief.data.dto.response.CategoryTypeResponseDto;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.service.CategoryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryTypeController {
    private final CategoryTypeService categoryTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryTypeResponseDto> addCategoryType(CategoryTypeRequestDto requestDto, Principal principal) throws ReliefApplicationException {
        String username = principal.getName();
        CategoryTypeResponseDto addedCategoryType = categoryTypeService.addCategoryType(requestDto, username);
        return ResponseEntity.ok(addedCategoryType);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CategoryTypeResponseDto>> getAllCategoryTypes(Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        return ResponseEntity.ok(categoryTypeService.categoryTypes(email));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteCategoryType(Principal principal, @RequestParam Integer categoryTypeId) throws ReliefApplicationException {
        String email = principal.getName();
        categoryTypeService.deleteCategoryTypeById(categoryTypeId, email);
        return ResponseEntity.noContent().build();
    }
}
