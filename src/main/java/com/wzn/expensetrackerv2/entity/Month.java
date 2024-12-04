package com.wzn.expensetrackerv2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "months")
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @OneToMany(mappedBy = "month", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // forward part
    private List<Expense> listOfExpenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        listOfExpenses.add(expense);
        expense.setMonth(this);
    }

    public void removeExpense(Expense expense) {
        listOfExpenses.remove(expense);
    }




}
