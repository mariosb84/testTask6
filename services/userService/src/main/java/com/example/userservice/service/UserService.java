package com.example.userservice.service;


import com.example.userservice.domain.model.User;
import com.example.userservice.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> add(User user);

    boolean update(User user);

    Optional<User> findById(long id);

    boolean delete(User user);

    boolean updatePatch(UserDto userDto);

    User findUserByUsername(String username);

    User save(User user);

    List<User> findUserByUsernameContains(String username);

    Optional<User> setRoleAdmin(long id);

    User getCurrentUser();

}
