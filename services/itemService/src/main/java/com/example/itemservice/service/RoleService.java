package com.example.itemservice.service;

import com.example.itemservice.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> add(Role role);

    boolean update(Role role);

    Optional<Role> findById(int id);

    boolean delete(Role role);

}
