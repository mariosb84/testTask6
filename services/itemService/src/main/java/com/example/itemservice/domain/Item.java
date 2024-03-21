package com.example.itemservice.domain;

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
    @Size(min = 3, max = 8, message = "Item name must be more than 0 and less 20")
    @Column(name = "item_name")
        private String name;
    @Size(max = 200, message = "Item name must be  less 200")
    @Column(name = "item_itemText")
    private String itemText;
    @NotNull
    @Column(name = "item_created")
    private LocalDateTime created = LocalDateTime.now();
    @NotNull()
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    /*СТАТУС ЗАЯВКИ - • черновик
• отправлено
• принято
• отклонено ПРИНЯТО/ОТКЛОНЕНО ОПЕРАТОРОМ*/
    /*НОМЕР ТЕЛЕФОНА ПОЛЬЗОВАТЕЛЯ*/
    /*ИМЯ ПОЛЬЗОВАТЕЛЯ*/
    /* РОЛИ - • Пользователь
• Оператор
• Администратор*/
    @ManyToMany
    @JoinTable(
                name = "item_persons",
                joinColumns = { @JoinColumn(name = "item_person_id") },
                inverseJoinColumns = { @JoinColumn(name = "person_id") }
        )
        private List<Person> persons = new ArrayList<>();

        public LocalDateTime getTime() {
            return created;
        }

        @Override
        public String toString() {
            return String.format("id: %s, name: %s, created: %s",
                    id, name, FORMATTER.format(created));
        }

}
