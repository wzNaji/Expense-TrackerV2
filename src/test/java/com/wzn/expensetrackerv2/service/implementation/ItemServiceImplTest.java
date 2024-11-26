package com.wzn.expensetrackerv2.service.implementation;

import com.wzn.expensetrackerv2.entity.Item;
import com.wzn.expensetrackerv2.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createItem_ShouldSaveItem() {
        // Arrange
        Item item = new Item();
        when(itemRepository.save(item)).thenReturn(item);

        // Act
        itemService.createItem(item);

        // Assert
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void createItem_ShouldThrowException_WhenItemIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> itemService.createItem(null));
    }

    @Test
    void deleteItem_ShouldDeleteItem() {
        // Arrange
        Long itemId = 1L;
        doNothing().when(itemRepository).deleteById(itemId);

        // Act
        boolean result = itemService.deleteItem(itemId);

        // Assert
        assertTrue(result);
        verify(itemRepository, times(1)).deleteById(itemId);
    }

    @Test
    void deleteItem_ShouldThrowException_WhenDeletionFails() {
        // Arrange
        Long itemId = 1L;
        doThrow(new RuntimeException("Deletion error")).when(itemRepository).deleteById(itemId);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.deleteItem(itemId));
        assertEquals("Could not delete Item with ID 1", exception.getMessage());
    }

    @Test
    void findItemById_ShouldReturnItem() {
        // Arrange
        Long itemId = 1L;
        Item item = new Item();
        item.setId(itemId);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // Act
        Item result = itemService.findItemById(itemId);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.getId());
        verify(itemRepository, times(1)).findById(itemId);
    }

    @Test
    void findItemById_ShouldThrowException_WhenItemNotFound() {
        // Arrange
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.findItemById(itemId));
        assertEquals("Item with ID 1 was not found.", exception.getMessage());
    }

    @Test
    void findAllItems_ShouldReturnItems() {
        // Arrange
        Item item1 = new Item();
        Item item2 = new Item();
        List<Item> items = Arrays.asList(item1, item2);
        when(itemRepository.findAll()).thenReturn(items);

        // Act
        List<Item> result = itemService.findAllItems();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void findAllItems_ShouldThrowException_WhenNoItemsFound() {
        // Arrange
        when(itemRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.findAllItems());
        assertEquals("No items found", exception.getMessage());
    }
}
