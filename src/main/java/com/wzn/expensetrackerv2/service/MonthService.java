package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;

import java.util.List;
import java.util.Optional;


public interface MonthService {

    Month createMonth(Month month);
    boolean deleteMonth(Long id);
    Optional<Month> findMonthById(Long id);
    List<Month> findAllMonths();
    Month findByYearAndMonth(int year, int month);

    List<Expense> getExpensesByMonth(Month month);

}
