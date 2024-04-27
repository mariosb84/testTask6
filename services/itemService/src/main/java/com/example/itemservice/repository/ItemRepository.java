package com.example.itemservice.repository;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findAll();

    Page<Item> findAllItemsByStatus(Pageable pageable, Status status);

}
