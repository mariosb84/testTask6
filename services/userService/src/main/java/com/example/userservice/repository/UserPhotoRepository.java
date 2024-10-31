package com.example.userservice.repository;

import com.example.userservice.domain.dto.model.UserPhoto;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {

    @NonNull
    List<UserPhoto> findAll();

    Optional<UserPhoto> findUserPhotoByPhoto(byte[] photo);

}
