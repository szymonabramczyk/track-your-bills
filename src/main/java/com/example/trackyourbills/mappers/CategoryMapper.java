package com.example.trackyourbills.mappers;

import com.example.trackyourbills.dto.CategoryDTO;
import com.example.trackyourbills.entities.Category;

import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        // dto.setTransactionIds(category.getTransactionList()
        //         .stream()
        //         .map(transaction -> transaction.getId())
        //         .collect(Collectors.toList()));
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category; // Bez ustawiania transakcji, zarządzane przez osobne serwisy
    }
}
