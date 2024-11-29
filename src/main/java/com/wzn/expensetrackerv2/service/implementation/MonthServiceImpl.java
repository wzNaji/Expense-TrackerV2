package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Expense;
import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.repository.MonthRepository;
import com.wzn.expensetrackerv2.service.MonthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class MonthServiceImpl implements MonthService {

    private final MonthRepository monthRepository;

    public MonthServiceImpl(MonthRepository monthRepository) {
        this.monthRepository = monthRepository;
    }


    @Override
    @Transactional
    public Month createMonth(Month month) {
        if (month == null) {
            throw new IllegalArgumentException("Month data must not be null");
        }
        try {
            return monthRepository.save(month);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Failed to save the month due to integrity issues: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the month", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteMonth(Long id) {
        if (!monthRepository.existsById(id)) {
            return false; // No need to throw an exception if the month simply does not exist
        }
        monthRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<Month> findMonthById(Long id) {
        return monthRepository.findById(id); // Return Optional instead of throwing exceptions
    }

    @Override
    @Transactional(readOnly = true)
    public List<Month> findAllMonths() {
        return monthRepository.findAll(); // No exception if no months found, just return the list
    }

    @Override
    @Transactional(readOnly = true)
    public Month findByYearAndMonth(int year, int month) {
        return monthRepository.findByYearAndMonth(year, month);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> getExpensesByMonth(Month month) {
        if (month == null) {
            throw new IllegalArgumentException("Month cannot be null");
        }
        return month.getListOfExpenses();
    }


}
