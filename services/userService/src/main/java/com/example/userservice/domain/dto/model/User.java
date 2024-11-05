package com.example.userservice.domain.dto.model;

import com.example.userservice.handlers.Operation;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "person_id")
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private long id;

    @NotNull(message = "UserLastName must be non null", groups = {
            Operation.OnCreate.class
    })
    @Size(min = 5, max = 50, message = "UserLastName must be more than 5 and less 50")
    @Column(name = "person_lastname")
    private String userLastName;

    @NotNull(message = "UserName must be non null", groups = {
            Operation.OnCreate.class
    })
    @Size(min = 5, max = 50, message = "UserName must be more than 5 and less 50")
    @Column(name = "person_login")
    private String userName;

    @NotNull(message = "UserMiddleName must be non null", groups = {
            Operation.OnCreate.class
    })
    @Size(min = 5, max = 50, message = "UserMiddleName must be more than 5 and less 50")
    @Column(name = "person_middlename")
    private String userMiddleName;

    @NotNull(message = "userBirthDate must be non null", groups = {
            Operation.OnCreate.class
    })
    @Column(name = "person_userbirthdate")
    private LocalDate userBirthDate;

    @Size(min = 8, max = 255, message = "Password must be more than 8 and less 255")
    @NotNull(message = "Password must be non null")
    @Column(name = "person_password")
    private String password;

    /*- делаем через @EntityGraph в методах репозитория*/
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_contacts_id")
    @NonNull
    private UserContacts userContacts;

    /*- делаем через @EntityGraph в методах репозитория*/
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_photo_id")
    @NonNull
    private UserPhoto userPhoto;

    @CollectionTable(
            name = "person_roles",
            joinColumns = {@JoinColumn(name = "person_id")}
    )
    @Column(name = "role")
    @NonNull
    @Enumerated(EnumType.STRING)
    /*- делаем через @EntityGraph в методах репозитория*/
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> listAuthorities = new ArrayList<>();
        roles.stream().map(Role::name)
                .map(SimpleGrantedAuthority::new).forEach(listAuthorities::add);
        return listAuthorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
