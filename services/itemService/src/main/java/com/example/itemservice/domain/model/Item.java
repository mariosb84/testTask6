package com.example.itemservice.domain.model;

import com.example.itemservice.handlers.Operation;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Item {

        private static final DateTimeFormatter FORMATTER =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "item_id")
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
        private int id;
    @Size(min = 5, max = 50, message = "Item name must be more than 5 and less 50")
    @Column(name = "item_name")
        private String name;
    @Size(min = 8, max = 255, message = "Item text must be more than 8 less 255")
    @Column(name = "item_Text")
    private String itemText;
    @NotNull
    @Column(name = "item_created")
    private LocalDateTime created = LocalDateTime.now();
    @Column(name = "item_status")
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany
    @JoinTable(
                name = "item_persons",
                joinColumns = { @JoinColumn(name = "item_person_id") },
                inverseJoinColumns = { @JoinColumn(name = "person_id") }
        )
        private List<User> users = new ArrayList<>();

        public LocalDateTime getTime() {
            return created;
        }

}
