package com.example.itemservice;

import com.example.itemservice.domain.model.Item;
import com.example.itemservice.domain.model.Status;
import com.example.itemservice.domain.model.User;
import com.example.itemservice.repository.ItemRepository;
import com.example.itemservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
/*import org.springframework.test.annotation.DirtiesContext;*/
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.itemservice.domain.model.Role.ROLE_USER;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

        itemRepository.deleteAll();
        userRepository.deleteAll();

        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("password");
        mockUser.setEmail("testUser@mail.ru");
        mockUser.setPhone("89212222222");
        mockUser.setRoles(List.of(ROLE_USER));

        userRepository.save(mockUser);

        Item mockItem1 = new Item();
        mockItem1.setId(1);
        mockItem1.setName("Test Item1");
        mockItem1.setItemText("Test Item Text");
        mockItem1.setStatus(Status.Draft);
        mockItem1.setCreated(LocalDateTime.now().minusDays(2));
        mockItem1.setUsers(List.of(mockUser));

        Item mockItem2 = new Item();
        mockItem2.setId(2);
        mockItem2.setName("Test Item2");
        mockItem2.setItemText("Test Item2 Text");
        mockItem2.setStatus(Status.Draft);
        mockItem2.setCreated(LocalDateTime.now().minusDays(1));
        mockItem2.setUsers(List.of(mockUser));

        Item mockItem3 = new Item();
        mockItem3.setId(3);
        mockItem3.setName("Test Item3");
        mockItem3.setItemText("Test Item3 Text");
        mockItem3.setStatus(Status.Draft);
        mockItem3.setCreated(LocalDateTime.now());
        mockItem3.setUsers(List.of(mockUser));

        itemRepository.saveAll(List.of(mockItem1, mockItem2, mockItem3));
    }

    /*МЕТОДЫ USER-а:_______________________________________________________________________________*/

    @Test
    /*@DirtiesContext*/
    @WithMockUser(username = "testUser", roles = "USER")
    public void testFindSortPageItemsByUserAsc() throws Exception {
        mockMvc.perform(get("/item/sortItemsByUser")
                        .param("sortDirection", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Item1"))
                .andExpect(jsonPath("$.content[1].name").value("Test Item2"))
                .andExpect(jsonPath("$.content[2].name").value("Test Item3"));
    }

    @Test
    /*@DirtiesContext*/
    @WithMockUser(username = "testUser", roles = "USER")
    public void testFindSortPageItemsByUserDesc() throws Exception {
        mockMvc.perform(get("/item/sortItemsByUser")
                        .param("sortDirection", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Item3"))
                .andExpect(jsonPath("$.content[1].name").value("Test Item2"))
                .andExpect(jsonPath("$.content[2].name").value("Test Item1"));
    }



}
