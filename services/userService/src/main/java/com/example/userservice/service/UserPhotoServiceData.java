package com.example.userservice.service;

import com.example.userservice.domain.dto.model.UserPhoto;
import com.example.userservice.repository.UserPhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserPhotoServiceData implements UserPhotoService {

    private final UserPhotoRepository userPhotoRepository;

    @Override
    public List<UserPhoto> findAll() {
        return userPhotoRepository.findAll();
    }

    @Override
    public Optional<UserPhoto> add(UserPhoto userPhoto) {
        return Optional.ofNullable(save(userPhoto));
    }

    @Override
    public boolean update(UserPhoto userPhoto) {
        userPhotoRepository.save(userPhoto);
        return userPhotoRepository.findById(userPhoto.getId()).isPresent();
    }

    @Override
    public Optional<UserPhoto> findById(long id) {
        return userPhotoRepository.findById(id);
    }

    @Override
    public boolean delete(UserPhoto userPhoto) {
        userPhotoRepository.delete(userPhoto);
        return userPhotoRepository.findById(userPhoto.getId()).isEmpty();
    }

    @Override
    public Optional<UserPhoto> findUserPhotoByUserPhoto(byte[] photo) {
        return userPhotoRepository.findUserPhotoByPhoto(photo);
    }

    @Override
    public UserPhoto save(UserPhoto userPhoto) {
        return userPhotoRepository.save(userPhoto);
    }

}
