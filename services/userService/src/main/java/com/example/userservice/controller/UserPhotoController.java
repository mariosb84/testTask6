package com.example.userservice.controller;

import com.example.userservice.domain.dto.model.UserPhoto;
import com.example.userservice.handlers.Operation;
import com.example.userservice.service.UserPhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/personPhoto")
public class UserPhotoController {

    private final UserPhotoService userPhotoService;

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/")
    public List<UserPhoto> findAll() {
        return this.userPhotoService.findAll();
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserPhoto> findById(@PathVariable int id) {
        var personPhoto = this.userPhotoService.findById(id);
        if (personPhoto.isPresent()) {
            return new ResponseEntity<UserPhoto>(
                    personPhoto.orElse(new UserPhoto()),
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<UserPhoto> create(@Valid @RequestBody UserPhoto userPhoto) {
        if (userPhoto.getPhoto() == null) {
            throw new NullPointerException("Photo mustn't be empty");
        }
        var result = this.userPhotoService.add(userPhoto);
        return new ResponseEntity<UserPhoto>(
                result.orElse(new UserPhoto()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Boolean> update(@Valid @RequestBody UserPhoto userPhoto) {
        if ((this.userPhotoService.update(userPhoto))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не обновлен!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Boolean> delete(@Valid @PathVariable int id) {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setId(id);
        if ((this.userPhotoService.delete(userPhoto))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не удален!");
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/findUserPhotoByPhoto")
    public ResponseEntity<UserPhoto> findUserPhotoByPhotoContains(@RequestParam(value = "photo") byte[] photo) {
        var personsPhoto = this.userPhotoService.findUserPhotoByUserPhoto(photo);
        if (personsPhoto.isPresent()) {
            return new ResponseEntity<UserPhoto>(
                    personsPhoto.orElse(new UserPhoto()),
                    HttpStatus.OK
            );

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

}
