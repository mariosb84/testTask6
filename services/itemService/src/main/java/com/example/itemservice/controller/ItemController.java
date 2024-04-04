package com.example.itemservice.controller;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Role;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.handlers.Operation;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    /*НАЙТИ ВСЕ ЗАЯВКИ*/
    @GetMapping("/findAll")
    @PreAuthorize("hasRole('USER') || hasRole('OPERATOR') || hasRole('ADMIN')")
    public List<Item> findAll() {
        return this.items.findAll();
    }

    /*НАЙТИ ЗАЯВКУ ПО ID*/
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

    /*ОТПРАВИТЬ ЗАЯВКУ*/
    @PostMapping("/send/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Item> sendItem(@PathVariable int id) {           /////////////////////////////////////
        Item item = findById(id).getBody();
        assert item != null;
        item.setStatus(Status.Sent);
        return null;
    }

    /*Просмотреть список заявок с возможностью сортировки по дате создания в оба
    направления (как от самой старой к самой новой, так и наоборот) и пагинацией
    по 5 элементов, фильтрация по статусу*/
    @GetMapping("/sort")
    @PreAuthorize("hasRole('OPERATOR') || hasRole('ADMIN')")
    public List<Item> findSortListItems() {           ///////////////////////////////////////////////
        return null;
    }

    /*Просмотреть список заявок  user-а с возможностью сортировки по дате создания в оба
   направления (как от самой старой к самой новой, так и наоборот) и пагинацией
   по 5 элементов, фильтрация по статусу*/
    @GetMapping("/sort/user")
    @PreAuthorize("hasRole('USER')")
    public List<Item> findSortListItemsByUser() {           ///////////////////////////////////////////////
        return findSortListItems().stream().filter(i -> i.getUsers().contains(Role.ROLE_USER)).toList();
    }

    /*СОЗДАТЬ ЗАЯВКУ*/
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Item> create(@Valid @RequestBody Item item) {
        item.setStatus(Status.Draft);
        var result = this.items.add(item);
        return new ResponseEntity<Item>(
                result.orElse(new Item()),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT
        );
    }

    /*ОБНОВИТЬ ЗАЯВКУ*/
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
     public ResponseEntity<Boolean> update(@RequestBody Item item) {
        if ((this.items.update(item))) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не обновлена!");
    }

    /*УДАЛИТЬ ЗАЯВКУ*/
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

    /*EXEPTION HANDLER*/
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
