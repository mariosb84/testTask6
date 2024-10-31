package com.example.userservice.repository;

import com.example.userservice.domain.model.UserContacts;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserContactsRepository extends JpaRepository<UserContacts, Long> {

    @NonNull
    List<UserContacts> findAll();

    Optional<UserContacts> findUserContactsByEmail(String email);

    Optional<UserContacts> findUserContactsByPhone(String phone);

}
