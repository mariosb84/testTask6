package com.example.userservice.domain.model;

import com.example.userservice.handlers.Operation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "person_photo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "person_photo_id")
    @NotNull(message = "person_photo_Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private long id;

    @OneToOne
    @JoinColumn(name = "person_id", unique = true)
    private User user;

    @Lob
    @Column(name = "person_photo")
    private byte[] photo;

}
