package com.example.itemservice.domain;

import com.example.itemservice.handlers.Operation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_status")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Status {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @EqualsAndHashCode.Include
        @Column(name = "status_id")
        @NotNull(message = "Id must be non null", groups = {
                Operation.OnUpdate.class, Operation.OnDelete.class
        })
        private int id;
        @NotNull(message = "Name must be non null", groups = {
                Operation.OnCreate.class
        })
        @Size(min = 3, max = 8, message = "Name must be more than 3 and less 8")
        @Column(name = "status_name")
        private String name;
}
