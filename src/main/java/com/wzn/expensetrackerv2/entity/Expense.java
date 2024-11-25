package com.wzn.expensetrackerv2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private double price;

    @JoinColumn(name = "item_id", nullable = true)
    @OneToOne
    private Item item;

    @JoinColumn(name = "category_id", nullable = true)
    @OneToOne
    private Category category;

    @OneToOne
    @JoinColumn(name = "month_id", nullable = false)
    private Month month_id;


}
