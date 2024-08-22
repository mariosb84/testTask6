package com.example.itemservice;

import com.example.itemservice.controller.ItemController;
import com.example.itemservice.domain.dto.ItemDto;
import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.domain.model.User;
import com.example.itemservice.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService items;

    @MockBean
    private UserServiceData persons;

    @MockBean
    private TokenBlackListServiceData tokenBlackListServiceData;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SpringLiquibase liquibase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /* Вспомогательный метод для настройки мока*/
    private void setUpJwtMocks(String testJwt) {
        when(jwtService.isTokenValid(eq(testJwt), any(UserDetails.class))).thenReturn(true);
        when(jwtService.extractUserName(eq(testJwt))).thenReturn("testUser");
        when(tokenBlackListServiceData.findByToken(eq(testJwt))).thenReturn(Optional.empty());
    }

    /*МЕТОДЫ USER-а:_______________________________________________________________________________*/

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testCreateIteByUser() throws Exception {
        String testJwt = "testToken";
        /* Используем общий метод для настройки*/
        setUpJwtMocks(testJwt);

        ItemDto itemDto = new ItemDto("Test Item New", "Test Item New Text");
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setItemText(itemDto.getItemText());
        item.setStatus(Status.Draft);

        when(items.addItemDto(any(ItemDto.class))).thenReturn(item);
        when(items.add(any(Item.class))).thenReturn(Optional.of(item));

        mockMvc.perform(post("/item/createItem")
                        .header("Authorization", "Bearer " + testJwt)
                        /* Добавляем CSRF-токен*/
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Item New\",\"itemText\":\"Test Item New Text\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(itemDto.getName()));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testCreateItem_Success() throws Exception {
        String testJwt = "testToken";

        /* Используем общий метод для настройки*/
        setUpJwtMocks(testJwt);

        ItemDto itemDto = new ItemDto("TestName 1", "TestText 1");
        Item item = new Item();
        when(items.addItemDto(any(ItemDto.class))).thenReturn(item);
        when(items.add(any(Item.class))).thenReturn(Optional.of(item));

        mockMvc.perform(post("/item/createItem")
                        .header("Authorization", "Bearer " + testJwt)
                        /* Добавляем CSRF-токен*/
                        .with(csrf())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(itemDto)))
                .andExpect(status().isCreated());
    }

 @Test
 @WithMockUser(username = "testUser", roles = "USER")
    public void testCreateItem_Conflict() throws Exception {
     String testJwt = "testToken";

     /* Используем общий метод для настройки*/
     setUpJwtMocks(testJwt);

     ItemDto itemDto = new ItemDto("TestName 1", "TestText 1");
        Item item = new Item();
        when(items.addItemDto(itemDto)).thenReturn(item);
        when(items.add(item)).thenReturn(Optional.empty());

     mockMvc.perform(post("/item/createItem")
                     .header("Authorization", "Bearer " + testJwt)
                     /* Добавляем CSRF-токен*/
                     .with(csrf())
                     .contentType("application/json")
                     .content(new ObjectMapper().writeValueAsString(itemDto)))
                .andExpect(status().isConflict());
    }


    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testSendItem_Success() throws Exception {
        String testJwt = "testToken";

        /* Используем общий метод для настройки*/
        setUpJwtMocks(testJwt);

        ItemDto itemDto = new ItemDto("Test Item New", "Test Item New Text");
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setItemText(itemDto.getItemText());
        item.setStatus(Status.Draft);

        when(items.findById(1)).thenReturn(Optional.of(item));
        User user = new User();
        user.setUsername("testUser");
        when(persons.getCurrentUser()).thenReturn(user);
        when(items.itemContains(item, Status.Draft, "testUser")).thenReturn(true);
        when(items.update(item)).thenReturn(true);

        mockMvc.perform(put("/item/sendItem/{id}", 1)
                        .header("Authorization", "Bearer " + testJwt)
                        .with(csrf())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(itemDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testSendItem_NotFound() throws Exception {
        String testJwt = "testToken";

        /* Используем общий метод для настройки*/
        setUpJwtMocks(testJwt);

        ItemDto itemDto = new ItemDto("Test Item New", "Test Item New Text");
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setItemText(itemDto.getItemText());
        item.setStatus(Status.Sent);

        when(items.findById(1)).thenReturn(Optional.of(item));
        User user = new User();
        user.setUsername("testUser");
        when(persons.getCurrentUser()).thenReturn(user);
        when(items.itemContains(new Item(), Status.Draft, "testUser")).thenReturn(false);
        when(items.update(item)).thenReturn(false);

        mockMvc.perform(put("/item/sendItem/{id}", 1)
                        .header("Authorization", "Bearer " + testJwt)
                        .with(csrf())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(itemDto)))
                .andExpect(status().isNotFound());
    }

/*"/editUserItem*/

}


  /*  @Test
    @WithMockUser(roles = "OPERATOR")
    void testFindSortPageItemsByOperator() throws Exception {
        Page<Item> page = new PageImpl<>(Collections.singletonList(item));
        Mockito.when(items.findAllItemsByStatusAndUsers(any(PageRequest.class), any(Status.class), any()))
                .thenReturn(page);

        mockMvc.perform(get("/item/sortItemsByOperator")
                        .param("sortDirection", "0")
                        .param("userName", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Item"));
    }*/







   /* @Test
    @WithMockUser(roles = "USER")
    public void testSendItem_NotFound() throws Exception {
        when(items.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/item/sendItem/{id}", 1))
                .andExpect(status().isNotFound());
    }*/



