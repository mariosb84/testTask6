package com.example.itemservice;

import com.example.itemservice.controller.ItemController;
import com.example.itemservice.domain.dto.ItemDto;
import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserService userService;

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1);
        item.setName("Test Item");
        item.setItemText("Test Item Text");
        item.setStatus(Status.Draft);
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    void testCreateItem() throws Exception {
        ItemDto itemDto = new ItemDto("Test Item", "Test Item Text");

        Mockito.when(itemService.add(any(Item.class))).thenReturn(Optional.of(item));

        mockMvc.perform(post("/item/createItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Item\",\"itemText\":\"Test Item Text\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    void testFindSortPageItemsByUser() throws Exception {
        Page<Item> page = new PageImpl<>(Collections.singletonList(item));
        Mockito.when(itemService.findAllItemsByStatusAndUsers(any(PageRequest.class), any(Status.class), any()))
                .thenReturn(page);

        mockMvc.perform(get("/item/sortItemsByUser")
                        .param("sortDirection", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Item"));
    }

    @Test
    @WithMockUser(roles = "ROLE_OPERATOR")
    void testFindSortPageItemsByOperator() throws Exception {
        Page<Item> page = new PageImpl<>(Collections.singletonList(item));
        Mockito.when(itemService.findAllItemsByStatusAndUsers(any(PageRequest.class), any(Status.class), any()))
                .thenReturn(page);

        mockMvc.perform(get("/item/sortItemsByOperator")
                        .param("sortDirection", "0")
                        .param("userName", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Item"));
    }

}
