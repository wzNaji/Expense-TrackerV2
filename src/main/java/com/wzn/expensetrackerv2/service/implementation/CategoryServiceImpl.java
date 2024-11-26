package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Category;
import com.wzn.expensetrackerv2.entity.Item;
import com.wzn.expensetrackerv2.repository.CategoryRepository;
import com.wzn.expensetrackerv2.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("'Category' was not found");
        }
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong. 'Category' was not saved");
        }
        return category;
    }

    @Override
    @Transactional
    public boolean deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete Category with ID " + id, e);
        }
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense with ID " + id + " was not found."));
    }

    @Override
    @Transactional
    public List<Category> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found");
        }
        return categories;
    }

}
