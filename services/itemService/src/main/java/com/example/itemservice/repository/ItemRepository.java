package com.example.itemservice.repository;

import com.example.itemservice.domain.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findAll();

}
