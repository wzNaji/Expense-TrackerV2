package com.wzn.expensetrackerv2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = true)
    private LocalDate date = LocalDate.now();

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

    @ManyToOne
    @JoinColumn(name = "month_id", nullable = true)
    @JsonBackReference
    private Month month;


}
