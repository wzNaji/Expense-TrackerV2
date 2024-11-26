package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Month;

import java.util.List;


public interface MonthService {

    void createMonth(Month month);
    boolean deleteMonth(Long id);
    Month findMonthById(Long id);
    List<Month> findAllMonths();

}
