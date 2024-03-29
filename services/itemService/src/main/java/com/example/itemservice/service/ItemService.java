package com.example.itemservice.service;

import com.example.itemservice.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> add(Item item);

    boolean update(Item item);

    Optional<Item> findById(int id);

    boolean delete(Item item);


}
