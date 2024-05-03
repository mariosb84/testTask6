package com.example.itemservice.service;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> add(Item item);

    boolean update(Item item);

    Optional<Item> findById(int id);

    boolean delete(Item item);

    Page<Item> findAllItemsByStatus(Pageable pageable, Status status, List<User> users);


}
