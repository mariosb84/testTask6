package com.example.userservice.service;

import com.example.userservice.domain.dto.model.UserContacts;
import com.example.userservice.repository.UserContactsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserContactsServiceData implements UserContactsService {

    private final UserContactsRepository userContactsRepository;

    @Override
    public List<UserContacts> findAll() {
        return userContactsRepository.findAll();
    }

    @Override
    public Optional<UserContacts> add(UserContacts userContacts) {
        return Optional.ofNullable(save(userContacts));
    }

    @Override
    public boolean update(UserContacts userContacts) {
        userContactsRepository.save(userContacts);
        return userContactsRepository.findById(userContacts.getId()).isPresent();
    }

    @Override
    public Optional<UserContacts> findById(long id) {
        return userContactsRepository.findById(id);
    }

    @Override
    public boolean delete(UserContacts userContacts) {
        userContactsRepository.delete(userContacts);
        return userContactsRepository.findById(userContacts.getId()).isEmpty();
    }

    @Override
    public Optional<UserContacts> findUserContactsByUserEmail(String email) {
        return userContactsRepository.findUserContactsByEmail(email);
    }

    @Override
    public Optional<UserContacts> findUserContactsByUserPhone(String phone) {
        return userContactsRepository.findUserContactsByPhone(phone);
    }

    @Override
    public UserContacts save(UserContacts userContacts) {
        return userContactsRepository.save(userContacts);
    }

}
