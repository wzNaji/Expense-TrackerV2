package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Item;
import com.wzn.expensetrackerv2.repository.ItemRepository;
import com.wzn.expensetrackerv2.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public void createItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("'Item' was not found");
        }
        try {
            itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong. 'Item' was not saved");
        }
    }

    @Override
    @Transactional
    public boolean deleteItem(Long id) {
        try {
            itemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete Item with ID " + id, e);
        }
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item with ID " + id + " was not found."));
    }

    @Override
    @Transactional
    public List<Item> findAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            throw new RuntimeException("No items found");
        }
        return items;
    }

}
