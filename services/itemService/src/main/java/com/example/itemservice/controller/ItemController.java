package com.example.itemservice.controller;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.handlers.Operation;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService items;

    private final UserService persons;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class.getSimpleName());

    private final ObjectMapper objectMapper;


    @GetMapping("/findAll")
    public List<Item> findAll() {
        return this.items.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable int id) {
        var item = this.items.findById(id);
        if (item.isPresent()) {
            return new ResponseEntity<Item>(
                    item.orElse(new Item()),
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не найдена!");
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Item> create(@Valid @RequestBody Item item) {

        /*if (person.getLogin() == null || person.getPassword() == null) {
            throw new NullPointerException("Login and password mustn't be empty");
        }
        if (person.getPassword().length() < 3
                || person.getPassword().isEmpty()
                || person.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Invalid password. Password length must be more than 3 characters.");
        }
        person.setPassword(encoder.encode(person.getPassword()));*/

        var result = this.items.add(item);
        return new ResponseEntity<Item>(
                result.orElse(new Item()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
     public ResponseEntity<Boolean> update(@RequestBody Item item) {
        if ((this.items.update(item))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не обновлена!");
    }

    @DeleteMapping("/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Boolean> delete(@Valid @PathVariable int id) {
        Item item = new Item();
        item.setId(id);
        if ((this.items.delete(item))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не удалена!");
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

}
