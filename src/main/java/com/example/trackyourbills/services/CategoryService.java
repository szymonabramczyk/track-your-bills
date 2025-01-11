package com.example.trackyourbills.services;

import com.example.trackyourbills.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategoriesForUser(Long userId);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
