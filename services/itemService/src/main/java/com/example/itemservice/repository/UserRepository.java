package com.example.itemservice.repository;

import com.example.itemservice.domain.model.User;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();
    List<User> findUserByUsernameContains(String username);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

}
