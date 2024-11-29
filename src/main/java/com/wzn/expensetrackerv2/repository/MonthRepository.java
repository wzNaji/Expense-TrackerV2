package com.wzn.expensetrackerv2.repository;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthRepository extends JpaRepository<Month, Long> {
    Month findByYearAndMonth(int year, int month);
}
