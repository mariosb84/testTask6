package com.example.userservice.service;

import com.example.userservice.domain.model.UserContacts;

import java.util.List;
import java.util.Optional;

public interface UserContactsService {

    List<UserContacts> findAll();

    Optional<UserContacts> add(UserContacts userContacts);

    boolean update(UserContacts userContacts);

    Optional<UserContacts> findById(long id);

    boolean delete(UserContacts userContacts);

    Optional<UserContacts> findUserContactsByUserEmail(String email);

    Optional<UserContacts> findUserContactsByUserPhone(String phone);

    UserContacts save(UserContacts userContacts);

}
