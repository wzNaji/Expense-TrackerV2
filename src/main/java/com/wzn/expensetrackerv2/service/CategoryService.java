package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category createCategory(Category category);
    boolean deleteCategory(Long id);
    Optional<Category> findCategoryById(Long id);
    List<Category> findAllCategories();

}
