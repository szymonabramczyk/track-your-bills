package com.example.trackyourbills.services.impl;

import com.example.trackyourbills.dto.CategoryDTO;
import com.example.trackyourbills.entities.Category;
import com.example.trackyourbills.entities.User;
import com.example.trackyourbills.exceptions.ResourceNotFoundException;
import com.example.trackyourbills.mappers.CategoryMapper;
import com.example.trackyourbills.repositories.CategoryRepository;
import com.example.trackyourbills.repositories.UserRepository;
import com.example.trackyourbills.services.BudgetTransactionService;
import com.example.trackyourbills.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BudgetTransactionService transactionService;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);

        User user = findUserById(categoryDTO.getUserId());
        category.setUser(user);
        
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
        return CategoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        category.setName(categoryDTO.getName());

        User user = findUserById(categoryDTO.getUserId());
        category.setUser(user);

        Category updatedCategory = categoryRepository.save(category);



        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

        transactionService.setCategoryToNullForTransactions(id);

        categoryRepository.delete(category);
    }

    private User findUserById(Long userID) {
        return userRepository.findById(userID)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID: " + userID));
    }
}
