package com.wzn.expensetrackerv2.repository;

import com.wzn.expensetrackerv2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
