package com.example.userservice.controller;

import com.example.userservice.domain.dto.model.UserContacts;
import com.example.userservice.handlers.Operation;
import com.example.userservice.service.UserContactsService;
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
@RequestMapping("/personContacts")
public class UserContactsController {

    private final UserContactsService userContactsService;

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/")
    public List<UserContacts> findAll() {
        return this.userContactsService.findAll();
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserContacts> findById(@PathVariable int id) {
        var personContacts = this.userContactsService.findById(id);
        if (personContacts.isPresent()) {
            return new ResponseEntity<UserContacts>(
                    personContacts.orElse(new UserContacts()),
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<UserContacts> create(@Valid @RequestBody UserContacts userContacts) {
        if (userContacts.getEmail() == null || userContacts.getPhone() == null) {
            throw new NullPointerException("Email and phone mustn't be empty");
        }
        var result = this.userContactsService.add(userContacts);
        return new ResponseEntity<UserContacts>(
                result.orElse(new UserContacts()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Boolean> update(@Valid @RequestBody UserContacts userContacts) {
        if ((this.userContactsService.update(userContacts))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не обновлен!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Boolean> delete(@Valid @PathVariable int id) {
        UserContacts userContacts = new UserContacts();
        userContacts.setId(id);
        if ((this.userContactsService.delete(userContacts))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не удален!");
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/findUserContactsByUserEmail")
    public ResponseEntity<UserContacts> findUserContactsByUserEmailContains(@RequestParam(value = "email") String email) {
        var personsContacts = this.userContactsService.findUserContactsByUserEmail(email);
        if (personsContacts.isPresent()) {
            return new ResponseEntity<UserContacts>(
                    personsContacts.orElse(new UserContacts()),
                    HttpStatus.OK
            );

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/findUserContactsByUserPhone")
    public ResponseEntity<UserContacts> findUserContactsByUserPhoneContains(@RequestParam(value = "phone") String phone) {
        var personsContacts = this.userContactsService.findUserContactsByUserPhone(phone);
        if (personsContacts.isPresent()) {
            return new ResponseEntity<UserContacts>(
                    personsContacts.orElse(new UserContacts()),
                    HttpStatus.OK
            );

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

}
