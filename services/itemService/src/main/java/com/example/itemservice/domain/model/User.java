package com.example.itemservice.domain.model;

import com.example.itemservice.handlers.Operation;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    @NotNull(message = "UserName must be non null", groups = {
            Operation.OnCreate.class
    })
    @Size(min = 5, max = 50, message = "UserName must be more than 5 and less 50")
    @Column(name = "person_login")
    private String username;
    @Size(min = 8, max = 255, message = "Password must be more than 8 and less 255")
    @NotNull(message = "Password must be non null")
    @Column(name = "person_password")
    private String password;
    @NotNull(message = "Email must be non null")
    @Size(min = 5, max = 255, message = "Email must be more than 5 and less 255")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    @Column(name = "person_email")
    private String email;
    @Size(min = 11, max = 12, message = "Phone must be more than 10 and less 13")
    @Column(name = "person_phone")
    private String phone;
    @CollectionTable(
            name = "person_roles",
            joinColumns = { @JoinColumn(name = "person_id") }
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
        return username;
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
