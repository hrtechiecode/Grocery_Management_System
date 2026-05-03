package com.grocery.groceryapp.service;

import com.grocery.groceryapp.model.Item;
import com.grocery.groceryapp.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // ✅ Get all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // ✅ Create item
    public Item createItem(Item item) {

        if (item.getPrice() < 0) {
            throw new RuntimeException("Price cannot be negative");
        }

        if (item.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }

        return itemRepository.save(item);
    }

    // ✅ Get item by ID
    public Item getById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // ✅ Update item
    public Item updateItem(Long id, Item newItem) {

        Item item = getById(id);

        item.setName(newItem.getName());
        item.setCategory(newItem.getCategory());
        item.setPrice(newItem.getPrice());
        item.setQuantity(newItem.getQuantity());
        item.setThreshold(newItem.getThreshold());
        item.setImageUrl(newItem.getImageUrl());

        return itemRepository.save(item);
    }

    // ✅ Delete item
    public void deleteItem(Long id) {
        Item item = getById(id);
        itemRepository.delete(item);
    }

    // 🔥 LOW STOCK FEATURE
    public List<Item> getLowStockItems() {
        return itemRepository.findAll()
                .stream()
                .filter(i -> i.getQuantity() < i.getThreshold())
                .toList();
    }

    // 🔥 STOCK REDUCTION (used in checkout)
    public void reduceStock(Long itemId, int quantity) {

        Item item = getById(itemId);

        if (item.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for " + item.getName());
        }

        item.setQuantity(item.getQuantity() - quantity);

        itemRepository.save(item);
    }
}