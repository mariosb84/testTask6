package com.example.itemservice.service;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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


    /*Просмотреть список заявок с возможностью сортировки по дате создания в оба
    направления (как от самой старой к самой новой, так и наоборот) и пагинацией
    по 5 элементов, фильтрация по статусу*/
    //надо сделать пагинацию по Item
    @Override
    public List<Item> sort() {
        return  findAll().
                stream().sorted(Comparator.comparing(
                        Item::getCreated)).
                toList();
    }

    @Override
    public List<Item> reverseSort() {
        return findAll().
                stream().sorted(Comparator.comparing(
                Item::getCreated).reversed()).
                toList();
    }

}
