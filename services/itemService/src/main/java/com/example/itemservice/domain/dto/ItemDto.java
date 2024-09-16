package com.example.itemservice.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ItemDto {

    @Size(min = 5, max = 50, message = "Название заявки должно содержать от 5 до 50 символов")
    @NotBlank(message = "Название заявки не может быть пустыми")
    private String name;

    @Size(min = 8, max = 255, message = "Длина текста должна быть от 8 до 255 символов")
    private String itemText;

}
