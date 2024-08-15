package com.example.itemservice;

import com.example.itemservice.controller.ItemController;
import com.example.itemservice.domain.dto.ItemDto;
import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.domain.model.User;
import com.example.itemservice.service.ItemService;
import com.example.itemservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ItemControllerTests {

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    public void testCreateItem_Success() throws Exception {
        ItemDto itemDto = new ItemDto("TestName", "TestText");
        Item item = new Item();
        when(itemService.addItemDto(itemDto)).thenReturn(item);
        when(itemService.add(item)).thenReturn(Optional.of(item));

        mockMvc.perform(post("/item/createItem")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(itemDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    public void testCreateItem_Conflict() throws Exception {
        ItemDto itemDto = new ItemDto();
        Item item = new Item();
        when(itemService.addItemDto(itemDto)).thenReturn(item);
        when(itemService.add(item)).thenReturn(Optional.empty());

        mockMvc.perform(post("/item/createItem")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(itemDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    public void testSendItem_Success() throws Exception {
        Item item = new Item();
        item.setStatus(Status.Draft);
        when(itemService.findById(1)).thenReturn(Optional.of(item));
        when(userService.getCurrentUser()).thenReturn(new User());
        when(itemService.itemContains(item, Status.Draft, "currentUser")).thenReturn(true);
        when(itemService.update(item)).thenReturn(true);

        mockMvc.perform(put("/item/sendItem/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    public void testSendItem_NotFound() throws Exception {
        when(itemService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/item/sendItem/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    public void testFindSortPageItemsByUser_Success() throws Exception {
        List<Item> items = new ArrayList<>();
        Page<Item> page = new PageImpl<>(items);
        when(userService.getCurrentUser()).thenReturn(new User());
        when(itemService.findAllItemsByStatusAndUsers(any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/item/sortItemsByUser")
                        .param("sortDirection", "0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ROLE_USER")
    public void testFindSortPageItemsByUser_NotFound() throws Exception {
        // Здесь можно протестировать сценарий, когда ничего не найдено
        when(itemService.findAllItemsByStatusAndUsers(any(), any(), any())).thenReturn(new PageImpl<>(new ArrayList<>()));

        mockMvc.perform(get("/item/sortItemsByUser")
                        .param("sortDirection", "0"))
                .andExpect(status().isOk());
    }

}
