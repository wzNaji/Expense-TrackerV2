package com.wzn.expensetrackerv2.repository;

import com.wzn.expensetrackerv2.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
