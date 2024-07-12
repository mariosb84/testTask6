package com.example.itemservice.repository;

import com.example.itemservice.domain.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_entity-graph")
    @NonNull
    User save(@NonNull User user);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_entity-graph")
    @NonNull
    List<User> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_entity-graph")
    List<User> findAllUsersByUsername(String username);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
