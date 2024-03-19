package com.example.itemservice.service;

import com.example.itemservice.domain.Item;
import com.example.itemservice.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceData implements ItemService {

    private final ItemRepository itemRepository;
    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Optional<Item> add(Item item) {
        Item result = itemRepository.save(item);
        if (Optional.of(result).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    @Override
    public boolean update(Item item) {
        itemRepository.save(item);
        return itemRepository.findById(item.getId()).isPresent();
    }

    @Override
    public Optional<Item> findById(int id) {
        return itemRepository.findById(id);
    }

    @Override
    public boolean delete(Item item) {
        return itemRepository.findById(item.getId()).isEmpty();
    }

}
