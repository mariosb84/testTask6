package com.example.userservice.domain.dto;

import com.example.userservice.domain.dto.model.UserContacts;
import com.example.userservice.domain.dto.model.UserPhoto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserAdditionDto {

    @Size(min = 5, max = 50, message = "Фамилия пользователя должно содержать от 1 до 50 символов")
    @NotBlank(message = "Фамилия пользователя не может быть пустыми")
    private String userLastName;

    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 1 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String userName;

    private String userMiddleName;

    private LocalDate userBirthDate;

    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    private String password;

    private UserContacts userContacts;

    private UserPhoto userPhoto;

}
