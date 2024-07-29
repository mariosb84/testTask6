package com.example.itemservice.controller;

import com.example.itemservice.domain.dto.ItemDto;
import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.domain.model.User;
import com.example.itemservice.handlers.Operation;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    /*МЕТОДЫ USER-а:_______________________________________________________________________________*/

    /*Просмотреть список заявок  user-а с возможностью сортировки по дате создания в оба
   направления (как от самой старой к самой новой, так и наоборот) и пагинацией
   по 5 элементов, фильтрация по статусу ("hasRole('USER')")*/
    @GetMapping("/sortItemsByUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<Item>> findSortPageItemsByUser(
            @RequestParam(value = "sortDirection", defaultValue = "0")@Min(0) @Max(1) Integer sortDirection
    ) {
        User currentUser = persons.getCurrentUser();
        return findSortByConditionPageItemsIncludeUsers(0, 5,
                sortDirection == 0 ? "asc" : "desc",
                Status.Draft,
                List.of(persons.findUserByUsername(currentUser.getUsername())));
    }

    /*СОЗДАТЬ ЗАЯВКУ ("hasRole('USER')")*/
    @PostMapping("/createItem")
    @Validated(Operation.OnCreate.class)
    @PreAuthorize("hasRole('USER')")
        public ResponseEntity<Item> create(@Valid @RequestBody ItemDto itemDto) {
            Item item = items.addItemDto(itemDto);
            var result = this.items.add(item);
            return new ResponseEntity<>(
                    result.orElse(new Item()),
                    result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT
            );
    }

    /*МЕТОД : ОТПРАВИТЬ ЗАЯВКУ ОПЕРАТОРУ НА РАССМОТРЕНИЕ ("hasRole('USER')")*/
    @PutMapping("/sendItem/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Item> sendItem(@PathVariable int id) {
        Item item = items.findById(id).get();
        User currentUser = persons.getCurrentUser();
        if (items.itemContains(item, Status.Draft, currentUser.getUsername())) {
                item.setStatus(Status.Sent);
                if (items.update(item)) {
                    return   ResponseEntity.ok().build();
                }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Статус заявки не изменен("
                + "возможно - неверный статус заявки"
                + "(не \"черновик\")/либо заявка создана другим пользователем)!");
    }

    /* МЕТОД РЕДАКТИРОВАНИЯ  ЗАЯВОК В СТАТУСЕ "ЧЕРНОВИК", СОЗДАННЫХ ПОЛЬЗОВАТЕЛЕМ ("hasRole('USER')")*/

    @PutMapping("/editUserItem/{id}")
    @Validated(Operation.OnUpdate.class)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Item> editUserItem(
            @PathVariable int id, @Valid @RequestBody ItemDto itemDto) {
        Item item = items.findById(id).get();
        User currentUser = persons.getCurrentUser();
        if (items.itemContains(item, Status.Draft, currentUser.getUsername())) {
            item = items.editItemDto(itemDto, id).get();
                if (items.update(item)) {
                    return   ResponseEntity.ok().build();
                }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не обновлена("
                + "возможно - неверный статус заявки"
                + "(не \"черновик\")/либо заявка создана другим пользователем)!");
    }

    /*МЕТОДЫ OPERATOR-а:_______________________________________________________________________*/

    /*Просмотреть список всех отправленных на рассмотрение заявок с возможностью
    сортировки по дате создания в оба
    направления (как от самой старой к самой новой, так и наоборот) и пагинацией
    по 5 элементов, фильтрация по статусу. Должна быть фильтрация по имени.
    Просматривать отправленные заявки только конкретного пользователя по его
    имени/части имени (у пользователя, соответственно, должно быть поле name)*/
    @GetMapping("/sortItemsByOperator")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<Page<Item>> findSortPageItemsByOperator(
            @RequestParam(value = "sortDirection", defaultValue = "0")@Min(0) @Max(1) Integer sortDirection,
            @RequestParam(value = "userName", defaultValue = "") String userName
    ) {
        if (userName != null) {
            return findSortByConditionPageItemsIncludeUsers(0, 5,
                    sortDirection == 0 ? "asc" : "desc",
                    Status.Sent,
                    persons.findUserByUsernameContains(userName));
        }
        return findSortByConditionPageItems(0, 5,
                sortDirection == 0 ? "asc" : "desc",
                Status.Sent);
    }

    /*МЕТОД : НАЙТИ ПО ID  ЗАЯВКУ*/
    @GetMapping("/findItem/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<Item> findItem(
            @PathVariable int id) {
        Item item = items.findById(id).get();
        if (items.itemContains(item, Status.Sent, null)) {
            return new ResponseEntity<>(
                    item,
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не найдена("
                + "возможно - неверный статус заявки"
                + "(не \"отправлено\")!");
    }

    /*МЕТОД : ПРИНЯТЬ ЗАЯВКУ*/
    @PutMapping("/acceptItem/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<Item> acceptItem(
            @PathVariable int id) {
        Item item = items.findById(id).get();
        if (items.itemContains(item, Status.Sent, null)) {
            item.setStatus(Status.Accepted);
            if (items.update(item)) {
                return   ResponseEntity.ok().build();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не найдена("
                + "возможно - неверный статус заявки"
                + "(не \"отправлено\")!");
    }

    /*МЕТОД : ОТКЛОНИТЬ ЗАЯВКУ*/
    @PutMapping("/rejectItem/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<Item> rejectItem(
            @PathVariable int id) {
        Item item = items.findById(id).get();
        if (items.itemContains(item, Status.Sent, null)) {
            item.setStatus(Status.Rejected);
            if (items.update(item)) {
                return   ResponseEntity.ok().build();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не найдена("
                + "возможно - неверный статус заявки"
                + "(не \"отправлено\")!");
    }

    /*МЕТОДЫ ADMIN-а:___________________________________________________________________________*/

    /*Просмотреть список заявок в любом статусе с возможностью сортировки по дате создания в оба
   направления (как от самой старой к самой новой, так и наоборот) и пагинацией
   по 5 элементов, фильтрация по статусу*/
    @GetMapping("/sortItemsByAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Item>> findSortPageItemsByAdmin(
            @RequestParam(value = "sortDirection", defaultValue = "0")@Min(0) @Max(1) Integer sortDirection,
            @RequestParam(value = "status", defaultValue = "0")@Min(0) @Max(2) Integer status,
            @RequestParam(value = "userName", defaultValue = "") String userName
    ) {
        Status inputStatus;
        if (status == 0) {
            inputStatus = Status.Sent;
        } else if (status == 1) {
            inputStatus = Status.Accepted;
        } else {
            inputStatus = Status.Rejected;
        }
        if (userName != null) {
            return findSortByConditionPageItemsIncludeUsers(0, 5,
                    sortDirection == 0 ? "asc" : "desc",
                    inputStatus,
                    persons.findUserByUsernameContains(userName));
        }
        return findSortByConditionPageItems(0, 5,
                sortDirection == 0 ? "asc" : "desc",
                inputStatus);
    }

    /*смотреть список пользователей*/
    @GetMapping("/findAllUsersList")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllUsersList() {
        return this.persons.findAll();
    }

    /* назначать пользователям права оператора*/

    @PutMapping("/setRoleOperator/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> setRoleOperator(
            @PathVariable int id) {
        User user = persons.setRoleOperator(id).get();
        if (persons.update(user)) {
            return (ResponseEntity.ok().build());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Роль оператора не назначена,"
                + " пользователь не найден, Объект не обновлен!");
    }

    /*ОБЩИЕ МЕТОДЫ:___________________________________________________________________________________*/

    /*НАЙТИ ВСЕ ЗАЯВКИ*/
    @GetMapping("/findAll")
    public List<Item> findAll() {
        return this.items.findAll();
    }

    /*НАЙТИ ЗАЯВКУ ПО ID*/
    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable int id) {
        var item = this.items.findById(id);
        if (item.isPresent()) {
            return new ResponseEntity<>(
                    item.orElse(new Item()),
                    HttpStatus.OK
            );
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Заявка не найдена!");
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

    /*универсальный метод сортировки, включая USER-s List
    Просмотреть список заявок с возможностью сортировки по дате создания в оба
    направления (как от самой старой к самой новой, так и наоборот) и пагинацией
    по 5 элементов, фильтрация по статусу*/
    private  ResponseEntity<Page<Item>> findSortByConditionPageItemsIncludeUsers(
            @RequestParam(value = "offset", defaultValue = "0")@Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5")@Min(1) @Max(100) Integer limit,
            String direction,
            Status status,
            List<User> users
    ) {
        return  new ResponseEntity<>(items.findAllItemsByStatusAndUsers(
                PageRequest.of(offset, limit,
                        Sort.by((direction.equals("asc") ? Sort.Order.asc("created")
                                        : Sort.Order.desc("created")))),
                status,
                users),
                HttpStatus.OK);
    }

    /*универсальный метод сортировки
   Просмотреть список заявок с возможностью сортировки по дате создания в оба
   направления (как от самой старой к самой новой, так и наоборот) и пагинацией
   по 5 элементов, фильтрация по статусу*/
    private  ResponseEntity<Page<Item>> findSortByConditionPageItems(
            @RequestParam(value = "offset", defaultValue = "0")@Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5")@Min(1) @Max(100) Integer limit,
            String direction,
            Status status
    ) {
        return  new ResponseEntity<>(items.findAllItemsByStatus(
                PageRequest.of(offset, limit,
                        Sort.by((direction.equals("asc") ? Sort.Order.asc("created")
                                : Sort.Order.desc("created")))),
                status
                ),
                HttpStatus.OK);
    }

    /*EXEPTION HANDLER*/
    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
            put("message", e.getMessage());
            put("type", e.getClass());
          }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

}
