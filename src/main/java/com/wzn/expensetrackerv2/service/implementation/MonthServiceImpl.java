package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Month;
import com.wzn.expensetrackerv2.repository.MonthRepository;
import com.wzn.expensetrackerv2.service.MonthService;
import org.springframework.stereotype.Service;


@Service
public class MonthServiceImpl implements MonthService {

    private final MonthRepository monthRepository;

    public MonthServiceImpl(MonthRepository monthRepository) {
        this.monthRepository = monthRepository;
    }


    @Override
    public void createMonth(Month month) {
        if (month == null) {
            throw new IllegalArgumentException("'Month' was not found");
        }
            try {
                monthRepository.save(month);
            } catch (Exception e) {
                throw new RuntimeException("Something went wrong. 'Month' was not saved");
            }

    }

    @Override
    public boolean deleteMonth(Long id) {
        try {
            monthRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete Month with ID " + id, e);
        }
    }


    @Override
    public Month findMonthById(Long id) {
        return monthRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Month with ID " + id + " was not found."));
    }

}
