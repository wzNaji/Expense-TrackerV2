package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    boolean deleteCategory(Long id);
    Category findCategoryById(Long id);
    List<Category> findAllCategories();

}
