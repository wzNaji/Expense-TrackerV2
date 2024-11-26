package com.wzn.expensetrackerv2.service;

import com.wzn.expensetrackerv2.entity.Item;
import com.wzn.expensetrackerv2.entity.Month;

import java.util.List;

public interface ItemService {

    void createItem(Item item);
    boolean deleteItem(Long id);
    Item findItemById(Long id);
    List<Item> findAllItems();

}
