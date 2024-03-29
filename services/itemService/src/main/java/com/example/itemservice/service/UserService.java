package com.example.itemservice.service;



import com.example.itemservice.domain.model.User;
import com.example.itemservice.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

     List<User> findAll();

    Optional<User> add(User user);

   boolean update(User user);

     Optional<User> findById(long id);

    boolean delete(User user);

    boolean updatePatch(UserDto userDto);

}
