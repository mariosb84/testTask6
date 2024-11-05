package com.example.userservice.controller;

import com.example.userservice.domain.dto.UserAdditionDto;
import com.example.userservice.domain.dto.model.User;
import com.example.userservice.handlers.Operation;
import com.example.userservice.service.UserService;
import com.example.userservice.service.UserServiceData;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/person")
public class UserController {

    private final UserService persons;

    private final UserServiceData personsData;

    private final BCryptPasswordEncoder encoder;

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/")
    public List<User> findAll() {
        return this.persons.findAll();
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        if (person.isPresent()) {
            return new ResponseEntity<User>(
                    person.orElse(new User()),
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<User> create(@Valid @RequestBody UserAdditionDto person) {
        if (person.getUserName() == null || person.getPassword() == null) {
            throw new NullPointerException("Login and password mustn't be empty");
        }
        if (person.getPassword().length() < 3
                || person.getPassword().isEmpty()
                || person.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid password. Password length must be more than 3 characters.");
        }
        person.setPassword(encoder.encode(person.getPassword()));
        var result = this.persons.add(person);
        return new ResponseEntity<User>(
                result.orElse(new User()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Boolean> update(@Valid @RequestBody UserAdditionDto person) {
        if ((this.persons.update(person))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не обновлен!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Boolean> delete(@Valid @PathVariable int id) {
        User user = new User();
        user.setId(id);
        if ((this.persons.delete(user))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не удален!");
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/getCurrentUser")
    public ResponseEntity<User> getCurrentUser(@CurrentSecurityContext(expression = "authentication?.name")
                                               String username) {
        var person = personsData.findUserByUserName(username);
        if (person != null) {
            return new ResponseEntity<>(
                    person,
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @GetMapping("/findByUserName")
    public ResponseEntity<List<User>> findUsersByUsernameContains(@RequestParam(value = "userName") String userName) {
        var personsList = this.persons.findUserByUserNameContains(userName);
        if (!personsList.isEmpty()) {
            return new ResponseEntity<>(
                    personsList,
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Объект не найден!");
    }

}
