package com.example.userservice.service;

import com.example.userservice.domain.model.UserPhoto;

import java.util.List;
import java.util.Optional;

public interface UserPhotoService {

    List<UserPhoto> findAll();

    Optional<UserPhoto> add(UserPhoto userPhoto);

    boolean update(UserPhoto userPhoto);

    Optional<UserPhoto> findById(long id);

    boolean delete(UserPhoto userPhoto);

    Optional<UserPhoto> findUserPhotoByUserPhoto(byte[] photo);

    UserPhoto save(UserPhoto userPhoto);

}
