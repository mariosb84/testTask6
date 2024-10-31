package com.example.userservice;

import com.example.userservice.domain.dto.model.User;
import com.example.userservice.domain.dto.model.UserContacts;
import com.example.userservice.domain.dto.model.UserPhoto;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
/*import org.springframework.test.annotation.DirtiesContext;*/
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.userservice.domain.dto.model.Role.ROLE_ADMIN;
import static com.example.userservice.domain.dto.model.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {

        userRepository.deleteAll();

        User mockUser = new User();
        UserContacts userContacts = new UserContacts();
        UserPhoto userPhoto = new UserPhoto();
        mockUser.setUserLastName("testUserLastName");
        mockUser.setUserName("testUser");
        mockUser.setUserMiddleName("testUserMiddleName");
        mockUser.setUserBirthDate(LocalDate.now());
        mockUser.setPassword("passwordUser");
        mockUser.setUserContacts(userContacts);
        mockUser.setUserPhoto(userPhoto);
        mockUser.setRoles(List.of(ROLE_USER));

        User mockAdmin = new User();
        UserContacts userContacts2 = new UserContacts();
        UserPhoto userPhoto2 = new UserPhoto();
        mockAdmin.setUserLastName("testAdminLastName");
        mockAdmin.setUserName("testAdmin");
        mockAdmin.setUserMiddleName("testAdminMiddleName");
        mockAdmin.setUserBirthDate(LocalDate.now());
        mockAdmin.setPassword("passwordAdmin");
        mockAdmin.setUserContacts(userContacts2);
        mockAdmin.setUserPhoto(userPhoto2);
        mockAdmin.setRoles(List.of(ROLE_ADMIN));

        userRepository.save(mockUser);
        userRepository.save(mockAdmin);

    }

    @Test
    @WithMockUser (username = "testAdmin", roles = "ADMIN")
    public void testCreateUserValidRequest() throws Exception {
        User user = new User();
        user.setUserName("newUser");
        user.setPassword("validPassword123");
        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("newUser "));
    }

    @Test
    @WithMockUser (username = "testAdmin", roles = "ADMIN")
    public void testCreateUserInvalidPassword() throws Exception {
        User user = new User();
        user.setUserName("newUser");
        user.setPassword("pw");
        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertEquals("Invalid password. Password length must be more than 3 characters.",
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @WithMockUser (username = "testAdmin", roles = "ADMIN")
    public void testCreateUserNullUsername() throws Exception {
        User user = new User();
        user.setUserName(null);
        user.setPassword("validPassword123");

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertEquals("Login and password mustn't be empty",
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testCreateUserUnauthorized() throws Exception {
        User user = new User();
        user.setUserName("newUser ");
        user.setPassword("newPassword123");
        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isForbidden());
    }

}

