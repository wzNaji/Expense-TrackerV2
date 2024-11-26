package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.entity.Item;

import java.util.List;

public interface CategoryService {

    void createCategory(Category category);
    boolean deleteCategory(Long id);
    Category findCategoryById(Long id);
    List<Category> findAllCategories();

}
