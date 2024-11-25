package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Month;

public interface MonthService {

    void createMonth(Month month);
    boolean deleteMonth(Long id);
    Month findMonthById(Long id);

}
