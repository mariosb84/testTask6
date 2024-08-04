package com.example.itemservice.repository;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.domain.model.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "users")
    @NonNull
    List<Item> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "users")
    Page<Item> findAllItemsByStatusAndUsersIn(Pageable pageable, Status status, List<User> users);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "users")
    Page<Item> findAllItemsByStatus(Pageable pageable, Status status);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "users")
    List<Item> findAllItemsByUsersIn(List<User> users);

}
