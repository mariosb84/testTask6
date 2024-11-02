package com.example.userservice.domain.dto.model;

import com.example.userservice.handlers.Operation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "person_contacts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "person_contacts_id")
    @NotNull(message = "person_contacts_Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private long id;

    /*@NotNull(message = "Email must be non null")*/
    /*@Size(min = 5, max = 255, message = "Email must be more than 5 and less 255")*/
    @Email(message = "Email адрес должен быть в формате user@example.com")
    @Column(name = "person_email")
    private String email;

    /*@Size(min = 11, max = 12, message = "Phone must be more than 10 and less 13")*/
    @Column(name = "person_phone")
    private String phone;

}
