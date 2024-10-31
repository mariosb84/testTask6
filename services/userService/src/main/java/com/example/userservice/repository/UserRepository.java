package com.example.userservice.repository;

import com.example.userservice.domain.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    @NonNull
    List<User> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    List<User> findAllUsersByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    List<User> findAllUsersByUsernameContaining(String usernamePart);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "roles")
    Optional<User> findUserByUsername(String username);

    boolean existsByUserName(String username);

    boolean existsByUserLastName(String username);

    boolean existsByUserMiddleName(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

}
